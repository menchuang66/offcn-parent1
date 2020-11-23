package mc.user.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("测试实体类")
public class User {
    @ApiModelProperty(value = "名字")
    private String name;
    @ApiModelProperty(value = "主键")
    private int id;
    @ApiModelProperty(value = "电子邮件")
    private String email;
    public User() {
    }

    public User(String name, int id, String email) {
        this.name = name;
        this.id = id;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
