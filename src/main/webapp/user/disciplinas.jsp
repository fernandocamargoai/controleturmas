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
		<link rel="stylesheet" type="text/css" href="../style/style1.css" />
		<script type="text/javascript" src="../javascript/script.js"></script>
		<script type="text/javascript" src="../javascript/validator.js"></script>
		<script type="text/javascript" src="../javascript/ajax.js"></script>
		<script type="text/javascript" src="../javascript/jquery.js"></script>
		<c:if test="${disciplinas != null}">	
			<link rel="stylesheet" type="text/css" href="../style/smartpaginator.css" />	
			<script type="text/javascript" src="../javascript/smartpaginator.js"></script>
			<script type="text/javascript">
				$(document).ready(function() {
					$('#smart-paginator').smartpaginator({ 	totalrecords: '<%=((List)request.getAttribute("disciplinas")).size() %>',
															recordsperpage: 10,
															length: 10,
															initval: 0,
															next: 'Próxima',
															prev: 'Anterior',
															first: 'Primeira',
															last: 'última',
															go: 'Ir',
															theme: 'brown',
															datacontainer: 'disciplinas',
															dataelement: 'tr'
					});
				});
			</script>		
		</c:if>
	</head>
	<body>
		<div id="allcontent">
			
			<%@ include file="header.jsp" %>
			
			<div id="main">
				<h2>Disciplinas</h2>
				
				<h3 class="mensagem" style="display: ${mensagem != null ? 'block' : 'none'};" id="mensagem">${mensagem}</h3>
				<form action="cadastrar_disciplina" method="post" onsubmit="return validarCadastroDisciplina(this)" style="display: ${mensagem != null ? 'block' : 'none'};">
					<p>Preencha todos os campos do formulário abaixo para cadastrar uma nova disciplina:</p>
					<p><input type="hidden" name="cmd" value="cadastrarDisciplina" /></p>
					<table class="form">
						<tr>
							<td class="label">Nome:</td>
							<td><input type="text" class="campo" name="nome" id="nome" value="${nome}" maxlength="100" /></td>
						</tr>
						<tr>
							<td class="label">Carga Horária:</td>
							<td><input type="text" class="campo" name="cargaHoraria" id="cargaHoraria" value="${cargaHoraria}" onkeyup="permitirApenas(this,'0123456789')" maxlength="3" /></td>
						</tr>
					</table>
					<p>
						<input type="submit" value="Cadastrar" />
						<input type="button" value="Cancelar" onclick="esconderForm(0,'botaoMostrar')" />
					</p>
				</form>
				<input id="botaoMostrar" type="button" value="Nova Disciplina" onclick="mostrarForm(0, this)" style="display: ${mensagem != null ? 'none' : 'block'};" />
				<c:if test="${disciplinas != null}">
				<hr />
				</c:if>
				<h3 class="mensagem" style="display: ${mensagem2 != null ? 'block' : 'none'};" id="mensagem2">${mensagem2}</h3>
				<c:if test="${disciplinas != null}">
					<table id="disciplinas" style="width: 100%">						
						<tr>
							<th>Nome<input type="hidden" value="nome" /></th>
							<th>Carga Horária<input type="hidden" value="cargaHoraria" /></th>
							<th>Editar</th>
							<th>Excluir</th>
						</tr>
						
						<c:forEach var="disciplina" items="${disciplinas}" varStatus="loop">
							<tr id="linha${loop.index}">
								<td><a href="turmas?codigoDisciplina=${disciplina.codigo}">${disciplina.nome}</a></td>
								<td>${disciplina.cargaHoraria}</td>
								<td style="text-align: center;"><a href="#" onclick="editarLinha(this,'editarDisciplina')">Editar</a><input type="hidden" value="${disciplina.codigo}" /></td>
								<td style="text-align: center;"><a href="#" onclick="excluirLinha(this, 'excluirDisciplina')">Excluir</a><input type="hidden" value="${disciplina.codigo}" /></td>
							</tr>
						</c:forEach>					
					</table>
					<div id="smart-paginator"></div>
				</c:if>
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