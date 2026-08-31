package bitbucket.jardier.pedidos.service;

import bitbucket.jardier.pedidos.client.ClientesClient;
import bitbucket.jardier.pedidos.client.ProdutosClient;
import bitbucket.jardier.pedidos.client.ServicoBancarioClient;
import bitbucket.jardier.pedidos.client.representation.ClienteRepresentation;
import bitbucket.jardier.pedidos.client.representation.ProdutoRepresentation;
import bitbucket.jardier.pedidos.enums.StatusPedido;
import bitbucket.jardier.pedidos.enums.TipoPagamento;
import bitbucket.jardier.pedidos.exception.ItemNaoEncontradoException;
import bitbucket.jardier.pedidos.model.DadosPagamento;
import bitbucket.jardier.pedidos.model.ItemPedido;
import bitbucket.jardier.pedidos.model.Pedido;
import bitbucket.jardier.pedidos.publicher.PagamentoPublisher;
import bitbucket.jardier.pedidos.repository.ItemPedidoRepository;
import bitbucket.jardier.pedidos.repository.PedidoRepository;
import bitbucket.jardier.pedidos.validator.PedidoValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ItemPedidoRepository itemPedidoRepository;
    private final PedidoValidator validator;
    private final ServicoBancarioClient servicoBancarioCleint;
    private final ClientesClient apiCliente;
    private final ProdutosClient apiProduto;
    private final PagamentoPublisher pagamentoPublisher;

    @Transactional
    public Pedido criarPedido(Pedido pedido) {
        validator.validar(pedido);
        realizarPersistencia(pedido);
        enviarSolicitacaoPagamento(pedido);

        return pedido;
    }

    private void enviarSolicitacaoPagamento(Pedido pedido) {
        var chavePagamento = servicoBancarioCleint.solcitarPagamento(pedido);
        pedido.setChavePagamento(chavePagamento);
    }

    private void realizarPersistencia(Pedido pedido) {
        pedidoRepository.save(pedido);
        itemPedidoRepository.saveAll(pedido.getItens());
    }

    @Transactional
    public void atualizarStatusPagamento(Long codigoPedido, String chavePagamento, boolean sucesso, String observacoes) {

        var pedidoEncontrado = pedidoRepository.findByCodigoAndChavePagamento(codigoPedido, chavePagamento);

        if(pedidoEncontrado.isEmpty()) {
            var msm = String.format("Pedido não encontrado para o código %d e chave pagaemnto %s",
                    codigoPedido, chavePagamento);
            log.error(msm);
            return;
        }

        Pedido pedido = pedidoEncontrado.get();
        if(sucesso) {
            preparaEPublicarPedidoPago(pedido);

        } else{
            pedido.setStatus(StatusPedido.ERRO_PAGAMENTO);
            pedido.setObservacoes(observacoes);
        }
        pedidoRepository.save(pedido);

    }

    private void preparaEPublicarPedidoPago(Pedido pedido) {
        pedido.setStatus(StatusPedido.PAGO);

        carregarDadosCliente(pedido);
        carregarItensPedido(pedido);
        pagamentoPublisher.publicar(pedido);
    }

    @Transactional
    public void adicionarNovoPagamento(Long codigoPedido, String dadosCartao, TipoPagamento tipoPagamento) {
        var pedidoEncontradp = pedidoRepository.findById(codigoPedido);

        if(pedidoEncontradp.isEmpty()) {
            throw new ItemNaoEncontradoException("Peiddo não encotrado para o código informado.");
        }

        var pedido = pedidoEncontradp.get();
        DadosPagamento dadosPagamento = new DadosPagamento();
        dadosPagamento.setTipoPagamento(tipoPagamento);
        dadosPagamento.setDados(dadosCartao);

        pedido.setDadosPagamento(dadosPagamento);
        pedido.setStatus(StatusPedido.REALIZADO);
        pedido.setObservacoes("Novo pagamento realizado, aguardando o processo");

        String novaChavePagamento = servicoBancarioCleint.solcitarPagamento(pedido);
        pedido.setChavePagamento(novaChavePagamento);
        pedidoRepository.save(pedido);

    }

    public Optional<Pedido> carragarDadosCompletoPedido(Long codigo) {
        Optional<Pedido> pedido = pedidoRepository.findById(codigo);
        pedido.ifPresent(this::carregarDadosCliente);
        pedido.ifPresent(this::carregarItensPedido);

        return pedido;
    }

    private void carregarDadosCliente(Pedido pedido) {
        var response = apiCliente.obterDados(pedido.getCodigoCliente());
        pedido.setDadosCliente(response.getBody());
    }

    private void carregarItensPedido(Pedido pedido) {
        List<ItemPedido> itens = itemPedidoRepository.findByPedido(pedido);
        pedido.setItens(itens);
        itens.forEach(this::carregarDadosProduto);

    }
    private void carregarDadosProduto(ItemPedido item) {
       var response = apiProduto.obterDados(item.getCodigoProduto());
       item.setNome(response.getBody().nome());

    }
}
