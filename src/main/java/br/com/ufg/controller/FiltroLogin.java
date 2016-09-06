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
 * Servlet Filter implementation class FiltroLogin
 */
public class FiltroLogin implements Filter {

    /**
     * Default constructor. 
     */
    public FiltroLogin() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		
		try {
			String matriculaStr = httpRequest.getParameter("matricula").trim();
			String senha = httpRequest.getParameter("senha");
			httpRequest.setAttribute("matricula", matriculaStr);
			
			if(matriculaStr.equals("")){
				httpRequest.setAttribute("mensagem", "A matrícula é obrigatória!");
			}
			else {
				if(senha.equals("")){
					httpRequest.setAttribute("mensagem", "A senha é obrigatória!");
				}
				else{
					if(senha.length() > 10){
						httpRequest.setAttribute("mensagem", "A senha deve conter no máximo 10 caracteres!");
					}
					else{
						if(senha.length() < 6){
							httpRequest.setAttribute("mensagem", "A senha deve conter no mínimo 6 caracteres!");
						}
						else{
							if(Integer.parseInt(matriculaStr) == 0){
								httpRequest.setAttribute("mensagem", "A matrícula deve conter apenas números (maior que 0)!");
							}
							else{
								if(matriculaStr.length() != 7){
									httpRequest.setAttribute("mensagem", "A matrícula deve conter apenas números e conter 7 caracteres!");
								}
							}
						}
					}
				}
			}
		} catch (NullPointerException e) {
			httpRequest.setAttribute("mensagem", "Não entendemos sua requisição!");
			e.printStackTrace();
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
