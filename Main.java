import com.sun.jna.Library;
import com.sun.jna.Native;
import java.util.Scanner;
import java.nio.file.*;

public class Main {

    // Interface que representa a DLL, usando JNA
    public interface ImpressoraDLL extends Library {
        ImpressoraDLL INSTANCE = (ImpressoraDLL) Native.load(
                Paths.get("E1_Impressora01.dll").toAbsolutePath().toString(), ImpressoraDLL.class); // DLL na raiz do projeto

        int AbreConexaoImpressora(int tipo, String modelo, String conexao, int param);

        int FechaConexaoImpressora();

        int ImpressaoTexto(String dados, int posicao, int estilo, int tamanho);

        int Corte(int avanco);

        int ImpressaoQRCode(String dados, int tamanho, int nivelCorrecao);

        int ImpressaoCodigoBarras(int tipo, String dados, int altura, int largura, int HRI);

        int AvancaPapel(int linhas);

        int StatusImpressora(int param);

        int AbreGavetaElgin(int pino, int ti, int tf);

        int AbreGaveta(int pino, int ti, int tf);

        int SinalSonoro(int qtd, int tempoInicio, int tempoFim);

        int ModoPagina();

        int LimpaBufferModoPagina();

        int ImprimeModoPagina();

        int ModoPadrao();

        int PosicaoImpressaoHorizontal(int posicao);

        int PosicaoImpressaoVertical(int posicao);

        int ImprimeXMLSAT(String dados, int param);

        int ImprimeXMLCancelamentoSAT(String dados, String assQRCode, int param);
    }

    private static boolean conexaoAberta = false;
    private static int tipo;
    private static String modelo;
    private static String conexao;
    private static int parametro;
    
    private static final Scanner scanner = new Scanner(System.in); // Scanner estático e final
    private static final String USUARIO = "admin";
    private static final String SENHA = "1234";
    private static final String ASS_QRCODE = "Q5DLkpdRijIRGY6YSSNsTWK1TztHL1vD0V1Jc4spo/CEUqICEb9SFy82ym8EhBRZjbh3btsZhF+sjHqEMR159i4agru9x6KsepK/q0E2e5xlU5cv3m1woYfgHyOkWDNcSdMsS6bBh2Bpq6s89yJ9Q6qh/J8YHi306ce9Tqb/drKvN2XdE5noRSS32TAWuaQEVd7u+TrvXlOQsE3fHR1D5f1saUwQLPSdIv01NF6Ny7jZwjCwv1uNDgGZONJdlTJ6p0ccqnZvuE70aHOI09elpjEO6Cd+orI7XHHrFCwhFhAcbalc+ZfO5b/+vkyAHS6CYVFCDtYR9Hi5qgdk31v23w==";

    // Função para capturar entradas do usuário
    private static String capturarEntrada(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine();
    }

    private static int lerInteiro(String mensagem) {
    while (true) {
        try {
            return Integer.parseInt(capturarEntrada(mensagem));
        } catch (NumberFormatException e) {
            System.out.println("Digite apenas números.");
        }
    }
}
private static String lerArquivo(String nomeArquivo) {
    try {
        return new String(Files.readAllBytes(Paths.get(nomeArquivo)), java.nio.charset.StandardCharsets.UTF_8);
    } catch (Exception e) {
        return null;
    }
}

    // Função para configurar conexão
    public static void configurarConexao() {

    tipo = lerInteiro("Tipo (ex: 1 para USB): ");
    modelo = capturarEntrada("Modelo (ex: i8 ou i9): ");
    conexao = capturarEntrada("Conexão (ex: USB): ");
    parametro = lerInteiro("Parâmetro (ex: 0): ");
    System.out.println("Configuração salva!");

    }

