<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="br.com.ufg.util.ItemMenu" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.HashMap" %>
<%@page import="br.com.ufg.model.bean.Disciplina" %>
<%@page import="br.com.ufg.model.bean.Turma" %>
<%@page import="br.com.ufg.model.bean.Data" %>
<%@page import="br.com.ufg.model.bean.Aluno" %>
<%@page import="br.com.ufg.model.bean.Presenca" %>
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
					$('#smart-paginator').smartpaginator({ 	totalrecords: '<%=((List)request.getAttribute("alunos")).size() %>',
															recordsperpage: 10,
															length: 10,
															initval: 0,
															next: 'Próxima',
															prev: 'Anterior',
															first: 'Primeira',
															last: 'última',
															go: 'Ir',
															theme: 'brown',
															datacontainer: 'presencas',
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
				<h2>Presen&ccedil;as - ${turma.descricao} - ${turma.curso} - ${disciplina.nome}</h2>
				
				<input id="botaoRelatorioFrequencia" type="button" value="Gerar relatório da Ficha de Frequ&ecirc;ncia" onclick="window.open('frequencia','_blank')" />
				
				<c:if test="${mensagem != null}">
					<h3 class="mensagem" id="mensagem">${mensagem}</h3>
				</c:if>
				<c:if test="${datas != null}">
					<p>
						Marque as aulas em que o aluno esteve presente:
					</p>
					<form method="post" action="cadastrar_presenca">
						<div style="width: 100%; overflow: auto;">
							<table id="presencas">
								<tr>
									<th>Nome</th>
									<c:forEach var="data" items="${datas}" varStatus="loop">
										<th style="width: 20px;"><img src="../imagens/icon_question.gif" title="${data.data}" /></th>
									</c:forEach>								
								</tr>
						
								<%
									HashMap<String, Presenca> mapaPresencas = (HashMap<String, Presenca>) request.getAttribute("mapaPresencas");
									List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
									for(int i=0; i<alunos.size(); i++){
								%>
									<tr>
										<td><%=alunos.get(i).getNome()%></td>
										<%
											List<Data> datas = (List<Data>) request.getAttribute("datas");
											for(int j=0; j<datas.size(); j++){
												Presenca presenca = mapaPresencas.get(alunos.get(i).getCodigo() + "$" + datas.get(j).getIdData());
										%>									
											<td><input type="checkbox" title="<%=alunos.get(i).getNome() + " - " + datas.get(j).getData() %>" name="presenca<%=presenca.getIdPresenca()%>" value="presente" <%=presenca.isPresenca() ? "checked='checked'" : "" %> /><input type="hidden" name="id<%=presenca.getIdPresenca()%>" value="<%=presenca.getIdPresenca()%>" /></td>									
										<%
											}
										%>
									</tr>
								<%
									}
								%>
							</table>
						</div>
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