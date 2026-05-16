# Basketball Store

E-commerce de produtos de basquete com catálogo público de produtos, carrinho de compras, gestão de pedidos e autenticação de usuários.

---

## Funcionalidades

- Catálogo de produtos acessível sem login, com filtro por nome e categoria
- Cadastro e login de usuários com JWT
- Carrinho de compras local (localStorage)
- Compra individual ("Comprar agora") ou em lote via carrinho
- Gestão de pedidos: visualização, pagamento e cancelamento
- Login exigido apenas ao interagir com o carrinho ou finalizar compra

---

## Arquitetura

```
basketballstore/
├── backend/          # API REST — Java 17 + Spring Boot 4
└── frontend/         # SPA estática — HTML + CSS + JS puro servida por Nginx
```

### Backend

Camadas:

```
controllers/   → recebe requisições HTTP e retorna respostas
services/      → regras de negócio (validações, fluxo de pedido, estoque)
repositories/  → acesso ao banco via Spring Data JPA
models/        → entidades JPA mapeadas para tabelas SQLite
dto/           → objetos de entrada/saída das APIs
config/        → Spring Security, filtro JWT, CORS
utils/         → JwtUtil (geração e validação de tokens)
```

Tecnologias:

| Tecnologia | Uso |
|---|---|
| Java 17 | Linguagem |
| Spring Boot 4.0.2 | Framework web |
| Spring Security (stateless) | Autenticação via JWT |
| Spring Data JPA + Hibernate 6 | ORM |
| SQLite | Banco de dados embutido |
| JJWT 0.12.6 | Geração e validação de JWT |
| Maven | Build e dependências |
| Docker (eclipse-temurin:21) | Empacotamento |

#### Endpoints principais

| Método | Rota | Auth | Descrição |
|---|---|---|---|
| POST | `/users/register` | Não | Cadastro de usuário |
| POST | `/users/login` | Não | Login, retorna JWT |
| GET | `/products` | Não | Lista todos os produtos |
| GET | `/categories` | Não | Lista categorias |
| GET | `/payment-methods` | Sim | Lista formas de pagamento |
| GET | `/orders/user/{id}` | Sim | Pedidos do usuário |
| POST | `/orders/create` | Sim | Cria pedido e debita estoque |
| PATCH | `/orders/{id}/pay` | Sim | Confirma pagamento |
| PATCH | `/orders/{id}/cancel` | Sim | Cancela pedido |

### Frontend

SPA sem framework, servida por Nginx como arquivos estáticos. O Nginx também atua como reverse proxy, encaminhando `/api/*` para o backend.

```
frontend/
├── nginx.conf
├── static/
│   ├── css/          → estilos por tela
│   └── js/
│       ├── auth/     → login, registro, JWT no localStorage
│       ├── cart/     → carrinho em localStorage
│       ├── orders/   → tela de pedidos
│       ├── products/ → dashboard de produtos
│       ├── toast.js  → notificações customizadas
│       └── pages_redirect.js
└── templates/
    ├── auth/         → login.html, register.html
    ├── orders/       → orders.html
    └── products/     → dashboard.html
```

#### Fluxo de autenticação

O token JWT é armazenado no `localStorage` após login/cadastro. Todas as requisições autenticadas enviam `Authorization: Bearer <token>` no header. O backend valida o token a cada requisição via `JwtAuthenticationFilter`.

---

## Como subir o projeto

### Pré-requisitos

- [Docker](https://www.docker.com/) e Docker Compose instalados

### Subir com Docker Compose

```bash
# Clone o repositório
git clone <url-do-repositorio>
cd basketballstore

# Suba os containers
docker compose up --build
```

Acesse em: **http://localhost**

O banco de dados é criado automaticamente na primeira execução e populado com produtos de exemplo via `data.sql`.

### Variáveis de ambiente

| Variável | Padrão | Descrição |
|---|---|---|
| `JWT_SECRET` | `basketballstore-secret-key-...` | Chave HMAC para assinar os tokens JWT (mínimo 256 bits) |

Para definir uma chave própria em produção:

```bash
JWT_SECRET=sua-chave-secreta-aqui docker compose up --build
```

### Parar o projeto

```bash
docker compose down
```

Para apagar também os dados do banco:

```bash
docker compose down -v
```

---

## Banco de dados

O SQLite é gerenciado pelo Hibernate com `ddl-auto=update` — as tabelas são criadas ou atualizadas automaticamente ao iniciar o backend. O arquivo `.db` é persistido em um volume Docker (`db-data`), sobrevivendo a reinicializações do container.

Para acessar o banco enquanto o container estiver rodando:

```bash
docker exec -it <nome-do-container-backend> sh
# dentro do container:
sqlite3 /app/data/basketballstore.db
```