    public static void abrirConexao() {
        // Função para abrir a conexão com a impressora
        if (!conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.AbreConexaoImpressora(tipo, modelo, conexao, parametro);
            if (retorno == 0) {
                conexaoAberta = true;
                System.out.println("Conexão aberta com sucesso.");
            } else {
                System.out.println("Erro ao abrir conexão. Código de erro: " + retorno);
            }
        } else {
            System.out.println("Conexão já está aberta.");
        }
    }

    public static void fecharConexao() {
        if (conexaoAberta) {
            int retorno = ImpressoraDLL.INSTANCE.FechaConexaoImpressora();
            if (retorno ==0) {
                conexaoAberta = false;
                System.out.println("Conexão fechada com sucesso.");
            } else {
                System.out.println("Erro ao fechar conexão. Código: " + retorno);
            }
        } else {
            System.out.println("Conexão já está fechada.");
        }
     }

public static boolean fazerLogin() {
    for (int tentativa = 1; tentativa <= 3; tentativa++) {
        String usuario = capturarEntrada("Usuário: ");
        String senha = capturarEntrada("Senha: ");

        if (usuario.equals(USUARIO) && senha.equals(SENHA)) {
            System.out.println("Login realizado com sucesso!");
            return true;
        } else {
            System.out.println("Dados incorretos. Restam " + (3 - tentativa) + " tentativa(s).");
        }
    }

    System.out.println("Tentativas esgotadas.");
    return false;
}


