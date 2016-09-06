<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="br.com.ufg.util.ItemMenu" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="pt-br" xml:lang="pt-br">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<title>Controle de Turmas</title>
		<link rel="stylesheet" type="text/css" href="style/style1.css" />
	</head>
	<body>
		<div id="allcontent">
			
			<%
				session.removeAttribute("mensagem");
				session.removeAttribute("professor");
			%>
			
			<%@ include file="header.jsp" %>
			
			<div id="main">
				<h2>Bem vindo!</h2>
				<p>
					Seja bem vindo ao sistema de Controle de turmas da unidade EEEC da UFG!<br />
					Um sistema criado para auxiliar professores a controlar frequ&ecirc;ncia e notas de seus alunos, organizados por turma.
				</p>
				<p>
					Com ele, os professores do EEEC poderão cadastrar suas disciplinas e turmas referentes com todos
					 seus detalhes, os alunos presentes em cada turma com suas frequ&ecirc;ncias e notas, além de outras funç&otilde;es.
					 Assim no fim do semestre, o professor poderá exportar para PDF todos os documentos exigidos, já no padrão correto, pela secretaria.
				</p>
				<p>
					Caso ainda não seja cadastrado, pode se cadastrar <a href="cadastro.jsp" title="">aqui</a>
					 ou no menu ao lado.
				</p>
			</div>
			
			<%
				List<ItemMenu> itensMenu = new ArrayList<ItemMenu>();
				itensMenu.add(new ItemMenu("Home", "index.jsp", ""));
				if(session.getAttribute("professor") == null){
					itensMenu.add(new ItemMenu("Cadastre-se", "cadastro.jsp", ""));
				}
				else{
					itensMenu.add(new ItemMenu("Painel de Controle", "user/painel.jsp", ""));
				}
				itensMenu.add(new ItemMenu("Manual de Instruções", "", ""));
				request.setAttribute("itensMenu", itensMenu);
			%>
			<%@ include file="sidebar.jsp" %>
			
			<%@ include file="footer.jsp" %>
		</div>
	</body>
</html>