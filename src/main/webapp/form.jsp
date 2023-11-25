<%@ page contentType="text/html; charset=UTF-8"
   pageEncoding="UTF-8"
   language="java"
   import="com.github.javafaker.Faker, shifttodev.introjdbc.model.Livro, shifttodev.introjdbc.dao.ConnectionFactory, java.sql.Connection"
%>
<%@ page import="shifttodev.introjdbc.dao.ConnectionFactory" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page import="java.sql.SQLException" %>
<jsp:include page="header.jsp"/>
<%
  Livro livro = new Livro();
  String isbnParam = request.getParameter("isbn");
  String modo = null;
  if (isbnParam !=null){
    Connection conn = ConnectionFactory.getConnection();
    String sql = "SELECT titulo, isbn, edicao, ano FROM livros WHERE isbn = ?";
    try {
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, isbnParam);
      ResultSet rs = ps.executeQuery();
      if (rs.next()){
          modo = "atualizar";
          livro.setTitulo(rs.getString("titulo"));
          livro.setIsbn(rs.getString("isbn"));
          livro.setEdicao(rs.getInt("edicao"));
          livro.setAno(rs.getInt("ano"));
      }

      rs.close();
      ps.close();
      conn.close();

    } catch (SQLException e) {
      throw new RuntimeException(e);
    }

  } else {
    modo = "inserir";
    Faker faker = new Faker();
    livro.setTitulo(faker.book().title());
    livro.setIsbn(faker.code().isbn13());
    livro.setEdicao(faker.number().numberBetween(1, 10));
    livro.setAno(faker.number().numberBetween(2000, 2023));
  }
%>
<div class="row">
  <div class="col-sm-12">
    <div class="card fluid">
      <div class="section">
        <form action="/<%=modo%>" method="post">
          <fieldset>
          <legend><%= modo.toUpperCase() %></legend>
            <div class="row responsive-label">
              <div class="col-sm-12 col-md-3">
                <label for="titulo" >Título</label>
              </div>
              <div class="col-sm-12 col-md">
                <input type="text" id="titulo" size="50" placeholder="Titulo" name="titulo" value="<%= livro.getTitulo()%>"/>
              </div>
            </div>
            <div class="row responsive-label">
              <div class="col-sm-12 col-md-3">
                <label for="isbn">ISBN</label>
              </div>
              <div class="col-sm-12 col-md">
                <input type="text" id="isbn" placeholder="ISBN" name="isbn" value="<%= livro.getIsbn()%>"/>
              </div>
            </div>
            <div class="row responsive-label">
              <div class="col-sm-12 col-md-3">
                <label for="edicao">Edição</label>
              </div>
              <div class="col-sm-12 col-md">
                <input type="number" id="edicao" placeholder="Edição" name="edicao" value="<%=livro.getEdicao() %>"/>
              </div>
            </div>
            <div class="row responsive-label">
              <div class="col-sm-12 col-md-3">
                <label for="ano">Ano</label>
              </div>
              <div class="col-sm-12 col-md">
                <input type="number" min="2000" max="2023" step="1" id="ano" placeholder="Ano" name="ano" value="<%= livro.getAno()%>"/>
              </div>
            </div>
            <input type="submit" value="<%= modo.toUpperCase()%>" class="primary"/>
          </fieldset>
        </form>
      </div>
    </div>
  </div>
</div>
<jsp:include page="footer.jsp"/>
