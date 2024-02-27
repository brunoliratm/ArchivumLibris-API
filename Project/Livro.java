public abstract class Livro {
    private String titulo;
    private String autor;
    private int ISBN;
    private boolean disponivel;
    private Usuario usuarioEmprestado;

    public Livro(String titulo, String autor, int ISBN) {
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.disponivel = true;
        this.usuarioEmprestado = null;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public int getISBN() {
        return ISBN;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public void setISBN(int iSBN) {
        this.ISBN = iSBN;
    }

    public Usuario getUsuarioEmprestado() {
        return usuarioEmprestado;
    }

    public void setUsuarioEmprestado(Usuario usuarioEmprestado) {
        this.usuarioEmprestado = usuarioEmprestado;
    }

    public abstract void emprestar();
}

class LivroFisico extends Livro {
    private boolean emBomEstado;

    public LivroFisico(String titulo, String autor, int ISBN) {
        super(titulo, autor, ISBN);
        this.emBomEstado = true;
    }

    public boolean isEmBomEstado() {
        return emBomEstado;
    }

    public void setEmBomEstado(boolean emBomEstado) {
        this.emBomEstado = emBomEstado;
    }

    public void emprestar() {
        if (isDisponivel() && isEmBomEstado()) {
            setDisponivel(false);
            System.out.println("Livro físico emprestado com sucesso!");
        } else {
            System.out.println("Não é possível emprestar o livro físico.");
        }
    }
}

class LivroDigital extends Livro {
    public LivroDigital(String titulo, String autor, int ISBN) {
        super(titulo, autor, ISBN);
    }

    public void emprestar() {
        if (isDisponivel()) {
            setDisponivel(false);
            System.out.println("Livro digital emprestado com sucesso!");
        } else {
            System.out.println("Não é possível emprestar o livro digital.");
        }
    }
}
