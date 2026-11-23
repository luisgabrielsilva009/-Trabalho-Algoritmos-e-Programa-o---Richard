# Controlando uma Impressora Termica Elgin (Java)

Trabalho da disciplina de Algoritmo e Programação feito em Java para controlar uma impressora termica Elgin i8 / i9 usando a DLL da Elgin (`E1_Impressora01.dll`) com a biblioteca JNA.

## Sobre o projeto

O programa roda no terminal (`Main.java`) e pede login antes de liberar o menu. Partindo da base com a interface `ImpressoraDLL` e o metodo `abrirConexao()`, foram implementados os metodos para configurar a conexao e mandar os comandos para a impressora.

Funcionalidades implementadas:
- Login com usuario (`admin`) e senha (`1234`), com ate 3 tentativas
- Configuracao, abertura e fechamento da conexao com a impressora
- Impressao de texto com escolha da quantidade de copias
- Impressao de QR Code e codigo de barras
- Leitura e impressao dos arquivos `XMLSAT.xml` e `CANC_SAT.xml`
- Abertura de gaveta, sinal sonoro (bips) e avanco de papel
- Validacao para nao tentar imprimir com a conexao fechada e verificacao do retorno da DLL

## Tecnologias usadas

- Java
- JNA (`jna-5.15.0.jar`)
- Biblioteca da Elgin (`E1_Impressora01.dll`)
- IntelliJ IDEA / VS Code

## Estrutura do projeto

- `Main.java`: codigo principal do programa
- `E1_Impressora01.dll`: biblioteca da Elgin (64 bits) na raiz do projeto
- `Bibliotecas E1 Impressora/`: pastas `x64` e `x86` com a DLL em 64 e 32 bits
- `libs Java/jna-5.15.0.jar`: biblioteca JNA usada para chamar a DLL no Java
- `XMLSAT.xml` e `CANC_SAT.xml`: arquivos XML de teste para cupom SAT e cancelamento

## Instalacao e execucao

### 1. Baixar o projeto
1. Clique no botao **Code** e depois em **Download ZIP** (ou clone o repositorio com `git clone`).
2. Extraia a pasta no computador.

### 2. Rodar no IntelliJ IDEA
1. Abra a pasta extraida no IntelliJ IDEA.
2. Adicione o arquivo `libs Java/jna-5.15.0.jar` nas bibliotecas do projeto (*File -> Project Structure -> Modules -> Dependencies -> + -> JARs or Directories*).
3. Confira se a `E1_Impressora01.dll` esta na raiz do projeto (se o Java instalado for 32 bits, troque pela DLL da pasta `Bibliotecas E1 Impressora/x86`).
4. Abra o `Main.java` e clique em **Run**.

### 3. Rodar pelo terminal / VS Code
Compile e execute passando a pasta `libs Java` no classpath:

```bash
javac -encoding UTF-8 -cp ".;libs Java/*" Main.java
java -cp ".;libs Java/*" Main
```

## Testes

- Login com acerto, erro e bloqueio apos 3 tentativas
- Menu em loop ate selecionar a opcao `0` (sair)
- Validacao de comandos bloqueados quando a conexao ainda esta fechada
- Carregamento da DLL via JNA e tentativa de abertura de conexao USB (`AbreConexaoImpressora`)
- Testes praticos na impressora Elgin i8/i9 (impressao de texto, QR Code, codigo de barras, XML SAT, gaveta, bip e avanco de papel)

## Integrantes

- Luis Gabriel
- Vinicius Rian
- Vitor H.
- Gustavo Rocha
