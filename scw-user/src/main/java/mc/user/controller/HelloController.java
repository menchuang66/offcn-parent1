package mc.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import mc.user.pojo.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "第一个swagger测试")
@RestController
public class HelloController {
    @ApiOperation("获取用户名")
    @ApiImplicitParams(value = {@ApiImplicitParam(name = "name",value = "名字",required = true),@ApiImplicitParam(name = "age",value = "年龄")})
    @GetMapping("/hello")
    public String getName(String name){
        return name;
    }
    @ApiOperation("保存用户")
    @ApiImplicitParams(value={
            @ApiImplicitParam(name="name",value="姓名",required = true),@ApiImplicitParam(name="email",value="电子邮件")
    })
    @PostMapping("/user")
    public User save(String name, String email){
        User user  = new User();
        user.setName(name);
        user.setEmail(email);
        return user;
    }
}
