package com.clpmonitor.clpmonitor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
    @Table(name = "Block")
    public class dboBlock {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Integer id;
    
        @Column(nullable = false)
        private Integer position;
    
        @Column(nullable = false)
        private Integer color;
    
        @Column(nullable = false)
        private Integer storageId;
    
        @Column
        private String productionOrder;
    
        // Getters e Setters
        public Integer getId() {
            return id;
        }
    
        public void setId(Integer id) {
            this.id = id;
        }
    
        public Integer getPosition() {
            return position;
        }
    
        public void setPosition(Integer position) {
            this.position = position;
        }
    
        public Integer getColor() {
            return color;
        }
    
        public void setColor(Integer color) {
            this.color = color;
        }
    
        public Integer getStorageId() {
            return storageId;
        }
    
        public void setStorageId(Integer storageId) {
            this.storageId = storageId;
        }
    
        public String getProductionOrder() {
            return productionOrder;
        }
    
        public void setProductionOrder(String productionOrder) {
            this.productionOrder = productionOrder;
        }
    }
    