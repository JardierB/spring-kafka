package org.bitbucket.jardier.icompras.faturamento.bucket;

import org.springframework.http.MediaType;

import java.io.InputStream;

public record BucketFile(String nome,
                         InputStream is,
                         MediaType type,
                         long size) {
}
