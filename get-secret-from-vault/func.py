import io
import json
import logging
import os
import base64

from fdk import response
from oci.config import from_file
from oci.secrets.secrets_client import SecretsClient
from oci.auth.signers import get_resource_principals_signer

logger = logging.getLogger("get-secret-from-vault")
secret_ocid = os.getenv("SECRET_OCID")
if "2.2".__eq__(os.getenv("OCI_RESOURCE_PRINCIPAL_VERSION", "")):
    logger.info("Use Resource principals")
    signer = get_resource_principals_signer()
    secret_client = SecretsClient(config={}, signer=signer)
else:
    logger.info("Use Config file")
    config = from_file()
    secret_client = SecretsClient(config=config)

def get_secret_from_vault(secret_ocid: str) -> str:
    """get_secret_from_vault
    Get secret from vault and decode content.
    """
    response = secret_client.get_secret_bundle(secret_id=secret_ocid)
    if response.status != 200:
        raise Exception("Something went wrong.")
    decoded_content = base64.b64decode(response.data.secret_bundle_content.content).decode(encoding="utf-8")
    logger.info(decoded_content)
    return decoded_content
    
def handler(ctx, data: io.BytesIO = None):
    try:
        body = json.loads(data.getvalue())
        secret_ocid = body.get("secret_ocid")
        decoded_content = get_secret_from_vault(secret_ocid=secret_ocid)
    except (Exception, ValueError) as ex:
        logger.info('error parsing json payload: ' + str(ex))
    logger.info("Inside get-secret-from-vault")
    return response.Response(
        ctx, response_data=json.dumps(
            {"decoded_content": decoded_content}),
        headers={"Content-Type": "application/json"}
    )

if __name__ == "__main__":
    get_secret_from_vault("ocid1.vaultsecret.oc1.ap-tokyo-1.amaaaaaassl65iqat7sraxpg4siv6obvfdujy4kjbmqeoy5i67dtfclbrweq")
