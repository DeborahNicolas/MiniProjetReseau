import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
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
import java.security.spec.X509EncodedKeySpec;

import java.util.Base64.*;




public class PB {
    
    final String encryptedValue = "I saw the real you" ; //mettre un fichier txt
	String secKey = "ubutru";   //a modifier
    
    
	public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException, Base64DecodingException{
		InetAddress addr;
		Socket client;
		PrintWriter out;
		BufferedReader in;
		String input;
		String userInput;
		boolean doRun = true;
                String encryptedVal = null; //sera rempli par la suite
		String valueEnc = "aazzaa"; //a modifier avec le message de la clé partagée
		String encodePK = null;
		
		Scanner k = new Scanner(System.in);
		try{
                    
                        //Generer paire de cles RSA
                    KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		    generator.initialize(2048, new SecureRandom());
		    KeyPair pair = generator.generateKeyPair();
                    
                    
                    //Recuperer cle publique et l'envoyer
                    
                    PublicKey clePu = pair.getPublic();
                    
                    
                    
			client = new Socket("localhost", 4444);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			
			System.out.print("enter msg> ");
			userInput = k.nextLine();
                        encodePK = Base64.encode(clePu.getEncoded());
                        
			out.println(encodePK.replace("\n",""));
			out.flush();
			//System.out.println(encodePK);
                        
                       
                        
                        
			
			if(userInput.compareToIgnoreCase("bye")==0)
			{
				System.out.println("shutting down");
				doRun = false;
			}else
			{
                            input = in.readLine();
                            while(input == null) input = in.readLine();
                            //On déchiffre la clé partagée reçue
                                        
                                        
                                        // creer un autre cipher et l'initialiser avec la cle privee et mode dechiffrement
                                        Cipher decryptCipher = Cipher.getInstance("RSA");
                                        decryptCipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());

                                      // dechiffrer encoded
                                        
                                        
                                        byte[] tobytes = decryptCipher.doFinal(Base64.decode(input));
                                        
                                        SecretKey originalKey = new SecretKeySpec(tobytes, 0, tobytes.length, "AES");
                                        
                                        System.out.println("next line is original text:");
                                        System.out.println(new String(tobytes));
                                        System.out.println(originalKey);
                            
                        }
                        input=null;
				while(doRun){
					input = in.readLine();
					while(input == null) input = in.readLine();
                                        
                                        
					
					System.out.println(input);
                                        
                                        System.out.println("clé partagée reçue");
                                        
                                        
                                        
                                        
                                        
                                        
                                        
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