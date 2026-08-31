package bitbucket.jardier.pedidos.model;

import lombok.Getter;

public record ErroResposta(String mensagem, String field, String erros) {
}
