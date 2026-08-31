package bitbucket.jardier.pedidos.exception;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@Getter
public class ValidationException extends RuntimeException{

    private final String field;
    private final String message;

}