    public static void exibirMenu() {
        System.out.println("\n*************************************************");
        System.out.println("**************** MENU IMPRESSORA *******************");
        System.out.println("*************************************************\n");

        System.out.println("1  - Configurar Conexao");
        System.out.println("2  - Abrir Conexao");
        System.out.println("3  - Fechar Conexao");
        System.out.println("4  - Imprimir Texto");
        System.out.println("5  - Imprimir QR Code");
        System.out.println("6  - Imprimir Codigo de Barras");
        System.out.println("7  - Imprimir XML SAT");
        System.out.println("8  - Imprimir XML Cancelamento SAT");
        System.out.println("9  - Abrir Gaveta");
        System.out.println("10 - Sinal Sonoro");
        System.out.println("11 - Avancar Papel");
        System.out.println("0  - Sair");

    }
    public static void abrirGaveta() {
    if (!conexaoAberta) {
        System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
        return;
    }
    int retorno = ImpressoraDLL.INSTANCE.AbreGaveta(1, 5, 10);
    if (retorno == 0) {
        System.out.println("Gaveta aberta!");
    } else {
        System.out.println("Erro ao abrir gaveta. Código: " + retorno);
    }
}

public static void sinalSonoro() {
    if (!conexaoAberta) {
        System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
        return;
    }
    int qtd = lerInteiro("Quantos bips? ");
    int retorno = ImpressoraDLL.INSTANCE.SinalSonoro(qtd, 50, 5);
    if (retorno == 0) {
        System.out.println("Bip executado!");
    } else {
        System.out.println("Erro no sinal sonoro. Código: " + retorno);
    }
}

public static void avancarPapel() {
    if (!conexaoAberta) {
        System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
        return;
    }
    int linhas = lerInteiro("Quantas linhas avançar? ");
    int retorno = ImpressoraDLL.INSTANCE.AvancaPapel(linhas);
    if (retorno == 0) {
        System.out.println("Papel avançado!");
    } else {
        System.out.println("Erro ao avançar papel. Código: " + retorno);
    }
}


public static void imprimirTexto() {
    if (!conexaoAberta) {
        System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
        return;
    }
    String texto = capturarEntrada("Texto a imprimir: ");
    int copias = lerInteiro("Quantas cópias? ");

    for (int i = 1; i <= copias; i++) {
        // Atenção: no ImpressaoTexto, só é erro se for negativo (< 0)
        int retorno = ImpressoraDLL.INSTANCE.ImpressaoTexto(texto, 1, 4, 0);
        if (retorno >= 0) {
            System.out.println("Cópia " + i + " impressa!");
        } else {
            System.out.println("Erro ao imprimir. Código: " + retorno);
        }
    }
    ImpressoraDLL.INSTANCE.Corte(5);
}

public static void imprimirQRCode() {
    if (!conexaoAberta) {
        System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
        return;
    }
    String dados = capturarEntrada("Conteúdo do QR Code: ");
    int retorno = ImpressoraDLL.INSTANCE.ImpressaoQRCode(dados, 6, 4);
    if (retorno == 0) {
        System.out.println("QR Code impresso!");
        ImpressoraDLL.INSTANCE.Corte(5);
    } else {
        System.out.println("Erro ao imprimir QR Code. Código: " + retorno);
    }
}

public static void imprimirCodigoBarras() {
    if (!conexaoAberta) {
        System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
        return;
    }
    int retorno = ImpressoraDLL.INSTANCE.ImpressaoCodigoBarras(8, "{A012345678912", 100, 2, 3);
    if (retorno == 0) {
        System.out.println("Código de barras impresso!");
        ImpressoraDLL.INSTANCE.Corte(5);
    } else {
        System.out.println("Erro ao imprimir código de barras. Código: " + retorno);
    }
}
public static void imprimirXMLSAT() {
    if (!conexaoAberta) {
        System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
        return;
    }
    String nome = capturarEntrada("Nome do arquivo XML (ex: XMLSAT.xml): ");
    String xml = lerArquivo(nome);
    if (xml == null) {
        System.out.println("Arquivo não encontrado.");
        return;
    }

    int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLSAT(xml, 0);
    if (retorno == 0) {
        System.out.println("XML SAT impresso!");
        ImpressoraDLL.INSTANCE.Corte(5);
    } else {
        System.out.println("Erro ao imprimir XML SAT. Código: " + retorno);
    }
}

public static void imprimirXMLCancelamentoSAT() {
    if (!conexaoAberta) {
        System.out.println("Conexão fechada. Abra a conexão antes (opção 2).");
        return;
    }
    String nome = capturarEntrada("Nome do arquivo XML (ex: CANC_SAT.xml): ");
    String xml = lerArquivo(nome);
    if (xml == null) {
        System.out.println("Arquivo não encontrado.");
        return;
    }

    int retorno = ImpressoraDLL.INSTANCE.ImprimeXMLCancelamentoSAT(xml, ASS_QRCODE, 0);
    if (retorno == 0) {
        System.out.println("XML Cancelamento SAT impresso!");
        ImpressoraDLL.INSTANCE.Corte(5);
    } else {
        System.out.println("Erro ao imprimir cancelamento SAT. Código: " + retorno);
    }
}




 public static void main(String[] args) throws java.io.IOException {
    if (!fazerLogin()) {
        scanner.close();
        return; // errou 3 vezes: encerra o programa
    }

    int opcao = -1;
    while (opcao != 0) {
        exibirMenu();
        opcao = lerInteiro("\nDigite a opção desejada: ");

        if (opcao == 1) {
            configurarConexao();
        } else if (opcao == 2) {
            abrirConexao();
        } else if (opcao == 3) {
            fecharConexao();
        } else if (opcao == 4) {
            imprimirTexto();
        } else if (opcao == 5) {
            imprimirQRCode();
        } else if (opcao == 6) {
            imprimirCodigoBarras();
        } else if (opcao == 7) {
            imprimirXMLSAT();
        } else if (opcao == 8) {
            imprimirXMLCancelamentoSAT();
        } else if (opcao == 9) {
            abrirGaveta();
        } else if (opcao == 10) {
            sinalSonoro();
        } else if (opcao == 11) {
            avancarPapel();
        } else if (opcao == 0) {
            fecharConexao();
            System.out.println("Programa encerrado.");
        } else {
            System.out.println("Opção inválida.");
        }
    }

    scanner.close();
}
}