Uma simples API de um estacionamento com 4 endpoints, feita com Spring Boot 1.5, Gradle e PostgreSQL, com deploy deito no Heroku.

Para rodar localmente, é preciso ter:
- Java 8 (jdk1.8)
- Gradle na versão 4.10 ou maior

Com esse ambiente configurado, basta rodar os comandos na pasta do projeto:
- ```gradle build ``` e ```gradle bootRun```(Linux)
- ```gradlew build ``` e ```gradlew bootRun```(Windows)

OBS: O projeto está apontando para o banco no *Heroku*, caso queira alterar para um banco local, basta criar um banco Postgresql na sua máquina e alterar o arquivo application.properties

Para executar os testes unitários, rode o comando:
- ```gradle test``` (Linux)
- ```gradlew test``` (Windows)

OBS: O application.properties dos testes está apontando para um banco local com nome 'estacionamento-teste', altere o usuário host e senha para utilizar.

Ou você pode utilizar a api hospedada no *Heroku* no host:
- https://estacionamento-teste.herokuapp.com + {endpoint desejado}

Para testar, pode utilizar os endpoint listados abaixo, com seus respectivos exemplos de envio e retorno:

- POST
/api/entrada
Envio:
```json
{
	"dataHora": "2019-11-08T10:00Z",
	"veiculo": {
		"placa": "NON2323",
		"cor": "azul",
		"modelo": "HB20"
	}
}
```

Retorno:
```json
{
  "id": 3,
  "tipo": "ENTRADA",
  "dataHora": "2019-11-08T10:00Z",
  "veiculo": {
    "id": 1,
    "placa": "NON2323",
    "cor": "azul",
    "modelo": "HB20"
  }
}
```

- POST
/api/saida
Envio:
```json
{
	"dataHora": "2019-11-08T10:00Z",
	"veiculo": {
		"placa": "NON2323",
		"cor": "azul",
		"modelo": "HB20"
	}
}
```

Retorno:
```json
{
  "id": 3,
  "tipo": "ENTRADA",
  "dataHora": "2019-11-08T10:00Z",
  "veiculo": {
    "id": 1,
    "placa": "NON2323",
    "cor": "azul",
    "modelo": "HB20"
  }
}
```

- GET
/api/preco?placa=NON2323
```json
{
  "valor": 1400,
  "id": 3,
  "entrada": {
    "id": 5,
    "tipo": "ENTRADA",
    "dataHora": "2019-11-09T12:00:00Z",
    "veiculo": {
      "id": 1,
      "placa": "NON2323",
      "cor": "azul",
      "modelo": "HB20"
    }
  },
  "saida": {
    "id": 6,
    "tipo": "SAIDA",
    "dataHora": "2019-11-09T16:00:00Z",
    "veiculo": {
      "id": 1,
      "placa": "NON2323",
      "cor": "azul",
      "modelo": "HB20"
    }
  },
  "veiculo": {
    "id": 1,
    "placa": "NON2323",
    "cor": "azul",
    "modelo": "HB20"
  }
  
}
```

- GET
/api/preco/relatorio
```json
[
  {
    "data": "2019-11-09T00:00:00Z",
    "faturamento": 1400
  },
  {
    "data": "2019-11-08T00:00:00Z",
    "faturamento": 1000
  }
]
```
