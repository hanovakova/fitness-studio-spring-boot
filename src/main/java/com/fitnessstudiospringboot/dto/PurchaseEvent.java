package com.fitnessstudiospringboot.dto;

import com.fitnessstudiospringboot.model.FitnessClass;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public class PurchaseEvent {
    private UUID PurchaseId;
    private Integer customerId;
    private List<FitnessClass> purchasedClasses;
    private Float purchaseSum;
    private Timestamp timestamp;

    public PurchaseEvent(UUID purchaseId, Integer customerId, List<FitnessClass> purchasedClasses, Float purchaseSum, Timestamp timestamp) {
        PurchaseId = purchaseId;
        this.customerId = customerId;
        this.purchasedClasses = purchasedClasses;
        this.purchaseSum = purchaseSum;
    }

    public UUID getPurchaseId() {
        return PurchaseId;
    }

    public void setPurchaseId(UUID purchaseId) {
        PurchaseId = purchaseId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public List<FitnessClass> getPurchasedClasses() {
        return purchasedClasses;
    }

    public void setPurchasedClasses(List<FitnessClass> purchasedClasses) {
        this.purchasedClasses = purchasedClasses;
    }

    public Float getPurchaseSum() {
        return purchaseSum;
    }

    public void setPurchaseSum(Float purchaseSum) {
        this.purchaseSum = purchaseSum;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
