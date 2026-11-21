# Domain Event Contracts

## PaymentAccepted v1
```json
{
  "eventId": "evt-123",
  "eventType": "PaymentAccepted",
  "schemaVersion": 1,
  "occurredAt": "2026-11-22T10:00:00Z",
  "aggregateId": "pay-9001",
  "correlationId": "corr-abc",
  "payload": {
    "paymentId": "pay-9001",
    "amount": "250.00",
    "currency": "USD",
    "status": "ACCEPTED"
  }
}
```

## Contract rules
- Use decimal strings for monetary event fields.
- Include event ID, type, schema version, occurrence time and correlation ID.
- Do not put full account numbers, credentials or unnecessary personal data into events.
- Define compatibility rules before changing schemas.
- Consumers must tolerate duplicate delivery and out-of-order events where applicable.
- Use partition keys aligned with ordering requirements, such as payment ID; understand hot-key risks.
- Define retention, replay authorization and data deletion/legal hold requirements.
