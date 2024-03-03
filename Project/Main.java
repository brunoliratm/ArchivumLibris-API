import java.io.IOException;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static Biblioteca biblioteca = new Biblioteca();

//limpar a linha de comando (apenas para manter a organização)
    public static void limpeza(){
        String os = System.getProperty("os.name").toLowerCase();

        try {
            Process process;
            if (os.contains("win")) {
                process = new ProcessBuilder("cmd", "/c", "cls").inheritIO().start();
            } else {
                process = new ProcessBuilder("clear").inheritIO().start();
            }
            process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
//Menus
    public static void main(String[] args) throws InterruptedException {
        limpeza();
        Thread.sleep(1000);
        System.out.print("B");
        Thread.sleep(100);
        System.out.print("e");
        Thread.sleep(100);
        System.out.print("m");
        Thread.sleep(100);
        System.out.print("-");
        Thread.sleep(100);
        System.out.print("V");
        Thread.sleep(100);
        System.out.print("i");
        Thread.sleep(100);
        System.out.print("n");
        Thread.sleep(100);
        System.out.print("d");
        Thread.sleep(100);
        System.out.print("o ");
        Thread.sleep(100);
        System.out.print("a ");
        Thread.sleep(100);
        System.out.print("B");
        Thread.sleep(100);
        System.out.print("i");
        Thread.sleep(100);
        System.out.print("b");
        Thread.sleep(100);
        System.out.print("l");
        Thread.sleep(100);
        System.out.print("i");
        Thread.sleep(100);
        System.out.print("o");
        Thread.sleep(100);
        System.out.print("t");
        Thread.sleep(100);
        System.out.print("e");
        Thread.sleep(100);
        System.out.print("c");
        Thread.sleep(100);
        System.out.print("a ");
        Thread.sleep(100);
        System.out.print("F");
        Thread.sleep(100);
        System.out.print("A");
        Thread.sleep(100);
        System.out.print("C");
        Thread.sleep(100);
        System.out.print("O");
        Thread.sleep(100);
        System.out.print("L");
        Thread.sleep(100);

        Thread.sleep(3000);
        principal();

    }

    public static void principal() throws InterruptedException{
            
            limpeza();
            System.out.print("  _Menu da Biblioteca_\n\n");
            Thread.sleep(1500);

            System.out.println("Digite o número da opção desejada: \n");
            System.out.println("1. Menu dos Livros");
            System.out.println("2. Registrar usuário");
            System.out.println("3. Remover usuário");
            System.out.println("4. Ver usuários registrados");
            System.out.println("5. Sair\n");
            

            try {
                
                System.out.print("--> ");
                int opcao = scanner.nextInt();
                limpeza();
                switch (opcao) {
                    case 1:
                        principalLivros();
                        break;
                    case 2:
                        registrarUsuario(biblioteca, scanner);
                        Thread.sleep(2000);
                        principal();
                        break;
                    case 3: 
                        removerUsuario(biblioteca, scanner);
                        Thread.sleep(2000);
                        principal();
                        break;
                    case 4:
                        biblioteca.verUsuarios();
                        Thread.sleep(4000);
                        principal();
                        break;
                    case 5:
                        System.out.println("Obrigado por usar nossa Biblioteca!");
                        Thread.sleep(2000);
                        System.out.print("Saindo");
                        Thread.sleep(150);
                        System.out.print(".");
                        Thread.sleep(150);
                        System.out.print(".");
                        Thread.sleep(150);
                        System.out.print(".");
                        Thread.sleep(1000);
                        scanner.close();
                        System.exit(0);
                    default:
                        scanner.nextLine();
                        System.out.println("Opção inválida. Tente novamente.");
                        Thread.sleep(2000);
                        principal();
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Opção inválida. Tente novamente.");
                Thread.sleep(2000);
                principal();
                
            }   
        }
    
    public static void principalLivros() throws InterruptedException{
        limpeza();
            System.out.print("  _Menu de Livros_\n\n");
            Thread.sleep(1500);

            System.out.println("Digite o número da opção desejada: \n");
            System.out.println("1. Adicionar livro");
            System.out.println("2. Remover livro");
            System.out.println("3. Emprestar livro");
            System.out.println("4. Ver livros");
            System.out.println("5. Devolver livro");
            System.out.println("6. Retornar ao menu inicial");
            System.out.print("--> ");
            
            try {
                int opcao = scanner.nextInt();
                limpeza();
                switch (opcao) {
                    case 1:
                        adicionarLivro(biblioteca, scanner);
                        Thread.sleep(2000);
                        principalLivros();
                        break;
                    case 2:
                        removerLivro(biblioteca, scanner);
                        Thread.sleep(2000);
                        principalLivros();
                        break;
                    case 3: 
                        emprestarLivro(biblioteca, scanner);
                        Thread.sleep(3000);
                        principalLivros();
                        break;
                    case 4:
                        biblioteca.verLivros();
                        Thread.sleep(4000);
                        principalLivros();
                        break;
                    case 5:
                        devolverLivro(biblioteca, scanner);
                        Thread.sleep(4000); 
                        principalLivros();
                        break;
                    case 6:
                        principal();
                    default:
                        scanner.nextLine();
                        System.out.println("Opção inválida. Tente novamente.");
                        Thread.sleep(2000);
                        principalLivros();
                }
            } catch (Exception e) {
                scanner.nextLine();
                System.out.println("Opção inválida. Tente novamente.");
                Thread.sleep(2000);
                principalLivros();
            }

    }


//Funções do livro
    private static void adicionarLivro(Biblioteca biblioteca, Scanner scanner) throws InterruptedException {
        try {
            scanner.nextLine(); 
            System.out.println("  _Adicionar Livro_ \n");
            System.out.print("Digite o título do livro: ");
            String titulo = scanner.nextLine();
            System.out.print("Digite o autor do livro: ");
            String autor = scanner.nextLine();
            System.out.print("Digite o ISBN do livro: ");
            int ISBN = scanner.nextInt();
            scanner.nextLine();
            System.out.print("O livro é físico ou digital? (F/D): ");
            String tipoLivro = scanner.nextLine().toUpperCase();

            if (tipoLivro.equals("F")) {
                biblioteca.adicionarLivro(new LivroFisico(titulo, autor, ISBN));
            } else if (tipoLivro.equals("D")) {
                biblioteca.adicionarLivro(new LivroDigital(titulo, autor, ISBN));
            } else {
                System.out.println("Tipo de livro inválido.");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Valor inválido");
            Thread.sleep(2000);
            principalLivros();
        }

        
    }

    private static void removerLivro(Biblioteca biblioteca, Scanner scanner) throws InterruptedException {
        try {
            System.out.println("  _Remover Livro_\n");
            System.out.print("Digite o ISBN do livro que deseja remover: ");
            scanner.nextLine(); 
            int ISBN = scanner.nextInt();
        
            Livro livroRemover = null;
            for (Livro livro : biblioteca.getLivros()) {
                if (livro.getISBN() == ISBN) {
                    livroRemover = livro;
                    break;
                }
            }
        
            if (livroRemover != null) {
                biblioteca.removerLivro(livroRemover);
            } else {
                System.out.println("Livro não encontrado na biblioteca.");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Valor inválido");
            Thread.sleep(2000);
            principalLivros();
        }
    }
    
    private static void emprestarLivro(Biblioteca biblioteca, Scanner scanner) throws InterruptedException {
        try {
            scanner.nextLine();
            System.out.println("  _Emprestar Livro_\n");
            System.out.print("Digite o ISBN do livro que deseja emprestar: ");
            int ISBN = scanner.nextInt();
            System.out.print("Digite o ID do usuário que está pegando emprestado: ");
            int idUsuario = scanner.nextInt();
        
            Livro livroEmprestar = null;
            Usuario usuario = null;
            for (Livro livro : biblioteca.getLivros()) {
                if (livro.getISBN() == ISBN) { 
                    livroEmprestar = livro;
                    break;
                }
            }
        
            for (Usuario user : biblioteca.getUsuarios()) {
                if (user.getId() == idUsuario) {
                    usuario = user;
                    break;
                }
            }
        
            if (livroEmprestar != null && usuario != null) {
                biblioteca.emprestarLivro(usuario, livroEmprestar);
            } else {
                System.out.println("Livro ou usuário não encontrado.");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Valor inválido");
            Thread.sleep(2000);
            principalLivros();
        }
    }

    private static void devolverLivro(Biblioteca biblioteca, Scanner scanner) throws InterruptedException {
        try {
            scanner.nextLine();
            System.out.println("  _Devolver Livro_\n");
            System.out.print("Digite o ISBN do livro que deseja devolver: ");
            int ISBN = scanner.nextInt();

            Livro livroDevolver = null;
            for (Livro livro : biblioteca.getLivros()) {
                if (livro.getISBN() == ISBN) {
                    livroDevolver = livro;
                    break;
                }
            }

            if (livroDevolver != null) {
                biblioteca.devolverLivro(livroDevolver);
            } else {
                System.out.println("Livro não encontrado na biblioteca.");
            }
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Valor inválido");
            Thread.sleep(2000);
            principalLivros();
        }
    }



//Funções do usuário
    private static void registrarUsuario(Biblioteca biblioteca, Scanner scanner) throws InterruptedException {
        try {
            scanner.nextLine(); 
            System.out.println("  _Registrar Usuário_ \n");
            System.out.print("Digite o nome do usuário: ");
            String nome = scanner.nextLine();
            System.out.print("Digite o ID do usuário: ");
            int id = scanner.nextInt();

            biblioteca.registrarUsuario(new Usuario(nome, id));
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Valor inválido");
            Thread.sleep(2000);
            principal();
        }
    }

    private static void removerUsuario(Biblioteca biblioteca, Scanner scanner) throws InterruptedException {
        scanner.nextLine(); 
        System.out.println("  _Remover Usuário_ \n");
        System.out.print("Digite o ID do usuário que deseja remover: ");
        try {
            int idUsuario = scanner.nextInt();
            Usuario usuarioRemover = null;
            for (Usuario usuario : biblioteca.getUsuarios()) {
                if (usuario.getId() == idUsuario) {
                    usuarioRemover = usuario;
                    break;
                }
            }
        
            if (usuarioRemover != null) {
                biblioteca.removerUsuario(usuarioRemover);
            } else {
                System.out.println("Usuário não encontrado na biblioteca.");
            }
        } catch (Exception e) {
            System.out.println("Valor inválido");
            Thread.sleep(2000);
            principal();

        }  
    }
}
