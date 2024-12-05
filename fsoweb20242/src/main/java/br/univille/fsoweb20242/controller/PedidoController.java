package br.univille.fsoweb20242.controller;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.univille.fsoweb20242.entity.ItemPedido;
import br.univille.fsoweb20242.entity.Pedido;
import br.univille.fsoweb20242.service.PedidoService;
import br.univille.fsoweb20242.service.ProdutoService;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ModelAndView index(@RequestParam(value = "mes", required = false) String mes){
        // Se um mês for selecionado, filtra os pedidos desse mês, senão retorna todos
        var listaPedidos = (mes != null && !mes.isEmpty()) ? service.getByMes(mes) : service.getAll();
        return new ModelAndView("pedido/index", "listaPedidos", listaPedidos);
    }

    @GetMapping("/novo")
    public ModelAndView novo(){
        var pedido = new Pedido();
        pedido.setMes("Dezembro"); // Definir o mês padrão, pode ser alterado no formulário

        var listaProdutos = produtoService.getAll();

        HashMap<String,Object> dados = new HashMap<>();
        dados.put("pedido", pedido);
        dados.put("novoItem", new ItemPedido());
        dados.put("listaProdutos", listaProdutos);
        
        return new ModelAndView("pedido/form", dados);
    }

    @PostMapping(params = "incitem")
    public ModelAndView incluirItem(Pedido pedido, ItemPedido novoItem){
        pedido.getItens().add(novoItem);

        var listaProdutos = produtoService.getAll();
        HashMap<String,Object> dados = new HashMap<>();
        dados.put("pedido", pedido);
        dados.put("novoItem", new ItemPedido());
        dados.put("listaProdutos", listaProdutos);

        return new ModelAndView("pedido/form", dados);
    }

    @PostMapping(params = "save")
    public ModelAndView save(Pedido pedido, @RequestParam("salario") double salario){
        // Atribuindo o salário no pedido
        pedido.setSalario(salario); 

        // Validação do mês
        if (pedido.getMes() == null || pedido.getMes().isEmpty()) {
            pedido.setMes("Dezembro");
        }

        // Salva o pedido
        service.save(pedido);
        return new ModelAndView("redirect:/pedidos?mes=" + pedido.getMes()); // Redireciona com o mês selecionado
    }

    @GetMapping("/alterar/{id}")
    public ModelAndView alterar(@PathVariable("id") long id){
        var pedido = service.getById(id);
        var listaProdutos = produtoService.getAll();

        HashMap<String,Object> dados = new HashMap<>();
        dados.put("pedido", pedido);
        dados.put("novoItem", new ItemPedido());
        dados.put("listaProdutos", listaProdutos);
        
        return new ModelAndView("pedido/form", dados);
    }

    @PostMapping(params = "removeitem")
    public ModelAndView removerItem(@RequestParam("removeitem") int itemIndex, Pedido pedido){
        // Remover o item com base no índice
        if (itemIndex >= 0 && itemIndex < pedido.getItens().size()) {
            pedido.getItens().remove(itemIndex); // Remover o item pela posição na lista
        }

        var listaProdutos = produtoService.getAll();
        HashMap<String,Object> dados = new HashMap<>();
        dados.put("pedido", pedido);
        dados.put("novoItem", new ItemPedido());
        dados.put("listaProdutos", listaProdutos);

        return new ModelAndView("pedido/form", dados);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView delete(@PathVariable("id") long id){
        var pedido = service.getById(id);
        if(pedido != null){
            service.delete(id); // Excluir o pedido por ID
        }
        return new ModelAndView("redirect:/pedidos");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ModelAndView handle404Exception(AccessDeniedException ex) {
        return new ModelAndView("erro/400"); // Página de erro personalizada
    }
}
