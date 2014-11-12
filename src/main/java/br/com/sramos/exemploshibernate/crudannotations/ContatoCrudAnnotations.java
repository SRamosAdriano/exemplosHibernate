package br.com.sramos.exemploshibernate.crudannotations;

import java.sql.Date;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import br.com.sramos.exemploshibernate.conexao.HibernateUtil;
import br.com.sramos.exemploshibernate.crudxml.Contato;

public class ContatoCrudAnnotations {
	
	public void salvar(ContatoAnnotations contato){
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
	
	public void atualizar(ContatoAnnotations contato){
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
	
	public void excluir(ContatoAnnotations contato){
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
	
	public List<ContatoAnnotations> listar(){
		Session sessao = null;
		Transaction transacao = null;
		Query consulta = null;
		List<ContatoAnnotations> resultado = null;
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
		ContatoCrudAnnotations contatoCrudAnnotations = new ContatoCrudAnnotations();
		String[] nomes = {"Solanu", "Lunare", "Venusiana"};
		String[] fones = {"(11) 7575-1423", "(11) 7895-6325", "(11) 7485-9587"};
		String[] emails = {"solanu@javapro.com.br", "lunare@javapro.com.br", "venusiane@javapro.com.br"};
		String[] observacoes = {"Novo cliente", "Cliente em dia", "Ligar na sexta"};
		ContatoAnnotations contato = null;
		
		for(int i = 0 ; i < nomes.length ; i++){
			contato = new ContatoAnnotations();
			contato.setNome(nomes[i]);
			contato.setTelefone(fones[i]);
			contato.setEmail(emails[i]);
			contato.setDataCadastro(new Date(System.currentTimeMillis()));
			contato.setObservacao(observacoes[i]);
			
			contatoCrudAnnotations.salvar(contato);			
		}
		System.out.println("Total de registros cadastrados:" + contatoCrudAnnotations.listar().size());
	}
}
