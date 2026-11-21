# Idempotency and Transactional Outbox

## Idempotency schema (illustrative)
```sql
CREATE TABLE idempotency_record (
  client_id VARCHAR(80) NOT NULL,
  idempotency_key VARCHAR(100) NOT NULL,
  request_hash CHAR(64) NOT NULL,
  resource_id UUID,
  response_status INTEGER,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  PRIMARY KEY (client_id, idempotency_key)
);

CREATE TABLE outbox_event (
  event_id UUID PRIMARY KEY,
  aggregate_id UUID NOT NULL,
  event_type VARCHAR(120) NOT NULL,
  schema_version INTEGER NOT NULL,
  payload JSONB NOT NULL,
  created_at TIMESTAMPTZ NOT NULL DEFAULT now(),
  published_at TIMESTAMPTZ
);
```

## Processing rules
1. Canonicalize request fields and calculate a cryptographic hash.
2. Attempt to insert the idempotency record. Let the unique constraint arbitrate concurrent requests.
3. If key exists and hash matches, return the original resource/result.
4. If key exists and hash differs, return `409 Conflict`.
5. In one local DB transaction, persist business state and outbox record.
6. A separate publisher reads unpublished outbox rows and publishes to Kafka.
7. Mark publication after broker acknowledgement; duplicate publication remains possible, so consumers must deduplicate.

## Consumer inbox
Use a unique `event_id` constraint in the consumer's local transaction. Apply the business change and record the processed event atomically. This provides effectively-once business effects within the consumer boundary; it does not make the whole distributed workflow exactly-once.
