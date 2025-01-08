package com.web.portal.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clientid")
    private Long clientId;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 10)
    private String gender;

    private Integer age;

    @Column(unique = true, nullable = false, length = 50)
    private String identification;

    @Column(length = 255)
    private String address;

    @Column(length = 20)
    private String telephone;

    @Column(nullable = false, length = 255)
    private String password;

    @Column(length = 50)
    private String state;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
 
    public Customer() {}
 
    public Customer(Long clientId, String name, String gender, Integer age, String identification, String address,
                    String telephone, String password, String state, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.clientId = clientId;
        this.name = name;
        this.gender = gender;
        this.age = age;
        this.identification = identification;
        this.address = address;
        this.telephone = telephone;
        this.password = password;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
 
    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getIdentification() {
        return identification;
    }

    public void setIdentification(String identification) {
        this.identification = identification;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}
