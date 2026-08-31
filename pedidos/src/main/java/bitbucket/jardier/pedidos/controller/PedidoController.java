package bitbucket.jardier.pedidos.controller;


import bitbucket.jardier.pedidos.dto.NovoPagamentoDTO;
import bitbucket.jardier.pedidos.dto.NovoPedidoDTO;
import bitbucket.jardier.pedidos.exception.ItemNaoEncontradoException;
import bitbucket.jardier.pedidos.exception.ValidationException;
import bitbucket.jardier.pedidos.mappers.PedidoMapper;
import bitbucket.jardier.pedidos.model.ErroResposta;
import bitbucket.jardier.pedidos.model.Pedido;
import bitbucket.jardier.pedidos.publicher.DetalhePedidoMapper;


import bitbucket.jardier.pedidos.publicher.representation.DetalheItemPedidoRepresentation;
import bitbucket.jardier.pedidos.publicher.representation.DetalhePedidoRepresentation;
import bitbucket.jardier.pedidos.service.PedidoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("pedidos")
@RequiredArgsConstructor
public class PedidoController {

    private final PedidoService service;
    private final PedidoMapper mapper;
    private final DetalhePedidoMapper detalhePedidoMapper;

    @PostMapping
    public ResponseEntity<Object> criar(@RequestBody NovoPedidoDTO dto) {
        try {
            var pedido = mapper.map(dto);
            var novoPedido = service.criarPedido(pedido);
            return ResponseEntity.ok(novoPedido.getCodigo());
        } catch (ValidationException e) {
            var erro = new ErroResposta("Erro de validação", e.getField(), e.getMessage());
            return ResponseEntity.badRequest().body(erro);
        }

    }

    @PostMapping("pagamentos")
    public ResponseEntity<Object> adicionarNovoPagamento(@RequestBody NovoPagamentoDTO dto){
        try {
            service.adicionarNovoPagamento(dto.codigoPedido(), dto.dados(), dto.tipoPagamento());
            return  ResponseEntity.noContent().build();

        } catch (ItemNaoEncontradoException e) {
            var erro = new ErroResposta("Item não encontrado", "codigoPedido", e.getMessage());
            return ResponseEntity.badRequest().body(erro);

        }
    }

    @GetMapping("{codigo}")
    public ResponseEntity<DetalhePedidoRepresentation> obterDetalhesPedido(@PathVariable Long codigo) {
        return service.carragarDadosCompletoPedido(codigo)
                .map(detalhePedidoMapper::map)
        .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
