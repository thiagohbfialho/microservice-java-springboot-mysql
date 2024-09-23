package com.canadafood.payments.service;

import com.canadafood.payments.dto.PaymentDto;
import com.canadafood.payments.model.Payment;
import com.canadafood.payments.model.Status;
import com.canadafood.payments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    private ModelMapper modelMapper;

    public Page<PaymentDto> getAll(Pageable pageable) {
        return paymentRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDto.class));
    }

    public PaymentDto getById(Long id) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto create(PaymentDto paymentDto) {
        var payment = modelMapper.map(paymentDto, Payment.class);
        payment.setStatus(Status.CREATED);
        paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public PaymentDto update(Long id, PaymentDto paymentDto) {
        var payment = modelMapper.map(paymentDto, Payment.class);
        payment.setId(id);
        paymentRepository.save(payment);

        return modelMapper.map(payment, PaymentDto.class);
    }

    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}
