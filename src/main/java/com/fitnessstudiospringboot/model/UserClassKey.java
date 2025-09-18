package com.fitnessstudiospringboot.model;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
public class UserClassKey implements Serializable {
        private Integer userId;
        private Integer classId;
        public UserClassKey(Integer userId, Integer classId) {
            this.userId = userId;
            this.classId = classId;
        }

    public UserClassKey() {

    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserClassKey that = (UserClassKey) o;
        return Objects.equals(userId, that.userId) && Objects.equals(classId, that.classId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, classId);
    }

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
