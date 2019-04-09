import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.X509EncodedKeySpec;
//import java.util.Base64;
//import java.util.Base64.*;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


public class test_asymp {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String spec="RSA";
		String encoded2 = "raFyOW+mtQwCRYLrMgbmR+zfyVEICUd3QWxDctfI3hf4VgIMG5Jot7v74WXdCDKFI6+0lTILV8bmD7SS4KGCbBFIF3JWuHrVCcmx/XDVcbgJSwUqlsMEyJMcl/c6rJUoSSdzj7HPpHKaqxTJzzXWMu3RtQPvzUmljMNxnaQLvJpvoAgfuZJrZQva5y2b6gBnOguhC0oZ8btpweM0sTTM7088ybITT0M1uJLGqZQ02oQN1CzT82L2HUsCsbdxfJm8a4Lw6srksh3hItxFr+imIvLCwelEghcWrS+JwSUyvuMrZuaiN+YeBkWrspM/wpreeLlcpCGUIm32Tf9nr1iCxw==";
		
		try{
			
		    String pp[] = {"D:\\Users\\rdalce.CASTRES\\Documents\\isis_castres\\4A\\QoS\\tp\\java_eclpse\\32\\workspace\\tp_secu_FIE4\\src\\pk.txt"};

		    //Importer la cl� publique
		    byte[] kb = Files.readAllBytes(Paths.get("", pp));
		    X509EncodedKeySpec speci =  new X509EncodedKeySpec(kb);
		    KeyFactory kf = KeyFactory.getInstance("RSA");
		    PublicKey pk =  kf.generatePublic(speci);
		    
		    
		    // cr�er un autre cipher et l'initialiser avec la cl� publique et mode d�chiffrement
		    Cipher decryptCipher2 = Cipher.getInstance(spec);
		    decryptCipher2.init(Cipher.DECRYPT_MODE, pk);
		    
		    // d�chiffrer encoded
		    byte[] tobytes2 = decryptCipher2.doFinal(java.util.Base64.getDecoder().decode(encoded2));
		    System.out.println("next line is original text:");
		    System.out.println(new String(tobytes2));
		    


		    		
		}catch( Exception e){
			e.printStackTrace();
			
		}
	}

}
