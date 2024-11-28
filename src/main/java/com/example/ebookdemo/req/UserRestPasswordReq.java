package com.example.ebookdemo.req;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class UserRestPasswordReq {
    private Long id;

    @NotNull(message = "[密码不能为空]")
//    @Length(min = 6,max = 18,message = "密码:6-12位")
    @Pattern(regexp = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,32}$", message = "【密码】至少包含 数字和英文，长度6-32")
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserRestPasswordReq{" +
                "id=" + id +
                ", password='" + password + '\'' +
                '}';
    }
}