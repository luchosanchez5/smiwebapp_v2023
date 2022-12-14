package com.dto;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.model.Status;

public class UserDto {

    private Long id;

    private String username;

    private String password;

    private String name;

    private String lastName;

    private String email;

    private LocalDateTime createDate;

    private Status status;

    private Set<RoleDto> role = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<RoleDto> getRole() {
        return role;
    }

    public void setRole(Set<RoleDto> role) {
        this.role = role;
    }
	
	
}
