
# OMS Java Backend Agent Guide

## Purpose
- Guidance for AI tools and engineers on designing, generating, and maintaining OMS code.
- Focus: spec-driven, simple, secure, and maintainable Java backend.

## Engineer Persona
- Senior Java backend engineer (Spring Boot, PostgreSQL + Liquibase, Kubernetes, Azure, trading domain).

## Canonical Inputs
- OpenAPI specs (`/api/`, `/spec/`)
- Liquibase changelogs (`/db/migration/`)
- Domain docs (`/docs/domain.md`)
- GitLab CI config (`/.gitlab-ci.yml`)
- ADRs & README (`/docs/adr/`)

## Expected Outputs
- REST controllers, DTOs, validators (OpenAPI-first)
- Service layer with clear transactions
- Spring Data JPA or JDBC repositories
- Liquibase changelogs (XML/SQL)
- Unit/integration tests (JUnit 5, Mockito, Testcontainers)

## Conventions & Style
- Java 21+, Spring Boot LTS
- Package: `com.companyname.oms.{orders,executions,common,config}`
- Layering: controller → dto/mapper → service → repository → db
- DTOs explicit, small mappers, prefer immutability
- Validation: Jakarta Validation in DTO/controller
- Error: RFC7807 Problem+JSON
- Logging: structured (JSON, slf4j), correlation/order IDs, no secrets/PII
- Secrets: Azure Key Vault or env vars

## Testing
- Unit: JUnit 5, Mockito (>80% coverage)
- Integration: Testcontainers (Postgres), Liquibase migrations
- Contract: Spring Cloud Contract/Pact
- Acceptance: API tests from OpenAPI

## Database & Migrations
- Liquibase changelogs in `/db/migration/`
- Backward-compatible schema changes
- Explicit SQL for complex queries

## API-First Workflow
1. Define OpenAPI contract
2. Generate server stubs
3. Implement service/tests
4. Add integration tests
5. Update contract/version as needed

## Deployment & CI/CD
- AKS via Dockerfiles
- GitLab pipeline: build → test → static analysis → integration → image → scan → push → deploy → smoke-test
- Static analysis: SpotBugs, Checkstyle/PMD, OWASP
- Helm charts generated in pipeline
- Rollback: use immutable image tags

## Security & Compliance
- No secrets in repo/agent.md
- Least privilege for cloud resources
- TLS for all endpoints
- Mask/redact sensitive logs/test data
- Audit logs for trading data

## Observability
- Prometheus metrics (Actuator + Micrometer)
- OpenTelemetry tracing
- Structured logs, correlation IDs
- Health endpoints for readiness/liveness

## AI Usage Guardrails
- Always include tests with generated code
- Never merge without human review
- No plaintext secrets
- Ask for clarification on ambiguous rules
- Migration SQL requires comments/review
- Prefer small, focused PRs

## Do-Not-Do List
- No credentials/API keys in code
- No auto-approve/merge AI PRs
- No schema/production changes without review

## Prompt Templates
**API + Controller + DTOs**
> "Given this OpenAPI path, generate a Spring Boot controller, DTOs with validation, and unit test examples. Keep controller thin, map to service, include Javadoc."

**Service Logic from Criteria**
> "Implement OrderService.placeOrder(OrderRequest): 1) validate funds; 2) persist order; 3) emit event; 4) return response. Use transactions and unit test."

**Liquibase Migration**
> "Generate a Liquibase changelog to add orders table with columns: id (uuid PK), account_id (uuid), symbol (varchar), quantity (numeric), price (numeric), side (BUY/SELL), status (varchar), created_at (timestamptz). Include indexes."

**Kubernetes Manifest**
> "Generate a K8s deployment manifest for oms: env vars for DB/Key Vault, resources, readiness/liveness probes, mount secret volumes. Helm charts generated in pipeline."

**Refactor for Simplicity**
> "Refactor this service class to reduce duplication and improve testability. Add unit tests."

## Examples
**OpenAPI Path**
```yaml
path: /api/orders
  post:
    summary: Place a new order
    requestBody:
      required: true
      content:
        application/json:
          schema:
            $ref: '#/components/schemas/OrderRequest'
    responses:
      '201':
        description: Order created
        content:
          application/json:
            schema:
              $ref: '#/components/schemas/OrderResponse'
```

**RFC7807 Error**
```json
{
  "type": "https://example.com/probs/invalid-order",
  "title": "Invalid order",
  "status": 400,
  "detail": "Quantity must be > 0",
  "instance": "/api/orders"
}
```

## PR Checklist
- [ ] API contract updated
- [ ] Unit tests added/updated
- [ ] Integration tests pass
- [ ] Liquibase migrations included
- [ ] Security review
- [ ] Observability hooks
- [ ] ADR updated

## Validation & Maintenance
- Validate agent.md by requesting affected code from Copilot/Chat
- Keep file up-to-date; no secrets
- For multiple agents, use agent-<role>.md files