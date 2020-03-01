import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.concurrent.TimeUnit;
 
public class Client {
	public static final String IP_ADDR = "ec2-34-210-250-69.us-west-2.compute.amazonaws.com"; // Server Address 
	public static final int PORT = 12345; // General Server Port
	public static int MY_PORT = -1; // Unique Server Port
	public static int CLIENT_ID = 1;
	private static final String header = "$$Hello! I am Client ";
	
    public static void main(String[] args) {  
        System.out.println("Client is starting...");  
        
        if (args.length > 0) {
    	   CLIENT_ID = Integer.parseInt(args[0]);
        }
        
        System.out.println("Client ID is" + CLIENT_ID);
        
        Socket firstTrial = null;
        try {
        	firstTrial = new Socket(IP_ADDR, PORT);
			DataOutputStream out = new DataOutputStream(firstTrial.getOutputStream());
			
			String content = header + Integer.toString(CLIENT_ID);
	    	out.writeUTF(content);
			
	    	DataInputStream input = new DataInputStream(firstTrial.getInputStream());  
	    	String ret = input.readUTF();
	    	
	    	input.close();
	    	out.close();
	    	
			int new_port = Integer.parseInt(ret);
			if (new_port < 12345) {
				System.out.println("Error in Client First Trial of sending message, receieved an invalid new port");
				System.exit(-1);
			}
			MY_PORT = new_port;
		} catch (Exception e) {
			System.out.println("Error in Client First Trial of sending message: " + e.getMessage());
			System.exit(-1);
		} finally {
    		if (firstTrial != null) {
				try {
					firstTrial.close();
				} catch (IOException e) {
					firstTrial = null; 
					System.out.println("Error in First Trial Finally: " + e.getMessage()); 
				}
    		}
		}
        
        try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
        

        Socket secondTrial = null;
        try {
        	System.out.println(MY_PORT);
        	secondTrial = new Socket(IP_ADDR, MY_PORT);
			DataOutputStream out = new DataOutputStream(secondTrial.getOutputStream());
			DataInputStream input = new DataInputStream(secondTrial.getInputStream()); 
			out.writeUTF("&Hello!&");
			String rtn = null;
			
			while (rtn == null) {
				rtn = input.readUTF();
			}
			System.out.println(rtn);
			
			secondTrial.close();
			input.close();
			out.close();
		} catch (Exception e) {
			System.out.println("Error in Client Main running: " + e.getMessage());
			System.exit(-1);
		} finally {
    		if (secondTrial != null) {
				try {
					secondTrial.close();
				} catch (IOException e) {
					secondTrial = null; 
					System.out.println("Error in Second Trial Finally: " + e.getMessage()); 
				}
    		}
		}

        
        // Mimic to do sth
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		Socket thirdTrial = null;
		String output = "123";
		
		try {
			thirdTrial = new Socket(IP_ADDR, MY_PORT);
			DataOutputStream out = new DataOutputStream(thirdTrial.getOutputStream());
			DataInputStream input = new DataInputStream(thirdTrial.getInputStream()); 
			out.writeUTF("&&DONE!" + output);
			
			String rtn = null;
			while (rtn == null) {
				rtn = input.readUTF();
			}
			
			System.out.println(rtn);
			if (rtn.equals("Thank you!")) {
				System.out.println("Achieved Here!");
			}
			
			input.close();
			out.close();
		} catch (Exception e) {
			System.out.println("Error in Client Third Trial: " + e.getMessage());
			System.exit(-1);
		} finally {
    		if (thirdTrial != null) {
				try {
					thirdTrial.close();
				} catch (Exception e) {
					thirdTrial = null; 
					System.out.println("Error in Third Trial Finally: " + e.getMessage()); 
				}
    		}
		}
    }  

}  