# list-objects-in-specific-bucket

## set up

バケットを2つ作ります。

```bash
C=<compartment-ocid>
NAMESPACE=<namespace>
ALLOW_BUCKET_NAME=test-bucket-allow
DENY_BUCKET_NAME=test-bucket-deny
```

```bash
oci os bucket create \
    --compartment-id $C \
    --name $ALLOW_BUCKET_NAME \
    --namespace-name $NAMESPACE
```

```bash
oci os bucket create \
    --compartment-id $C \
    --name $DENY_BUCKET_NAME \
    --namespace-name $NAMESPACE
```

## build & deploy

```bash
APP_NAME=<app-name>
```

```bash
fn deploy --app $APP_NAME --no-bump
```

## exec

```bash
echo -n '{"namespace": "<namespace>", "bucketName": "<bucketName>"}' | fn invoke sandbox list-objects-in-specific-bucket
```

```bash
[{"name":"dummy","size":null,"md5":null,"timeCreated":null,"etag":null,"timeModified":null}]
```
