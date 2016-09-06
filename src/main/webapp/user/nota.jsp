<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="br.com.ufg.util.ItemMenu" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="java.util.HashMap" %>
<%@page import="br.com.ufg.model.bean.Disciplina" %>
<%@page import="br.com.ufg.model.bean.Turma" %>
<%@page import="br.com.ufg.model.bean.Aluno" %>
<%@page import="br.com.ufg.model.bean.Nota" %>
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
		<c:if test="${mapaNotas != null}">	
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
															datacontainer: 'notas',
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
				<h2>Notas - ${turma.descricao} - ${turma.curso} - ${disciplina.nome}</h2>				
				
				<p>Antes de gerar o relatório, registre corretamente a frequ&ecirc;ncia, os pesos e as notas.</p>
				<input id="botaoRelatorioNotas" type="button" value="Gerar relatório da Rela&ccedil;ão de Notas" onclick="window.open('relacao_notas','_blank')" />
				
				<p class="mensagem">Recomenda&ccedil;ão: Sempre que cadastrar novos alunos, altere o peso das notas para que estes sejam usados nos novos alunos.</p>
				<h3 class="mensagem" style="display: ${mensagemPeso != null ? 'block' : 'none'};" id="mensagemPeso">${mensagemPeso}</h3>
				<form action="alterar_pesos" method="post" style="display: ${mensagemPeso != null ? 'block' : 'none'};">
					<p>Preencha os pesos que cada nota deve ter. No caso em que queira desconsiderar uma nota, apenas coloque peso 0.</p>
					<table>
						<tr>
							<td>N1:</td>
							<td><input type="text" name="pesoN1" value="${pesoN1}" /></td>
						</tr>
						<tr>
							<td>N2:</td>
							<td><input type="text" name="pesoN2" value="${pesoN2}" /></td>
						</tr>
						<tr>
							<td>N3:</td>
							<td><input type="text" name="pesoN3" value="${pesoN3}" /></td>
						</tr>
						<tr>
							<td>N4:</td>
							<td><input type="text" name="pesoN4" value="${pesoN4}" /></td>
						</tr>
						<tr>
							<td>N5:</td>
							<td><input type="text" name="pesoN5" value="${pesoN5}" /></td>
						</tr>
						<tr>
							<td>N6:</td>
							<td><input type="text" name="pesoN6" value="${pesoN6}" /></td>
						</tr>
						<tr>
							<td>N7:</td>
							<td><input type="text" name="pesoN7" value="${pesoN7}" /></td>
						</tr>
						<tr>
							<td>N8:</td>
							<td><input type="text" name="pesoN8" value="${pesoN8}" /></td>
						</tr>
						<tr>
							<td>N9:</td>
							<td><input type="text" name="pesoN9" value="${pesoN9}" /></td>
						</tr>
						<tr>
							<td>N10:</td>
							<td><input type="text" name="pesoN10" value="${pesoN10}" /></td>
						</tr>
					</table>
					<p>
						<input type="submit" value="Alterar" />
						<input type="button" value="Fechar" onclick="esconderForm(0,'botaoMostrarPeso', 'mensagemPeso')" />
					</p>
				</form>
				<div>
					<input id="botaoMostrarPeso" type="button" value="Alterar pesos das notas" onclick="mostrarForm(0, this)" style="display: ${mensagemPeso != null ? 'none' : 'block'}; float:left;" />		
				</div>
				
				<hr style="clear: both;" />
				
				<c:if test="${mensagem != null}">
					<h3 class="mensagem" id="mensagem">${mensagem}</h3>
				</c:if>
				<c:if test="${mapaNotas != null}">
					<p>
						Registre as notas dos alunos:
					</p>
					<form method="post" action="cadastrar_nota">
						<div style="width: 100%; overflow: auto;">
							<table id="notas">
								<tr>
									<th>Nome</th>
									<th>N1</th>
									<th>N2</th>
									<th>N3</th>
									<th>N4</th>
									<th>N5</th>
									<th>N6</th>
									<th>N7</th>
									<th>N8</th>
									<th>N9</th>
									<th>N10</th>
									<th>Média</th>
								</tr>
						
								<%
									HashMap<Integer, List<Nota>> mapaNotas = (HashMap<Integer, List<Nota>>) request.getAttribute("mapaNotas");
									HashMap<Integer, Float> mapaMedia = (HashMap<Integer, Float>) request.getAttribute("mapaMedia");
									List<Aluno> alunos = (List<Aluno>) request.getAttribute("alunos");
									for(int i=0; i<alunos.size(); i++){
								%>
									<tr>
										<td><%=alunos.get(i).getNome()%></td>
										<%
											List<Nota> notas = mapaNotas.get(alunos.get(i).getCodigo());
										%>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(0).getIdNota()%>" value="<%=notas.get(0).getNota()%>" <%= notas.get(0).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(1).getIdNota()%>" value="<%=notas.get(1).getNota()%>" <%= notas.get(1).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(2).getIdNota()%>" value="<%=notas.get(2).getNota()%>" <%= notas.get(2).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(3).getIdNota()%>" value="<%=notas.get(3).getNota()%>" <%= notas.get(3).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(4).getIdNota()%>" value="<%=notas.get(4).getNota()%>" <%= notas.get(4).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(5).getIdNota()%>" value="<%=notas.get(5).getNota()%>" <%= notas.get(5).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(6).getIdNota()%>" value="<%=notas.get(6).getNota()%>" <%= notas.get(6).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(7).getIdNota()%>" value="<%=notas.get(7).getNota()%>" <%= notas.get(7).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(8).getIdNota()%>" value="<%=notas.get(8).getNota()%>" <%= notas.get(8).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><input style="width: 92%;" type="text" name="nota<%=notas.get(9).getIdNota()%>" value="<%=notas.get(9).getNota()%>" <%= notas.get(9).getPeso() == 0 ? "disabled=\"disabled\"" : "" %> /></td>
										<td><%=mapaMedia.get(alunos.get(i).getCodigo())%></td>
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