# Payment Lifecycle — Sequence and Failure Semantics

```mermaid
sequenceDiagram
  autonumber
  participant Client
  participant Gateway
  participant Payment as Payment Service
  participant Risk as Risk/Policy
  participant Ledger
  participant DB as Payment DB + Outbox
  participant Kafka
  participant Settle as Settlement
  Client->>Gateway: POST /payments + Idempotency-Key
  Gateway->>Gateway: Authenticate, authorize, rate-limit
  Gateway->>Payment: Validated request + trace context
  Payment->>DB: Claim idempotency key / request hash
  Payment->>Risk: Evaluate rules and risk signal
  Risk-->>Payment: ALLOW / STEP_UP / REVIEW / REJECT
  Payment->>Ledger: Post journal (if policy permits)
  Ledger->>DB: Persist journal + outbox atomically in ledger boundary
  Payment->>DB: Persist payment state + outbox
  Payment-->>Client: 202 Accepted + payment reference
  DB-->>Kafka: Outbox relay publishes versioned event
  Kafka-->>Settle: PaymentPosted event
  Settle->>Settle: Submit to clearing partner
  Settle-->>Kafka: Settlement status event
```

## Important implementation note
The diagram is conceptual. If payment state and ledger journal live in separate service databases, they cannot be committed atomically with a local database transaction. Use an explicit saga/process manager, durable commands/events, idempotent handlers and compensating business actions. Never imply a distributed ACID transaction exists across services.

## State machine
```mermaid
stateDiagram-v2
  [*] --> RECEIVED
  RECEIVED --> VALIDATING
  VALIDATING --> REJECTED
  VALIDATING --> RISK_REVIEW
  VALIDATING --> ACCEPTED
  RISK_REVIEW --> HELD
  RISK_REVIEW --> ACCEPTED
  RISK_REVIEW --> REJECTED
  ACCEPTED --> POSTING
  POSTING --> POSTED
  POSTING --> FAILED
  POSTED --> SETTLEMENT_PENDING
  SETTLEMENT_PENDING --> SETTLED
  SETTLEMENT_PENDING --> SETTLEMENT_EXCEPTION
  SETTLEMENT_EXCEPTION --> SETTLEMENT_PENDING
  POSTED --> REVERSAL_PENDING
  REVERSAL_PENDING --> REVERSED
```

Only authorized transitions are permitted. Persist transition time, actor/system identity, reason, correlation ID, policy version and source evidence.
