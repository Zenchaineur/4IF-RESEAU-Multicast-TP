/***
 * EchoClient
 * Example of a TCP client 
 * Date: 10/01/04
 * Authors:
 */

package multicast;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

/**
 * Classe représentant un client de l'application
 * @author Binome 1-8
 *	
 */
public class Client {

	int groupPort;
    MulticastSocket multicastSocket = null;
    InetAddress groupAddr;
    String addr = null;
	private BufferedReader stdIn = null;

    
	public Client(int groupPort, String addr) {
		this.groupPort = groupPort;
		this.addr = addr;
		try {
		this.groupAddr = InetAddress.getByName(addr);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * Méthode appelée dans le MainClient, qui permet de gérer les thread d'envoi et de réception de messages des clients du groupe
	 **/
	@SuppressWarnings("deprecation")
	public void run() {

        try {

        	
        this.multicastSocket = new MulticastSocket(this.groupPort);
        multicastSocket.joinGroup(this.groupAddr);
	    this.stdIn = new BufferedReader(new InputStreamReader(System.in));

        byte[] buf = new byte[1000];
        
        // creation des DatagramPacket à envoyer et recevoir

	    
        Thread envoyer = new Thread (new Runnable(){
        	
            String msg = null;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
				try {
					msg = stdIn.readLine();
		        	if (msg.equals(".")) break;
			        DatagramPacket envoi = new DatagramPacket(msg.getBytes(),msg.length(),groupAddr,groupPort);
			        multicastSocket.send(envoi);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
        });
        
        Thread recevoir = new Thread (new Runnable(){

            
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (true) {
	        	try {
	                DatagramPacket reception = new DatagramPacket(buf, buf.length); 
	        		multicastSocket.receive(reception);
	        		String msgreception = new String( buf, StandardCharsets.UTF_8 );
	        		System.out.println(msgreception);
	        	} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			}
        });
        
        recevoir.start();
        envoyer.start();
        
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host:" + this.addr);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for "
                               + "the connection to:"+ this.addr);
            System.exit(1);
        }
    }

	
        
}

