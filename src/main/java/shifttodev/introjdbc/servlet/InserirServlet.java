package shifttodev.introjdbc.servlet;

import shifttodev.introjdbc.dao.ConnectionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(urlPatterns = "/inserir")
public class InserirServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String titulo = req.getParameter("titulo");
        String isbn = req.getParameter("isbn");
        Integer edicao = Integer.valueOf(req.getParameter("edicao"));
        Integer ano = Integer.valueOf(req.getParameter("ano"));

        String sql = "INSERT INTO livros (titulo, isbn, edicao, ano) VALUES (?,?,?,?)";

        try(
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
        ) {
            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setInt(3, edicao);
            ps.setInt(4, ano);
            ps.executeUpdate();
            ps.clearParameters();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

        resp.sendRedirect("listar.jsp");
    }
}
