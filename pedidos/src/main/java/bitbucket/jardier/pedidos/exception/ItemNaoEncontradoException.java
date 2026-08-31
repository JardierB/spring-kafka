package bitbucket.jardier.pedidos.exception;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ItemNaoEncontradoException extends RuntimeException{

    public ItemNaoEncontradoException(String mensagem) {
        super(mensagem);
    }
}
