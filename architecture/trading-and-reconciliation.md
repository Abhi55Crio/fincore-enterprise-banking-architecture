# Trading, Settlement and Reconciliation

## Trading order lifecycle
```mermaid
flowchart TD
  A[Order Received] --> B[Authentication + Entitlement]
  B --> C[Instrument / Session Validation]
  C --> D[Pre-trade Limits & Restrictions]
  D -->|Pass| E[Order Accepted]
  D -->|Fail| X[Rejected with Reason]
  E --> F[Route to Venue / Broker]
  F --> G[Execution Reports]
  G --> H[Partial / Filled / Cancelled]
  H --> I[Position & Trade Capture]
  I --> J[Settlement Instruction]
```

Order acceptance, execution, clearing and settlement are different states and must be represented separately. Enforce client entitlements, limit checks, duplicate client order IDs and auditable changes.

## Reconciliation process
```mermaid
flowchart LR
  INT[Internal Ledger / Trade Records] --> N[Normalize]
  EXT[External Bank / Broker / Clearing Files] --> N
  N --> MATCH[Match by Business Keys]
  MATCH -->|Matched| OK[Matched Evidence]
  MATCH -->|Unmatched / Mismatch| EX[Exception Case]
  EX --> REVIEW[Maker Review]
  REVIEW --> CHECK[Checker Approval]
  CHECK --> RES[Controlled Resolution]
  RES --> AUD[Audit Trail]
```

Matching keys may include external reference, internal reference, amount, currency, value date and counterparty. Tolerance rules must be approved, versioned and explainable. Never auto-write a financial correction simply because a record failed matching.
