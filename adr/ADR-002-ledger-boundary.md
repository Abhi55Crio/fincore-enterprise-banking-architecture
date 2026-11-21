# ADR-002: Keep ledger posting inside a strict financial boundary

- Status: Accepted
- Context: Financial records require strong integrity, auditability and controlled correction.
- Decision: Ledger service owns journal persistence and validates balanced postings per currency in a local database transaction. Other services submit idempotent posting commands and consume posting outcomes.
- Alternatives: shared database writes; distributed transaction; event-sourced ledger; relational journal with append-oriented entries.
- Trade-offs: explicit boundary and auditability versus additional workflow coordination.
- Consequences: payment and ledger state across service boundaries use durable commands/events and explicit pending/failure states. No claim of cross-service ACID.
