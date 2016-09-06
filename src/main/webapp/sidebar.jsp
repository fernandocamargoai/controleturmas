<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page import="java.util.List"%>
<%@page import="br.com.ufg.util.ItemMenu" %>
			<div id="sidebar">
				<h2>Menu</h2>
				<ul id="menu">
					<c:forEach var="itemMenu" items="${itensMenu}">
						<li><a href="${itemMenu.url}" title="${itemMenu.titulo}">${itemMenu.nome}</a></li>
					</c:forEach>
				</ul>
			</div>