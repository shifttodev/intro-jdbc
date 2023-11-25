package shifttodev.introjdbc;

import com.github.javafaker.*;
import shifttodev.introjdbc.dao.ConnectionFactory;
import shifttodev.introjdbc.model.Livro;

import java.sql.*;


public class Main {
    public static void main(String[] args) {

//        insert();
//        delete();
        read();
        update();
        read();
    }

    static void read(){
        try(
            Connection conn = ConnectionFactory.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT titulo, isbn FROM livros");

        ) {
            while(rs.next()) {
                System.out.printf("%-30s %s\n",
                        rs.getString("titulo"), rs.getString("isbn"));
            }
        } catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    static void insert(){
        Livro livro = getNewBook();
        try(
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO livros (titulo, isbn, edicao, ano)" +
                        "VALUES (?,?,?,?)");
        ){
            ps.setString(1, livro.getTitulo());
            ps.setString(2, livro.getIsbn());
            ps.setInt(3, livro.getEdicao());
            ps.setInt(4, livro.getAno());

            int row = ps.executeUpdate();

            if (row > 0){
                System.out.printf("Livro %s inserido com sucesso!\n", livro);
            } else {
                System.out.println("Falha ao inserir o registro.");
            }

            ps.clearParameters();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    static void delete(){
        String isbnToDel = "9781886701939";

        try(
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM livros WHERE isbn = ?"
            );
        ){
            ps.setString(1, isbnToDel);
            int row = ps.executeUpdate();

            if (row > 0){
                System.out.printf("Registro  %s removido com sucesso.\n", isbnToDel);
                return;
            }

            System.out.println("Falha ao remover o registro.");

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    static void update(){
        Livro livro = getNewBook();
        String isbn = "9790060776267";

        try(
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE livros SET " +
                            "titulo = ?, " +
                            "edicao = ?, " +
                            "ano = ? " +
                            "WHERE isbn = ?")
        ) {
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getEdicao());
            ps.setInt(3, livro.getAno());
            ps.setString(4, isbn);

            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.printf("Registro %s atualizado com sucesso.\n",
                        isbn);
            } else {
                System.out.println("Falha ao atualizar o registro.");
            }

            ps.clearParameters();

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    static Livro getNewBook(){
        Faker faker = new Faker();
        Livro livro = new Livro();
        livro.setTitulo(faker.book().title());
        livro.setIsbn(faker.code().isbn13());
        livro.setEdicao(1);
        livro.setAno(faker.number().numberBetween(2000, 2023));
        return livro;
    }
}
