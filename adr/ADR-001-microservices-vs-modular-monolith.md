# ADR-001: Service decomposition strategy

- Status: Accepted with conditions
- Context: Payment, ledger, trading, settlement and reconciliation have distinct business rules and operational lifecycles.
- Decision: Use domain-aligned services where independent ownership, scaling and release cadence justify distribution. Start with a modular monolith if team maturity, traffic or domain boundaries do not justify microservices.
- Alternatives: single layered monolith; modular monolith; coarse-grained services; fine-grained microservices.
- Trade-offs: independent deployment and fault isolation versus network failures, eventual consistency, observability and platform cost.
- Guardrail: no service split solely by technical layer or fashion.
