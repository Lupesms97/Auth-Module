# PROJETO - Collaborator Space

http://localhost:8081/swagger-ui/index.html#/


ReadMe compartilhado entre todos os modulos do espaço do colaborado

## 🚀 Como começar

Essas instruções permitirão que você obtenha uma cópia do projeto em operação na sua máquina local para fins de desenvolvimento e teste.

### 📋 Pré-requisitos

O back-end faz uso do Java SE 17.
O front-end faz uso do Angular CLI version 16.1.8.

### 🔧 Instalação

Faça o download da aplicação e abra na IDE de sua preferência, para melhor desempenho sugiro IntelliJ e VSCode

## ⚙️ Executando

ContentModule-CollaboratorSpace - Vá em "ContentModule-CollaboratorSpace/src/main/java/com/content/module" e rode a aplicação em "ModuleApplication.java".

ContentModule-CollaboratorSpace - Vá em "AuthenticationModule-CollaboratorSpace/src/main/java/com/developement/crm
/" e rode a aplicação em "BackEndCrmApplication.java".

Rode `ng build` para fazer a build do projeto. O artefato da build vai estar no `dist/collaborator-module` diretótiro.

# 💻 Funcionalidades da aplicação

## Cadastro de Usuários

A aplicação permite o cadastro de usuários com os seguintes dados:

- Nome de Usuário (Login)*
- Senha*
- E-mail*
- Nome*
- Papel (Role)
- Unidade*
- Data de Criação
- Data de Atualização
- Último Acesso

*Obrigatórios - input Usuario 


### Geração de Token

A aplicação gera tokens de autenticação para os usuários com base nas informações fornecidas.

### Login de Usuários

Os usuários podem fazer login na aplicação fornecendo seu nome de usuário (login) e senha. A autenticação é realizada usando o Spring Security.

### Atualização de Usuários

Os usuários podem atualizar informações pessoais, como nome, senha, etc.

### Validação de Token

A aplicação permite a validação de tokens de autenticação para garantir a segurança da autenticação.

### Serviço de Autenticação Personalizado

A aplicação inclui um serviço de autenticação personalizado para carregar detalhes do usuário com base no nome de usuário (login)

##  Modulo de Eventos

### Listagem de Todos os Eventos

A API permite listar todos os eventos cadastrados na seguinte rota:

- GET /events/all

A lista de eventos é ordenada por data de início e pode ser paginada.

### Criação de Novos Eventos

A API permite a criação de novos eventos através da seguinte rota:

- POST /events/event

É necessário fornecer os detalhes do evento, incluindo título, data de início, data de término e cor.

### Salvamento de Múltiplos Eventos

A API também permite o salvamento de múltiplos eventos de uma só vez usando a rota:

- POST /events/saveMultipleEvents

Basta fornecer uma lista de eventos e eles serão salvos no banco de dados.

### Atualização de Eventos

Os eventos existentes podem ser atualizados utilizando a seguinte rota:

- PUT /events/update

É necessário fornecer os detalhes do evento a ser atualizado, incluindo o ID do evento, título, data de início, data de término e cor.

### Exclusão de Eventos

Eventos podem ser excluídos por meio da rota:

- DELETE /events/eventDeletion

Basta fornecer o ID do evento que deseja excluir.

##  Modulo de Posts

### Criação de Novos Posts

A API permite a criação de novos posts através da seguinte rota:

- POST /posts/post

É necessário fornecer os detalhes do post, incluindo título, conteúdo, resumo, data e URL da imagem.

### Atualização de Posts

Posts existentes podem ser atualizados usando a seguinte rota:

- POST /posts/atualizationPost

É necessário fornecer os detalhes do post a ser atualizado, incluindo o ID do post, título, conteúdo, resumo, data e URL da imagem.

### Exclusão de Posts

Posts podem ser excluídos por meio da rota:

- DELETE /posts/postDeletion

Basta fornecer o ID do post que deseja excluir.

### Salvamento de Múltiplos Posts

A API também permite o salvamento de múltiplos posts de uma só vez usando a rota:

- POST /posts/saveMultiplePosts

Forneça uma lista de posts e eles serão salvos no banco de dados.

### Busca de Posts por ID

Você pode buscar um post específico por ID usando a rota:

- GET /posts/idKey

### Listagem de Todos os Posts

A API permite listar todos os posts cadastrados na seguinte rota:

- GET /posts/allPosts

### Encontrar Posts com Títulos Iguais

Você pode encontrar posts com títulos iguais e removê-los usando a rota:

- GET /posts/findForEqualsId

### Validação de Token

A API oferece validação de token de autenticação usando a rota:

- GET /posts/allPostsValidation

Forneça um token de autenticação no cabeçalho para validar a operação.


## 🛠️ Construído com

* Java(TM) SE Runtime Environment
* Spring Boot 3
* Spring DATA
* Spring Cloud(OpenFeign)
* Spring Security
* JSON Web Token
* PostgreSQL | Mongo DB | H2(testes)
* Junit  e Mockito
* Clean Code
* Versionamento Semântico
  ************
* Angular
* Bootstrap 5.3v
* Rxjs
* Ngrx
* angular-calendar
* jwt-decode
* ngx-cookie-service
* ngx-pagination



## 👨‍💻 Desenvolvedor

Luis Felipe Mota
- [GitHub](https://github.com/lupesms97)
- [LinkedIn](https://www.linkedin.com/in/luis-felipe-mota-desenvolvedor-java)

## 📬 Contato

Para informações adicionais ou dúvidas, entre em contato via e-mail: lupesms97@gmail.com.

## 📝 Licença

Este projeto está licenciado sob a Licença MIT - consulte o arquivo LICENSE de cada modulo para obter detalhes.

