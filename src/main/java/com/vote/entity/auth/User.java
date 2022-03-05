package com.vote.entity.auth;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
public class User implements Serializable, UserDetails {
    private Long id;
    private String username;
    private String password;
    private String photo;
    private Boolean sex;
    private LocalDateTime loginTime;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean status;

    private List<GrantedAuthority> authorities;

    public User(String username, String password, List<GrantedAuthority> authorities) {
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }


    public User(Long id, String username, String password, String photo, Boolean sex, LocalDateTime loginTime, LocalDateTime createTime, LocalDateTime updateTime, Boolean status, List<GrantedAuthority> authorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.photo = photo;
        this.sex = sex;
        this.loginTime = loginTime;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.status = status;
        this.authorities = authorities;
    }

    public User() {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    @Override
    public boolean equals(Object obj) {
        //会话并发生效，使用username判断是否是同一个用户

        if (obj instanceof User){
            //字符串的equals方法是已经重写过的
            return ((User) obj).getUsername().equals(this.username);
        }else {
            return false;
        }
    }

    @Override
    public String getUsername() {
        return username;
    }
    //继承后必须返回为true
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
