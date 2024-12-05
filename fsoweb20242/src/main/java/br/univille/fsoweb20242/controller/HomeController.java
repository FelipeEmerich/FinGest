package br.univille.fsoweb20242.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.univille.fsoweb20242.service.PedidoService;
import br.univille.fsoweb20242.entity.ItemPedido;
import br.univille.fsoweb20242.entity.Pedido;

@Controller
public class HomeController {

    @Autowired
    private PedidoService pedidoService;

    // Página inicial (home/home.html)
    @GetMapping("/")
    public String home() {
        return "home/home";
    }

    // Página de balanço (home/index.html)
    @GetMapping("/balanco")
    public ModelAndView index(@RequestParam(required = false) String mes) {
        ModelAndView mv = new ModelAndView("home/index");

        // Buscar pedidos filtrados por mês
        List<Pedido> listaPedidos = (mes != null && !mes.isEmpty()) ? pedidoService.getByMes(mes) : pedidoService.getAll();

        double salario = 0;
        double gastos = 0;

        for (Pedido pedido : listaPedidos) {
            salario += pedido.getSalario(); // Salário do mês
            for (ItemPedido item : pedido.getItens()) {
                gastos += item.getValor(); // Total de gastos do pedido
            }
        }

        double balanco = salario - gastos; // Balanço: Salário - Gastos

        mv.addObject("salario", salario);
        mv.addObject("gastos", gastos);
        mv.addObject("balanco", balanco);
        mv.addObject("listaPedidos", listaPedidos);

        return mv;
    }
}
