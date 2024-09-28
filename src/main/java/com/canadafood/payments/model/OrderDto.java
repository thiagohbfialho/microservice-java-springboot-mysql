package com.canadafood.payments.model;

import java.util.List;

public record OrderDto(List<OrderItemsDto> items) {
}
