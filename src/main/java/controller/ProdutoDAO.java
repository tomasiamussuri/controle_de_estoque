package controller;

import model.Produto;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ProdutoDAO {
    Connection conn;

    public ProdutoDAO() throws SQLException {
        this.conn = ConexaoSQLite.getConexao();
    }

    // LISTAR TODOS OS PRODUTOS
    public ArrayList<Produto> getAll() throws SQLException {
        ArrayList<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto;";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Produto produto = new Produto();
            produto.setId(resultSet.getInt("Id"));
            produto.setNome(resultSet.getString("Nome"));
            produto.setDescricao(resultSet.getString("Descricao"));
            produto.setCategoria(resultSet.getString("Categoria"));

            produtos.add(produto);
        }
        return produtos;
    }

    // SELECIONAR UM PRODUTO
    public Produto getOne(int id) throws SQLException {
        String sql = "SELECT * FROM produto WHERE Id = ?;";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        Produto produto = new Produto();
        produto.setId(resultSet.getInt("Id"));
        produto.setNome(resultSet.getString("Nome"));
        produto.setDescricao(resultSet.getString("Descricao"));
        produto.setCategoria(resultSet.getString("Categoria"));
        return produto;
    }

    // INSERIR NOVO PRODUTO
    public Produto insert() throws SQLException {
        String sql = "INSERT INTO produto VALUES(null, ?, ?, ?);";
        String sql2 = "SELECT MAX(Id) as Id,nome,descricao,categoria FROM produto;";

        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o nome do produto: ");
        String nome = sc.nextLine();
        System.out.print("\nInforme a descrição do produto: ");
        String descricao = sc.nextLine();
        System.out.print("\nInforme a categoria do produto: ");
        String categoria = sc.nextLine();
        preparedStatement.setString(1, nome);
        preparedStatement.setString(2, descricao);
        preparedStatement.setString(3, categoria);
        preparedStatement.executeUpdate();

        PreparedStatement prepareStatement2 = conn.prepareStatement(sql2);
        ResultSet resultSet = prepareStatement2.executeQuery();

        Produto produto = new Produto();
        produto.setId(resultSet.getInt("Id"));
        produto.setNome(resultSet.getString("Nome"));
        produto.setDescricao(resultSet.getString("Descricao"));
        produto.setCategoria(resultSet.getString("Categoria"));
        return produto;
    }


    // ALTERAR UM PRODUTO
    public void update(int id, String campo, String valor) throws SQLException {
        String sql = "UPDATE produto SET " + campo + " = '" + valor + " ' WHERE Id = " + id + ";";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.executeUpdate();
    }


    // DELETAR UM PRODUTO
    public void delet() throws SQLException {
        String sql = "DELETE FROM produto WHERE Id = ?;";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);

        Scanner sc = new Scanner(System.in);
        System.out.print("\nInforme o Id do produto: ");
        int id = sc.nextInt();
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();

    }
}