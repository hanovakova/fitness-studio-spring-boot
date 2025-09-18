package com.fitnessstudiospringboot.model;

import jakarta.persistence.*;

@Entity
@Table(name = "user_classes")
public class UserClass {

    @EmbeddedId
    private UserClassKey id;
    private boolean paid;

    public UserClass() {
    }

    public UserClass(UserClassKey id, boolean paid) {
        this.id = id;
        this.paid = paid;
    }

    public UserClassKey getId() {
        return id;
    }

    public void setId(UserClassKey id) {
        this.id = id;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }
}
