package com.awesometickets.business.entities;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class User {
    private Integer userId;
    private String phoneNum;
    private Integer remainPurchase;

    public User() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "phone_num", nullable = false, length=11, unique = true)
    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Column(name = "remain_purchase", nullable = false)
    public Integer getRemainPurchase() {
        return remainPurchase;
    }

    public void setRemainPurchase(Integer remainPurchase) {
        this.remainPurchase = remainPurchase;
    }
}
