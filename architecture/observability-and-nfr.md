# Observability, NFRs and Performance Engineering

## Golden signals
- Traffic: request rate, payment initiation rate, event throughput.
- Errors: API failures, rejected events, settlement exceptions, DLQ volume.
- Latency: p50/p95/p99 by endpoint and workflow stage.
- Saturation: CPU, memory, JVM heap/GC, thread pools, DB pool wait, broker lag.

## Layer-wise diagnosis
1. Client/edge: DNS, TLS, WAF, CDN, network and client retry behavior.
2. Gateway: authentication latency, rate limits, routing, target health.
3. Service/JVM: thread contention, heap, GC pauses, CPU profiles, pool exhaustion.
4. Database: slow queries, locks, execution plans, connection pool, replication lag.
5. Kafka: producer errors, partition skew, consumer lag, retries, DLQ and broker health.
6. External partners: timeout, response codes, rate limits, callback delays.
7. Platform: pod restarts, readiness, node pressure, network policies and autoscaling events.

## Financial workflow dashboards
- Payment state counts and age by state.
- Posting success/failure and duplicate suppression.
- Unbalanced journal attempts (target zero successful unbalanced postings).
- Settlement completion and exception aging.
- Reconciliation match rate and unresolved break age.
- Fraud model latency, drift and review queue age.
- Cost per successful business operation.

## SLO practice
Define SLIs with product and operations. Separate availability and latency for payment acceptance, ledger posting, settlement and reconciliation. Alert on sustained user impact and error-budget burn. Avoid a single broad SLO that hides critical workflow failure.

## Performance test scenarios
- Normal, peak and burst traffic profiles agreed with business.
- Hot-account contention and concurrent duplicate submissions.
- Slow DB, exhausted connection pool and broker lag.
- Partner timeout / rate limiting.
- Large reconciliation file and end-of-day batch overlap.
- Recovery after pod/zone failure and consumer restart.
