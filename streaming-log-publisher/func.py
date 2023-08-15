import io
import json
import logging
import oci
from base64 import b64encode

from py_zipkin import Encoding
from py_zipkin.zipkin import zipkin_span

import requests

from fdk import response


def handler(ctx, data: io.BytesIO = None):
    tracing_ctx = ctx.TracingContext()
    with zipkin_span(
            service_name=tracing_ctx.service_name(),
            span_name="Handle Function",
            transport_handler=(
                lambda encoded_span: transport_handler(
                    encoded_span, tracing_ctx
                )
            ),
            zipkin_attrs=tracing_ctx.zipkin_attrs(),
            encoding=Encoding.V2_JSON,
            binary_annotations=tracing_ctx.annotations()
        ):
        try:
            body = json.loads(data.getvalue())
        except (Exception, ValueError) as ex:
            logging.getLogger().info('error parsing json payload: ' + str(ex))
        config = ctx.Config()
        result = publish_message(
            tracing_ctx, body, config['stream_id'], config['message_endpoint'])
        logging.getLogger().info(result)
        return response.Response(
            ctx,
            response_data=json.dumps(result),
            headers={"Content-Type": "application/json"}
        )


def publish_message(tracing_ctx, log_content, stream_id, message_endpoint):
    with zipkin_span(
        service_name=tracing_ctx.service_name(),
        span_name="Put Messages",
        binary_annotations=tracing_ctx.annotations()
    ) as put_messages_span:
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
    value = b64encode(json.dumps(log_content).encode()).decode()
    message_entry = oci.streaming.models.PutMessagesDetailsEntry(
        key=None, value=value)
    message_list.append(message_entry)
    return oci.streaming.models.PutMessagesDetails(messages=message_list)


def transport_handler(encoded_span, tracing_context):
    return requests.post(
        tracing_context.trace_collector_url(),
        data=encoded_span,
        headers={"Content-Type": "application/json"},
    )
