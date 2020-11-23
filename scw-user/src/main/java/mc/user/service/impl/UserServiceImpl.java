package mc.user.service.impl;

import mc.user.enums.UserExceptionEnum;
import mc.user.exception.UserException;
import mc.user.mapper.TMemberMapper;
import mc.user.pojo.TMember;
import mc.user.pojo.TMemberExample;
import mc.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private TMemberMapper memberMapper;
    @Override
    public void registerUser(TMember member) {
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria().andLoginacctEqualTo(member.getLoginacct());
        long l = memberMapper.countByExample(example);
        if (l>0){
            throw new UserException(UserExceptionEnum.LOGINACCT_EXIST);
        }
        BCryptPasswordEncoder encoder =new BCryptPasswordEncoder();
        String encode = encoder.encode(member.getUserpswd());
        member.setUsername(member.getLoginacct());
        member.setUserpswd(encode);
        member.setEmail(member.getEmail());
        member.setAuthstatus("0");
        member.setUsertype("0");
        member.setUsertype("2");
        memberMapper.insertSelective(member);


    }

    @Override
    public TMember login(String username, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        TMemberExample example = new TMemberExample();
        example.createCriteria().andLoginacctEqualTo(username);
        List<TMember> list = memberMapper.selectByExample(example);
        if(list!=null && list.size()==1){
            TMember member = list.get(0);
            boolean matches = encoder.matches(password, member.getUserpswd());
            return matches?member:null;
        }
        return null;
    }
}
