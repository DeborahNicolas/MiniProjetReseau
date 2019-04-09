
import com.sun.org.apache.xml.internal.security.utils.Base64;
import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Scanner;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class ChatServer implements Runnable{
	PrintWriter out;
	BufferedReader in;
	Socket s;
	Scanner keyboard;
	int index;
	String input;
	boolean doRun = true;
	
	public ChatServer(Socket a, int u){
		s = a;
		keyboard = new Scanner(System.in);
		index = u;
	}
	
	public void run(){
            
            
		try{
                    
                        
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
			out = new PrintWriter(s.getOutputStream());
			
			System.out.println("connexion de "+s.getInetAddress().toString()+" sur le port "+s.getPort());
			String talk = in.readLine();
                        
                        
                        //On génère la clé partagée
                        
                        KeyGenerator generator = KeyGenerator.getInstance("AES");
                        generator.init(128);
                        SecretKey key = generator.generateKey();
                        
                        System.out.println("cle partagee : " + key);
                        
                       // System.out.println(talk);
                        
                        //transformer talk en cle publique
                        
                        byte[] decodedKey = Base64.decode(talk);
		    X509EncodedKeySpec speci =  new X509EncodedKeySpec(decodedKey);
		    KeyFactory kf = KeyFactory.getInstance("RSA");
		    PublicKey pk =  kf.generatePublic(speci);
                        
                    
                    //On chiffre la clé partagée 
                    //On utilise l'algo RSA
                    //initialiser cypher avec cle publique
                        
                   
                        Cipher cipher = Cipher.getInstance("RSA");
                        cipher.init(Cipher.ENCRYPT_MODE, pk);
                        byte[] res = cipher.doFinal(key.getEncoded());
                        String res_str =  Base64.encode(res);//new String(res);
                        
                        
                        System.out.println("cle partagee codee : " + res_str);
                        
                        //Envoi de ma clé partagée codée
                        
                        out.println(res_str.replace("\n",""));
                        //Oblige le message à s'envoyer dessuite
                        out.flush();
                        
                        
                        //Réception des messages avec la clé partagée
                       
                     //   cipher.init(Cipher.DECRYPT_MODE, key);
	               
                     //   byte[] res2 = cipher.doFinal(Base64.decode(talk));
	                
                     //   String res_str2 =  new String(res2);
                        
                     //   System.out.println("message :");
                     //   System.out.println(res_str2);
                        
                        
			while(doRun)
			{                     
				while(talk == null)
				{
					talk = in.readLine();
				}
				System.out.println(talk);
				if(talk.compareToIgnoreCase("bye")==0)
				{
					System.out.println("shutting down following remote request");
					doRun = false;
				}else
				{
					System.out.print("to client#"+index+"> ");
					input = keyboard.nextLine();
					out.println(input);
					out.flush();
					if(input.compareToIgnoreCase("bye")==0)
					{
						System.out.println("server shutting down");
						doRun = false;
					}else
						talk = in.readLine();
                                                                                     
				}
			}
			s.close();
		}
		catch(Exception e){
			System.out.println("raaah! what did u forget this time?");
			e.printStackTrace();
		}		
	}
}
