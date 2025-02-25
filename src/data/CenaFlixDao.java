/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package data;

import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CenaFlixDao {
    
    Connection conn;
    PreparedStatement st;
    ResultSet rs;
    
         public CenaFlixDao() {
                this.conn = this.conectar();
                }
    
    public Connection conectar(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atividade1","root","Supersenha0112");
            return conn;
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println("Erro ao conectar: " + ex.getMessage());
            return null;
        }
    }
    
        public int salvar(CenaFlix cenaFlix){
        int status;
        try {
            st = conn.prepareStatement("INSERT INTO filmes (nome, datalancamento, categoria) VALUES (?, ?, ?)");
            st.setString(1,cenaFlix.getNomeFilme());
            st.setString(2,cenaFlix.getLancamentoFilme());
            st.setString(3,cenaFlix.getCategoriaFilme());
            status = st.executeUpdate();
            return status; //retornar 1
        } catch (SQLException ex) {
            System.out.println("Não foi possível inserir os dados! Por favor, verifique valores digitados " + ex.getMessage());
            return ex.getErrorCode();
        }
        
        }
    
            public void editar(CenaFlix cenaFlix) {
                String sql = "UPDATE filmes SET nome=?, datalancamento=?, categoria=? WHERE id=?";
                
                try {

                    PreparedStatement stmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_UPDATABLE);

                    stmt.setString(1, cenaFlix.getNomeFilme());
                    stmt.setString(2, cenaFlix.getLancamentoFilme());
                    stmt.setString(3, cenaFlix.getCategoriaFilme());
                    stmt.setInt(4, cenaFlix.getId());

                    stmt.execute();

                } catch (Exception e) {
                    System.out.println("Erro aos editar empresa, por favor, verifique e tente novamente: " + e.getMessage());
                }
           }
           
           public void excluir (int id){
                
                String sql = "DELETE FROM filmes WHERE id = ?";
                try {
                    PreparedStatement stmt = this.conn.prepareStatement(sql);
                    
                    stmt.setInt(1, id);
                    
                    stmt.execute();

                } catch (Exception e) {
                    System.out.println("Erro ao excluir empresa, por favor, verifique e tente novamente: " + e.getMessage());
                }
                
            }
           
         public List<CenaFlix> getFilmes() {
                    
                    String sql = "SELECT * FROM filmes";
                    try {
                        
                        PreparedStatement stmt = this.conn.prepareStatement(sql);
                        ResultSet rs = stmt.executeQuery();
                        List<CenaFlix> lista = new ArrayList<>();
                        
                        while(rs.next()){
                            CenaFlix cenaFlix = new CenaFlix();
                            
                            cenaFlix.setId(rs.getInt("id"));
                            cenaFlix.setNomeFilme(rs.getString("nome"));
                            cenaFlix.setLancamentoFilme(rs.getString("datalancamento"));
                            cenaFlix.setCategoriaFilme(rs.getString("categoria"));

                            lista.add(cenaFlix);

                        }
                        return lista;
                    } catch (Exception e) {
                        return null;
                    }
                
            }
         
           public CenaFlix getFilmes(int id){
                String sql = "SELECT * FROM filmes WHERE id = ?";
                try {

                    PreparedStatement stmt = this.conn.prepareStatement(sql);
                    stmt.setInt(1, id);
                    ResultSet rs = stmt.executeQuery();

                    CenaFlix cenaFlix = new CenaFlix();
                    
                    rs.next();
                    
                    cenaFlix.setId(id);
                    cenaFlix.setNomeFilme(rs.getString("nome"));
                    cenaFlix.setLancamentoFilme(rs.getString("datalancamento"));
                    cenaFlix.setCategoriaFilme(rs.getString("categoria"));

                    return cenaFlix;
                    
                    //tratando o erro, caso ele ocorra
                } catch (Exception e) {
                    System.out.println("Erro: " + e.getMessage());
          return null;
                 }
           }

            public CenaFlix getFilmeNome (String nomeFilme){
                String sql = "SELECT * FROM filmes WHERE nome LIKE ?";
                
                try {        
                    PreparedStatement stmt = this.conn.prepareStatement(sql);
                  
                    stmt.setString(1,"%" + nomeFilme + "%");
                    
                    ResultSet rs = stmt.executeQuery();
              
                    CenaFlix cenaFlix = new CenaFlix();
                    
                    rs.next();
                    
                    cenaFlix.setId(rs.getInt("id"));
                    cenaFlix.setNomeFilme(rs.getString("nome"));
                    cenaFlix.setLancamentoFilme(rs.getString("datalancamento"));
                    cenaFlix.setCategoriaFilme(rs.getString("categoria"));

                    return cenaFlix;
                  
                  //tratando o erro, caso ele ocorra
                } catch (Exception e) {
                    System.out.println("erro: " + e.getMessage());
                    return null;
                }
            }
            
           public List<CenaFlix> getListaFilme(String nome){
                String sql = "SELECT * FROM filmes WHERE nome LIKE ?";
                
                try {
                    PreparedStatement stmt = this.conn.prepareStatement(sql);
 
                    stmt.setString(1,"%" + nome + "%"); 

                    ResultSet rs = stmt.executeQuery();            
          
                    List<CenaFlix> listaCenaFlix = new ArrayList<>();


                    while (rs.next()) { 
                    CenaFlix  cenaFlix = new CenaFlix();
                    
                    cenaFlix.setId(rs.getInt("id"));
                    cenaFlix.setNomeFilme(rs.getString("nome"));
                    cenaFlix.setLancamentoFilme(rs.getString("datalancamento"));
                    cenaFlix.setCategoriaFilme(rs.getString("categoria"));

                    listaCenaFlix.add(cenaFlix);
                            
                    }
                    return listaCenaFlix;
                    
                } catch (Exception e) {
                    return null;
                }
           }
                
        public void desconectar(){
            try {
                conn.close();
            } catch (SQLException ex) {
        }
    }

}
