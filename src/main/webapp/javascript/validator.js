function validarCadastro(formulario){
	var campoNome = document.getElementById("nome");
	var campoMatricula = document.getElementById("matricula");
	var campoSenha1 = document.getElementById("senha1");
	var campoSenha2 = document.getElementById("senha2");
	if(!notEmpty(campoNome)){
		setMensagem(formulario,"O nome é obrigatório para o cadastro!");
		return false;
	}
	if(!notEmpty(campoMatricula)){
		setMensagem(formulario,"A matrícula é obrigatória para o cadastro!");
		return false;
	}
	if(!notEmpty(campoSenha1)){
		setMensagem(formulario,"A senha é obrigatória para o cadastro!");
		return false;
	}
	if(!notEmpty(campoSenha2)){
		setMensagem(formulario,"Vocé deve repetir sua senha para completar o cadastro!");
		return false;
	}
	if(!lengthRestriction(campoNome, 10, 60)){
		setMensagem(formulario,"O nome deve conter entre 10 e 60 caracteres!");
		return false;
	}
	if(!isNumeric(campoMatricula)){		
		setMensagem(formulario,"A matrícula deve conter apenas números!");
		return false;		
	}
	if(parseFloat(campoMatricula.value) <= 0){
		setMensagem(formulario,"A matrícula deve ser maior que 0!");
		campoMatricula.focus();
		return false;
	}
	if(!lengthRestriction(campoMatricula, 7, 7)){
		setMensagem(formulario,"A matrícula deve conter 7 caracteres!");
		return false;
	}
	if(!lengthRestriction(campoSenha1, 6, 10)){
		setMensagem(formulario,"A senha deve conter entre 6 e 10 caracteres!");
		return false;
	}
	if(!segundoCampoRepete(campoSenha1, campoSenha2)){
		setMensagem(formulario,"A repetição da senha está incorreta!");
		return false;
	}
	return true;
}

function validarLogin(formulario){
	var campoMatricula = document.getElementById("matricula");
	var campoSenha = document.getElementById("senha");
	if(!notEmpty(campoMatricula)){
		setMensagem(formulario,"A matrícula é obrigatória!");
		return false;
	}
	if(!notEmpty(campoSenha)){
		setMensagem(formulario,"A senha é obrigatória!");
		return false;
	}
	if(!isNumeric(campoMatricula)){		
		setMensagem(formulario,"A matrícula deve conter apenas números!");
		return false;		
	}
	if(parseFloat(campoMatricula.value) <= 0){
		setMensagem(formulario,"A matrícula deve ser maior que 0!");
		campoMatricula.focus();
		return false;
	}
	if(!lengthRestriction(campoMatricula, 7, 7)){
		setMensagem(formulario,"A matrícula deve conter 7 caracteres!");
		return false;
	}
	if(!lengthRestriction(campoSenha, 6, 10)){
		setMensagem(formulario,"A senha deve conter entre 6 e 10 caracteres!");
		return false;
	}
	return true;
}

function validarCadastroDisciplina(formulario){
	var campoNome = document.getElementById("nome");
	var campoCargaHoraria = document.getElementById("cargaHoraria");
	if(!notEmpty(campoNome)){
		setMensagem(formulario,"O nome é obrigatório!");
		return false;
	}
	if(!notEmpty(campoCargaHoraria)){
		setMensagem(formulario,"A carga horária é obrigatória!");
		return false;
	}
	if(!lengthRestriction(campoNome, 0, 100)){
		setMensagem(formulario,"O nome deve ter no máximo 50 caractéres!");
		return false;
	}
	if(!isNumeric(campoCargaHoraria)){
		setMensagem(formulario,"A carga horária deve conter apenas números!");
		return false;		
	}
	return true;
}

