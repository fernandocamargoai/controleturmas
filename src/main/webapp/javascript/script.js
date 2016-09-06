function permitirApenas(field, validChars, e){
	var evtobj = window.event? event : e;
	var unicode = evtobj.charCode? evtobj.charCode : evtobj.keyCode;
	if(unicode != 38 && unicode != 40 && unicode != 37 && unicode != 39 && unicode != 33
			&& unicode != 34 && unicode != 36 && unicode != 35 && unicode != 13
			&& unicode != 9 && unicode != 16 && unicode != 17 && unicode != 18 
			&& unicode != 20 && unicode != 27){
		var texto = field.value;
		for(var i=0; i<texto.length; i++){
			var char = texto.charAt(i);
			if(validChars.indexOf(char) == -1){
				texto = texto.substring(0,i) + texto.substring(i+1,texto.length);
				i--;
			}
		}
		if(field.value != texto){
			field.value = texto;
		}
	}
}

function permitirApenasUmDigito(field, validChars){
	var texto = field.value;
	for(var i=0; i<texto.length; i++){
		var char = texto.charAt(i);
		if(validChars.indexOf(char) == -1){
			texto = texto.substring(0,i) + texto.substring(i+1,texto.length);
			i--;
		}
	}
	texto = texto.substring(texto.length-1);
	field.value = texto;
}

function mostrarForm(numeroForm, botao){
	document.forms[numeroForm].style.display = "block";
	botao.style.display = "none";
	document.getElementById("mensagem").style.display = "none";
}

function esconderForm(numeroForm, idBotaoMostrar){
	var frm_elements = document.forms[numeroForm].elements;
	for (i = 0; i < frm_elements.length; i++){
	    field_type = frm_elements[i].type.toLowerCase();
	    switch (field_type){
	    	case "text":
	    	case "password":
	    	case "textarea":
	    		frm_elements[i].value = "";
	    		break;
	    	case "checkbox":
	    	case "radio":
	    		if (frm_elements[i].checked){
	    			frm_elements[i].checked = false;
	    		}
	    		break;
	    	case "select-one":
	    	case "select-multi":
	    		frm_elements[i].selectedIndex = -1;
	    		break;
	    	default:
	    		break;
	    }
	}	
	document.forms[numeroForm].style.display = "none";
	document.getElementById(idBotaoMostrar).style.display = "block";
	document.getElementById("mensagem").style.display = "none";
}

function esconderFormSemApagar(numeroForm, idBotaoMostrar, idMensagem){
	document.forms[numeroForm].style.display = "none";
	document.getElementById(idBotaoMostrar).style.display = "block";
	document.getElementById(idMensagem).style.display = "none";
}

var xmlhttp;

function editarLinha(linkEditar, servlet){
	//Troca o texto de 'Editar' para 'Salvar'
	linkEditar.innerHTML = "Salvar";
	//Pega a célula do link editar
	var tdEditar = linkEditar.parentNode;
	//Pega codigo do item a ser editado
	var codigo = tdEditar.getElementsByTagName("input")[0].value;
	//Pega a linha a ser editada
	var trEditar = tdEditar.parentNode;
	//Listar todas as células da linha a ser editada
	var listaTd = trEditar.getElementsByTagName("td");
	//Listar os ths
	var listaTh = trEditar.parentNode.getElementsByTagName("tr")[0].getElementsByTagName("th");
	//Declara o array que conterá o nome dos campos que será usado na validação
	var nomeCampos = new Array();
	//Modificar as labels por inputText
	for(var i=0; i<listaTd.length; i++){
		if(listaTd[i] == tdEditar){
			break;
		}
		//Pega o nome equivalente no form
		var nome = listaTh[i].getElementsByTagName("input")[0].value;
		//Pega o valor atual da célula (ou do link dentro da célula, caso haja um)
		//Depois cria a inputText equivalente (caso tenha o link, cria um campo oculto tbm)
		if(listaTd[i].getElementsByTagName("a")[0] == null){
			var valor = listaTd[i].innerHTML;
			listaTd[i].innerHTML = "<input type=\"text\" name=\""+nome+"$"+codigo+"\" id=\""+nome+"$"+codigo+"\" value=\""+valor+"\" />";
		}
		else{
			var valor = listaTd[i].getElementsByTagName("a")[0].innerHTML;
			var url = listaTd[i].getElementsByTagName("a")[0].href;
			url = url.substring(url.lastIndexOf("/") + 1);
			listaTd[i].innerHTML = "<input type=\"text\" name=\""+nome+"$"+codigo+"\" id=\""+nome+"$"+codigo+"\" value=\""+valor+"\" />" + "<input type=\"hidden\" value=\""+ url +"\" />";
		}		
		//Adiciona o id do campo ao array
		nomeCampos[i] = nome+"$"+codigo;
	}
	//Troca a função de 'editarLinha' para 'salvarLinha'
	linkEditar.onclick = function() {
		var teste = true;
		if(servlet == "editarDisciplina"){
			teste = validarEditarDisciplina(nomeCampos);
		}
		if(servlet == "editarTurma"){
			teste = validarEditarTurma(nomeCampos);
		}
		if(servlet == "editarAluno"){
			teste = validarEditarAluno(nomeCampos);
		}
		if(teste){
			salvarLinha(this, servlet);
		}
	};
}

