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
public class FiltroCadastro implements Filter {

	/**
     * Default constructor. 
     */
    public FiltroCadastro() {
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
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		try {
			String nome = httpRequest.getParameter("nome").trim();
			String matriculaStr = httpRequest.getParameter("matricula");
			String senha1 = httpRequest.getParameter("senha1");
			String senha2 = httpRequest.getParameter("senha2");
			httpRequest.setAttribute("nome", nome);
			httpRequest.setAttribute("matricula", matriculaStr);
			
			if(nome.equals("")){
				httpRequest.setAttribute("mensagem", "O nome é obrigatório para o cadastro!");
			}
			else{
				if(matriculaStr.equals("")){
					httpRequest.setAttribute("mensagem", "A matrícula é obrigatória para o cadastro!");
				}
				else{
					if(senha1.equals("")){
						httpRequest.setAttribute("mensagem", "A senha é obrigatória para o cadastro!");
					}
					else{
						if(senha2.equals("")){
							httpRequest.setAttribute("mensagem", "Você deve repetir sua senha para completar o cadastro!");
						}
						else{
							if(nome.length() > 60){
								httpRequest.setAttribute("mensagem", "O nome deve ter no máximo 60 caracteres!");
							}
							else{
								if(nome.length() < 10){
									httpRequest.setAttribute("mensagem", "O nome deve ter pelo menos 10 caracteres!");
								}
								else{								
									if(senha1.length() > 10){
										httpRequest.setAttribute("mensagem", "A senha deve conter no máximo 10 caracteres!");
									}
									else{
										if(senha1.length() < 6){
											httpRequest.setAttribute("mensagem", "A senha deve conter no mínimo 6 caracteres!");
										}
										else{
											if(!senha1.equals(senha2)){
												httpRequest.setAttribute("mensagem", "A repetição da senha está incorreta!");
											}
											else{
												try{
													if(Integer.parseInt(matriculaStr) == 0){
														httpRequest.setAttribute("mensagem", "A matrícula deve conter apenas números (maior que 0)!");
													}
													else{
														if(matriculaStr.length() != 7){
															httpRequest.setAttribute("mensagem", "A matrícula deve conter apenas números e conter 7 caracteres!");
														}
													}
												}
												catch(NumberFormatException e){
													httpRequest.setAttribute("mensagem", "A matrícula deve conter apenas números (maior que 0)!");
												}
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
		
	}

}
