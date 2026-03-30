# usercheck

API REST para verificar se um associado pode votar com base no CPF informado.

## Tecnologias

- Java 17
- Spring Boot 4
- Caelum Stella (validação de CPF)
- Maven

## Como executar

### Localmente

```bash
mvn spring-boot:run
```

### Com Docker

```bash
docker build -t usercheck .
docker run -p 8090:8090 --name usercheck usercheck
```

Depois de criado o container

```bash
docker start usercheck
```

A aplicação sobe na porta `8090`.

## Configuração de porta

A porta padrão é `8090`, definida em `src/main/resources/application.properties`.

**Localmente**, edite o arquivo:

```properties
server.port=8090
```

Ou passe a propriedade na linha de comando, sem precisar alterar o arquivo:

```bash
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=9000
```

**Com Docker**, remapeie a porta no `docker run` e informe a nova porta via variável de ambiente:

```bash
docker run -p 9000:9000 -e SERVER_PORT=9000 --name usercheck usercheck
```

## Endpoint

### Verificar status de voto

```
GET /users/{cpf}
```

**Parâmetro de rota:**

| Parâmetro | Tipo   | Descrição                        |
|-----------|--------|----------------------------------|
| `cpf`     | string | CPF do associado (somente dígitos) |

**Respostas:**

| Status | Descrição                        | Corpo                              |
|--------|----------------------------------|------------------------------------|
| 200    | CPF válido                       | `{ "status": "ABLE_TO_VOTE" }` ou `{ "status": "UNABLE_TO_VOTE" }` |
| 404    | CPF inválido                     | `{ "message": "CPF inválido: ..." }` |

**Exemplo:**

```bash
curl http://localhost:8090/users/52998224725
```

```json
{ "status": "ABLE_TO_VOTE" }
```

## Testes

```bash
mvn test
```
