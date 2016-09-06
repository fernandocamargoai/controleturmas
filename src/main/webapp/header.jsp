<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
			<div id="header">
				<img src="imagens/topo.jpg" alt="topo" width="757" height="80" /><br />
				<span id="login"><c:if test="${professor != null}">Bem vindo, <em>${professor.nome}</em> | </c:if><a href="${professor == null ? 'login.jsp' : 'Controller?cmd=logout'}" title="${professor == null ? 'Login' : 'Logout'}">${professor == null ? 'Login' : 'Logout'}</a></span>
				<br />
				<h1>Controle de Turmas - EEEC</h1>
			</div>