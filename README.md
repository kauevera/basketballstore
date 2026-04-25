# Basketball Store

Aplicação web de e-commerce voltada para produtos de basquete. Permite que usuários se cadastrem, naveguem pelo catálogo, adicionem produtos ao carrinho e realizem pedidos.

## Funcionalidades

- Cadastro e login de usuários com autenticação via JWT
- Listagem de produtos com filtro por nome e categoria, com paginação
- Carrinho de compras e finalização de pedido
- Seleção de forma de pagamento no momento da compra
- Confirmação de pagamento e cancelamento de pedidos
- Visualização de pedidos separados por status: em andamento, finalizados e cancelados

## Stack tecnológica

**Backend**
- Java 17 com Spring Boot 4
- Spring Security + JWT (JJWT)
- Spring Data JPA + Hibernate
- SQLite
- Maven / Lombok

**Frontend**
- HTML, CSS e JavaScript puro
- Nginx (servidor de arquivos estáticos)

**Infraestrutura**
- Docker e Docker Compose

## Como subir a aplicação

Pré-requisito: ter o [Docker](https://docs.docker.com/get-docker/) instalado.

```bash
git clone <url-do-repositorio>
cd basketballstore
docker compose up --build
```

Após a inicialização:

- Frontend: http://localhost
- API: http://localhost:8080
