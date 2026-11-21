# Payment Service — Implementation Blueprint

## Responsibilities
Validate payment instruction, enforce idempotency, coordinate risk policy and payment lifecycle, persist state and reliably emit domain events. Ledger posting is a separate financial boundary in this conceptual design.

## API
`POST /api/v1/payments`
Headers: `Idempotency-Key`, `Authorization`, `X-Correlation-ID`.

Return `202 Accepted` only when the request has been durably accepted into the defined workflow. Return a payment reference and current state; settlement is a later state.

## Suggested package structure
```text
com.example.payment
  api/PaymentController.java
  application/PaymentApplicationService.java
  domain/Payment.java
  domain/PaymentStatus.java
  domain/PaymentPolicy.java
  persistence/PaymentRepository.java
  persistence/IdempotencyRecord.java
  messaging/OutboxEvent.java
  messaging/OutboxPublisher.java
  config/SecurityConfig.java
```

## Production hardening checklist
- Unique DB constraint on client + idempotency key.
- Request hash validation for key reuse.
- Transactional state + outbox persistence in same local DB transaction.
- Consumer deduplication and event schema compatibility.
- Authorization at account/customer resource level.
- Input validation and stable error contract.
- Timeouts, bounded retries and circuit breaker for external calls.
- Unit, integration, contract, security and load tests.
