<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         import="java.sql.*, shifttodev.introjdbc.dao.ConnectionFactory"
%>
<%@ page import="java.net.URLDecoder" %>
<%@ page import="java.util.Map" %>
<jsp:include page="header.jsp"/>
<div class="row">
  <div class="col-sm-12">
    <div class="card fluid">
      <div class="section">
        <table class="striped">
          <caption>Livros</caption>
          <thead>
          <tr>
            <th>Título</th>
            <th>ISBN</th>
            <th>Edição</th>
            <th>Ano</th>
            <th>Action</th>
          </tr>
          </thead>
          <tbody>
        <%
          Connection conn = ConnectionFactory.getConnection();
          try {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("SELECT titulo, isbn, edicao, ano, descricao FROM livros");
            while(rs.next()){
        %>
          <tr>
            <td data-label="Titulo"><%= rs.getString("titulo")%></td>
            <td data-label="ISBN"><%= rs.getString("isbn")%></td>
            <td data-label="Edicao"><%= rs.getInt("edicao")%></td>
            <td data-label="Ano"><%= rs.getInt("ano")%></td>
            <td data-label="Action">
              <a href="/form.jsp?isbn=<%= rs.getString("isbn")%>">Atualizar</a> |
              <a href="/deletar?isbn=<%= rs.getString("isbn")%>">Deletar</a></td>
          </tr>
           <%
               }
            st.close();
            rs.close();
            conn.close();
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        %>
          </tbody>
        </table>
        <a href="form.jsp" class="button primary">INSERIR</a>
      </div>
    </div>
  </div>
</div>
<jsp:include page="footer.jsp"/>