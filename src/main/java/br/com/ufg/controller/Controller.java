package br.com.ufg.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.ufg.controller.command.Command;
import br.com.ufg.controller.command.CommandLogout;
import br.com.ufg.model.helper.Helper;

/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Helper helper;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

    public void init(){
    	helper = new Helper(getServletContext());
    }
    public void destroy(){
    	helper.getPool().fecharConnections();
    	helper = null;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processarRequisicao(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processarRequisicao(request, response);
	}
	
	private void processarRequisicao(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		this.helper.setRequest(request);
		Command comando = helper.getCommand();
		String pagina = comando.execute(request, response);
		if(pagina != null){
			if(pagina == "user/paineldecontrole.jsp" || comando instanceof CommandLogout){
				response.sendRedirect(pagina);
			}
			else{
				request.getRequestDispatcher(pagina).forward(request, response);	
			}
		}
	}
}
