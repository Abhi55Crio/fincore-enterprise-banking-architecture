# ADR-003: AI risk score is advisory to an approved policy engine

- Status: Accepted
- Context: Model output may be uncertain, drift and create false positives/negatives.
- Decision: AI returns score, reason codes and model version. Deterministic policy maps results to allow, step-up, hold/review or reject. Material decisions support authorized human review and audit.
- Alternatives: model directly authorizes; rules-only; hybrid rules + model.
- Trade-offs: more governance and workflow complexity in exchange for control, explainability and accountable decisions.
- Controls: shadow mode, validation, drift monitoring, fallback, approval and rollback.
