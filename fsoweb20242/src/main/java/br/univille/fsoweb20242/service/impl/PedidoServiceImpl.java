package br.univille.fsoweb20242.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.univille.fsoweb20242.entity.Pedido;
import br.univille.fsoweb20242.repository.PedidoRepository;
import br.univille.fsoweb20242.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository repository;

    @Override
    public List<Pedido> getAll() {
        return repository.findAll();  // Já está correto, retorna todos os pedidos
    }

    @Override
    public Pedido save(Pedido pedido) {
        return repository.save(pedido);
    }

    @Override
    public Pedido delete(long id) {
        var pedido = getById(id);
        repository.delete(pedido);
        return pedido;
    }

    @SuppressWarnings("deprecation")
    @Override
    public Pedido getById(long id) {
        return repository.getById(id);  // Essa é uma forma mais antiga de obter, mas funciona
    }

    @Override
    public List<Pedido> getByMes(String mes) {
        return repository.findByMes(mes);  // A filtragem dos pedidos pelo mês
    }

    // Implementação explícita de findAll()
    @Override
    public List<Pedido> findAll() {
        // Caso você precise de alguma lógica de manipulação ou filtro, faça aqui.
        return repository.findAll();  // Retorna todos os pedidos sem filtragem (igual ao getAll)
    }
}
