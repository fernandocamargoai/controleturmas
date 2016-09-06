package br.com.ufg.controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class FiltroCadastro
 */
public class FiltroCadastroTurma implements Filter {

	/**
	 * Default constructor.
	 */
	public FiltroCadastroTurma() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {

	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		try {
			String descricao = httpRequest.getParameter("descricao").trim();
			String curso = httpRequest.getParameter("curso").trim();
			String anoStr = httpRequest.getParameter("ano");
			String semestreStr = httpRequest.getParameter("semestre");
			httpRequest.setAttribute("descricao", descricao);
			httpRequest.setAttribute("curso", curso);
			httpRequest.setAttribute("ano", anoStr);
			httpRequest.setAttribute("semestre", semestreStr);

			if (descricao.equals("")) {
				httpRequest.setAttribute("mensagem", "A turma é obrigatória!");
			} else {
				if (curso.equals("")) {
					httpRequest.setAttribute("mensagem", "O curso é obrigatório!");
				}
				else {
					if(anoStr.equals("")){
						httpRequest.setAttribute("mensagem", "O ano é obrigatório!");
					}
					else{
						if(semestreStr.equals("")){
							httpRequest.setAttribute("mensagem", "O semestre é obrigatório!");
						}
						else{
							if(descricao.length() > 3){
								httpRequest.setAttribute("mensagem", "A turma deve conter no máximo 3 caracteres!");
							}
							else{
								if(curso.length() > 30){
									httpRequest.setAttribute("mensagem", "O curso deve conter no máximo 30 caracteres!");
								}
								else{
									if(anoStr.length() != 4){
										httpRequest.setAttribute("mensagem", "O ano deve conter 4 caracteres!");
									}
									else{
										if(semestreStr.length() != 1){
											httpRequest.setAttribute("mensagem", "O semestre deve conter 1 caracter (1 ou 2)!");
										}
										else{
											try{
												Integer.parseInt(anoStr);
												try{
													int semestre = Integer.parseInt(semestreStr);
													if(semestre != 1 && semestre != 2){
														httpRequest.setAttribute("mensagem", "O semestre deve conter 1 ou 2!");
													}
												}
												catch(NumberFormatException e){
													httpRequest.setAttribute("mensagem", "O semestre deve conter apenas números!");
												}
											}
											catch(NumberFormatException e){
												httpRequest.setAttribute("mensagem", "O ano deve conter apenas números!");
											}
										}
									}
								}
							}
						}
					}
				}
			}

		} catch (NullPointerException e) {
			httpRequest.setAttribute("mensagem",
					"Não entendemos sua requisição!");
			e.printStackTrace();
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
