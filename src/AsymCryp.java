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


public class AsymCryp {
	
	
public static void main(String[] args) {
		String spec="RSA";
		String valueEnc = "comme dans un tipi���";
		
		try{
			//G�n�rer paire de cl�s RSA
			KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
		    generator.initialize(2048, new SecureRandom());
		    KeyPair pair = generator.generateKeyPair();
		    
		    // Cr�er le cipher et l'initialiser avec la cl� publique
		    Cipher encryptCipher = Cipher.getInstance(spec);
		    encryptCipher.init(Cipher.ENCRYPT_MODE, pair.getPublic());
		    System.out.println("original text: "+valueEnc);
		    
		    //chiffrer valueEnc avec la cl� publique
		    byte[] cipherText = encryptCipher.doFinal(valueEnc.getBytes());
		    
		    //encoded: cryptogramme
		    System.out.println(java.util.Base64.getEncoder().encodeToString(cipherText));
		    String encoded = java.util.Base64.getEncoder().encodeToString(cipherText);
		    
		    // cr�er un autre cipher et l'initialiser avec la cl� priv�e et mode d�chiffrement
		    Cipher decryptCipher = Cipher.getInstance(spec);
		    decryptCipher.init(Cipher.DECRYPT_MODE, pair.getPrivate());
		    
		    // d�chiffrer encoded
		    byte[] tobytes = decryptCipher.doFinal(java.util.Base64.getDecoder().decode(encoded));
		    System.out.println("next line is original text:");
		    System.out.println(new String(tobytes));
		    
		    /////////////////////////////////////////////////////////////////////
		    
		    valueEnc = "yodeling";
		    // Cr�er le cipher et l'initialiser avec la cl� publique
		    Cipher encryptCipher2 = Cipher.getInstance(spec);
		    encryptCipher2.init(Cipher.ENCRYPT_MODE, pair.getPrivate());
		    System.out.println("original text: "+valueEnc);
		    
		    //chiffrer valueEnc avec la cl� publique
		    byte[] cipherText2 = encryptCipher2.doFinal(valueEnc.getBytes());
		    
		    //encoded: cryptogramme
		    System.out.println(java.util.Base64.getEncoder().encodeToString(cipherText2));
		    String encoded2 = java.util.Base64.getEncoder().encodeToString(cipherText2);
		    
		    String encoded3 =  new String(encoded2);
		    
		    
		    String pp[] = {"D:\\Users\\rdalce.CASTRES\\Documents\\isis_castres\\4A\\QoS\\tp\\java_eclpse\\32\\workspace\\tp_secu_FIE4\\src\\pk.txt"};
		    X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(pair.getPublic().getEncoded());
    		FileOutputStream fos = new FileOutputStream(pp[0]);
    		fos.write(x509EncodedKeySpec.getEncoded());
    		fos.close();
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
