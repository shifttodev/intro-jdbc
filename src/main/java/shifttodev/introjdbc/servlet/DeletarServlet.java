package shifttodev.introjdbc.servlet;

import shifttodev.introjdbc.dao.ConnectionFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet(urlPatterns = "/deletar")
public class DeletarServlet extends  HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {

        String isbn = req.getParameter("isbn");

        try(
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM livros WHERE isbn = ?");
        ) {
            ps.setString(1, isbn);
            ps.executeUpdate();
            ps.clearParameters();

        } catch (Exception e){
            throw new RuntimeException(e);
        }

        resp.sendRedirect("listar.jsp");
    }
}
