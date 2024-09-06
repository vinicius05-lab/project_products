# Projeto de API com Spring Boot e MySQL

## Sobre o Projeto
Este projeto é uma API desenvolvida com Spring Boot que utiliza MySQL como banco de dados. A API permite o cadastro de usuários, login, além de operações CRUD (Create, Read, Update, Delete) para categorias e produtos. Neste projeto também há testes unitários para os serviços de categorias, produtos e usuários.

# Sobre o funcionamento da API:
Para que esta API funcione corretamente na sua máquina você deve realizar as configurações corretas do banco de dados mysql no "application.properties":

- **Banco de Dados MySQL:**
   - Configure o acesso ao seu banco de dados MySQL no arquivo `application.properties`.
   - Exemplo:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost/seu_banco_de_dados
     spring.datasource.username=seu_usuario
     spring.datasource.password=sua_senha
     ```

# Sobre o envio de requisição:
As requisições devem ser feitas nos seguintes formatos JSON:

## Requisição para cadastro de usuário:
```json
{
    "name": "user",
    "email": "user@exemple.com",
    "password": "123user"
}
```
## Requisição para efetuar login:
```json
{
    "email": "user@exemple.com",
    "password": "123user"
}
```

## Requisição para cadastro e atualização de uma categoria:
```json
{
    "name": "Smartphones"
}
```
## Requisição para cadastro e atualização de uma produto:
```json
{
    "name": "Galaxy S21",
    "description": "Samsung smartphone with 128GB storage",
    "price": 799.99,
    "quantity": 50,
    "categoryId": 1
}
```
# Obs:
- Todos os usuários por padrão são salvo no banco de dados com a role de ADMIN:

```java
public User(UserRequest data){
        this.name = data.name();
        this.email = data.email();
        this.password = data.password();
        this.role = UserRole.ADMIN; //Para mudar é só trocar UserRole.ADMIN para UserRole.USER
}
```
Nota: Em uma aplicação real, não é recomendado salvar os usários como administradores, pois eles terão o poder de adicionar, deletar e atualizar produtos e categorias.
Coloquei ADMIN como padrão por ser apenas um projeto individual e para facilitar a vida quem quer usar e testar a aplicação de forma completa, realizando coisas que só o administrador poderia fazer.
