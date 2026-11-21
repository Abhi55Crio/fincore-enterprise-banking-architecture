# Business Context and Realistic Scenarios

## Business scenario A — Digital payment
A corporate customer submits a payment instruction through a portal or partner API. The platform authenticates the caller, validates beneficiary and account details, checks limits and risk policies, reserves or posts funds according to the product's accounting model, and initiates downstream clearing/settlement.

**Key distinction:** `RECEIVED`, `VALIDATED`, `ACCEPTED`, `POSTED`, `SETTLEMENT_PENDING`, `SETTLED`, `REJECTED` and `REVERSED` are distinct business states. A successful HTTP response must not be interpreted as final settlement.

## Business scenario B — Duplicate request / client retry
A client times out after submitting a payment and retries. The same idempotency key and same request hash must return the original outcome. Reuse of the same key with a different payload must be rejected. A unique database constraint is the final concurrency guard.

## Business scenario C — Downstream clearing outage
A clearing partner is unavailable. The platform records a durable state, applies bounded retry/backoff, monitors queue age and exposes a pending status. It must not fabricate a successful settlement. Operations can replay eligible work under controlled, audited procedures.

## Business scenario D — Reconciliation break
The internal ledger shows a posted payment but the external clearing file does not contain a matching item. Reconciliation creates an exception with source references, amount/currency/date, reason code and evidence. A maker-checker workflow controls resolution; no silent data correction.

## Business scenario E — Suspicious payment
Rules and an AI model generate a risk signal. Policy determines whether to allow, step-up authenticate, hold for review or reject. The model supplies a score and reason codes; deterministic policy and authorized staff remain accountable for consequential actions.

## Business scenario F — Trading pre-trade control
A client submits an order. The system validates instrument, market session, client permissions, credit/exposure limits and applicable restrictions before routing. Order status and execution reports are correlated and auditable. This reference does not prescribe exchange-specific market connectivity.

## Stakeholders
- Product owner: customer outcomes, product rules and prioritization.
- Operations: settlement windows, exceptions, incident response.
- Risk/compliance: screening policy, audit and escalation.
- Finance: ledger semantics, reconciliation and financial control.
- Security: identity, data classification, threat model and control assurance.
- Engineering: architecture, implementation, testing and operability.

## Discovery questions
1. Which jurisdictions, legal entities, payment rails and currencies are in scope?
2. What is the system of record for accounts, balances and financial postings?
3. Are funds reserved at acceptance or only posted at a later stage?
4. What are peak TPS, burst profile, p95/p99 latency and batch windows?
5. What are RTO/RPO and service-specific availability objectives?
6. Which customer and transaction data classifications apply?
7. Which external partners have idempotency, callback and reconciliation contracts?
8. What is the exception approval and maker-checker process?
