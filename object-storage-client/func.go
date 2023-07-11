package main

import (
	"context"
	"encoding/json"
	"io"

	fdk "github.com/fnproject/fdk-go"

	"github.com/oracle/oci-go-sdk/v49/common"
	"github.com/oracle/oci-go-sdk/v49/common/auth"
	"github.com/oracle/oci-go-sdk/v49/example/helpers"
	"github.com/oracle/oci-go-sdk/v49/objectstorage"
)

func main() {
	fdk.Handle(fdk.HandlerFunc(myHandler))
}

func listBucket(ctx context.Context, client objectstorage.ObjectStorageClient, namespace string, compartmentId string) []objectstorage.BucketSummary {
	listBucketRequest := objectstorage.ListBucketsRequest{
		NamespaceName: common.String(namespace),
		CompartmentId: common.String(compartmentId),
	}
	response, err := client.ListBuckets(ctx, listBucketRequest)
	if err != nil {
		panic(err)
	}
	return response.Items
}

func myHandler(ctx context.Context, in io.Reader, out io.Writer) {
	provider, err := auth.ResourcePrincipalConfigurationProvider()
	helpers.FatalIfError(err)

	client, client_err := objectstorage.NewObjectStorageClientWithConfigurationProvider(provider)
	helpers.FatalIfError(client_err)

	items := listBucket(ctx, client, "orasejapan", "ocid1.compartment.oc1..aaaaaaaanjtbllhqxcg67dq7em3vto2mvsbc6pbgk4pw6cx37afzk3tngmoa")
	json.NewEncoder(out).Encode(&items)
}
