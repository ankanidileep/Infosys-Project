#!/usr/bin/env bash
set -euo pipefail
kubectl apply -f deploy/k8s/00-namespace.yaml
kubectl apply -f deploy/k8s/01-config.yaml
kubectl apply -f deploy/k8s/services/
kubectl apply -f deploy/k8s/90-ingress.yaml
kubectl apply -f deploy/k8s/95-network-policy.yaml
kubectl -n financial-platform rollout status deployment/account-ingestion-service --timeout=180s
kubectl -n financial-platform get pods,svc,ingress,hpa
