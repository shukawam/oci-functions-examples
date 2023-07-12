package main

import (
	"context"
	"encoding/json"
	"fmt"
	"io"

	fdk "github.com/fnproject/fdk-go"

	"github.com/oracle/oci-go-sdk/v65/common"
	"github.com/oracle/oci-go-sdk/v65/common/auth"
	"github.com/oracle/oci-go-sdk/v65/objectstorage"
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
	rp, err := auth.InstancePrincipalConfigurationProvider()
	if err != nil {
		panic(err)
	}
	objectStorageClient, err := objectstorage.NewObjectStorageClientWithConfigurationProvider(rp)
	if err != nil {
		fmt.Printf("failed to create ObjectStorageClient : %s", err)
	}

	items := listBucket(ctx, objectStorageClient, "orasejapan", "ocid1.compartment.oc1..aaaaaaaanjtbllhqxcg67dq7em3vto2mvsbc6pbgk4pw6cx37afzk3tngmoa")
	json.NewEncoder(out).Encode(&items)
}
