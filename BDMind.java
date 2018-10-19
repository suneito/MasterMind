/*
 * BDMind.java
 * 2018-06-12
 * 
 * Copyright 2018 Adrià Babot <daw1ababotllinares@iesjoanramis.org>
 */
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class BDMind {
	public static void mostraSQLException(SQLException ex){
		ex.printStackTrace(System.err);
		System.out.println("SQLState: "+ ex.getSQLState());
		System.out.println("Error code: "+ ex.getErrorCode());
		System.out.println("Message: "+ ex.getMessage());
		Throwable t=ex.getCause();
		while(t!=null){
			System.out.println("Cause: "+t );
			t=t.getCause();
		}
	}
		
	/**Metode per la creacio de la BD,taula i usuari*/
	public static void creaBD(){
		String url="jdbc:mysql://localhost:3307";
		String user="root";
		String pwd= "";
		String inUse= "use MasterMindBD";
		
		String creacioBD="create database if not exists MasterMindBD";
		String creacioTaulaPartida="create table if not exists Partida ("+
			"id_partida int primary key,"+
			"size tinyint,"+
			"tirades tinyint,"+
			"num_aleatori varchar(10),"+
			"partida_acabada bool,"+
			"partida_guanyada bool)";
		String creacioTaulaTirada="create table if not exists Tirada ("+
			"id_tirada int auto_increment primary key,"+
			"id_partida_propietaria int,"+
			"size tinyint,"+
			"resposta varchar(10),"+
			"taula_ajuda varchar(10),"+
			"be_posicionats tinyint,"+
			"mal_posicionats tinyint)";
	
		String creacioUsuari= "create user if not exists 'usuariMind'@'localhost' identified by 'Mind18'";
		String permisosUsuari="grant all privileges on MasterMindBD.* to 'usuariMind'@'localhost' identified by 'Mind18' with grant option"; 
		
		Connection con=null;
		Statement st=null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			
			st.executeUpdate(creacioBD);
			st.executeUpdate(inUse);
			st.executeUpdate(creacioTaulaPartida);
			st.executeUpdate(creacioTaulaTirada);
			st.executeUpdate(creacioUsuari);
			st.executeUpdate(permisosUsuari);
			
			if(st!=null)st.close();
			if(con!=null)con.close();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			mostraSQLException(e);
		}
	}
	
	/**
	 * Mètode per fer una inserció de dades de partida
	 */
	public static void guardaPartida(Partida p1){
		String url="jdbc:mysql://localhost:3307";
		String user="usuariMind";
		String pwd= "Mind18";
		String inUse= "use MasterMindBD";
		
		String insert="insert into partida values ("+
			p1.exportaPartida()+");";
			
		Connection con=null;
		Statement st=null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			
			st.executeUpdate(inUse);
			System.out.println("Partida guardada "+st.executeUpdate(insert));
			if(st!=null)st.close();
			if(con!=null)con.close();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			mostraSQLException(e);
		}
	}		
	/**
	 * Mètode per fer una inserció de dades de tirada
	 */
	public static void guardaTirada(Tirada t1){
		String url="jdbc:mysql://localhost:3307";
		String user="usuariMind";
		String pwd= "Mind18";
		String inUse= "use MasterMindBD";
		
		String insert="insert into tirada values ("+
			t1.exportaTirada()+");";
			
		Connection con=null;
		Statement st=null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			
			st.executeUpdate(inUse);
			st.executeUpdate(insert);
			if(st!=null)st.close();
			if(con!=null)con.close();
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){
			mostraSQLException(e);
		}
	}		
	
	/**
	 * Recupera l'últim id de partida registrat
	 */
	public static int idPartida(){
		String url="jdbc:mysql://localhost:3307";
		String user="usuariMind";
		String pwd= "Mind18";
		String inUse= "use MasterMindBD";
		
		int idPartida=0;
		
		String select="select max(id_partida) from partida";
		Connection con=null;
		Statement st=null;
		ResultSet rs;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			st.executeUpdate(inUse);
			
			rs=st.executeQuery(select);
			while(rs.next()){
				idPartida=rs.getInt("max(id_partida)");
			}
			if(st!=null)st.close();
			if(con!=null)con.close();
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){}
		
		return idPartida;
	}
		
	/**
	 * Retorna si una partida existeix o no
	 */
	public static boolean partidaExistent(int id){
		String url="jdbc:mysql://localhost:3307";
		String user="usuariMind";
		String pwd= "Mind18";
		String inUse= "use MasterMindBD";
		
		String select="select count(id_partida) from partida where id_partida="+id;
		boolean result = false;
		
		Connection con=null;
		Statement st=null;
		ResultSet rs;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			st.executeUpdate(inUse);
			
			rs=st.executeQuery(select);
			while(rs.next()){
				if (rs.getInt("count(id_partida)")>0) {
					result =true;
					};
			}
			if(st!=null)st.close();
			if(con!=null)con.close();

		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){}
		
		return result;
	}
	
	/**
	 * Recupera l'últim id de partida registrat
	 */	
	public static String llistatPartides(){
		String url="jdbc:mysql://localhost:3307";
		String user="usuariMind";
		String pwd= "Mind18";
		String inUse= "use MasterMindBD";
		String select="select * from partida";
		String result="ID\tDigits\tTirades\tAcabada\tGuanyada\n";
		
		Connection con=null;
		Statement st=null;
		ResultSet rs;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			st.executeUpdate(inUse);
			
			rs=st.executeQuery(select);

			while(rs.next()){
				result=result+rs.getInt("id_partida")+"\t";
				result=result+rs.getInt("size")+"\t";
				result=result+rs.getInt("tirades")+"\t";
				result=result+rs.getBoolean("partida_acabada")+"\t";
				result=result+rs.getBoolean("partida_guanyada") + "\n";	
			}
			
			if(st!=null)st.close();
			if(con!=null)con.close();
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){}
		
		return result;
	}
	
	/**
	 * Mètode per esborrar una partida. 
	 */
	public static void borraPartida(int id){
		String url="jdbc:mysql://localhost:3307";
		String user="usuariMind";
		String pwd= "Mind18";
		String inUse= "use MasterMindBD";
		String delete="delete from partida where id_partida="+id+";";
		
		Connection con=null;
		Statement st=null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			st.executeUpdate(inUse);
			
			st.executeUpdate(delete);
			
			if(st!=null)st.close();
			if(con!=null)con.close();
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){}
	}
	
	/**
	 * Mètode que recupera una partida de la BD 
	 */
	public static Partida obtePartida(int id){
		String url="jdbc:mysql://localhost:3307";
		String user="usuariMind";
		String pwd= "Mind18";
		String inUse= "use MasterMindBD";
		String select="select * from partida where id_partida="+id+";";
		
		Partida partida=null;
		Connection con=null;
		Statement st=null;
		ResultSet rs;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con=DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			st.executeUpdate(inUse);
			
			rs=st.executeQuery(select);

			while(rs.next()){
				partida = new Partida(
					rs.getInt("id_partida"),
					rs.getInt("size"),
					rs.getInt("tirades"),
					rs.getString("num_aleatori"),
					rs.getBoolean("partida_acabada"),
					rs.getBoolean("partida_guanyada"),
					obteTirades(id)
				);
			}
			
			if(st!=null)st.close();
			if(con!=null)con.close();
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch(SQLException e){}

		return partida;
	}
	
	/**
	 * Mètode que recupera un llistat de tirades que pertanyen a una partida
	 */
	private static List<Tirada> obteTirades(int id){
		List<Tirada> taula=new ArrayList<Tirada>();
		String url="jdbc:mysql://localhost:3307";
		String user="usuariMind";
		String pwd= "Mind18";
		String inUse= "use MasterMindBD";
		String select = "select * from tirada where id_partida_propietaria="+id+";";
		
		Connection con=null;
		Statement st=null;
		ResultSet rs;
		
		try{
			Class.forName("com.mysql.jdbc.Driver"); 
			con = DriverManager.getConnection(url,user,pwd);
			st=con.createStatement();
			st.executeUpdate(inUse);
			
			rs=st.executeQuery(select);
			while(rs.next()){
				taula.add(new Tirada(
					rs.getString("resposta"),
					rs.getInt("size"),
					rs.getString("taula_ajuda"),
					rs.getInt("be_posicionats"),
					rs.getInt("mal_posicionats")
				));
			}

			if (st!=null) st.close();
			if (con!=null) con.close();
			
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}catch (SQLException e){
			mostraSQLException(e);	
		}
		return taula;
	}
	
	
	public static void main (String args[]){
		creaBD();
		System.out.println(llistatPartides());
		System.out.println(obtePartida(1));
		System.out.println("partida existent :"+partidaExistent(7));
	}

}

