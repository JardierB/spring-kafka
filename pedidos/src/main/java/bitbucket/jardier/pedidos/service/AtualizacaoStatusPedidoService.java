package bitbucket.jardier.pedidos.service;

import bitbucket.jardier.pedidos.enums.StatusPedido;
import bitbucket.jardier.pedidos.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AtualizacaoStatusPedidoService {

    private final PedidoRepository repository;


    @Transactional
    public void atualizarStatus(Long codigo, StatusPedido statusPedido,
                                String urlNotaFiscal, String rastreio) {

        repository.findById(codigo).ifPresent(pedido -> {
            pedido.setStatus(statusPedido);
            pedido.setUrlNf(urlNotaFiscal);
            pedido.setCodigoRastreio(rastreio);
        });



    }

}
