import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Server {
	public static final int PORT = 12345; // Port to listen 
	public static int CLIENT_COUNTER = 0; // Counter for client connections
	public static int CLIENT_PORT = 12345; // Port for further connections (data transfers)
	public static Lock PORT_LOCK = new ReentrantLock(); // Lock for CLIENT_PORT
	public static int CONTACTS = 0;
	public static int EXPECTED_CONTACTS = 2;
	
    public static void main(String[] args) {  
        System.out.println("Server is starting! \n");  
        Server server = new Server();  
        server.init();  

    }  
  
    public void init() {  
        try {  
            ServerSocket serverSocket = new ServerSocket(PORT);  
            while (true) {  
                // As long as there is a block, there is a connection from client 
                Socket client = serverSocket.accept();  
                System.out.println("Now we have " + (++CONTACTS) + "Contacts");
                
                // Handle this connection  
                new HandlerThread(client);  
                
                if (CONTACTS >= EXPECTED_CONTACTS)
                	break;
            }  
        } catch (Exception e) {  
            System.out.println("Server Error: " + e.getMessage());  
        }  
    }  
  
    private class HandlerThread implements Runnable {  
        private Socket connectionSocket;  
        private boolean validClient = true;
        private ServerSocket uniqueSocket = null;
        private long start = System.currentTimeMillis();
        private long finish;
        private long timeElapsed;
        
        
        public HandlerThread(Socket client) {  
        	connectionSocket = client;  
            System.out.println("Got a connection from client!");
            new Thread(this).start();  
        }  
  
        public void run() {  
            try {  
            	
            	// Test the first message. If passed, then send back new port number
            	// Lock to ensure it is the same client receiving new port 
            	PORT_LOCK.lock();
            	int clientID = checkFirstMessage();
            	System.out.println("Connection from client " + clientID);
            	
            	int newPortNum = sendNewPortNumber();
            	PORT_LOCK.unlock();
            	
            	System.out.println("Building new server socket for Client " + clientID + " at Port " + newPortNum);
            	uniqueSocket = new ServerSocket(newPortNum);
            	
            	while (true) {
            		Socket uniqueClient = uniqueSocket.accept();
            		DataInputStream input = new DataInputStream(uniqueClient.getInputStream());
            		DataOutputStream out = new DataOutputStream(uniqueClient.getOutputStream());
            		
            		
            		String clientInputStr = input.readUTF();
            		if (clientInputStr.equals("&Hello!&")) {
            			System.out.println("\nReceived Second Hello!\n");
            			out.writeUTF("TEST" + CONTACTS);
            		} else if (clientInputStr.substring(0, 7).equals("&&DONE!")) {
            			String rtn = clientInputStr.substring(7);
            			System.out.println(rtn);
            			out.writeUTF("Thank you!");
            			break;
            		}
            		
            		input.close();
            		out.close();
            	}          	

            	finish = System.currentTimeMillis();
            	timeElapsed = finish - start;
            	
            	System.out.println("-----Time Elapsed: " + timeElapsed + "-----\n");
            	
            } catch (Exception e) {  
                System.out.println("Error in Thread Run: " + e.getMessage());  
            } finally {  
                if (connectionSocket != null) {  
                    try {  
                    	connectionSocket.close();  
                    } catch (Exception e) {  
                    	connectionSocket = null;  
                        System.out.println("Error in Thread Finally with connectionSocket: " + e.getMessage());  
                    }  
                }  
                
                if (uniqueSocket != null) {  
                    try {  
                    	uniqueSocket.close();  
                    } catch (Exception e) {  
                    	uniqueSocket = null;  
                        System.out.println("Error in Thread Finally with uniqueSocket: " + e.getMessage());  
                    }  
                }
            } 
        } 
        
        /**
         * Local Method to check if the first message is valid
         * @throws IOException
         */
        private int checkFirstMessage() throws IOException {
        	int rtn = -1;
        	
        	// Read the first message from client  
            DataInputStream input = new DataInputStream(connectionSocket.getInputStream());
            String clientInputStr = input.readUTF();
            
            // Check if the first message is valid
            if (clientInputStr.length() < 21) {
            	System.out.println("Non-valid Client Connection!");
            	validClient = false;
            	
            } else {
            	final String header = "$$Hello! I am Client ";
                final String inputSubstring = clientInputStr.substring(0, 21);
                if (header.equals(inputSubstring)) {  
	                System.out.println("Valid Client Connection!");  
	                rtn = Integer.parseInt(clientInputStr.substring(21));
	            } else {
	            	System.out.println("Non-valid Client Connection!"); 
	            	validClient = false;
	            }
            }
            
            if (validClient == false) {
            	DataOutputStream out = new DataOutputStream(connectionSocket.getOutputStream());
            	out.writeUTF("CLOSE");  
                out.close();
                input.close();
            	Thread.currentThread().interrupt();
            }
            
			return rtn;
        	
        }
        
        /**
         * Local Method to send new port number
         * @throws IOException
         */
        private int sendNewPortNumber() throws IOException {
        	DataOutputStream newPortNumSender = new DataOutputStream(connectionSocket.getOutputStream());
        	int newPortNum = 10000;
        	newPortNum = ++CLIENT_PORT;
        	
        	String sending = Integer.toString(newPortNum);
        	newPortNumSender.writeUTF(sending);
        	
        	return newPortNum;
        }
    }  
}  