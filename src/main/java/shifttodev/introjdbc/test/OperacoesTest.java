package shifttodev.introjdbc.test;

import com.github.javafaker.*;
import shifttodev.introjdbc.dao.ConnectionFactory;
import shifttodev.introjdbc.model.Livro;

import java.sql.*;


public class OperacoesTest {
    public static void main(String[] args) {
        read();
        insert();
        read();
        delete();
        read();
        update();
        read();
    }

    static void read(){
        try(
            Connection conn = ConnectionFactory.getConnection();
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(
                    "SELECT titulo, isbn, ano FROM livros");

        ) {
            while(rs.next()) {
                System.out.printf("%-30s %13s %d \n",
                        rs.getString("titulo"), rs.getString("isbn"),
                        rs.getInt("ano"));
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
                System.out.println("Nenhum registro inserido.");
            }

            ps.clearParameters();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    static void delete(){
        Integer ano = new Faker().number().numberBetween(2000, 2023);

        try(
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM livros WHERE ano = ?"
            );
        ){
            ps.setInt(1, ano);
            int row = ps.executeUpdate();

            if (row > 0){
                System.out.printf(
                        "Registros do ano de %d removidos com sucesso.\n",
                        ano);
                return;
            }

            System.out.printf("Nenhum registro de %d removido.\n", ano);

        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    static void update(){
        Livro livro = getNewBook();
        Integer ano = new Faker().number().numberBetween(2000, 2023);

        try(
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE livros SET " +
                            "titulo = ?, " +
                            "edicao = ?, " +
                            "ano = ? " +
                            "WHERE ano = ?")
        ) {
            ps.setString(1, livro.getTitulo());
            ps.setInt(2, livro.getEdicao());
            ps.setInt(3, livro.getAno());
            ps.setInt(4, ano);

            int row = ps.executeUpdate();
            if (row > 0) {
                System.out.printf(
                        "Registros do ano de %d atualizados com sucesso.\n",
                        ano);
            } else {
                System.out.printf("Nenhum registro de %d atualizado.\n", ano);
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
