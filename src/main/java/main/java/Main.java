import java.util.Scanner;

public class Main {
    static Scanner entrada = new Scanner(System.in);

    static Tarefa[] tarefas = new Tarefa[100];

    static int quantidadeTarefas = 0;
    static int proximoId = 1;

    static String[] categorias = {
        "Estudo",
        "Trabalho",
        "Pessoal"
    };

    static String[] prioridades = {
        "Baixa",
        "Média",
        "Alta"
    };

    public static void main(String[] args) {
        int opcao;

        do {
            exibirMenu();
            opcao = lerInteiro("Escolha uma opção: ", 0, 6);
            switch (opcao) {

                case 1:
                    cadastrarTarefa();
                    break;

                case 2:
                    listarTarefas();
                    break;

                case 3:
                    concluirTarefa();
                    break;

                case 4:
                    registrarTempo();
                    break;

                case 5:
                    gerarRelatorio();
                    break;

                case 6:
                    calcularIndicadores();
                    break;

                case 0:
                    System.out.println("Encerrando Calcula+...");
                    break;

                default:
                    System.out.println("Opção inválida!");
            }

        } while (opcao != 0);

        entrada.close();
    }

    // Menu principal
    static void exibirMenu() {
        System.out.println("\n===============================");
        System.out.println("Calcula+");
        System.out.println("Controle de Produtividade");
        System.out.println("===============================");

        System.out.println("1. Cadastrar tarefa");
        System.out.println("2. Listar tarefas");
        System.out.println("3. Concluir tarefa");
        System.out.println("4. Registrar tempo gasto");
        System.out.println("5. Gerar relatório");
        System.out.println("6. Indicadores");
        System.out.println("0. Sair");

        System.out.println("===============================");
    }

    // Cadastro de tarefas
    static void cadastrarTarefa() {
        if (quantidadeTarefas >= tarefas.length) {
            System.out.println("Limite de tarefas atingido!");
            return;
        }

        System.out.println("\n--- Cadastro de Tarefa ---");

        System.out.print("Descrição: ");
        String descricao = entrada.nextLine().trim();

        while (descricao.isEmpty()) {
            System.out.print("Descrição não pode ficar vazia: ");
            descricao = entrada.nextLine().trim();
        }

        System.out.println("\nCategorias:");

        for (int i = 0; i < categorias.length; i++) {
            System.out.println((i + 1) + " - " + categorias[i]);
        }

        int categoria = lerInteiro("Categoria: ", 1, categorias.length) - 1;

        System.out.println("\nPrioridades:");

        for (int i = 0; i < prioridades.length; i++) {
            System.out.println((i + 1) + " - " + prioridades[i]);
        }

        int prioridade = lerInteiro("Prioridade: ", 1, prioridades.length) - 1;

        int tempoEstimado = lerInteiro(
            "Tempo estimado (minutos): ", 1, 100000
        );

        tarefas[quantidadeTarefas] = new Tarefa(
            proximoId,
            descricao,
            categoria,
            prioridade,
            tempoEstimado
        );

        quantidadeTarefas++;
        proximoId++;

        System.out.println("\nTarefa cadastrada com sucesso!");
    }

    // Listagem de tarefas
    static void listarTarefas() {

        System.out.println("\n--- Lista de Tarefas ---");

        if (quantidadeTarefas == 0) {
            System.out.println("Nenhuma tarefa cadastrada!");
            return;
        }

        for (int i = 0; i < quantidadeTarefas; i++) {

            Tarefa t = tarefas[i];

            System.out.println("\nID: " + t.id);
            System.out.println("Descrição: " + t.descricao);
            System.out.println("Categoria: " + categorias[t.categoria]);
            System.out.println("Prioridade: " + prioridades[t.prioridade]);

            System.out.println("Tempo estimado: " + t.tempoEstimado + "minutos");
            System.out.println("Tempo gasto: " + t.tempoGasto + "minutos");

            System.out.println(
                "Status: " + (t.concluida ? "Concluída" : "Pendente")
            );
        }
    }

    // Conclusão de tarefas
    static void concluirTarefa() {
        if (quantidadeTarefas == 0) {
            System.out.println("Nenhuma tarefa cadastrada!");
            return;
        }

        listarTarefas();

        int id = lerInteiro(
            "Informe o ID da tarefa: ", 1, proximoId - 1
        );

        Tarefa t = buscarTarefa(id);
        if (t == null) {
            System.out.println("Tarefa não encontrada!");

        } else if (t.concluida) {
            System.out.println("Essa tarefa já está concluída!");

        } else {
            t.concluida = true;
            System.out.println("Tarefa concluída com sucesso!");
        }
    }

