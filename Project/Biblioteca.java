import java.util.ArrayList;
import java.util.List;


class Biblioteca {
    private List<Livro> livros;
    private List<Usuario> usuarios;

    public Biblioteca() {
        this.livros = new ArrayList<>();
        this.usuarios = new ArrayList<>();
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
        System.out.println("Livro adicionado à biblioteca: " + livro.getTitulo());
    }

    public void removerLivro(Livro livro) {
        if (livro.isDisponivel()) {
            livros.remove(livro);
            System.out.println("Livro removido da biblioteca: " + livro.getTitulo());
        } else {
            System.out.println("Não é possível remover o livro, pois está emprestado.");
        }
    }

    public void registrarUsuario(Usuario usuario) throws InterruptedException {
        usuarios.add(usuario);
        System.out.println("Usuário registrado na biblioteca: " + usuario.getNome());
        Thread.sleep(2000);
    }

    public void emprestarLivro(Usuario usuario, Livro livro) {
        if (livro.isDisponivel()) {
            livro.setDisponivel(false);
            livro.setUsuarioEmprestado(usuario);
            System.out.println("Livro emprestado com sucesso: " + livro.getTitulo());
        } else {
            System.out.println("O livro não está disponível para empréstimo.");
        }
    }

    public void verUsuarios() {
    System.out.println("  _Lista de usuários_ \n");
    for (Usuario usuario : usuarios) {
        System.out.println("Nome: " + usuario.getNome() + ", ID: " + usuario.getId());
    }
}

    public void verLivros() {
        System.out.println("  _Lista de livros na biblioteca_");
        for (Livro livro : livros) {
            System.out.print("Título: " + livro.getTitulo() + ", Autor: " + livro.getAutor() + ", ISBN: " + livro.getISBN());
            if (livro.isDisponivel()) {
                System.out.println(" (Disponível)");
            } else {
                System.out.println(" (Emprestado para: " + livro.getUsuarioEmprestado().getNome() + ")");
            }
        }
    }

    public void devolverLivro(Livro livro) {
        if (!livro.isDisponivel()) {
            livro.setDisponivel(true);
            livro.setUsuarioEmprestado(null);
            System.out.println("Livro devolvido com sucesso: " + livro.getTitulo());
        } else {
            System.out.println("O livro não está emprestado.");
        }
    }
    
    public void removerUsuario(Usuario usuario) {
        boolean possuiLivrosEmprestados = false;
        for (Livro livro : usuario.getLivrosEmprestados()) {
            if (!livro.isDisponivel()) {
                possuiLivrosEmprestados = true;
                break;
            }
        }
    
        if (!possuiLivrosEmprestados) {
            usuarios.remove(usuario);
            System.out.println("Usuário removido da biblioteca: " + usuario.getNome());
        } else {
            System.out.println("Não é possível remover o usuário, pois ele tem livros emprestados.");
        }
    }
}