function salvarLinha(linkSalvar, servlet){
	//Troca o texto de 'Salvar' para 'Editar'
	linkSalvar.innerHTML = "Editar";
	//Pega a célula do link salvar
	var tdSalvar = linkSalvar.parentNode;
	//Pega codigo o item a ser salvado
	var codigo = tdSalvar.getElementsByTagName("input")[0].value;
	//Pega a linha a ser salvada
	var trSalvar = tdSalvar.parentNode;
	//Listar todas as células da linha a ser salvada
	var listaTd = trSalvar.getElementsByTagName("td");
	
	var nomeArray = new Array();
	var valorArray = new Array();
	//Modificar as inputText para labels e guardar os argumentos que serão usados para salvar
	for(var i=0; i<listaTd.length; i++){
		if(listaTd[i] == tdSalvar){
			break;
		}
		var inputField = listaTd[i].getElementsByTagName("input")[0];
		if(listaTd[i].getElementsByTagName("input")[1] == null){
			listaTd[i].innerHTML = inputField.value;
		}
		else{
			var url = listaTd[i].getElementsByTagName("input")[1].value;
			listaTd[i].innerHTML = "<a href=\""+url+"\">"+inputField.value+"</a>";
		}
		nomeArray[i] = inputField.id;
		valorArray[i] = inputField.value;
	}
	//Chama a função que salvará no servidor usando AJAX
	salvar(nomeArray, valorArray, servlet);
	//Troca a função de 'salvarLinha' para 'editarLinha'
	linkSalvar.onclick = function() {
		editarLinha(this, servlet);
	};
}

function excluirLinha(linkExcluir, servlet){
	if(confirm("Tem certeza que deseja excluir esse registro?")){
		//Pega a célula do link excluir
		var tdExcluir = linkExcluir.parentNode;
		//Pega codigo do item a ser excluido
		var codigo = tdExcluir.getElementsByTagName("input")[0].value;
		//Pega a linha a ser excluida
		var trExcluir = tdExcluir.parentNode;
		//Pega a tabela (na verdade, pega a seção tbody da tabela)
		var tableExcluir = trExcluir.parentNode;
		//Pega o objeto XML
		xmlhttp = CreateHTTPRequestObject();
		if(xmlhttp){
			xmlhttp.open("POST", servlet, true);
			xmlhttp.onreadystatechange = handleServerResponse;
			xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
			var parametros = "cmd="+servlet+"&"+"codigo="+codigo;
			xmlhttp.send(parametros);
		}
		tableExcluir.removeChild(trExcluir);
		//Atualiza o paginator
		var quantLinhas = tableExcluir.getElementsByTagName("tr").length - 1;
		var idTabela = tableExcluir.parentNode.id;
		$('#smart-paginator').smartpaginator({ 	totalrecords: quantLinhas,
			recordsperpage: 10,
			length: 10,
			initval: 0,
			next: 'Próxima',
			prev: 'Anterior',
			first: 'Primeira',
			last: 'última',
			go: 'Ir',
			theme: 'brown',
			datacontainer: idTabela,
			dataelement: 'tr'
		});
	}
}

//Função que usa ajax para salvar a linha
function salvar(nomeArray, valorArray, servlet){
	xmlhttp = CreateHTTPRequestObject();
	if(xmlhttp){
		var parametros = "cmd="+servlet+"&";
		for(var i=0; i<nomeArray.length; i++){
			if(i == nomeArray.length - 1){
				parametros += nomeArray[i] + "=" + encodeURIComponent(valorArray[i]);
			}
			else{
				parametros += nomeArray[i] + "=" + encodeURIComponent(valorArray[i]) + "&";
			}
		}
		xmlhttp.open("GET", servlet+"?"+parametros, true);
		xmlhttp.onreadystatechange = handleServerResponse;
		xmlhttp.setRequestHeader('Content-Type', 'application/x-www-form-urlencoded');
		xmlhttp.send(null);
	}
}

//function getXMLObject()  //XML OBJECT
//{
//   var xmlHttp = false;
//   try {
//     xmlHttp = new ActiveXObject("Msxml2.XMLHTTP"); // For Old Microsoft Browsers
//   }
//   catch (e) {
//     try {
//       xmlHttp = new ActiveXObject("Microsoft.XMLHTTP"); // For Microsoft IE 6.0+
//     }
//     catch (e2) {
//       xmlHttp = false;  // No Browser accepts the XMLHTTP Object then false
//     }
//   }
//   if (!xmlHttp && typeof XMLHttpRequest != 'undefined') {
//     xmlHttp = new XMLHttpRequest();        //For Mozilla, Opera Browsers
//   }
//   return xmlHttp;  // Mandatory Statement returning the ajax object created
//}
//Função que aguarda o resultado da requisição
function handleServerResponse() {
	if (xmlhttp.readyState == 4) {
		if(xmlhttp.status == 200) {
			var xmlDoc = ParseHTTPResponse(xmlhttp);
			var tipo = xmlDoc.getElementsByTagName("tipo")[0].firstChild.nodeValue;
			var mensagem = xmlDoc.getElementsByTagName("mensagem")[0].firstChild.nodeValue;
			document.getElementById("mensagem2").innerHTML = mensagem;
			document.getElementById("mensagem2").style.display = "block";
			if(tipo == "erro"){
				var t = setTimeout("atualizarPagina()", 5000);
			}
		}
		else {
			document.getElementById("mensagem2").innerHTML ="Houve um erro na requisição AJAX! Verifique seu navegador!";
			document.getElementById("mensagem2").style.display = "block";
		}
	}
}
function atualizarPagina(){
	window.location.reload();
}
