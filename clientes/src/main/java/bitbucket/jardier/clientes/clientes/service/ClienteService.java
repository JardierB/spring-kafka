package bitbucket.jardier.clientes.clientes.service;

import lombok.RequiredArgsConstructor;
import bitbucket.jardier.clientes.clientes.model.Cliente;
import org.springframework.stereotype.Service;
import bitbucket.jardier.clientes.clientes.repository.ClienteRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository repository;

    public Cliente salvar(Cliente cliente) {
        return repository.save(cliente);
    }

    public Optional<Cliente> obeterPorCodigo(Long codigo) {
        return repository.findById(codigo);
    }
}
