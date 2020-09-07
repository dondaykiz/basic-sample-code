package com.basic.boot.api.service;

import com.basic.boot.api.controller.sample.RxJavaTest2;
import com.basic.boot.api.exception.CommonException;
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.blob.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

@Service
public class BlobServiceImpl implements BlobService {
    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(BlobServiceImpl.class);

    @Override
    public String uploadBlob(String data) {
        String connString = "1DefaultEndpointsProtocol=https;AccountName=yjstorage01;AccountKey=497SIStSqXHdRD0dGM/iIqSwPz3P2WGbRjCdzBFis+t2G3NNbO04xVS+2oXV00Mpp5zCMRUxcKXOxCplvlaz6Q==;EndpointSuffix=core.windows.net";
        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient = null;
        CloudBlobContainer container=null;
        File sourceFile = null;

        try {
            storageAccount = CloudStorageAccount.parse(connString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference("yjblobtest");
            logger.debug("CREATING_BLOB: " + data + ".txt");
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());

            sourceFile = File.createTempFile(data, ".txt");
            logger.debug("CREATING_FILE: " + sourceFile.getName());
            Writer output = new BufferedWriter(new FileWriter(sourceFile));
            output.write("Hello Azure!");
            output.close();

            CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());
            logger.debug("UPLOADING_FILE: " + sourceFile.getAbsolutePath());
            blob.uploadFromFile(sourceFile.getAbsolutePath());
            logger.debug("UPLOAD_FINISHED");
            return "OK";
        } catch (Exception e) {
            throw new CommonException();
        }
    }
}
