CREATE TABLE Professores (
  matricula INTEGER NOT NULL,
  senha VARCHAR(10) NOT NULL,
  nome VARCHAR(60) NOT NULL,
  PRIMARY KEY(matricula)
);

CREATE INDEX professor_senha_index ON Professores (senha);

CREATE TABLE Disciplinas (
  codigo SERIAL,
  matriculaProfessor INTEGER NOT NULL,
  cargaHoraria INTEGER  NOT NULL,
  nome VARCHAR(100) NULL,
  PRIMARY KEY(codigo),
  FOREIGN KEY(matriculaProfessor)
    REFERENCES Professores(matricula)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE INDEX disciplina_matriculaProfessor_index ON Disciplinas (matriculaProfessor);

CREATE TABLE Turmas (
  codigo SERIAL,
  codigoDisciplina INTEGER  NOT NULL,
  descricao VARCHAR(3) NOT NULL,
  semestre INTEGER NOT NULL,
  ano INTEGER NOT NULL,
  curso VARCHAR(30) NOT NULL,
  dataInicio DATE,
  dataFim DATE,
  PRIMARY KEY(codigo),
  FOREIGN KEY(codigoDisciplina)
    REFERENCES Disciplinas(codigo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE INDEX turmas_codigoDisciplina_index ON Turmas (codigoDisciplina);

CREATE TABLE Datas (
  idData SERIAL,
  codigoTurma INTEGER NOT NULL,
  conteudo VARCHAR(200) NULL,
  dia DATE NOT NULL,
  aulaNormal BOOL NOT NULL,
  PRIMARY KEY(idData),
  FOREIGN KEY(codigoTurma)
    REFERENCES Turmas(codigo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE INDEX datas_codigoTurma_index ON Datas (codigoTurma);

CREATE TABLE Alunos (
  codigo SERIAL,
  codigoTurma INTEGER NOT NULL,
  matricula INTEGER NOT NULL,
  nome VARCHAR(60) NOT NULL,
  email VARCHAR(60) NOT NULL,
  PRIMARY KEY(codigo),
  FOREIGN KEY(codigoTurma)
	REFERENCES Turmas(codigo)
	  ON DELETE CASCADE
	  ON UPDATE CASCADE
);

CREATE INDEX alunos_codigoTurma_index ON Alunos (codigoTurma);

CREATE TABLE DiasSemana (
  idDiaSemana SERIAL,
  codigoTurma INTEGER NOT NULL,
  diaSemana VARCHAR(3) NOT NULL,
  PRIMARY KEY(idDiaSemana),
  FOREIGN KEY(codigoTurma)
    REFERENCES Turmas(codigo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE INDEX diasSemana_codigoTurma_index ON DiasSemana (codigoTurma);

CREATE TABLE Presencas (
  idPresenca SERIAL,
  idData INTEGER NOT NULL,
  codigoAluno INTEGER NOT NULL,
  presenca BOOL NOT NULL,
  PRIMARY KEY(idPresenca),
  FOREIGN KEY(idData)
    REFERENCES Datas(idData)
      ON DELETE CASCADE
      ON UPDATE CASCADE,
  FOREIGN KEY(codigoAluno)
    REFERENCES Alunos(codigo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE INDEX presencas_idData_index ON Presencas (idData);
CREATE INDEX presencas_codigoAluno_index ON Presencas (codigoAluno);

CREATE TABLE Notas (
  idNota SERIAL,
  codigoAluno INTEGER NOT NULL,
  nota FLOAT NOT NULL,
  peso INTEGER NOT NULL,
  PRIMARY KEY(idNota),
  FOREIGN KEY(codigoAluno)
    REFERENCES Alunos(codigo)
      ON DELETE CASCADE
      ON UPDATE CASCADE
);

CREATE INDEX notas_codigoAluno_index ON Notas (codigoAluno);