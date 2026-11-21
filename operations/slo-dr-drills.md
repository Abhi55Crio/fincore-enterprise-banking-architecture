# SLO, DR and Operational Readiness

## Define service-specific objectives
| Journey | Example SLI | Target |
|---|---|---|
| Payment acceptance | Successful valid requests within latency objective | Set with product / risk owners |
| Ledger posting | Correctly committed postings and processing latency | Integrity errors: zero tolerated |
| Settlement | Items completed within agreed rail/cut-off window | Set per rail and business calendar |
| Reconciliation | Source records processed and exceptions surfaced | Completeness and timeliness targets |
| Risk screening | Screening completed within decision window | Set with compliance / product |

## DR readiness
- Agree RTO/RPO by service and data class.
- Test database restore and point-in-time recovery.
- Validate Kafka recovery, offsets, replay authorization and duplicate safety.
- Test secrets, certificates, keys and identity recovery.
- Exercise regional failover and DNS changes where designed.
- Validate external partner connectivity and settlement cut-off implications.
- Document reconciliation after recovery to detect missing/duplicate work.
- Run tabletop and technical exercises; record evidence and remediation.

## Incident scenario: ledger database unavailable
1. Stop or reject new posting commands according to the approved availability/integrity policy.
2. Preserve accepted work durably without claiming it was posted.
3. Protect connection pools and prevent retry storms.
4. Alert on business impact, DB health and queue age.
5. Restore service and validate journal consistency.
6. Replay idempotently and reconcile against source/payment state.
7. Obtain authorized sign-off before reopening affected flows.
