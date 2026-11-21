# Ledger and Financial Data Integrity

## Double-entry journal invariant
For each journal and currency, total debits must equal total credits. A journal is complete or absent; partial posting is not acceptable.

Illustrative journal for a transfer of 100 USD:
| Account | Direction | Amount | Currency |
|---|---|---:|---|
| Customer A payable | Debit | 100.00 | USD |
| Customer B payable | Credit | 100.00 | USD |

This is a simplified illustration. Real account mapping, sign conventions, fees, holds, FX, settlement and accounting treatment must be defined by finance/product owners.

## Data rules
- Store monetary values using fixed-precision decimal types, never binary floating point.
- Include currency explicitly; never sum unlike currencies without an FX conversion event/rate reference.
- Use immutable journal entries and compensating entries for correction.
- Enforce uniqueness for business references and posting keys.
- Use database transactions within the ledger boundary.
- Apply optimistic/pessimistic concurrency based on contention and invariant requirements.
- Record who/what initiated a posting and the policy/authorization context.
- Separate operational logs from authoritative financial records.

## Example relational shape
```sql
CREATE TABLE journal (
  journal_id UUID PRIMARY KEY,
  business_reference VARCHAR(80) NOT NULL,
  status VARCHAR(20) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL,
  UNIQUE (business_reference)
);

CREATE TABLE journal_line (
  line_id UUID PRIMARY KEY,
  journal_id UUID NOT NULL REFERENCES journal(journal_id),
  account_id VARCHAR(80) NOT NULL,
  direction VARCHAR(6) NOT NULL CHECK (direction IN ('DEBIT','CREDIT')),
  amount NUMERIC(19,4) NOT NULL CHECK (amount > 0),
  currency CHAR(3) NOT NULL
);
```
A production design also needs a posting procedure/service that validates balance per currency before committing, access controls, partition/retention strategy, indexes, audit evidence and tested recovery.
