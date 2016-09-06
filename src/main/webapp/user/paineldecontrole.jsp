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
		<title>Painel de Controle - Controle de Turmas</title>
		<link rel="stylesheet" type="text/css" href="../style/style1.css" />
	</head>
	<body>
		<div id="allcontent">
			
			<%@ include file="header.jsp" %>
			
			<div id="main">
				<h2>Painel de controle</h2>
				<p>Bem vindo ao Painel de Controle, ${professor.nome}!</p>
				<p>Selecione, no menu ao lado, a operação desejada.</p>
			</div>
			
			<%
				List<ItemMenu> itensMenu = new ArrayList<ItemMenu>();
				itensMenu.add(new ItemMenu("Painel de Controle", "paineldecontrole.jsp", ""));
				itensMenu.add(new ItemMenu("Disciplinas", "disciplinas", ""));
				request.setAttribute("itensMenu", itensMenu);
			%>
			<%@ include file="../sidebar.jsp" %>
			
			<%@ include file="../footer.jsp" %>
		</div>
	</body>
</html>