<!-- DEPRECATED -->
<%@ page contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         language="java"
         import="com.github.javafaker.Faker, shifttodev.introjdbc.model.Livro"
%>
<%@ page import="shifttodev.introjdbc.dao.ConnectionFactory" %>
<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.SQLException" %>
<jsp:include page="header.jsp"/>
<div class="row">
  <div class="col-sm-12">
    <div class="card fluid warning">
      <div class="section">
        <%
          String titulo = request.getParameter("titulo");
          String isbn = request.getParameter("isbn");
          int edicao = Integer.parseInt(request.getParameter("edicao"));
          int ano = Integer.parseInt(request.getParameter("ano"));

          Connection conn = ConnectionFactory.getConnection();
          String sql = "INSERT INTO livros (titulo, isbn, edicao, ano)" +
              "VALUE (?,?,?,?)";

          try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, titulo);
            ps.setString(2, isbn);
            ps.setInt(3, edicao);
            ps.setInt(4, ano);
            ps.executeUpdate();
            ps.clearParameters();

            ps.close();
            conn.close();
          } catch (Exception e){
            throw new RuntimeException(e);
          }
        %>
        <h3 class="doc">Não recomendado</h3>
        <p class="doc">
          Requisição ainda ativa. Utilizar <i>redirect</i>.
        </p>
      </div>
    </div>
  </div>
  <span class="toast">Cadastro realizado com sucesso.</span>
</div>
<jsp:include page="footer.jsp"/>
