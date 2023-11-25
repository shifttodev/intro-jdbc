package shifttodev.introjdbc.model;

import lombok.Data;

@Data
public class Livro {
    private String isbn;
    private String titulo;
    private Integer edicao;
    private Integer ano;
    private String descricao;

    @Override
    public String toString(){
        return this.titulo;
    }
}
