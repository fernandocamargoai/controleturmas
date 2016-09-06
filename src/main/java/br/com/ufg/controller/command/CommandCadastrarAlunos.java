package br.com.ufg.controller.command;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.csvreader.CsvReader;

import br.com.ufg.model.bean.Aluno;
import br.com.ufg.model.bean.Turma;
import br.com.ufg.model.dao.AlunoDAO;

public class CommandCadastrarAlunos implements Command {

	private AlunoDAO dao;
	
	public CommandCadastrarAlunos(AlunoDAO dao){
		super();
		this.dao = dao;
	}

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("nocmd", true);
		if(ServletFileUpload.isMultipartContent(request)){
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(1024 * 1024); //1MB
			try {
				List<FileItem> items = upload.parseRequest(request);
				Iterator<FileItem> iterator = items.iterator();
				int contadorErros = 0;
				int contadorAcertos = 0;
				while(iterator.hasNext()){
					FileItem file = iterator.next();
					
					if(!file.isFormField()){
						CsvReader csvReader = new CsvReader(file.getInputStream(), ';', Charset.forName("UTF-8"));
						while(csvReader.readRecord()){
							String matricula = csvReader.get(0);
							String nome = csvReader.get(1);
							String email = csvReader.get(2);
							try{
								if(validarMatricula(matricula) && validarNome(nome) && validaEmail(email) && dao.notExists((Turma)request.getSession().getAttribute("turma"), matricula)){
									Aluno aluno = new Aluno();
									aluno.setMatricula(Integer.parseInt(matricula));
									aluno.setNome(nome);
									aluno.setEmail(email);
									aluno.setCodigoTurma(((Turma)request.getSession().getAttribute("turma")).getCodigo());

									dao.cadastrar(aluno);
									contadorAcertos++;
								}
								else{
									contadorErros++;
								}
							}
							catch(SQLException e){
								e.printStackTrace();
								request.setAttribute("mensagem", "Houve um problema com o banco de dados! Tente mais tarde ou entre em contato com a administração!");
								return "turma?codigoTurma="+((Turma)request.getSession().getAttribute("turma")).getCodigo();
							}
						}
					}
				}
				if(contadorAcertos == 0 && contadorErros != 0){
					request.setAttribute("mensagem", "Nenhum dos registros inseridos no .csv foram validados! Verifique se estão na ordem correta, se os nomes estão completos e não ultrapassa 60 caracteres, se a matrícula não ultrapassa 6 caracteres e é numérica, se o e-mail é válido e se esse aluno já foi inserido.");
				}
				else{
					if(contadorAcertos == 0 && contadorErros == 0){
						request.setAttribute("mensagem", "Não há registros no arquivo enviado!");
					}
					else{
						if(contadorAcertos != 0 && contadorErros != 0){
							request.setAttribute("mensagem", "Arquivo submetido com sucesso! Porém, apenas " + contadorAcertos + " alunos foram inseridos com sucesso. Houveram " + contadorErros + " registros não validados ou repetidos!");
						}
						else{
							if(contadorAcertos != 0 && contadorErros == 0){
								request.setAttribute("mensagem", "Arquivo submetido com sucesso! Todos os " + contadorAcertos + " alunos foram inseridos!");
							}
						}
					}
				}
			}
			catch(FileUploadBase.FileSizeLimitExceededException e){
				request.setAttribute("mensagem", "Seu arquivo excedeu o tamanho limite de 1MB!");
				e.printStackTrace();
			}
			catch (FileUploadException e) {
				request.setAttribute("mensagem", "Houve um problema ao submeter seu arquivo!");
				e.printStackTrace();
			} catch (IOException e) {
				request.setAttribute("mensagem", "Houve um problema ao submeter seu arquivo!");
				e.printStackTrace();
			}
		}
		return "turma?codigoTurma="+((Turma)request.getSession().getAttribute("turma")).getCodigo();
	}

	private boolean validaEmail(String email) {
		if(email.length() > 60 || email.isEmpty()){
			return false;
		}
		Pattern pattern = Pattern.compile("^[\\w\\-\\.\\+]+\\@[a-zA-Z0-9\\.\\-]+\\.[a-zA-z0-9]{2,4}$");
		Matcher matcher = pattern.matcher(email);
		
		return matcher.matches();
	}

	private boolean validarNome(String nome) {
		if(nome.length() > 60 && nome.isEmpty()){
			return false;
		}
		if(nome.split(" ").length < 2){
			return false;
		}
		return true;
	}

	private boolean validarMatricula(String matricula) {
		if(matricula.length() > 6 && matricula.isEmpty()){
			return false;
		}
		Pattern pattern = Pattern.compile("^[0-9]+$");
		Matcher matcher = pattern.matcher(matricula);
		
		return matcher.matches();
	}


}
