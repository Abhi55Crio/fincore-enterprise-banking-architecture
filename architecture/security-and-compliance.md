# Security, Privacy and Compliance Architecture

## Defense in depth
| Layer | Design controls |
|---|---|
| Channel/edge | TLS, WAF, DDoS controls, rate limiting, secure headers |
| Identity | OIDC/OAuth 2.0, MFA/step-up where required, short-lived credentials |
| Authorization | RBAC/ABAC, customer/account ownership checks, service identity |
| Service | Input validation, secure coding, dependency scanning, threat modelling |
| Data | Classification, encryption at rest/in transit, tokenization/masking where appropriate |
| Secrets | Central secrets manager, rotation, workload identity; never commit secrets |
| Audit | Tamper-evident access and business decision evidence, time synchronization |
| Operations | Privileged access management, vulnerability management, incident response |

## Financial controls to consider
- Customer due diligence and sanctions/PEP screening where applicable.
- AML monitoring and suspicious activity case workflows where applicable.
- Segregation of duties and maker-checker approval for sensitive operations.
- Data residency, retention, deletion and legal hold requirements.
- Payment authentication and local payment-system rules.
- Market conduct, order surveillance and trading restrictions where relevant.
- Third-party risk and outsourcing controls.

This list is not a legal compliance determination. Applicability depends on jurisdiction, institution, product and data. Engage legal, compliance, privacy and security specialists.

## Audit event fields
`eventId`, `eventTime`, `actorId/serviceIdentity`, `action`, `resource`, `decision`, `reasonCode`, `policyVersion`, `correlationId`, `sourceSystem`, `integrityMetadata`.

Avoid putting credentials, full payment credentials, unnecessary personal data or sensitive model features into logs.