    // Registro de tempo
    static void registrarTempo() {
        if (quantidadeTarefas == 0) {
            System.out.println("Nenhuma tarefa cadastrada!");
            return;
        }

        listarTarefas();

        int id = lerInteiro(
            "Informe o ID da tarefa: ", 1, proximoId - 1
        );

        Tarefa t = buscarTarefa(id);

        if (t == null) {
            System.out.println("Tarefa não encontrada!");
        } else {
            int tempo = lerInteiro(
                "Informe os minutos trabalhados: ", 1, 100000
            );
            t.tempoGasto += tempo;
            System.out.println("Tempo registrado com sucesso!");
        }
    }

    // Busca de tarefas
    static Tarefa buscarTarefa(int id) {
        for (int i = 0; i < quantidadeTarefas; i++) {
            if (tarefas[i].id == id) {
                return tarefas[i];
            }
        }
        return null;
    }

    // Relatório por categoria utilizando matriz
    static void gerarRelatorio() {
        int[][] resumo = new int[3][2];

        for (int i = 0; i < quantidadeTarefas; i++) {
            Tarefa t = tarefas[i];
            if (t.concluida) {
                resumo[t.categoria][1]++;
            } else {
                resumo[t.categoria][0]++;
            }
        }

        System.out.println("\n--- Relatório por Categoria ---");

        System.out.printf(
            "%-15s %-12s %-12s%n",
            "Categoria",
            "Pendentes",
            "Concluídas"
        );

        int totalPendentes = 0;
        int totalConcluidas = 0;

        for (int i = 0; i < resumo.length; i++) {
            System.out.printf(
                "%-15s %-12d %-12d%n",
                categorias[i],
                resumo[i][0],
                resumo[i][1]
            );

            totalPendentes += resumo[i][0];
            totalConcluidas += resumo[i][1];
        }

        System.out.println("-----------------------------------------");

        System.out.printf(
            "%-15s %-12d %-12d%n",
            "TOTAL",
            totalPendentes,
            totalConcluidas
        );
    }

    // Indicadores e cálculos
    static void calcularIndicadores() {
        int totalConcluidas = 0;
        int totalPendentes = 0;

        int tempoEstimado = 0;
        int tempoGasto = 0;

        for (int i = 0; i < quantidadeTarefas; i++) {

            Tarefa t = tarefas[i];

            tempoEstimado += t.tempoEstimado;
            tempoGasto += t.tempoGasto;

            if (t.concluida) {
                totalConcluidas++;
            } else {
                totalPendentes++;
            }
        }

        double taxaConclusao = 0;

        if (quantidadeTarefas > 0) {
            taxaConclusao =
                (totalConcluidas * 100.0) / quantidadeTarefas;
        }

        int diferenca = tempoEstimado - tempoGasto;

        System.out.println("\n--- Indicadores de Produtividade ---");

        System.out.println("Total de tarefas: " + quantidadeTarefas);
        System.out.println("Concluídas: " + totalConcluidas);
        System.out.println("Pendentes: " + totalPendentes);

        System.out.printf(
            "Taxa de conclusão: %.2f%%%n",
            taxaConclusao
        );

        System.out.println("Tempo estimado: " + tempoEstimado + " minutos");
        System.out.println("Tempo gasto: " + tempoGasto + " minutos");

        System.out.println("Diferença de tempo: " + diferenca + " minutos");

        if (diferenca >= 0) {
            System.out.println("Resultado: Dentro do tempo estimado.");
        } else {
            System.out.println("Resultado: Tempo estimado ultrapassado.");
        }
    }

    // Leitura e validação de números inteiros
    static int lerInteiro(String mensagem, int minimo, int maximo) {
        int valor;
        while (true) {

            System.out.print(mensagem);
            try {
                valor = Integer.parseInt(entrada.nextLine());

                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }

                System.out.println(
                    "Informe um valor entre " + minimo + " e " + maximo + "."
                );

            } catch (NumberFormatException e) {

                System.out.println("Digite um número inteiro válido.");
            }
        }
    }

    // Classe Tarefa
    static class Tarefa {
        int id;
        String descricao;
        int categoria;
        int prioridade;

        int tempoEstimado;
        int tempoGasto;

        boolean concluida;

        Tarefa(
            int id,
            String descricao,
            int categoria,
            int prioridade,
            int tempoEstimado
        ) {

            this.id = id;
            this.descricao = descricao;
            this.categoria = categoria;
            this.prioridade = prioridade;
            this.tempoEstimado = tempoEstimado;

            this.tempoGasto = 0;
            this.concluida = false;
        }
    }
}