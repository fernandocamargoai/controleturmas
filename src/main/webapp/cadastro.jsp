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
		<title>Cadastro - Controle de Turmas</title>
		<link rel="stylesheet" type="text/css" href="style/style1.css" />
		<script type="text/javascript" src="javascript/script.js"></script>
		<script type="text/javascript" src="javascript/validator.js"></script>
	</head>
	<body>
		<div id="allcontent">
			
			<%
				session.removeAttribute("mensagem");
				session.removeAttribute("professor");
			%>
			
			<%@ include file="header.jsp" %>
			
			<div id="main">
				<h2>Cadastro</h2>
				<form action="cadastrar_professor" method="post" onsubmit="return validarCadastro(this)">
					<h3 class="mensagem" style="display: ${mensagem != null ? 'block' : 'none'};" id="mensagem">${mensagem}</h3>
					
					<p>Para se cadastrar, preencha todos os campos do formulário abaixo:</p>
					<p><input type="hidden" name="cmd" value="cadastrarProfessor"  /></p>
					<table class="form">
						<tr>
							<td class="label" style="width: 180px;">Nome:</td>
							<td><input type="text" class="campo" name="nome" id="nome" value="${nome}" maxlength="60" /></td>
						</tr>
						<tr>
							<td class="label" >Matrícula:</td>
							<td><input type="text" class="campo" name="matricula" id="matricula" value="${matricula}" onkeyup="permitirApenas(this,'0123456789')" maxlength="7" /></td>
						</tr>
						<tr>
							<td class="label" style="width: 180px;">Senha:</td>
							<td><input type="password" class="campos" name="senha1" id="senha1" maxlength="10" /></td>
						</tr>
						<tr>
							<td class="label" style="width: 180px;">Repita a Senha:</td>
							<td><input type="password" class="campos" name="senha2" id="senha2" maxlength="10" /></td>
						</tr>
					</table>
					<p class="mensagem">Obs: A senha deve conter entre 6 e 10 caracteres!</p>
					<p><input type="submit" value="Cadastrar" /></p>
				</form>
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