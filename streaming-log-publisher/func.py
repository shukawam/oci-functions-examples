import io
import json
import logging
import oci
from base64 import b64encode

from fdk import response


def handler(ctx, data: io.BytesIO = None):
    try:
        body = json.loads(data.getvalue())
    except (Exception, ValueError) as ex:
        logging.getLogger().info('error parsing json payload: ' + str(ex))
    config = ctx.Config()
    result = publish_message(
        body, config['stream_id'], config['message_endpoint'])
    logging.getLogger().info(result)
    return response.Response(
        ctx,
        response_data=json.dumps(result),
        headers={"Content-Type": "application/json"}
    )


def publish_message(log_content, stream_id, message_endpoint):
    signer = oci.auth.signers.get_resource_principals_signer()
    stream_client = oci.streaming.StreamClient(
        config={}, service_endpoint=message_endpoint, signer=signer)
    message = create_messages_details(log_content)
    put_message_result = stream_client.put_messages(
        stream_id=stream_id, put_messages_details=message)
    entry = put_message_result.data.entries[0]
    return {"partition": entry.partition, "offset": entry.offset}


def create_messages_details(log_content):
    message_list = []
    # key = b64encode("key".encode()).decode()
    value = b64encode(json.dumps(log_content).encode()).decode()
    # message_entry = oci.streaming.models.PutMessagesDetailsEntry(
    #     key=key, value=value)
    message_entry = oci.streaming.models.PutMessagesDetailsEntry(key=None ,value=value)
    message_list.append(message_entry)
    return oci.streaming.models.PutMessagesDetails(messages=message_list)
