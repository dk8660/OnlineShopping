package com.example.onlineshopping.boundedContext.payment.service;

import com.example.onlineshopping.base.enums.PaymentMethod;
import com.example.onlineshopping.base.enums.PaymentStatus;
import com.example.onlineshopping.base.rsData.RsData;
import com.example.onlineshopping.boundedContext.payment.enity.Payment;
import com.example.onlineshopping.boundedContext.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public Payment tryCreatePayment(long orderId, int totalPrice, String paymentMethod) {
        try {
            Enum<PaymentMethod> paymentMethodEnum;
            if(paymentMethod.equals("card")){
                paymentMethodEnum = PaymentMethod.CARD;
            }
            else if(paymentMethod.equals("line_pay")){
                paymentMethodEnum = PaymentMethod.LINE_PAY;
            }
            else {
                paymentMethodEnum = PaymentMethod.BANK_TRANSFER;
            }
            Payment payment = Payment.builder()
                    .orderId(orderId)
                    .paymentMethod(paymentMethodEnum)
                    .status(PaymentStatus.PENDING)
                    .amount(totalPrice)
                    .build();
            paymentRepository.save(payment);

            return payment;
        }
        catch (Exception e) {
            return null;
        }
    }

    public Payment getPaymentById(long paymentid) {
        return paymentRepository.findById(paymentid).orElse(null);
    }

    public RsData tryUpdatePaymentStatus(long paymentid) {
        try{
            Payment payment = getPaymentById(paymentid);
            if(payment == null) {return RsData.of("F-1", "결제 정보를 찾지 못하였습니다.");}

            payment.setStatus(PaymentStatus.SUCCESS);
            paymentRepository.save(payment);

            return RsData.of("S-1", "결제 상태 업데이트에 성공하였습니다.");
        }
        catch (Exception e) {
            return RsData.of("F-2", "결제 정보 수정에 실패하였습니다.");
        }

    }
}
