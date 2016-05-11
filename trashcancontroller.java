package trashcancontroller;
import java.io.IOException;
import java.io.*;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class trashcancontroller implements MqttCallback{
	MqttClient client;
	String MosquittoBrokerUrl = "tcp://54.213.225.111:2882";
	public static void main(String[] args) {
		new trashcancontroller().demo();
		 	}
	@Override
	public void connectionLost(Throwable cause) {
	    // TODO Auto-generated method stub

	}
	public void demo()
	{
		try {
	    	
	        client = new MqttClient(MosquittoBrokerUrl, "Sending");
	        client.connect();
	        client.setCallback(this);	
	        client.subscribe("/trashcan/alert");
	        System.in.read();
	        client.disconnect();
	        client.close();
	    } catch (MqttException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	@Override
	public void messageArrived(String topic, MqttMessage message)
	        throws Exception {	
		System.out.println(topic);
		//String dic = message.toString();
		System.out.println(message);   
		try{
			
		}
		catch(Exception ex)
		{
			System.out.println(ex.toString());
		}
	}
	@Override
	public void deliveryComplete(IMqttDeliveryToken token) {
	    // TODO Auto-generated method stub

	}
}
