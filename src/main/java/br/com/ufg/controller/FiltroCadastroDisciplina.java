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
public class FiltroCadastroDisciplina implements Filter {

	/**
	 * Default constructor.
	 */
	public FiltroCadastroDisciplina() {
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
			String nome = httpRequest.getParameter("nome").trim();
			String cargaHorariaStr = httpRequest.getParameter("cargaHoraria");
			httpRequest.setAttribute("nome", nome);
			httpRequest.setAttribute("cargaHoraria", cargaHorariaStr);

			if (nome.equals("")) {
				httpRequest.setAttribute("mensagem", "O nome é obrigatório!");
			} else {
				if (cargaHorariaStr.equals("")) {
					httpRequest.setAttribute("mensagem", "A carga horária é obrigatória!");
				}
				else {
					if (nome.length() > 100) {
						httpRequest.setAttribute("mensagem", "O nome deve ter no máximo 50 caracteres!");
					}
					else {
						try{
							Integer.parseInt(cargaHorariaStr);
						}
						catch(NumberFormatException e){
							httpRequest.setAttribute("mensagem", "A carga horária deve conter apenas números!");
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
