package br.univille.fsoweb20242.service;

import java.util.List;
import br.univille.fsoweb20242.entity.Pedido;

public interface PedidoService {
    List<Pedido> getAll();
    Pedido save(Pedido Pedido);
    Pedido delete(long id);
    Pedido getById(long id);
    List<Pedido> getByMes(String mes);  // Atualizado para retornar List<Pedido>
    List<Pedido> findAll();  // Já está correto
}