function validarEditarDisciplina(nomeCampos){
	var campoNome;
	var campoCargaHoraria;
	for(var i=0; i<nomeCampos.length; i++){
		if(nomeCampos[i].indexOf("nome", 0) != -1){
			campoNome = document.getElementById(nomeCampos[i]);
		}
		if(nomeCampos[i].indexOf("cargaHoraria", 0) != -1){
			campoCargaHoraria = document.getElementById(nomeCampos[i]);
		}
	}
	if(!notEmpty(campoNome)){
		document.getElementById("mensagem2").innerHTML = "O nome é obrigatório!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!notEmpty(campoCargaHoraria)){
		document.getElementById("mensagem2").innerHTML = "A carga horária é obrigatória!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!lengthRestriction(campoNome, 0, 100)){
		document.getElementById("mensagem2").innerHTML = "O nome deve conter no máximo 50 caractéres!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!isNumeric(campoCargaHoraria)){		
		document.getElementById("mensagem2").innerHTML = "A carga horária deve conter apenas números!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	return true;
}

function validarCadastroTurma(formulario){
	var campoDescricao = document.getElementById("descricao");
	var campoCurso = document.getElementById("curso");
	var campoAno = document.getElementById("ano");
	var campoSemestre = document.getElementById("semestre");
	if(!notEmpty(campoDescricao)){
		setMensagem(formulario,"A turma é obrigatória!");
		return false;
	}
	if(!notEmpty(campoCurso)){
		setMensagem(formulario,"O curso é obrigatório!");
		return false;
	}
	if(!notEmpty(campoAno)){
		setMensagem(formulario,"O ano é obrigatório!");
		return false;
	}
	if(!notEmpty(campoSemestre)){
		setMensagem(formulario,"O semestre é obrigatório!");
		return false;
	}
	if(!lengthRestriction(campoDescricao, 0, 3)){
		setMensagem(formulario,"A turma deve conter no máximo 3 caracteres!");
		return false;
	}
	if(!lengthRestriction(campoCurso, 0, 30)){
		setMensagem(formulario,"O curso deve conter no máximo 30 caracteres!");
		return false;
	}
	if(!lengthRestriction(campoAno, 4, 4)){
		setMensagem(formulario,"O ano deve conter 4 caracteres!");
		return false;
	}
	if(!lengthRestriction(campoSemestre, 1, 1)){
		setMensagem(formulario,"O semestre deve conter 1 caracter (1 ou 2)!");
		return false;
	}
	if(!isNumeric(campoAno)){
		setMensagem(formulario,"O ano deve conter apenas números!");
		return false;
	}
	if(!isNumeric(campoSemestre)){
		setMensagem(formulario,"O semestre deve conter apenas números!");
		return false;
	}
	if(campoSemestre.value != "1" && campoSemestre.value != "2"){
		campoSemestre.focus();
		setMensagem(formulario, "O semestre deve conter 1 ou 2!");
		return false;
	}
	return true;
}

function validarEditarTurma(nomeCampos){
	var campoDescricao;
	var campoCurso;
	var campoAno;
	var campoSemestre;
	for(var i=0; i<nomeCampos.length; i++){
		if(nomeCampos[i].indexOf("descricao", 0) != -1){
			campoDescricao = document.getElementById(nomeCampos[i]);
		}
		if(nomeCampos[i].indexOf("curso", 0) != -1){
			campoCurso = document.getElementById(nomeCampos[i]);
		}
		if(nomeCampos[i].indexOf("ano", 0) != -1){
			campoAno = document.getElementById(nomeCampos[i]);
		}
		if(nomeCampos[i].indexOf("semestre", 0) != -1){
			campoSemestre = document.getElementById(nomeCampos[i]);
		}
	}
	if(!notEmpty(campoDescricao)){
		document.getElementById("mensagem2").innerHTML = "A descrição é obrigatório!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!notEmpty(campoCurso)){
		document.getElementById("mensagem2").innerHTML = "O curso horária é obrigatório!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!notEmpty(campoAno)){
		document.getElementById("mensagem2").innerHTML = "O ano é obrigatório!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!notEmpty(campoSemestre)){
		document.getElementById("mensagem2").innerHTML = "O semestre é obrigatório!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!lengthRestriction(campoDescricao, 0, 30)){
		document.getElementById("mensagem2").innerHTML = "A descrição deve conter no máximo 30 caracteres!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!lengthRestriction(campoCurso, 0, 30)){
		document.getElementById("mensagem2").innerHTML = "O curso deve conter no máximo 30 caracteres!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!lengthRestriction(campoAno, 4, 4)){
		document.getElementById("mensagem2").innerHTML = "O ano deve conter 4 caracteres!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!lengthRestriction(campoSemestre, 1, 1)){
		document.getElementById("mensagem2").innerHTML = "O semestre deve conter 1 caracter (1 ou 2)!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!isNumeric(campoAno)){
		document.getElementById("mensagem2").innerHTML = "O ano deve conter apenas números!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!isNumeric(campoSemestre)){
		document.getElementById("mensagem2").innerHTML = "O semestre deve conter apenas números!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(campoSemestre.value != "1" && campoSemestre.value != "2"){
		campoSemestre.focus();
		document.getElementById("mensagem2").innerHTML = "O semestre deve conter 1 ou 2!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	return true;
}

function validaArquivoCsv(campo){  
	var tamanhoString = campo.value.length;
	var extensao = campo.value.substr(tamanhoString - 4,tamanhoString);
	if (tamanhoString == 0 ){
		setMensagem("", "O arquivo é obrigatário!");
		return false;
	}
	else{
		var ext = new Array('.csv','.CSV');
		for(var i = 0; i < ext.length; i++){
			if (extensao == ext[i]){
				flag = "ok";
				break;
			}
			else{
				flag = "erro";
			}
		}
		if(flag=="erro"){  
			setMensagem("", "O arquivo deve estar na extenção .csv!");
			campo.value="";
			return false;
		}
	}
	return true;
}

function validarEditarAluno(nomeCampos){
	var campoMatricula;
	var campoNome;
	var campoEmail;
	for(var i=0; i<nomeCampos.length; i++){
		if(nomeCampos[i].indexOf("matricula", 0) != -1){
			campoMatricula = document.getElementById(nomeCampos[i]);
		}
		if(nomeCampos[i].indexOf("nome", 0) != -1){
			campoNome = document.getElementById(nomeCampos[i]);
		}
		if(nomeCampos[i].indexOf("email", 0) != -1){
			campoEmail = document.getElementById(nomeCampos[i]);
		}
	}
	if(!notEmpty(campoMatricula)){
		document.getElementById("mensagem2").innerHTML = "A matrícula é obrigatória!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!notEmpty(campoNome)){
		document.getElementById("mensagem2").innerHTML = "O nome é obrigatório!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!notEmpty(campoEmail)){
		document.getElementById("mensagem2").innerHTML = "O e-mail é obrigatório!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!lengthRestriction(campoMatricula, 0, 6)){
		document.getElementById("mensagem2").innerHTML = "A matrícula deve conter no máximo 30 caracteres!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!lengthRestriction(campoNome, 0, 60)){
		document.getElementById("mensagem2").innerHTML = "O nome deve conter no máximo 60 caracteres!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!lengthRestriction(campoEmail, 0, 60)){
		document.getElementById("mensagem2").innerHTML = "O e-mail deve conter no máximo 60 caracteres!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!isNumeric(campoMatricula)){
		document.getElementById("mensagem2").innerHTML = "A mtríla deve conter apenas números!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	if(!isAlphabet(campoNome)){
		document.getElementById("mensagem2").innerHTML = "O nome deve conter apenas letras!";
		document.getElementById("mensagem2").style.display = "block";
		return false;
	}
	return true;
}

function confirmarGeracao(numeroForm){
	if(confirm("Tem certeza que deseja gerar as datas de aula?")){
		document.forms[numeroForm].submit();
	}
}

//função que muda a mensagem da página
function setMensagem(formulario, mensagem){
	var mensagemElem = document.getElementById("mensagem");
	mensagemElem.innerHTML = mensagem;
	if(mensagemElem.style.display = "none"){
		mensagemElem.style.display = "block";
	}
}
//validações
function segundoCampoRepete(elem1, elem2){
	if(elem1.value == elem2.value){
		return true;
	}
	else{
		elem2.focus();
		return false;
	}
}
function emailValidator(elem){
	var emailExp = /^[\w\-\.\+]+\@[a-zA-Z0-9\.\-]+\.[a-zA-z0-9]{2,4}$/;
	if(elem.value.match(emailExp)){
		return true;
	}else{
		elem.focus();
		return false;
	}
}
function lengthRestriction(elem, min, max){
	var uInput = elem.value;
	if(uInput.length >= min && uInput.length <= max){
		return true;
	}else{
		elem.focus();
		return false;
	}
}
function isAlphanumeric(elem){
	var alphaExp = /^[a-zA-Z0-9\u00C0-\u017F .]+$/;
	if(elem.value.match(alphaExp)){
		return true;
	}else{
		elem.focus();
		return false;
	}
}
function isAlphabet(elem){
	var alphaExp = /^[a-zA-Z\u00C0-\u017F .]+$/;
	if(elem.value.match(alphaExp)){
		return true;
	}else{
		alert(helperMsg);
		elem.focus();
		return false;
	}
}
function isNumeric(elem){
	var numericExpression = /^[0-9]+$/;
	if(elem.value.match(numericExpression)){
		return true;
	}else{
		elem.focus();
		return false;
	}
}
function notEmpty(elem){
	if(elem.value.length == 0){
		elem.focus();
		return false;
	}
	return true;
}



