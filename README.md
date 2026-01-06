# Task Manager API

[![Build Status](https://img.shields.io/github/actions/workflow/status/kleberson154/TaskManagerAPI/maven.yml?branch=main)](https://github.com/kleberson154/TaskManagerAPI/actions)  
[![Deploy Status](https://img.shields.io/badge/deploy-railway-brightgreen)](https://taskmanagerapi-production-139f.up.railway.app)  
[![License](https://img.shields.io/badge/license-MIT-blue)](LICENSE)

API RESTful para **gerenciamento de tarefas**, com **autenticação JWT**, **Swagger** e deploy automático via **Railway**.

---

## 🏗 Arquitetura

- **Backend:** Java + Spring Boot 3  
- **Segurança:** Spring Security + JWT  
- **Banco de Dados:** PostgreSQL (via JPA/Hibernate)  
- **Deploy:** Railway (Docker)  
- **Testes:** JUnit 5 + Mockito  

---

## 🔐 Autenticação JWT

- Usuário faz login → recebe **JWT token**  
- Token enviado no header `Authorization: Bearer <token>`  
- `JwtAuthFilter` valida o token em cada requisição  
- Tokens têm **expiração automática**, garantindo segurança  

> **Vantagem:** Stateless, seguro e escalável.

---

## 📄 Documentação Swagger

A documentação interativa da API está disponível em:

[**Swagger UI - Task Manager API**](https://taskmanagerapi-production-139f.up.railway.app/swagger-ui/index.html)  

- Permite testar todos os endpoints diretamente pelo navegador  
- Mostra parâmetros, respostas e códigos HTTP  

---

## ⚙ CI/CD

O projeto possui **integração contínua e deploy automático**:

1. **GitHub Actions:**  
   - Executa testes (`mvn test`)  
   - Constrói o JAR (`mvn package`)  
   - Cria imagem Docker  

2. **Railway:**  
   - Recebe a imagem e faz deploy automático  
   - Variáveis de ambiente configuradas (`PGHOST`, `PGUSER`, `PGPASSWORD`, `PGDATABASE`, `JWT_SECRET`, `JWT_EXPIRATION`)  
   - Porta definida automaticamente (`PORT`)  

> Benefícios: testes automáticos e deploy rápido, sem intervenção manual.

---

## 🌐 Deploy Online

A API está disponível em produção:

[**Task Manager API - Railway**](https://taskmanagerapi-production-139f.up.railway.app)  

**Principais endpoints:**

| Método | Endpoint        | Descrição                    |
|--------|-----------------|------------------------------|
| POST   | `/auth/register`| Cadastro de usuário          |
| POST   | `/auth/login`   | Login e geração do JWT       |
| GET    | `/tasks`        | Listar tarefas (JWT)         |
| POST   | `/tasks`        | Criar tarefa (JWT)           |
| PUT    | `/tasks/{id}`   | Atualizar tarefa (JWT)       |
| DELETE | `/tasks/{id}`   | Deletar tarefa (JWT)         |

> Para endpoints protegidos, inclua: `Authorization: Bearer <token>`

---

## 📦 Tecnologias

- Java 17  
- Spring Boot 3  
- Spring Security + JWT  
- PostgreSQL  
- Swagger UI  
- Maven  
- Docker  
- GitHub Actions  
- Railway (Deploy)
