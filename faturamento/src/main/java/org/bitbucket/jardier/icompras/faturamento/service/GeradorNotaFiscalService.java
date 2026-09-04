package org.bitbucket.jardier.icompras.faturamento.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bitbucket.jardier.icompras.faturamento.bucket.BucketFile;
import org.bitbucket.jardier.icompras.faturamento.bucket.BucketService;
import org.bitbucket.jardier.icompras.faturamento.modelo.Pedido;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;

@Component
@Slf4j
@RequiredArgsConstructor
public class GeradorNotaFiscalService {

    private final NotaFsicalService service;
    private final BucketService bucketService;

    public void gerar(Pedido pedido) {
        log.info("Gerando nota fiscal para o pedido {}", pedido.codigo());
        try {
            byte[] byteArray = service.gerarNota(pedido);
            String nomeArquivo = String.format("notafical_pedido_%d.pdf", pedido.codigo());

            var file = new BucketFile(nomeArquivo, new ByteArrayInputStream(byteArray),
                    MediaType.APPLICATION_PDF, byteArray.length);

            bucketService.upload(file);
            log.info("Nota fiscal gerada com sucesso!. Número: {}", nomeArquivo);

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }
}
