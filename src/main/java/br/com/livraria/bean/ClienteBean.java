package br.com.livraria.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;

import br.com.livraria.DAO.ClienteDAO;
import br.com.livraria.DAO.entity.Cliente;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class ClienteBean implements Serializable {

	private Cliente cliente;

	private List<Cliente> clientes;

	@PostConstruct
	public void listar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clientes = clienteDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os clientes");
			erro.printStackTrace();
		}
	}

	public void novo() {
		try {
			cliente = new Cliente();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar criar um novo cliente");
			erro.printStackTrace();
		}
	}

	public void salvar() {
		try {
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.merge(cliente);
			cliente = new Cliente();
			clientes = clienteDAO.listar();
			clientes = clienteDAO.listar();
			Messages.addGlobalInfo("Cliente salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o cliente");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
			ClienteDAO clienteDAO = new ClienteDAO();
			clienteDAO.excluir(cliente);
			clientes = clienteDAO.listar();
			Messages.addGlobalInfo("Cliente removido com sucesso");
		} catch (RuntimeException erro) {
			Messages.addFlashGlobalError("Ocorreu um erro ao tentar remover o Cliente");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		cliente = (Cliente) evento.getComponent().getAttributes().get("clienteSelecionado");
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

}