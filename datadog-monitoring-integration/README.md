# Datadog Monitoring Integration

## How to use

### oci functions mode

set config(func.yaml)

```yaml
schema_version: 20180708
name: datadog-monitoring-integration
version: 0.0.1
runtime: python
build_image: fnproject/python:3.9-dev
run_image: fnproject/python:3.9
entrypoint: /python/bin/fdk /function/func.py handler
memory: 256
config:
  DATADOG_METRICS_API_ENDPOINT: <your-datadog-metrics-api-endpoint>
  DATADOG_API_KEY: <your-datadog-api-key>
  # Optional - default value
  FORWARD_TO_DATADOG: True
  METRICS_TAG_KEYS: name, namespace, displayName, resourceDisplayName, unit
  LOGGING_LEVEL: DEBUG
  ENABLE_TRACING: False
```

deploy function

```bash
export APP_NAME=<your-application-name>
fn deploy --app $APP_NAME
```

### local debug mode

```bash
export DATADOG_METRICS_API_ENDPOINT=<your-datadog-metrics-api-endpoint>
export DATADOG_API_KEY=<your-datadog-api-key>

python3 func.py
```
