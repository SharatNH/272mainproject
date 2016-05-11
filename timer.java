package trashcancontroller;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

public class timer {

	public static void main(String args[]){
		//Displaying current time

		System.out.println("main function");
		//Creating timer which executes once after five seconds
		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new checkdb(), 10000, 5000);
	}
}

class checkdb extends TimerTask{
	//This task will repeat every five seconds
	public void run(){
		System.out.println("entered run function");
		// access the db to check if the parameter has been pushed
		Connection con = null;
		try {
			System.out.println("connecting to db");
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test","root","abc123");
			System.out.println("connected to db");
		} catch(Exception e) {
			System.out.println("errors");
			e.printStackTrace();
		}

		//write the query
		int count = 1;
		int count2 =0;
		String[] sensorid = new String[20];
		Statement stmt = null;
		String query = 
				"select sensorid from sensordata where settrigger=1";
		// if condition which will check --- done through sql query

		try {
			stmt = (Statement) con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			System.out.println("query executed");

			if(rs.next()){
				rs.beforeFirst();	
				System.out.println("///////////////");
				while (rs.next()) {

					sensorid [count]  = rs.getString("sensorid");
					count=count + 1;

				}

				//update the db
				count2=count;
				System.out.println("Checking for number of rows fetched");
				while(count > 0){
					System.out.println("updated db");
					String query2= 
							"UPDATE sensordata SET settrigger=0 WHERE sensorid = ?";
					PreparedStatement preparedStmt = (PreparedStatement) con.prepareStatement(query2);
					preparedStmt.setString (1, sensorid[count]);
					preparedStmt.execute();
					count--;
				}
				System.out.println("executed if block");
			}
		}catch (SQLException ex ) {
			System.out.println(ex);  
			System.exit(1); 
		} finally {
			System.out.println("finish");
			System.out.println("***********");


		}



		//count2--;
		while(count2 >= 1 )
		{
			System.out.println("messaged will be called " + count2);

			final String username = "sjsu.project.ms@gmail.com";
			final String password = "abcxyz123$";

			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.port", "587");

			Session session = Session.getInstance(props,
					new javax.mail.Authenticator() {
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(username, password);
				}
			});

			try {

				Message message = new MimeMessage(session);
				message.setFrom(new InternetAddress("sjsu.project.ms@gmail.com"));
				message.setRecipients(Message.RecipientType.TO,
						InternetAddress.parse("sjsu.project.ms@gmail.com"));
				message.setSubject("hello");
				message.setText("hi");

				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}
			count2--;
		}


		//call the messaging function


	}






}








