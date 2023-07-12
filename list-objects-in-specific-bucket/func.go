package main

import (
	"context"
	"encoding/json"
	"io"

	fdk "github.com/fnproject/fdk-go"

	"github.com/oracle/oci-go-sdk/v65/common"
	"github.com/oracle/oci-go-sdk/v65/common/auth"
	"github.com/oracle/oci-go-sdk/v65/objectstorage"
)

func main() {
	fdk.Handle(fdk.HandlerFunc(myHandler))
}

type Object struct {
	Namespace  string `json:"namespace"`
	BucketName string `json:"bucketName"`
}

func listObject(ctx context.Context, client objectstorage.ObjectStorageClient, namespaceName string, bucketName string) []objectstorage.ObjectSummary {
	listObjectsRequest := objectstorage.ListObjectsRequest{
		NamespaceName: common.String(namespaceName),
		BucketName:    common.String(bucketName),
	}
	response, err := client.ListObjects(ctx, listObjectsRequest)
	if err != nil {
		panic(err)
	}
	return response.Objects
}

func myHandler(ctx context.Context, in io.Reader, out io.Writer) {
	o := &Object{Namespace: "orasejapan", BucketName: "test-bucket-allow"}
	json.NewDecoder(in).Decode(o)

	// if you exec this program in oci functions.
	auth, err := auth.ResourcePrincipalConfigurationProvider()

	// if you exec this program in vm(oci compute).
	// auth, err := auth.InstancePrincipalConfigurationProvider()
	if err != nil {
		panic(err)
	}
	client, err := objectstorage.NewObjectStorageClientWithConfigurationProvider(auth)
	if err != nil {
		panic(err)
	}
	result := listObject(ctx, client, o.Namespace, o.BucketName)

	json.NewEncoder(out).Encode(&result)
}
