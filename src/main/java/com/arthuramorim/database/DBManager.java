package com.arthuramorim.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
    private static Connection connection;

    private final String user;
    private final String pass;
    private final String host;
    private final int port;
    private final String db;
    private int query;




    public DBManager(String user, String pass, String host, int port, String db) {
        this.user = user;
        this.pass = pass;
        this.host = host;
        this.port = port;
        this.db = db;
        this.query = 0;
        loadDB();
    }

    public void openConnection() {
        try {
            query++;
            if ((connection != null) && (!connection.isClosed()))
                return;

            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + db + "?useSSL=true", user, pass);
            System.out.println("Conexao aberta com sucesso!");
        } catch (Exception e) {
            query--;
            e.getStackTrace();
            System.out.println(
                    "Ocorreu um erro ao abrir a conexão: " + e.getMessage());
        }

    }

    public void closeConnection() {
        query--;
        if (query <= 0) {
            try {
                if (connection != null && !connection.isClosed())
                    connection.close();
                System.out.println("Conexao fechada com sucesso!");
            } catch (Exception e) {
                System.out.println(
                        "Houve um erro ao fechar a conexão!");
            }
        }
    }

    public Connection getConnection() {
        return this.connection;
    }

    private void loadDB() {
        openConnection();
        criarTabelas();
        closeConnection();
    }

    private void criarTabela(String tabela, String colunas) {
        try {
            if ((connection != null) && (!connection.isClosed())) {
                Statement stm = connection.createStatement();
                stm.executeUpdate("CREATE TABLE IF NOT EXISTS " + tabela + " (" + colunas + ");");
            }
            System.out.println("tabela recuperada com sucesso!");
        } catch (SQLException e) {
            System.out.println("Ocorreu um erro ao criar as tabelas: " + e.getMessage());
        }
    }

    private void criarTabelas() {
        criarTabela("neromissoes", "uuid varchar(255) primary key, name varchar(16) not null, missoes JSON");
    }
}
