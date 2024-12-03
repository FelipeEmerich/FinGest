package br.univille.fsoweb20242.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

import br.univille.fsoweb20242.entity.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByMes(String mes); // Método para buscar pedidos por mês
}
