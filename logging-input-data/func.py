import io
import json
import logging
import oci

from fdk import response


def delete_public_bucket():
    provider = oci.auth.signers.resource_principals_signer
    object_storage_client = oci.object_storage.ObjectStorageClient(provider)


def handler(ctx, data: io.BytesIO = None):
    name = "World"
    try:
        body = json.loads(data.getvalue())
        print(body)
    except (Exception, ValueError) as ex:
        logging.getLogger().info('error parsing json payload: ' + str(ex))

    logging.getLogger().info("Inside Python Hello World function")
    return response.Response(
        ctx, response_data=json.dumps(
            {"message": "Hello {0}".format(name)}),
        headers={"Content-Type": "application/json"}
    )
