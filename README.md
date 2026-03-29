# 🛒 E-commerce API - Spring Boot

API REST desenvolvida com Java e Spring Boot para simular um sistema de e-commerce completo, com foco em aprendizado prático e aplicação de boas práticas de desenvolvimento backend.

---

## 🚀 Sobre o projeto

Este projeto foi desenvolvido com o objetivo de consolidar conhecimentos em:

* Construção de APIs REST
* Modelagem de dados
* Regras de negócio reais
* Segurança com JWT
* Boas práticas com Spring Boot
* Testes automatizados

Embora seja um projeto de estudo, ele foi construído com uma arquitetura sólida e funcionalidades próximas a aplicações reais utilizadas no mercado.

---

## 🧠 Funcionalidades

* 📦 CRUD de produtos (com suporte a imagem via URL)
* 🗂️ CRUD de categorias
* 📍 CRUD de bairros (com taxa de entrega)
* 🛒 Criação de pedidos com:

  * múltiplos itens
  * cálculo automático de total
  * aplicação de cupom
  * taxa de entrega
* 🔄 Controle de status do pedido
* 🔍 Filtro, ordenação e paginação
* 🔐 Autenticação com JWT
* 🧾 Documentação interativa estilo Swagger

---

## 🧪 Testes automatizados

O projeto possui testes unitários cobrindo as principais regras de negócio:

* ✔️ PedidoService (cálculo, validações, cupons, regras)
* ✔️ ProdutoService (CRUD e validações)

Tecnologias utilizadas nos testes:

* JUnit 5
* Mockito
* Spring Boot Test

---

## 🔐 Autenticação

A API utiliza autenticação via JWT.

### Login

```http id="login01"
POST /auth/login
```

```json id="login02"
{
  "username": "admin",
  "password": "123"
}
```

### Uso do token

```http id="login03"
Authorization: Bearer SEU_TOKEN
```

---

## 📚 Documentação da API

A documentação interativa pode ser acessada em:

```id="doc01"
http://localhost:8080/doc.html
```

---

## 🏗️ Tecnologias utilizadas

* Java 17
* Spring Boot
* Spring Data JPA
* MySQL
* JWT (JSON Web Token)
* Lombok

---

## 📂 Estrutura do projeto

```id="estrutura01"
controller/
service/
repository/
model/
dto/
exception/
security/
config/
```

---

## ⚙️ Como rodar o projeto

### 1. Clone o repositório

```bash id="clone01"
git clone https://github.com/seu-usuario/ecommerce-api.git
```

---

### 2. Configurar variáveis locais 🔐

Este projeto utiliza um arquivo local para armazenar credenciais sensíveis.

Crie o arquivo:

```id="config01"
src/main/resources/application-dev.properties
```

Use como base:

```id="config02"
src/main/resources/application-dev.example.properties
```

---

### Exemplo:

```properties id="config03"
spring.datasource.url=jdbc:mysql://localhost:3306/delivery
spring.datasource.username=SEU_USUARIO
spring.datasource.password=SUA_SENHA

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

jwt.secret=SUA_CHAVE_JWT
```

---

### ⚠️ Importante

* O arquivo `application-dev.properties` **não é versionado**
* Contém dados sensíveis (banco e JWT)
* Cada ambiente deve ter sua própria configuração

---

### 3. Executar a aplicação

```bash id="run01"
./mvnw spring-boot:run
```

---

## 🌍 Deploy

A API está preparada para deploy em plataformas como:

* Render
* Railway

Para produção, utilize variáveis de ambiente para:

* `spring.datasource.*`
* `jwt.secret`

---

## ⚠️ Observações importantes

* O projeto não utiliza controle de estoque (controle via campo `ativo`)
* Estruturado com boas práticas desde o início para evitar refatorações futuras
* Documentação customizada para contornar limitações do Swagger em versões recentes do Spring Boot

---

## 👨‍💻 Autor

Desenvolvido por Gustavo 🚀
