package br.com.ufg.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class FiltroCadastroDatas implements Filter {

	/**
	 * Default constructor.
	 */
	public FiltroCadastroDatas() {
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
			String dataInicioStr = httpRequest.getParameter("dataInicio").trim();
			String dataFimStr = httpRequest.getParameter("dataFim").trim();
			httpRequest.setAttribute("dataInicio", dataInicioStr);
			httpRequest.setAttribute("dataFim", dataFimStr);
			
			String diaInicio = dataInicioStr.substring(0, dataInicioStr.indexOf('/'));
			String mesInicio = dataInicioStr.substring(dataInicioStr.indexOf('/') + 1);
			mesInicio = mesInicio.substring(0, mesInicio.indexOf('/'));
			String anoInicio = dataInicioStr.substring(dataInicioStr.lastIndexOf('/')+1);
			
			String diaFim = dataFimStr.substring(0, dataFimStr.indexOf('/'));
			String mesFim = dataFimStr.substring(dataFimStr.indexOf('/') + 1);
			mesFim = mesFim.substring(0, mesFim.indexOf('/'));		
			String anoFim = dataFimStr.substring(dataFimStr.lastIndexOf('/')+1);
			
			Date dataInicio = null;
			Date dataFim = null;
			
			try {
				dataInicio = Date.valueOf(anoInicio + "-" + mesInicio + "-" + diaInicio);
				httpRequest.setAttribute("dataInicioDate", dataInicio);
				try{
					dataFim = Date.valueOf(anoFim + "-" + mesFim + "-" + diaFim);
					httpRequest.setAttribute("dataFimDate", dataFim);
					if(dataInicio.compareTo(dataFim) >= 0){
						httpRequest.setAttribute("mensagemData", "A data de início deve ser maior que a data de fim!");
					}
				}
				catch(IllegalArgumentException e){
					httpRequest.setAttribute("mensagemData", "Data de fim inválida!");
				}
			} catch (IllegalArgumentException e) {
				httpRequest.setAttribute("mensagemData", "Data de início inválida!");
				e.printStackTrace();
			}			
		} catch (NullPointerException e) {
			httpRequest.setAttribute("mensagemData",
			"Não entendemos sua requisição!");
			e.printStackTrace();
		}
		catch(StringIndexOutOfBoundsException e){
			httpRequest.setAttribute("mensagemData", "Preencha os campos corretamente!");
			e.printStackTrace();
		}

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	public boolean isValidEmail(String email){
		String expression = "/^[\\w\\-\\.\\+]+\\@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z0-9]{2,4}$/";
		CharSequence inputStr = email;
		Pattern pattern = Pattern.compile(expression);
		Matcher matcher = pattern.matcher(inputStr);
		if(matcher.matches()){
			return true;
		}
		return false;
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
