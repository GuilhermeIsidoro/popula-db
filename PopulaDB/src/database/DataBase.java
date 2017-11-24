package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {
	
	private String url = "jdbc:mysql://localhost:3306/estreaming?useTimezone=true&serverTimezone=UTC&nullNamePatternMatchesAll=true";
	private String usuario = "root";
	private String senha = "";
	
	private Connection conexao;
	
	public Connection getConnection() throws ClassNotFoundException {		
		
		if (conexao == null) {
		
			try {
				
				conexao = DriverManager.getConnection(url, usuario, senha);
				
			} catch (SQLException e) {
				
				e.printStackTrace();			
			}		
		}
			
		return conexao;
	}

}
