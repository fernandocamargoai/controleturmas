<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="br.com.ufg.util.ItemMenu" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="br.com.ufg.model.bean.Disciplina" %>
<%@page import="br.com.ufg.model.bean.Turma" %>
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
		<c:if test="${datas != null}">	
			<link rel="stylesheet" type="text/css" href="../style/smartpaginator.css" />	
			<script type="text/javascript" src="../javascript/smartpaginator.js"></script>
			<script type="text/javascript">
				$(document).ready(function() {
					$('#smart-paginator').smartpaginator({ 	totalrecords: '<%=((List)request.getAttribute("datas")).size() %>',
															recordsperpage: 10,
															length: 10,
															initval: 0,
															next: 'Próxima',
															prev: 'Anterior',
															first: 'Primeira',
															last: 'última',
															go: 'Ir',
															theme: 'brown',
															datacontainer: 'datas',
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
				<h2>Conteúdos - ${turma.descricao} - ${turma.curso} - ${disciplina.nome}</h2>
				
				<input id="botaoRelatorioAnexo" type="button" value="Gerar relatório do Anexo do Diário de Classe" onclick="window.open('anexo','_blank')" />
				
				<c:if test="${mensagem != null}">
					<h3 class="mensagem" id="mensagem">${mensagem}</h3>
				</c:if>
				<c:if test="${datas != null}">
					<p>
						Desmarque as datas que não deverão ser contadas na chamada (Ex: feriados, recessos acad&ecirc;micos, etc.).
						 Também registre o conteúdo de cada data, pois isso será usado no relatório.
					</p>
					<form method="post" action="cadastrar_conteudo">
						<table id="datas" style="width: 100%;">
							<tr>
								<th style="width: 80px;">Data</th>
								<th style="width: 20px;"><img src="../imagens/icon_question.gif" title="Marcado quando for uma aula normal. Caso marcado, aparecerá na chamada." /></th>
								<th>Conteúdo</th>
							</tr>
						
							<c:forEach var="data" items="${datas}" varStatus="loop">
								<tr id="linha${loop.index}">
									<td>${data.data}</td>
									<td><input type="checkbox" name="aulaNormal${data.idData}" value="aulaNormal" ${data.aulaNormal ? 'checked=\'checked\'' : ''} /></td>
									<td><input type="text" name="conteudo${data.idData}" value="${data.conteudo}" maxlength="200" /></td>
								</tr>
							</c:forEach>
						</table>
						<div id="smart-paginator"></div>
						<p><input type="submit" value="Salvar" /></p>
					</form>
				</c:if>
			</div>
			
			<%
				List<ItemMenu> itensMenu = new ArrayList<ItemMenu>();
				itensMenu.add(new ItemMenu("Painel de Controle", "paineldecontrole.jsp", ""));
				itensMenu.add(new ItemMenu("Disciplinas", "disciplinas", ""));
				itensMenu.add(new ItemMenu("Turmas - " + ((Disciplina)session.getAttribute("disciplina")).getNome(), "turmas?codigoDisciplina=" + ((Disciplina)session.getAttribute("disciplina")).getCodigo(), ""));
				itensMenu.add(new ItemMenu("Turma - " + ((Turma)session.getAttribute("turma")).getDescricao() + " - " + ((Turma)session.getAttribute("turma")).getCurso(), "turma?codigoTurma=" + ((Turma)session.getAttribute("turma")).getCodigo(), ""));
				request.setAttribute("itensMenu", itensMenu);
			%>
			<%@ include file="../sidebar.jsp" %>
			
			<%@ include file="../footer.jsp" %>
		</div>
	</body>
</html>