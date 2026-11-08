/*
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.l2jfree;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestConfigRunner {
    
    public static void main(String[] args) {
        System.out.println("===============================================");
        System.out.println("L2JFree CT2.3 - Teste de Configuração");
        System.out.println("===============================================");
        System.out.println();
        
        try {
            // Testar carregamento da configuração
            System.out.println("Carregando configuração...");
            Config.load();
            System.out.println("✅ Configuração carregada com sucesso!");
            
            // Verificar propriedades do banco
            System.out.println("\nVerificando propriedades do banco:");
            String driver = System.getProperty("com.l2jfree.db.driverclass");
            String url = System.getProperty("com.l2jfree.db.urldb");
            String user = System.getProperty("com.l2jfree.db.user");
            String password = System.getProperty("com.l2jfree.db.password");
            
            System.out.println("Driver: " + driver);
            System.out.println("URL: " + url);
            System.out.println("User: " + user);
            System.out.println("Password: " + (password != null ? "***" : "null"));
            
            // Testar conexão com banco
            System.out.println("\nTestando conexão com banco de dados...");
            try (Connection conn = DriverManager.getConnection(url, user, password)) {
                System.out.println("✅ Conexão com banco estabelecida com sucesso!");
                System.out.println("Banco conectado: " + conn.getCatalog());
            }
            
            System.out.println("\n🎯 Todos os testes passaram!");
            
        } catch (Exception e) {
            System.err.println("❌ Erro no teste: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
