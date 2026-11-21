package com.example.payment.api;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
class PaymentController {
    private final PaymentApplicationService service;

    PaymentController(PaymentApplicationService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    PaymentAccepted create(
            @RequestHeader("Idempotency-Key") @NotBlank @Size(max = 100) String key,
            @RequestHeader(value = "X-Correlation-ID", required = false) String correlationId,
            @Valid @RequestBody CreatePaymentRequest request) {
        return service.accept(key, correlationId, request);
    }
}

record CreatePaymentRequest(
        @NotBlank String debtorAccount,
        @NotBlank String creditorReference,
        @Positive BigDecimal amount,
        @Pattern(regexp = "^[A-Z]{3}$") String currency,
        @NotEmpty List<@NotBlank String> purposeCodes) {}

record PaymentAccepted(UUID paymentId, String status, String correlationId) {}

/*
 * Illustrative interface. Implement with a transaction that atomically:
 * 1) claims the idempotency key under a unique constraint,
 * 2) persists payment state,
 * 3) persists an outbox event.
 * Never call a remote service while holding a DB transaction open.
 */
interface PaymentApplicationService {
    PaymentAccepted accept(String key, String correlationId, CreatePaymentRequest request);
}
