import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.OperationContext;
import com.microsoft.azure.storage.blob.*;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.ArrayList;
import java.util.List;

public class RxJavaTest {

    /**
     * Logger.
     */
    private static final Logger logger = LoggerFactory.getLogger(RxJavaTest.class);

    @Test
    public void rxJavaJust() {
        logger.debug("RXJAVA_JUST_STARTED");
        Observable.just("apple", "banana", "grape", "orange", "tomato")
                .subscribe(logger::debug);
    }

    @Test
    public void rxJavaArray() {
        logger.debug("RXJAVA_ARRAY_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .subscribe(logger::debug);
    }

    @Test
    public void rxJavaIterator() {
        logger.debug("RXJAVA_ITERABLE_STARTED");
        List<String> list = new ArrayList<>();
        list.add("apple");
        list.add("banana");
        list.add("grape");
        list.add("orange");
        list.add("tomato");

        Observable.fromIterable(list)
                .subscribe(logger::debug);
    }

    @Test
    public void rxJavaFlatMap() {
        logger.debug("RXJAVA_FLATMAP_STARTED");
        String[] array = {"apple", "banana", "grape", "orange", "tomato"};
        Observable.fromArray(array)
                .flatMap(data ->
                        Observable.fromCallable(() -> formatString(data))
                                .doOnNext(nextData -> logger.debug("DATA : {}, Thread: {}", nextData, Thread.currentThread().getName()))
                                .subscribeOn(Schedulers.trampoline())
                )
                .subscribe();

        logger.debug("RXJAVA_FLATMAP_FINISHED");
    }

    public String formatString(String data) {
        try {
            Thread.sleep(3000);
            return data + " + @";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    @Test
    public void uploadBlob() {
        logger.debug("BLOB_UPLOAD_STARTED");
        String connString = "DefaultEndpointsProtocol=https;AccountName=yjstorage01;AccountKey=497SIStSqXHdRD0dGM/iIqSwPz3P2WGbRjCdzBFis+t2G3NNbO04xVS+2oXV00Mpp5zCMRUxcKXOxCplvlaz6Q==;EndpointSuffix=core.windows.net";
        CloudStorageAccount storageAccount;
        CloudBlobClient blobClient = null;
        CloudBlobContainer container=null;
        File sourceFile = null;

        try {
            storageAccount = CloudStorageAccount.parse(connString);
            blobClient = storageAccount.createCloudBlobClient();
            container = blobClient.getContainerReference("yjblobtest");
            container.createIfNotExists(BlobContainerPublicAccessType.CONTAINER, new BlobRequestOptions(), new OperationContext());

            sourceFile = File.createTempFile("sampleFile", ".txt");
            Writer output = new BufferedWriter(new FileWriter(sourceFile));
            output.write("Hello Azure!");
            output.close();

            CloudBlockBlob blob = container.getBlockBlobReference(sourceFile.getName());
            blob.uploadFromFile(sourceFile.getAbsolutePath());

            logger.debug("BLOB_UPLOAD_FINISHED");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
