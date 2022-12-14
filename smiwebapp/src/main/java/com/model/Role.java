package com.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import static javax.persistence.GenerationType.SEQUENCE;

import com.dto.RoleDto;

@Entity
@Table(name="role")
public class Role {

    @Id
    @Column(name = "ID")
    @SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ", initialValue = 1, allocationSize=100)
    @GeneratedValue(strategy = SEQUENCE, generator = "ROLE_SEQ")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public RoleDto toDTO(){
        RoleDto roleDTO = new RoleDto();
        roleDTO.setId(this.getId());
        roleDTO.setName(this.getName());
        roleDTO.setDescription(this.getDescription());
        roleDTO.setStatus(this.getStatus());
        roleDTO.setCreateDate(this.getCreateDate());
        return roleDTO;
    }
	
}
