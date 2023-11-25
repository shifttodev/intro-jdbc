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

@WebServlet(urlPatterns = "/deletar")
public class DeletarServlet extends  HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String isbn = req.getParameter("isbn");

        Connection conn = ConnectionFactory.getConnection();

        try {
            String sql = "DELETE FROM livros WHERE isbn = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, isbn);
            ps.executeUpdate();

            ps.close();
            conn.close();

        } catch (Exception e){
            throw new RuntimeException(e);
        }

        resp.sendRedirect("listar.jsp");
    }
}
