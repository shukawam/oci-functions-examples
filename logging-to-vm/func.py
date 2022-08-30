import io
import paramiko
import oci
import base64

from fdk import response


def handler(ctx, data: io.BytesIO = None):
    get_binary_secret_into_file(
        'ocid1.vaultsecret.oc1.ap-tokyo-1.amaaaaaassl65iqahrdwgnmuiwjr23rigrxfzsihozfwwa7ibieyv2fcnokq',
        '/tmp/id_rsa'
    )

    with paramiko.SSHClient() as ssh:
        ssh.set_missing_host_key_policy(paramiko.AutoAddPolicy())

    ssh.connect(
        '',
        port=22,
        username='opc',
        key_filename='/tmp/id_rsa'
    )

    ssh.exec_command('touch /tmp/2022-0830.log')

    return 'ok'

def get_binary_secret_into_file(secret_ocid, filepath):
    signer = oci.auth.signers.get_resource_principals_signer()
    try:
        client = oci.secrets.SecretsClient({}, signer=signer)
        secret_content = client.get_secret_bundle(secret_ocid).data.secret_bundle_content.content.encode('utf-8')
    except Exception as ex:
        print("ERROR: failed to retrieve the secret content", ex, flush=True)
        raise
    try:
        with open(filepath, 'wb') as secretfile:
            decrypted_secret_content = base64.decodebytes(secret_content)
            print(decrypted_secret_content)
            secretfile.write(decrypted_secret_content)
    except Exception as ex:
        print("ERROR: cannot write to file " + filepath, ex, flush=True)
        raise