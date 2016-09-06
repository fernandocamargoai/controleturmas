package br.com.ufg.controller.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CommandLogout implements Command {

		
	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) {
		request.getSession().removeAttribute("professor");
		return "index.jsp";
	}

}
