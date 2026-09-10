package bitbucket.jardier.clientes.clientes.controller;

import lombok.RequiredArgsConstructor;
import bitbucket.jardier.clientes.clientes.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import bitbucket.jardier.clientes.clientes.service.ClienteService;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService service;

    @PostMapping
    public ResponseEntity<Cliente> salvar(@RequestBody Cliente cliente) {
        service.salvar(cliente);
        return ResponseEntity.ok(cliente);
    }

    @GetMapping("{codigo}")
    public ResponseEntity<Cliente> obterDados(@PathVariable("codigo") Long codigo) {
        return service.obeterPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{codigo}")
    public ResponseEntity<Void> deletar(@PathVariable("codigo") Long codigo){
        var cliente = service.obeterPorCodigo(codigo).orElseThrow(() ->
                new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cliente inexistente"
                ));
        service.deletar(cliente);
        return ResponseEntity.noContent().build();
    }
}
