package org.bitbucket.jardier.icompras.faturamento.bucket;

import io.minio.*;
import io.minio.http.Method;
import lombok.RequiredArgsConstructor;
import org.bitbucket.jardier.icompras.faturamento.config.props.MinioProps;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class BucketService {

    private final MinioClient client;
    private final MinioProps props;

    public void upload(BucketFile file) {
        try {
            var object = PutObjectArgs.builder()
                    .bucket(props.getBucketName())
                    .object(file.nome())
                    .stream(file.is(), file.size(), -1)
                    .contentType(file.type().toString())
                    .build();
            client.putObject(object);

        } catch (Exception e) {
            throw new RuntimeException(e);

        }

    }

    public String getUrl(String fileName){
        try {
            var object = GetPresignedObjectUrlArgs
                    .builder()
                    .method(Method.GET)
                    .bucket(props.getBucketName())
                    .object(fileName)
                    .expiry(7, TimeUnit.DAYS)
                    .build();
            return client.getPresignedObjectUrl(object);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean removerBucket(String bucketName) {
        try{
            var object = BucketExistsArgs
                    .builder()
                    .bucket(bucketName)
                    .build();

            boolean exists = client.bucketExists(object);
            if (exists){

               client.removeBucket(RemoveBucketArgs
                       .builder()
                       .bucket(bucketName)
                       .build());
               return true;
            }else {
                return false;
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
