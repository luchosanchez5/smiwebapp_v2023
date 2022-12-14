package com.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name="user_role")
public class UserRole {
    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "USER_ROLE_SEQ", sequenceName = "USER_ROLE_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "USER_ROLE_SEQ")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_role")
    private Role role;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_users")
    @JsonBackReference
    private Users user;

    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Column(name = "create_date")
    private LocalDateTime createDate;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
