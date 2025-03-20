package org.entity;

import java.time.LocalDateTime;

public class Order {
    private int id;
    private LocalDateTime time;
    private Status status;
    private boolean isPayment;
    private String name;
    private String phone;
    private String email;
    private String district;
    private String province;
    private String ward;
    private boolean payment;

    public Order(LocalDateTime time, int id, Status status, boolean isPayment, String phone, String name, String district, String email, String province, boolean payment, String ward) {
        this.time = time;
        this.id = id;
        this.status = status;
        this.isPayment = isPayment;
        this.phone = phone;
        this.name = name;
        this.district = district;
        this.email = email;
        this.province = province;
        this.payment = payment;
        this.ward = ward;
    }

    public Order() {
    }
}
