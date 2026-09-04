package org.bitbucket.jardier.icompras.faturamento.service;

import lombok.extern.slf4j.Slf4j;
import org.bitbucket.jardier.icompras.faturamento.modelo.Pedido;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class GeradorNotaFiscalService {

    public void gerar(Pedido pedido) {
        log.info("Gerada nota fical para o pedido {}", pedido.codigo());
    }
}
