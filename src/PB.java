import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import java.util.Base64.*;




public class PB {
    
    final String encryptedValue = "I saw the real you" ; //mettre un fichier txt
	String secKey = "ubutru";   //a modifier
    
    
	public static void main(String[] args) {
		InetAddress addr;
		Socket client;
		PrintWriter out;
		BufferedReader in;
		String input;
		String userInput;
		boolean doRun = true;
                String encryptedVal = null; //sera rempli par la suite
		String valueEnc = "aazzaa"; //a modifier avec le message de la clé partagée
		
		
		Scanner k = new Scanner(System.in);
		try{
			client = new Socket("localhost", 4444);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			System.out.print("enter msg> ");
			userInput = k.nextLine();
			out.println(userInput);
			out.flush();
			System.out.println("done");
			
			if(userInput.compareToIgnoreCase("bye")==0)
			{
				System.out.println("shutting down");
				doRun = false;
			}else
			{
				while(doRun){
					input = in.readLine();
					while(input == null) input = in.readLine();
					
					System.out.println(input);
					if(input.compareToIgnoreCase("bye")==0)
					{
						System.out.println("client shutting down from server request");
						doRun = false;
					}else
					{
						System.out.print("enter msg> ");
						userInput = k.nextLine();
						out.println(userInput);
						out.flush();
						if(userInput.compareToIgnoreCase("bye")==0)
						{
							System.out.println("shutting down");
							doRun = false;
						}
						
					}
				}
			}
			client.close();
			k.close();
		}catch(UnknownHostException e){
			e.printStackTrace();
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
}