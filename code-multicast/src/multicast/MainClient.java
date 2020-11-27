/***
 * EchoServer
 * Example of a TCP server
 * Date: 10/01/04
 * Authors:
 */

package multicast;

/**
 * Classe représentant le Main du côté du client
 * @author Binome 1-8
 *	
 */
public class MainClient  {
	
	/**
	 * main method
	 * @param args String
	 * 
	 **/
	public static void main(String args[]){ 
		
		Client client;

		if (args.length != 1) {
			System.out.println("Usage: java MainClient <port>");
			System.exit(1);
		}
		client = new Client(Integer.parseInt(args[0]),"228.5.6.7");
		client.run();
	}
}