

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;

class Helper {
	   //JDBC driver name and database URL
	   static final String JDBC_DRIVER = "org.postgresql.Driver";  
	   static final String DB_URL = "jdbc:postgresql://localhost:5432/local_kinodatenbank";

	   //Database credentials
	   static final String USER = "postgres";
	   static final String PASS = "postgres";
	   
		static Scanner sc = new Scanner(System.in);
		
		static int showMenu() {
			System.out.println(System.lineSeparator());
			System.out.println("=============== [ Menu ] ===============");
			System.out.println("1) Ausgabe der Tabelle");
			System.out.println("2) Neuen Datensatz eingeben");
			System.out.println("3) Datensatz loeschen");
			System.out.println("4) Durch Datensaetze einzeln navigieren");
			System.out.println("0) Programm beenden");
			System.out.print(System.lineSeparator() +System.lineSeparator() +"Bitte waehlen sie (0-4): ");
			
			try {
				return sc.nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Fehlerhafte eingabe");
				return 0;
			}
		}
	   
	  }

/**
 * @author Sina Haddadi Sedehi s0566440
 *
 */
public class Main {	
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
			   Connection conn = null;
			   Statement stmt = null;
			   	try {
					//Register JDBC driver
					Class.forName(Helper.JDBC_DRIVER);
					//Open a connection
					System.out.println("Connecting to database...");
					conn = DriverManager.getConnection(Helper.DB_URL, Helper.USER, Helper.PASS);
					System.out.println("Creating statement...");
					stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
				} catch (ClassNotFoundException e1) {
					//Handle errors for Class.forName
					e1.printStackTrace();
				} catch (SQLException e1) {
					System.out.println("Error while connecting");
					e1.printStackTrace();
				}
			   	
			   int choice;
			   do {
				  choice = Helper.showMenu();
				  
				  if(choice == 1) {
					  try {
						ResultSet rs = stmt.executeQuery("SELECT * FROM kunde");
						System.out.println();
						System.out.println("=============== [ Tabelle: Kunde ] ===============");
						//Extract data from result set
					      while(rs.next()){
					         //Retrieve by column name
					         int id  = rs.getInt("id");
					         String vorname = rs.getString("vorname");
					         String nachname = rs.getString("nachname");
					         String gebDatum = rs.getString("geburtsdatum");
					         String email = rs.getString("email");

					         //Display values
					         System.out.print(id +": " +vorname +" " +nachname +", " +gebDatum +", " +email +System.lineSeparator());
					      }
					      pressEnterToContinue();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				  }
				  
				  if(choice == 2) {
					  System.out.println("=============== [ Datensatz eingeben ] ===============");
					  
					  System.out.print(System.lineSeparator() +"ID: ");
					  String id = sc.nextLine();
					  
					  System.out.print(System.lineSeparator() +"Vorname: ");
					  String vorname = sc.nextLine();
					  
					  System.out.print(System.lineSeparator() +"Nachname: ");
					  String nachname = sc.nextLine();
					  
					  System.out.print(System.lineSeparator() +"Geburtsdatum (dd.mm.yyyy): ");
					  String geburtsdatum = sc.nextLine();
					  
					  System.out.print(System.lineSeparator() +"Email (optional): ");
					  String email = sc.nextLine();
					  
					  if(email.equals("")) {
						  email = "null";
					  }else {
						  email = "'"+email+"'";
					  }
					  
					  try {
						stmt.executeUpdate("INSERT INTO kunde(id, vorname, nachname, geburtsdatum, email) "
						  					+ "VALUES ('"+id+"', '"+vorname+"', '"+nachname+"', '"+geburtsdatum+"', "+email+");");
						System.out.println("Datensatz hinzugefuegt!");
						pressEnterToContinue();
					} catch (SQLException e) {
						System.err.println("INSERT INTO FEHLER");
					}
				  }
				  
				  if(choice == 3) {	
						System.out.print("ID eingeben: ");
						int id = sc.nextInt();
					  
					  try {
						stmt.executeUpdate("DELETE FROM kunde WHERE id = " +id);
						System.out.println("Datensatz ID " +id +" geloescht!");
						pressEnterToContinue();
					} catch (SQLException e) {
						System.out.println("DELETE FEHLER");
					}
				  }
				  
				  if(choice == 4) {
					  // TODO
					  Scanner s4 = new Scanner(System.in);
					  ResultSet rs = null;
					  try {
						rs = stmt.executeQuery("SELECT * FROM kunde");
						String nav = "";
						
						while(true) {
							System.out.print("n/p/e: ");
							nav = s4.next();
							if(nav.equals("e")) {
								break;
							}
							
							if(nav.equals("n")) {
								if (rs.next()) {
							
								}else {
									rs.first();
									
								}
								
							}
							
							if(nav.equals("p")) {
								if (rs.previous()) {
									
								}else {
									rs.last();
									
								}
							}
							
							//Retrieve by column name
					         int id  = rs.getInt("id");
					         String vorname = rs.getString("vorname");
					         String nachname = rs.getString("nachname");
					         String gebDatum = rs.getString("geburtsdatum");
					         String email = rs.getString("email");

					         //Display values
					         System.out.print(id +": " +vorname +" " +nachname +", " +gebDatum +", " +email +System.lineSeparator());
					     
						}
					
				         pressEnterToContinue();
					} catch (SQLException e) {
						e.printStackTrace();
					}
				  }
				  
				  if(choice == 0) {
					  try {
						//Clean-up environment
						  stmt.close();
						  conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					  System.out.println("Programm beendet!");
				  }
				  
			   }while(choice >= 1);
			   
			   
			  
			   
	}

	private static void pressEnterToContinue() {
		Scanner s = new Scanner(System.in);
		System.out.println(System.lineSeparator());
		System.out.println("Press Enter to continue...");
		s.nextLine();
	}
}
