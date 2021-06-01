# Carlos Galves

Desafio Globo ID Devices

**Globo.com: coding challenge**

## Considerações Gerais

Você deverá usar este repositório como o principal do projeto. Todos os seus commits devem estar registrados aqui.

**Registre tudo**: Ideias que gostaria de implementar se tivesse mais tempo (explique como você as resolveria), decisões tomadas e seus porquês, arquiteturas testadas e os motivos de terem sido modificadas ou abandonadas.

Sinta-se livre para incluir ferramentas e bibliotecas open source.

## Sobre o desafio
Dado o seguinte Endpoint de uma listagem de produtos https://60885258a6f4a30017426369.mockapi.io/products nós iremos construir 2 aplicações para trabalhar com estes dados.

1 - Lib Android/iOS (de acordo com o perfil da Vaga)

O  objetivo dessa lib é acessar o Endpoint, consumir os dados  deste Endpoint e disponibilizar uma API de métodos que virão a ser consumidos por um produto. Aqui precisamos que você utilize a tecnologia nativa do sistema para o qual você está aplicando a vaga (Swift para iOS, Kotlin/Java para Android). Alguns métodos que esperamos que possuam na api da sua Lib:
- Método para listar todos os produtos.
- Método para listar o produto mais caro.
- Método para listar o produto mais barato.
- Método que receberá como parâmetro um preço e deve retornar a quantidade de produtos que custam o preço passado.

Além disso sinta-se a vontade para implementar qualquer outro método que você achar interessante. 

2  - Aplicativo Cliente

O objetivo desse aplicativo é consumir a Api que você irá disponibilizar na sua lib. Construa aqui um produto com uma interface simples para que possamos testar a lib desenvolvida na etapa 1.

## PLUS - Um BFF

Caso você tenha disponibilidade e o interess, você pode construir um BFF para ficar no meio do caminho entre o endpoint que disponibilizamos e a Lib que você vai implementar.

Esse BFF, pode por exemplo, realizar uma simplificação dos dados do Endpoint. Disponibilizando-os em outro Endpoint de forma mais enxuta. Por exemplo, com os dados de nome, o preço e a descrição do produto apenas.

Aqui sinta-se livre para construir o BFF com a tecnologia que achar melhor e com as práticas que achar interessante, mas lembre-se que o problema é bem simples, use seu tempo e esforço com sabedoria.

## O que iremos avaliar?

O que iremos avaliar? Nosso objetivo é avaliar a resolução do problema como um todo, não preocupe-se em fazer um super devops para o BFF ou uma interface cheia de animação para o client, aqui iremos avaliar conceitos como DRY, KISS, SOC e SRP. Coisas que valorizamos bastante e utilizamos bastante no nosso dia-a-dia. 
