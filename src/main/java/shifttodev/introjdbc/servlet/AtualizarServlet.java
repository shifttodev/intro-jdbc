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
import java.sql.SQLException;

@WebServlet(urlPatterns = "/atualizar")
public class AtualizarServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        String titulo = req.getParameter("titulo");
        String isbn = req.getParameter("isbn");
        int edicao = Integer.parseInt(req.getParameter("edicao"));
        int ano = Integer.parseInt(req.getParameter("ano"));

        try(
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE livros SET " +
                        "titulo = ?, " +
                        "edicao = ?, " +
                        "ano = ? " +
                            "WHERE isbn = ?");
        ) {
            ps.setString(1, titulo);
            ps.setInt(2, edicao);
            ps.setInt(3, ano);
            ps.setString(4, isbn);
            ps.executeUpdate();
            ps.clearParameters();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        resp.sendRedirect("listar.jsp");
    }
}
