import io
import json
import logging
import requests
import os

from fdk import response

def handler(ctx, data: io.BytesIO = None):
    try:
        logs = json.loads(data.getvalue())
        opensearch_api_endpoint = os.environ['OPENSEARCH_API_ENDPOINT']
        headers = {'Content-Type': 'application/json'}
        for item in logs:
            url = opensearch_api_endpoint + "/oci/_doc/" + item['id']
            requests.post(url=url, data=json.dumps(item),
                          headers=headers, verify=False)
    except (Exception, ValueError) as ex:
        logging.getLogger().info('error parsing json payload: ' + str(ex))

    return response.Response(
        ctx, response_data=json.dumps({"message": "ok"}),
        headers={"Content-Type": "application/json"}
    )
