# Controle de Turmas - UFG

Esse projeto foi executado em 2011 (quando estava começando a desenvolver para web com Java) com o fim de servir de ferramenta para os professores da UFG. Ele foi proposto como trabalho final da disciplina de DSOO. Os requisitos desse projeto encontram-se no arquivo "Requisitos do Projeto.rar".

OBS: A versão do JasperReports foi atualizada em relação à versão original utilizada no projeto. Além disso, o projeto foi migrado para maven para facilitar sua portabilidade.

Este projeto foi adicionado ao GitHub para servir de exemplo aos meus alunos do curso de Java Web da 3Way. Espera-se que este seja migrado para o uso de Frameworks.

## Passos para rodar o projeto:

### Preparação do banco de dados

O banco de dados utilizado nesse projeto é o PostgreSQL. Portanto, é ncessário instalá-lo, criar o banco de dados de nome "ControleTurmas" e executar o arquivo "script-postgresql.sql" para que as tabelas sejam criadas. Por padrão, o projeto espera o usuário "postgres" com senha "123456". Para alterar, atualize o arquivo /src/main/resources/br/com/ufg/database/bancodedados.properties.

### Criação da cadastro do professor

Apenas execute o projeto em container de preferência e cadastre matrícula e senha de professor. A matrícula deve possuir 7 caracteres numéricos (padrão da UFG) e a senha deve conter mais de 6 caracteres. Isso será utilizado no login.

### Registro da turma padrão

Realizar login, criar nova disciplina (na UFG, as disciplinas costumam ter carga horária igual à quantidade de dias da semana multiplicada por 32h) e criar uma nova turma dentro dessa disciplina. A seguir, executar os seguintes passos:

- Registrar dias da semana: esses são os dias da semana em que acontecerão as aulas.
- Registrar datas: essas são as datas de início e fim do semestre.
- Gerar datas: o sistema registrará todas as datas de aula entre o início e fim do semestre, considerando os dias de semana cadastrados.
- Cadastrar alunos: para isso, faça upload do arquivo alunos.csv.

### Registro de informações da turma

Depois disso, o professor poderá registrar as seguintes informações:

- Conteúdo: aqui será especificado o conteúdo apresentado em cada aula e também serão desmarcadas as datas que não houveram aula (feriado, falta do professor e afins).
- Presenças: aqui será registrada a presença de cada aluno para cada data de aula.
- Notas: antes de registrar notas, deve-se especificar os pesos de cada nota. Depois, os campos de notas para cada aluno estarão disponíveis.

### Relatórios

Após o preenchimento das informações, o professor poderá gerar os seguintes relatórios:

- Anexo do Diário de Classe: presente na tela de Conteúdo, demonstra o conteúdo apresentado em cada data de aula.
- Ficha de Frequência: presente na tela de Presenças, demonstra a presença dos alunos para cada data de aula.
- Relação de Notas: presenta na tela de notas, demonstra a relação de notas dos alunos, inclusive a % de presença do mesmo.

