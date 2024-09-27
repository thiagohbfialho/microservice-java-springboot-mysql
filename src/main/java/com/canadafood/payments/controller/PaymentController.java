package com.canadafood.payments.controller;

import com.canadafood.payments.dto.PaymentDto;
import com.canadafood.payments.service.PaymentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping
    public Page<PaymentDto> getPayments(@PageableDefault() Pageable pageable) {
        return paymentService.getAll(pageable);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDto> getPayment(@PathVariable @Valid Long id) {
        var paymentDto = paymentService.getById(id);

        return ResponseEntity.ok(paymentDto);
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody @Valid PaymentDto paymentDto, UriComponentsBuilder uriBuilder) {
        var newPaymentDto = paymentService.create(paymentDto);
        URI path = uriBuilder.path("payment/{id}").buildAndExpand(paymentDto.getId()).toUri();

        return ResponseEntity.created(path).body(newPaymentDto);
    }

    @PutMapping
    public ResponseEntity<PaymentDto> updatePayment(@RequestBody @Valid PaymentDto paymentDto) {
        PaymentDto updatedPaymentDto = paymentService.update(paymentDto.getId(), paymentDto);
        return ResponseEntity.ok(updatedPaymentDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PaymentDto> deletePayment(@PathVariable @NotNull Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/confirm")
    public void confirmPayment(@PathVariable @NotNull Long id){
        paymentService.confirmPayment(id);
    }
}
