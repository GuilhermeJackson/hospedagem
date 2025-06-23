# Sistema de Hospedagem - API REST

Este projeto foi desenvolvido com Spring Boot para gerenciamento de hóspedes.

---

## Tecnologias Utilizadas
* Java 17 
* PostgreSQL
* Maven  

---

## Como Executar o Projeto

### 1. Clonar o repositório

```bash
git clone https://github.com/GuilhermeJackson/hospedagem.git
cd hospedagem
```
### 2. Configurar o banco postgres
Antes de iniciar o projeto, certifique-se de que o banco está rodando e acessível.

Nome do banco: hospedagem  
Usuário do banco: postgres  
Senha do banco: postgres  
```
spring.datasource.url=jdbc:postgresql://localhost:5432/hospedagem
spring.datasource.username=postgres
spring.datasource.password=postgres
```

### 3. Build do projeto

```bash
./mvnw clean install
```

### 4. Executar a aplicação

```bash
./mvnw spring-boot:run
```

A API será iniciada em: `http://localhost:8080`

---

## Testando com Postman

Você pode importar a [coleção do Postman](https://.postman.co/workspace/My-Workspace~c49d67c4-cd12-44f2-8535-ac1a68f14ec0/collection/12597678-3a96b345-3a79-4c2f-8ba4-538061fa68c4?action=share&creator=12597678) ou usar os exemplos abaixo. Caso o hiperlink nao funcione use o link abaixo.  

[https://.postman.co/workspace/My-Workspace~c49d67c4-cd12-44f2-8535-ac1a68f14ec0/collection/12597678-3a96b345-3a79-4c2f-8ba4-538061fa68c4?action=share&creator=12597678](https://.postman.co/workspace/My-Workspace~c49d67c4-cd12-44f2-8535-ac1a68f14ec0/collection/12597678-3a96b345-3a79-4c2f-8ba4-538061fa68c4?action=share&creator=12597678)

### Criar Hóspede

**POST** `http://localhost:8080/api/guests`

```json
{
  "name": "Maria Oliveira",
  "cpf": "98765432100",
  "phone": "(21) 98888-8888"
}
```

### Listar Hóspedes

**GET** `http://localhost:8080/api/guests`

### Buscar Hóspede
**GET** `http://localhost:8080/api/guests/{id}`

### Atualizar Hóspede

**PUT** `http://localhost:8080/api/guests/{id}`

```json
{
  "name": "Maria",
  "cpf": "98765432100",
  "phone": "(21) 97777-7777"
}
```

### Deletar Hóspede

**DELETE** `http://localhost:8080/api/guests/{id}`

___

### Criar Reserva

**POST** `http://localhost:8080/api/reserves`

```json
{
  "checkin": "2025-06-22T14:00:00",
  "checkout": "2025-06-25T12:00:00",
  "garage": true,
  "guest": {
    "id": "uuid-do-hospede"
  }
}
```

### Buscar Reservas por Nome/CPF/Telefone

**GET** `http://localhost:8080/api/reserves/search?params=maria`

### Listar Check-ins - hóspedes que estão no hotel

**GET** `http://localhost:8080/api/reserves/checkin`

### Listar Check-outs - hóspedes que ja não estão mais no hotel

**GET** `http://localhost:8080/api/reserves/checkout`

### Listar todas as reservas

**GET** `http://localhost:8080/api/reserves`
