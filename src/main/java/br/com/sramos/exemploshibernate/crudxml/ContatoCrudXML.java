package br.com.sramos.exemploshibernate.crudxml;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.sramos.exemploshibernate.conexao.HibernateUtil;

public class ContatoCrudXML {
	
	public void salvar(Contato contato){
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.save(contato);
			transacao.commit();			
		} catch (HibernateException e) {
			System.out.println("Não foi possivel inserir o contato. Erro: "+ e.getMessage());
		}finally{
			try {
				sessao.close();
			} catch (Throwable t) {
				System.out.println("Erro ao fechar operação de inserção. Mensagem: "+ t.getMessage());
			}
		}
	}
	
	public void atualizar(Contato contato){
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.update(contato);
			transacao.commit();			
		} catch (HibernateException e) {
			System.out.println("Não foi possivel alterar o contato. Erro: "+ e.getMessage());
		}finally{
			try {
				sessao.close();
			} catch (Throwable t) {
				System.out.println("Erro ao fechar operação de atualização. Mensagem: "+ t.getMessage());
			}
		}
	}
	
	public void excluir(Contato contato){
		Session sessao = null;
		Transaction transacao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			sessao.delete(contato);
			transacao.commit();			
		} catch (HibernateException e) {
			System.out.println("Não foi possivel excluir o contato. Erro: "+ e.getMessage());
		}finally{
			try {
				sessao.close();
			} catch (Throwable t) {
				System.out.println("Erro ao fechar operação de exclusão. Mensagem: "+ t.getMessage());
			}
		}
	}
	
	public List<Contato> listar(){
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<Contato> resultado = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato");
			resultado = consulta.list();
			transacao.commit();
			return resultado;
		} catch (HibernateException e) {
			System.out.println("Não foi possivel selecionar os contatos. Erro: "+ e.getMessage());
		}finally{
			try {
				sessao.close();
			} catch (Throwable t) {
				System.out.println("Erro ao fechar operação de consulta. Mensagem: "+ t.getMessage());
			}
		}
		return resultado;
	}
	
	public Contato buscaContato(int valor){
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		Contato contato = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			transacao = sessao.beginTransaction();
			consulta = sessao.createQuery("from Contato where codigo = :parametro");
			consulta.setInteger("parametro", valor);			
			contato = (Contato) consulta.uniqueResult();
			transacao.commit();			
			return contato;
		} catch (HibernateException e) {
			System.out.println("Não foi possivel buscar contato. Erro: "+ e.getMessage());
		}finally{
			try {
				sessao.close();
			} catch (Throwable t) {
				System.out.println("Erro ao fechar operação de busca. Mensagem: "+ t.getMessage());
			}
		}
		return contato;
	}

	
	public static void main(String[] args) {
		ContatoCrudXML contatoCrudXML = new ContatoCrudXML();
		String[] nomes = {"Ciclano", "Adriano", "Carol"};
		String[] fones = {"(11) 4455-1122", "(11) 7788-9966", "(11) 4477-8855"};
		String[] emails = {"ciclano@teste.com.br", "adriano@teste.com.br", "carol@teste.com.br"};
		String[] observacoes = {"Novo cliente", "Cliente em dia", "Ligar na quinta"};
		Contato contato = null;
		
		for(int i = 0 ; i < nomes.length ; i++){
			contato = new Contato();
			contato.setNome(nomes[i]);
			contato.setTelefone(fones[i]);
			contato.setEmail(emails[i]);
			contato.setDataCadastro(new Date(System.currentTimeMillis()));
			contato.setObservacao(observacoes[i]);
			
			contatoCrudXML.salvar(contato);			
		}
		System.out.println("Total de registros cadastrados:" + contatoCrudXML.listar().size());
		
		System.out.println(" ");
		
		System.out.println("Busca Todos Contatos");
		for(Contato c : contatoCrudXML.listar()){
			DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
			System.out.println("codigo: "+ c.getCodigo());
			System.out.println("nome: "+ c.getNome());
			System.out.println("telefone: "+ c.getTelefone());
			System.out.println("email: "+ c.getEmail());
			System.out.println("dataCadastro: "+ df.format(c.getDataCadastro()));
			System.out.println("observacao: "+ c.getObservacao());
		}
		
		
		
	}

}
