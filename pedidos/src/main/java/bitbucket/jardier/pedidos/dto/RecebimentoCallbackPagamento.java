package bitbucket.jardier.pedidos.dto;

public record RecebimentoCallbackPagamento(
        Long codigo,
        String chavePagamento,
        boolean status,
        String observacoes
) {
}
