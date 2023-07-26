package com.sistema.senac.controle;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.sistema.senac.modelo.Cliente;
import com.sistema.senac.repositorio.ClienteRepositorio;
import com.sistema.senac.repositorio.CidadeRepositorio;

@Controller
public class FornecedorControle {
	
	@Autowired
	private ClienteRepositorio fornecedorRepositorio;
	@Autowired
	private CidadeRepositorio cidadeRepositorio;
	
	@GetMapping("/cadastroCliente")
	public ModelAndView cadastrar(Cliente fornecedor) { 
		ModelAndView mv = new ModelAndView("administrativo/fornecedores/cadastro");
		mv.addObject("listaCidades", cidadeRepositorio.findAll());
		mv.addObject("fornecedor",fornecedor);
		return mv;
		
	}
	
	
	@PostMapping("/salvarCliente")
	public ModelAndView salvar(Cliente fornecedor, BindingResult result) { 
		if(result.hasErrors()) {
			return cadastrar(fornecedor);
	}
		fornecedorRepositorio.saveAndFlush(fornecedor);
		return cadastrar(new Cliente());
	}
	
	@GetMapping("/listarCliente")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView("administrativo/fornecedores/lista");
		mv.addObject("listaClientes", fornecedorRepositorio.findAll());
		return mv;	
	}
	
	@GetMapping("/editarCliente/{id}")
	public ModelAndView editar(@PathVariable("id") Long id) {
		Optional<Cliente> fornecedor = fornecedorRepositorio.findById(id);  //busca fornecedor por id e armazena
		return cadastrar(fornecedor.get());		   //chama função cadastrar e carrega valores nela
	}
	
	@GetMapping("/removerCliente/{id}")
	public ModelAndView remover(@PathVariable("id") Long id) {
		Optional<Cliente> fornecedor = fornecedorRepositorio.findById(id);
		fornecedorRepositorio.delete(fornecedor.get());
		return listar();
	}

}
