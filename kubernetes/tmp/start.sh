#!/bin/bash

kubectl port-forward svc/backend-service   30001:1000 &
kubectl port-forward svc/front-service   30002:1001 