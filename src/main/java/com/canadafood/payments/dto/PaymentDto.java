package com.canadafood.payments.dto;

import com.canadafood.payments.model.Status;

import java.math.BigDecimal;

public record PaymentDto (

    Long id,
    BigDecimal value,
    String name,
    String number,
    String expirationDate,
    String code,
    Status status,
    Long paymentType,
    Long orderId

){



}
