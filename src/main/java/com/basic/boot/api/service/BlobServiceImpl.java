package com.basic.boot.api.service;

import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@Service
public class BlobServiceImpl implements BlobService {
    @Override
    public void uploadBlob() {
        String accountName = "";
        String accountKey = "";
        StorageSharedKeyCredential credential = new StorageSharedKeyCredential(accountName, accountKey);
        String endpoint = "";

        BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(credential).buildClient();
        BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient("myjavacontainerbasic" + System.currentTimeMillis());
        blobContainerClient.create();
        BlockBlobClient blobClient = blobContainerClient.getBlobClient("HelloWorld.txt").getBlockBlobClient();

        try {
            String data = "Hello world!";
            InputStream dataStream = new ByteArrayInputStream(data.getBytes(StandardCharsets.UTF_8));
            blobClient.upload(dataStream, data.length());
            dataStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
