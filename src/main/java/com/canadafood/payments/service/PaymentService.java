package com.canadafood.payments.service;

import com.canadafood.payments.dto.PaymentDto;
import com.canadafood.payments.http.OrderClient;
import com.canadafood.payments.model.Payment;
import com.canadafood.payments.model.Status;
import com.canadafood.payments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderClient order;

    public Page<PaymentDto> getAll(Pageable pageable) {
        return paymentRepository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDto.class));
    }

    public PaymentDto getById(Long id) {
        var payment = paymentRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        var paymentDto = modelMapper.map(payment, PaymentDto.class);
        paymentDto.setListItems(order.getOrder(paymentDto.getOrderId()).items());
        return paymentDto;
    }

    public PaymentDto create(PaymentDto paymentDto) {
        Payment payment = modelMapper.map(paymentDto, Payment.class);
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

    public void confirmPayment(Long id){
        Optional<Payment> payment = paymentRepository.findById(id);

        if (!payment.isPresent()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED);
        paymentRepository.save(payment.get());
        order.updatePayment(payment.get().getOrderId());
    }

    public void updateStatus(Long id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (!payment.isPresent()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED_WITHOUT_INTEGRATION);
        paymentRepository.save(payment.get());
    }
}
