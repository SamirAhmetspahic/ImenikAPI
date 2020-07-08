package org.imenik.rest.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Entity
@NamedQueries({
        @NamedQuery(name = "ContactInfo.findAll", query = "FROM Contact c"),
        @NamedQuery(name = "ContactInfo.findByEmail", query = "FROM Contact c WHERE c.email = :email"),
        @NamedQuery(name = "ContactInfo.findById", query = "FROM Contact c WHERE c.id = :id")
})
public class Contact implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    @Size(min = 2, max = 20, message = "")
    private String name;
    @NotNull
    @Size(min = 2, max = 20, message = "")
    private String lastName;
    @NotNull
    @Column(unique = true)
    @Size(min = 2, max = 20, message = "")
    private String email;
    @Size(min = 2, max = 20, message = "")
    private String phone;
    @NotNull
    @Size(min = 2, max = 20, message = "")
    private String mobile;
    @NotNull
    @Size(min = 2, max = 40, message = "")
    private String address;
    @Size(min = 2, max = 250, message = "")
    private String description;
    protected Date createdAt;
    protected Date updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }

    public Contact() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
