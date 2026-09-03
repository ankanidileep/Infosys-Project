# Infosys Financial Account Enrollment & Customer Eligibility Platform

A production-like, hands-on DevOps project based on the previously defined Infosys project narrative.

> Important: this is a practical reference implementation, not a copy of any proprietary Infosys/Charles Schwab source code.
> Business rules are simplified for training. Replace sample values, secrets, domains, and compliance controls before any real production use.

## Business objective

The platform receives customer/account information from an **Account Management System**, validates it, checks customer eligibility, calculates an account segment, performs enrollment processing, transforms the result, and sends a notification to a **Transaction Processing System**.

### 7 business services

1. Account Ingestion Service
2. Data Validation Service
3. Customer Eligibility Service
4. Account Segmentation Service
5. Enrollment Service
6. Data Transformation Service
7. Transaction Notification Service

## 3-tier architecture

- **Tier 1 – Presentation/API:** ALB → Kubernetes Ingress → API/service endpoints
- **Tier 2 – Business:** Spring Boot microservices running on Amazon EKS
- **Tier 3 – Data:** PostgreSQL + Redis (optional cache) + object storage/audit integration

## DevOps lifecycle

Developer → Git → Jenkins → Maven test/build → SonarQube → OWASP Dependency-Check → Nexus → Docker → Trivy → ECR → GitOps repo → Argo CD → EKS → Prometheus/Grafana

## Quick local deployment

Requirements:
- Docker
- Docker Compose
- Java 21
- Maven 3.9+
- curl

```bash
docker compose -f docker-compose.yml up -d --build
curl http://localhost:8080/actuator/health
```

Create a sample account:

```bash
curl -X POST http://localhost:8080/api/v1/accounts \
  -H 'Content-Type: application/json' \
  -d '{
    "customerId":"CUST1001",
    "accountId":"ACC5001",
    "accountType":"INVESTMENT",
    "status":"OPEN",
    "assetValue":500000
  }'
```

The API gateway routes the request through the workflow services.

## AWS/EKS deployment

For a learning lab, use the Terraform in `infra/terraform`. It creates a VPC and EKS cluster using the AWS EKS module. For a real production environment, use separate AWS accounts for environments, private endpoints where appropriate, centralized logging, KMS, secrets management, policy controls, backups, DR, and approved networking.

AWS recommends multi-AZ EKS designs, private worker nodes, careful security groups, and topology-aware workload placement. See the AWS EKS Best Practices references in `docs/production-standards.md`.

## Repository structure

```text
infosys-financial-enrollment-platform/
├── services/
│   ├── account-ingestion-service/
│   ├── data-validation-service/
│   ├── customer-eligibility-service/
│   ├── account-segmentation-service/
│   ├── enrollment-service/
│   ├── data-transformation-service/
│   └── transaction-notification-service/
├── gateway/
├── deploy/
│   ├── docker/
│   ├── k8s/
│   ├── helm/
│   └── argocd/
├── infra/terraform/
├── monitoring/
├── scripts/
├── Jenkinsfile
├── docker-compose.yml
└── docs/
```

## Suggested 3-hour lab sequence

### 0:00–0:30
Run locally with Docker Compose. Test the API and inspect logs.

### 0:30–1:10
Build/push images to ECR. Provision or use an existing EKS cluster.

### 1:10–2:00
Deploy namespace, secrets, PostgreSQL, services, ingress and HPA.

### 2:00–2:30
Install Prometheus/Grafana and verify metrics.

### 2:30–3:00
Run failure tests: bad payload, pod restart, rolling update, HPA load, image scan, rollback.

## Interview story

"At Infosys I worked on a financial account enrollment and customer eligibility platform. The application received account and customer information from an Account Management System. The processing was split into seven microservices for ingestion, validation, eligibility, segmentation, enrollment, transformation and transaction notification. After processing, the information was sent to a Transaction Processing System. On the DevOps side I worked with Git, Jenkins, Maven, SonarQube, OWASP Dependency-Check, Nexus, Docker, Trivy, ECR, Terraform, Argo CD, EKS, Prometheus and Grafana."

## Production note

Do not claim that every item in this repository existed exactly this way in the historical Infosys environment. This repository is a production-like practical implementation designed to demonstrate the DevOps responsibilities and architecture.
