package com.springproject.fileparserwebapp.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name = "users")
@Setter
@Getter
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    @OneToMany
    @JoinTable(
            name = "users_files",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "file_id")
    )
    private Collection<File> files = new ArrayList<>();

    public User() {}

    public User(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public boolean isAccountNonExpired() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isAccountNonLocked() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status.equals(Status.ACTIVE);
    }

    @Override
    public boolean isEnabled() {
        return status.equals(Status.ACTIVE);
    }
}
