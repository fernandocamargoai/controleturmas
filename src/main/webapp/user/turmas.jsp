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
		<script type="text/javascript" src="../javascript/jquery.maskedinput.js"></script>
		<script type="text/javascript">
			jQuery(function($){
				$("#ano").mask("9999");
				$("#semestre").mask("9");
			});
		</script>
		<c:if test="${turmas != null}">	
			<link rel="stylesheet" type="text/css" href="../style/smartpaginator.css" />	
			<script type="text/javascript" src="../javascript/smartpaginator.js"></script>
			<script type="text/javascript">
				$(document).ready(function() {
					$('#smart-paginator').smartpaginator({ 	totalrecords: '<%=((List)request.getAttribute("turmas")).size() %>',
															recordsperpage: 10,
															length: 10,
															initval: 0,
															next: 'Próxima',
															prev: 'Anterior',
															first: 'Primeira',
															last: 'última',
															go: 'Ir',
															theme: 'brown',
															datacontainer: 'turmas',
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
				<h2>Turmas - ${disciplina.nome}</h2>
				
				<h3 class="mensagem" style="display: ${mensagem != null ? 'block' : 'none'};" id="mensagem">${mensagem}</h3>
				<form action="cadastrar_turma" method="post" onsubmit="return validarCadastroTurma(this)" style="display: ${mensagem != null ? 'block' : 'none'};">
					<p>Preencha todos os campos do formulário abaixo para cadastrar uma nova turma para a disciplina ${disciplina.nome}:</p>
					<p><input type="hidden" name="cmd" value="cadastrarTurma" /></p>
					<table class="form">
						<tr>
							<td class="label">Turma:</td>
							<td><input type="text" class="campo" name="descricao" id="descricao" value="${descricao}" maxlength="3" /></td>
						</tr>
						<tr>
							<td class="label">Curso:</td>
							<td><input type="text" class="campo" name="curso" id="curso" value="${curso}" maxlength="30" /></td>
						</tr>
						<tr>
							<td class="label">Ano:</td>
							<td><input type="text" class="campo" name="ano" id="ano" value="${ano}" maxlength="4" /></td>
						</tr>
						<tr>
							<td class="label">Semestre:</td>
							<td><input type="text" class="campo" name="semestre" id="semestre" value="${semestre}" /></td>
						</tr>
					</table>
					<p>
						<input type="submit" value="Cadastrar" />
						<input type="button" value="Cancelar" onclick="esconderForm(0,'botaoMostrar')" />
					</p>
				</form>
				<input id="botaoMostrar" type="button" value="Nova Turma" onclick="mostrarForm(0, this)" style="display: ${mensagem != null ? 'none' : 'block'};" />
				<c:if test="${turmas != null}">
				<hr />
				</c:if>
				<h3 class="mensagem" style="display: ${mensagem2 != null ? 'block' : 'none'};" id="mensagem2">${mensagem2}</h3>
				<c:if test="${turmas != null}">
					<table id="turmas" style="width: 100%;">						
						<tr>
							<th>Turma<input type="hidden" value="descricao" /></th>
							<th>Curso<input type="hidden" value="curso" /></th>
							<th>Ano<input type="hidden" value="ano" /></th>
							<th>Semestre<input type="hidden" value="semestre" /></th>
							<th>Editar</th>
							<th>Excluir</th>
						</tr>						
						
						<c:forEach var="turma" items="${turmas}" varStatus="loop">
							<tr id="linha${loop.index}">
								<td><a href="turma?codigoTurma=${turma.codigo}">${turma.descricao}</a></td>
								<td>${turma.curso}</td>
								<td>${turma.ano}</td>
								<td>${turma.semestre}</td>
								<td><a href="#" onclick="editarLinha(this,'editarTurma')">Editar</a><input type="hidden" value="${turma.codigo}" /></td>
								<td><a href="#" onclick="excluirLinha(this, 'excluirTurma')">Excluir</a><input type="hidden" value="${turma.codigo}" /></td>
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