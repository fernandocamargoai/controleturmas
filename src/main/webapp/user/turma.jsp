<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="br.com.ufg.util.ItemMenu" %>
<%@page import="java.util.List" %>
<%@page import="java.util.ArrayList" %>
<%@page import="br.com.ufg.model.bean.Disciplina" %>
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
				$("#dataInicio").mask("99/99/9999");
				$("#dataFim").mask("99/99/9999");
			});
		</script>
		<c:if test="${alunos != null}">	
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
															datacontainer: 'alunos',
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
				<h2>${turma.descricao} - ${turma.curso} - ${disciplina.nome}</h2>				
				
				<c:if test="${!temDatas}">
					<h3 class="mensagem" style="display: ${mensagemSemana != null ? 'block' : 'none'};" id="mensagemSemana">${mensagemSemana}</h3>
					<form action="cadastrar_diasSemana" method="post" style="display: ${mensagemSemana != null ? 'block' : 'none'};">
						<p>Marque os dias da semana em que esta turma tem aulas:</p>
						<table>
							<tr>
								<td class="label"><input type="checkbox" name="domingo" value="dom" ${domingo != null ? 'checked=\'checked\'' : ''} />Domingo</td>
							</tr>
							<tr>
								<td class="label"><input type="checkbox" name="segunda" value="seg" ${segunda != null ? 'checked=\'checked\'' : ''} />Segunda-feira</td>
							</tr>
							<tr>
								<td class="label"><input type="checkbox" name="terca" value="ter" ${terca != null ? 'checked=\'checked\'' : '' } />Ter&ccedil;a-feira</td>
							</tr>
							<tr>
								<td class="label"><input type="checkbox" name="quarta" value="qua" ${quarta != null ? 'checked=\'checked\'' : '' } />Quarta-feira</td>
							</tr>
							<tr>
								<td class="label"><input type="checkbox" name="quinta" value="qui" ${quinta != null ? 'checked=\'checked\'' : '' } />Quinta-feira</td>
							</tr>
							<tr>
								<td class="label"><input type="checkbox" name="sexta" value="sex" ${sexta != null ? 'checked=\'checked\'' : '' } />Sexta-feira</td>
							</tr>
							<tr>
								<td class="label"><input type="checkbox" name="sabado" value="sab" ${sabado != null ? 'checked=\'checked\'' : '' } />Sábado</td>
							</tr>
						</table>
						<p>
							<input type="submit" value="Registrar" />
							<input type="button" value="Fechar" onclick="esconderFormSemApagar(0,'botaoMostrarSemana', 'mensagemSemana')" />
						</p>
					</form>
				
				
					<h3 class="mensagem" style="display: ${mensagemData != null ? 'block' : 'none'};" id="mensagemData">${mensagemData}</h3>
					<form action="cadastrar_datas" method="post" style="display: ${mensagemData != null ? 'block' : 'none'};">
						<p>Preencha as datas de início e fim do semestre:</p>
						<table class="form">
							<tr>
								<td class="label">Data de início:</td>
								<td><input type="text" class="campo" name="dataInicio" id="dataInicio" value="${dataInicio}" maxlength="10" /></td>
							</tr>
							<tr>
								<td class="label">Data de fim:</td>
								<td><input type="text" class="campo" name="dataFim" id="dataFim" value="${dataFim}" maxlength="10" /></td>
							</tr>
						</table>
						<p>
							<input type="submit" value="Registrar" />
							<input type="button" value="Fechar" onclick="esconderFormSemApagar(1,'botaoMostrarDia', 'mensagemData')" />
						</p>
					</form>
				
					<h3 class="mensagem" style="display: ${mensagemGerar != null ? 'block' : 'none'};" id="mensagemGerar">${mensagemGerar}</h3>
					<form action="gerar_datas" method="post" style="display: ${mensagemGerar != null ? 'block' : 'none'};">
						<p class="mensagem" style="text-align: justify;">
							Aten&ccedil;ão! Apenas clique no botão a seguir quando tiver preenchido
							 corretamente os dias da semana e as datas de início e fim do semestre! Ao pressioná-lo,
							 serão geradas as datas de aula dessa disciplina, e após isso, não poderá ser corrigido!
						</p>
						<p>
							<input type="button" value="Gerar datas de aula" onclick="confirmarGeracao(2)" />
							<input type="button" value="Fechar" onclick="esconderForm(2,'botaoMostrarGerar')" />
						</p>
					</form>
				</c:if>
				<h3 class="mensagem" style="display: ${mensagem != null ? 'block' : 'none'};" id="mensagem">${mensagem}</h3>
				<form action="cadastrar_alunos" method="post" enctype="multipart/form-data" onsubmit="return validaArquivoCsv(this.arquivo)" style="display: ${mensagem != null ? 'block' : 'none'};">
					<p>Selecione o arquivo .csv com os alunos (matrícula, nome, e-mail):</p>
					<table class="form">
						<tr>
							<td class="label">Arquivo .csv:</td>
							<td><input type="file" id="arquivo" name="arquivo" id="matricula" /></td>
						</tr>
					</table>
					<p>
						<input type="submit" value="Enviar" />
						<input type="button" value="Fechar" onclick="esconderForm(${temDatas ? 0 : 3},'botaoMostrar')" />
					</p>
				</form>
				<div>
					<c:if test="${!temDatas}">
						<input id="botaoMostrarSemana" type="button" value="Registrar dias da semana" onclick="mostrarForm(0, this)" style="display: ${mensagemSemana != null ? 'none' : 'block'}; float:left;" />
						<input id="botaoMostrarDia" type="button" value="Registrar datas" onclick="mostrarForm(1, this)" style="display: ${mensagemDia != null ? 'none' : 'block'}; float:left;" />
						<input id="botaoMostrarGerar" type="button" value="Gerar datas" onclick="mostrarForm(2, this)" style="display: ${mensagemGerar != null ? 'none' : 'block'}; float:left;" />
					</c:if>
					<input id="botaoMostrar" type="button" value="Cadastrar alunos" onclick="mostrarForm(${temDatas ? 0 : 3}, this)" style="display: ${mensagem != null ? 'none' : 'block'}; float:left;" />				
					<c:if test="${temDatas}">
						<input id="botaoIrDatas" type="button" value="Conteúdo" onclick="window.location = 'conteudo?codigoTurma=${turma.codigo}'" />
						<c:if test="${alunos != null}">
							<input id="botaoIrPresenca" type="button" value="Presen&ccedil;as" onclick="window.location = 'presenca?codigoTurma=${turma.codigo}'" />
						</c:if>
					</c:if>
					<c:if test="${alunos != null}">
						<input id="botaoIrNota" type="button" value="Notas" onclick="window.location = 'nota?codigoTurma=${turma.codigo}'" />
					</c:if>
				</div>
				<hr style="clear: both;" />
				
				
				<h3 class="mensagem" style="display: ${mensagem2 != null ? 'block' : 'none'};" id="mensagem2">${mensagem2}</h3>
				<c:if test="${alunos != null}">
					<table id="alunos" style="width: 100%;">
						<tr>
							<th>Matrícula<input type="hidden" value="matricula" /></th>
							<th>Nome<input type="hidden" value="nome" /></th>
							<th>E-mail<input type="hidden" value="email" /></th>
							<th>Editar</th>
							<th>Excluir</th>
						</tr>
						
						<c:forEach var="aluno" items="${alunos}" varStatus="loop">
							<tr id="linha${loop.index}">
								<td>${aluno.matricula}</td>
								<td>${aluno.nome}</td>
								<td>${aluno.email}</td>
								<td><a href="#" onclick="editarLinha(this,'editarAluno')">Editar</a><input type="hidden" value="${aluno.codigo}" /></td>
								<td><a href="#" onclick="excluirLinha(this, 'excluirAluno')">Excluir</a><input type="hidden" value="${aluno.codigo}" /></td>
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
				itensMenu.add(new ItemMenu("Turmas - " + ((Disciplina)session.getAttribute("disciplina")).getNome(), "turmas?codigoDisciplina=" + ((Disciplina)session.getAttribute("disciplina")).getCodigo(), ""));
				request.setAttribute("itensMenu", itensMenu);
			%>
			<%@ include file="../sidebar.jsp" %>
			
			<%@ include file="../footer.jsp" %>
		</div>
	</body>
</html>