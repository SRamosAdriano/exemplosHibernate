package br.com.sramos.exemploshibernate.conexao;

import org.hibernate.Session;

public class ConectaHibernateMySQL {
	
	public static void main(String[] args) {
		Session sessao = null;
		try {
			sessao = HibernateUtil.getSessionFactory().openSession();
			System.out.println("Conectou!");
		} finally {
			sessao.close();
		}
	}

}
