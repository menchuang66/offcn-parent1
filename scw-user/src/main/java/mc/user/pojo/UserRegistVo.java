package mc.user.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;

@ApiModel

public class UserRegistVo {
    @ApiModelProperty("手机号")
    private String loginacct;
    @ApiModelProperty("密码")
    private String userpswd;
    @ApiModelProperty("邮箱")
    private String email;
    @ApiModelProperty("验证码")
    private String code;

    public UserRegistVo() {
    }

    public String getLoginacct() {
        return loginacct;
    }

    public void setLoginacct(String loginacct) {
        this.loginacct = loginacct;
    }

    public String getUserpswd() {
        return userpswd;
    }

    public void setUserpswd(String userpswd) {
        this.userpswd = userpswd;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public UserRegistVo(String loginacct, String userpswd, String email, String code) {
        this.loginacct = loginacct;
        this.userpswd = userpswd;
        this.email = email;
        this.code = code;
    }
}
