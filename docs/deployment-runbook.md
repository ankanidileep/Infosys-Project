# Deployment runbook

## 1. Local validation

```bash
docker compose up -d --build
docker compose ps
./scripts/test-api.sh
```

## 2. Build artifacts

```bash
./scripts/build-all.sh
```

## 3. Provision AWS

```bash
cd infra/terraform
terraform init
terraform validate
terraform plan
terraform apply
aws eks update-kubeconfig --region us-east-1 --name financial-platform-eks
kubectl get nodes
```

## 4. ECR

Create repositories through Terraform, then authenticate:

```bash
aws ecr get-login-password --region us-east-1 |
docker login --username AWS --password-stdin YOUR_ACCOUNT.dkr.ecr.us-east-1.amazonaws.com
```

Build/push each service.

## 5. Deploy

Replace `ECR_REGISTRY` and `IMAGE_TAG` in the Kubernetes/Helm values.

```bash
kubectl apply -f deploy/k8s/
kubectl -n financial-platform get pods
kubectl -n financial-platform get svc
kubectl -n financial-platform get ingress
```

## 6. Verify

```bash
kubectl -n financial-platform rollout status deployment/account-ingestion-service
kubectl -n financial-platform describe pod -l app=account-ingestion-service
kubectl -n financial-platform logs -l app=account-ingestion-service --tail=100
```

## 7. Failure testing

```bash
kubectl -n financial-platform delete pod -l app=account-ingestion-service
kubectl -n financial-platform rollout status deployment/account-ingestion-service
```

Change an image tag and demonstrate a rolling deployment and rollback:

```bash
kubectl -n financial-platform rollout history deployment/account-ingestion-service
kubectl -n financial-platform rollout undo deployment/account-ingestion-service
```

## 8. Destroy

```bash
cd infra/terraform
terraform destroy
```
