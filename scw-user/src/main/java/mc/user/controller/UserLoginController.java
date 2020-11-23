package mc.user.controller;

import com.alibaba.druid.util.StringUtils;
import enums.AppResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import mc.user.comp.SmsTemplate;
import mc.user.pojo.TMember;
import mc.user.pojo.User;
import mc.user.pojo.UserRegistVo;
import mc.user.pojo.UserRespVo;
import mc.user.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
@RestController
@RequestMapping("/user")
@Api(tags = "用户登录/注册模块（包括忘记密码等）")
public class UserLoginController {
    @Autowired
    private SmsTemplate smsTemplate;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private UserService userService;

    @ApiOperation("获取注册的验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "phoneNo", value = "手机号", required = true)
    })//@ApiImplicitParams：描述所有参数；@ApiImplicitParam描述某个参数
    @PostMapping("/sendCode")
    public AppResponse<Object> sendCode(String phoneNo){
        String substring = UUID.randomUUID().toString().substring(0, 4);
        stringRedisTemplate.opsForValue().set(phoneNo,substring,10000, TimeUnit.MINUTES);
        //3、短信发送构造参数
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("mobile", phoneNo);
        querys.put("param", "code:" + substring);
        querys.put("tpl_id", "TP1711063");//短信模板
        //4、发送短信
        String sendCode = smsTemplate.sendCode(querys);
        if (sendCode.equals("") || sendCode.equals("fail")) {
            //短信失败
            return AppResponse.fail("短信发送失败");
        }
        return AppResponse.ok(sendCode);
    }
    @ApiOperation("用户注册")
    @PostMapping("/regist")
    public AppResponse<Object> regist(UserRegistVo registVo){
        String code = stringRedisTemplate.opsForValue().get(registVo.getLoginacct());
        if (!StringUtils.isEmpty(code)){
            boolean b = code.equalsIgnoreCase(registVo.getCode());
            if (b){
                try {
                    TMember tMember = new TMember();
                    BeanUtils.copyProperties(registVo,tMember);
                    userService.registerUser(tMember);
                    stringRedisTemplate.delete(registVo.getLoginacct());
                    return AppResponse.ok("success");
                } catch (BeansException e) {
                    return AppResponse.fail(e.getMessage());
                }
            }else {
                return AppResponse.fail("yanzhengmacuowu!!");
            }
        }else {
            return AppResponse.fail("yanzhengmaguoqi");
        }
    }
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true),
            @ApiImplicitParam(name = "password", value = "密码", required = true)
    })//@ApiImplicitParams：描述所有参数；@ApiImplicitParam描述某个参数
    @PostMapping("/login")
    public AppResponse<UserRespVo> login(String username, String password) {
        //1、尝试登录
        TMember member = userService.login(username, password);
        if (member == null) {
            //登录失败
            AppResponse<UserRespVo> fail = AppResponse.fail(null);
            fail.setMsg("用户名密码错误");
            return fail;
        }
        //2、登录成功;生成令牌
        String token = UUID.randomUUID().toString().replace("-", "");
        UserRespVo vo = new UserRespVo();
        BeanUtils.copyProperties(member, vo);
        vo.setAccessToken(token);

        //3、经常根据令牌查询用户的id信息
        stringRedisTemplate.opsForValue().set(token,member.getId()+"",2,TimeUnit.HOURS);
        return AppResponse.ok(vo);
    }

}
