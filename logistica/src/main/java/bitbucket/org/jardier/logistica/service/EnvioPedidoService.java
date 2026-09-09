package bitbucket.org.jardier.logistica.service;

import bitbucket.org.jardier.logistica.model.AtualizacaoEnvioPedido;
import bitbucket.org.jardier.logistica.model.StatusPedido;
import bitbucket.org.jardier.logistica.publisher.EnvioPedidoPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EnvioPedidoService {

    private final EnvioPedidoPublisher publisher;


    public void enviar(Long codigoPedido, String urlNotaFiscal) {
        var codigoRatreio = gerarCodigRatreio();
        var representation = AtualizacaoEnvioPedido
                .builder()
                .codigo(codigoPedido)
                .status(StatusPedido.ENVIADO)
                .rastreio(codigoRatreio)
                .build();
        publisher.enviar(representation);

    }

    private String gerarCodigRatreio() {
        //AB123456789BR
        var random = new Random();
        char letra1 = (char) ('A' + random.nextInt(26));
        char letra2 = (char) ('A' + random.nextInt(26));

        int numeros = 100000000 + random.nextInt(900000000);

        return "" + letra1 + letra2 + numeros + "BR";
    }
}
