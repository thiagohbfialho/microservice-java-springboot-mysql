package com.canadafood.payments.dto;

import com.canadafood.payments.model.OrderItemsDto;
import com.canadafood.payments.model.Status;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class PaymentDto {
    private Long id;
    private BigDecimal value;
    private String name;
    private String number;
    private String expiration;
    private String code;
    private Status status;
    private Long orderId;
    private Long paymentTypeId;
    private List<OrderItemsDto> listItems;
}

