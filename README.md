# Controlando uma Impressora Termica Elgin (Java)

Trabalho da disciplina de Logica de Programacao feito em Java para controlar uma impressora termica Elgin i8 / i9 usando a DLL oficial da Elgin (`E1_Impressora01.dll`) com a biblioteca JNA.

## Sobre o projeto

O sistema roda no terminal (`Main.java`) e pede login antes de liberar o menu. A partir do arquivo base com a interface `ImpressoraDLL` e o `abrirConexao()`, implementamos os metodos para configurar a conexao e enviar comandos para a impressora.

O que o programa faz:
- Login com usuario (`admin`) e senha (`1234`), com ate 3 tentativas
- Configuracao, abertura e fechamento da conexao com a impressora
- Impressao de texto (com escolha de quantidade de copias)
- Impressao de QR Code e codigo de barras
- Leitura e impressao dos arquivos `XMLSAT.xml` e `CANC_SAT.xml`
- Abertura de gaveta, sinal sonoro (bips) e avanco de papel
- Validacao para nao tentar imprimir com a conexao fechada e conferencia do retorno da DLL

## Tecnologias usadas

- Java
- JNA (`jna-5.15.0.jar`)
- Biblioteca `E1_Impressora01.dll`

## Como rodar

1. Baixe ou clone este repositorio.
2. Deixe a `E1_Impressora01.dll` na raiz do projeto (64 bits para Java 64 bits, ou a versao `x86` da pasta `Bibliotecas E1 Impressora` se o Java for 32 bits).
3. Compile e execute passando a pasta `libs Java` no classpath:

```bash
javac -encoding UTF-8 -cp ".;libs Java/*" Main.java
java -cp ".;libs Java/*" Main
```

## Integrantes

- Luis Gabriel
- Vinicius Rian
- Vitor H.
- Gustavo Rocha
