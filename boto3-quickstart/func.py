import io
import json
import logging
import boto3

from fdk import response

s3 = boto3.resource("s3")

def handler(ctx, data: io.BytesIO = None):
    for b in s3.buckets.all():
        print(b.name)
    return response.Response(
        ctx, response_data=json.dumps(
            {"message": "Hello world"}),
        headers={"Content-Type": "application/json"}
    )

if __name__ == "__main__":
    print()
