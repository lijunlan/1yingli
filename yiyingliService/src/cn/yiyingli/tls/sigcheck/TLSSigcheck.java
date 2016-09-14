package cn.yiyingli.tls.sigcheck;

import java.io.PrintWriter;

import com.tls.tls_sigature.tls_sigature;
import com.tls.tls_sigature.tls_sigature.CheckTLSSignatureResult;
import com.tls.tls_sigature.tls_sigature.GenTLSSignatureResult;

public class TLSSigcheck {

	//private static final String PRIVATE_KEY = "-----BEGIN EC PRIVATE KEY-----MHQCAQEEIAfxeQJ4neieLPrF3TKm0QEqYXcK/eOOcn0dG8rV+AoRoAcGBSuBBAAKoUQDQgAE/NKGsUBiADdPlA2Qk2FefQlkbnnP+PaRDxWOcFULBO6SNyJW4ahjmVVy6PHFcKqe2DSocJDZ+QracDrpJECPVw==-----END EC PRIVATE KEY-----";

	public static void main(String[] args) {
		try{			
			//Use pemfile keys to test
		    String privStr = "-----BEGIN PRIVATE KEY-----\n" +
			"MIGEAgEAMBAGByqGSM49AgEGBSuBBAAKBG0wawIBAQQgiBPYMVTjspLfqoq46oZd\n" +
			"j9A0C8p7aK3Fi6/4zLugCkehRANCAATU49QhsAEVfIVJUmB6SpUC6BPaku1g/dzn\n" +
			"0Nl7iIY7W7g2FoANWnoF51eEUb6lcZ3gzfgg8VFGTpJriwHQWf5T\n" +
			"-----END PRIVATE KEY-----";
		    
			//change public pem string to public string
			String pubStr = "-----BEGIN PUBLIC KEY-----\n"+
			"MFYwEAYHKoZIzj0CAQYFK4EEAAoDQgAE1OPUIbABFXyFSVJgekqVAugT2pLtYP3c\n"+
			"59DZe4iGO1u4NhaADVp6BedXhFG+pXGd4M34IPFRRk6Sa4sB0Fn+Uw==\n"+
			"-----END PUBLIC KEY-----";
			
			
			
			String fileName = "sig14"; 
	    	PrintWriter write = new PrintWriter(fileName, "UTF-8");
		    for (int i = 0 ;i < 1000; ++i){
		    	GenTLSSignatureResult result = tls_sigature.GenTLSSignature(10000, new String("1400000955"), 1400000955, "216c0fcbf8e3053f5c4072d0640ffc98", 373,privStr);
		    	//check signature.
		    	CheckTLSSignatureResult checkResult = tls_sigature.CheckTLSSignature(result.urlSig,
		    			new String("1400000955"), 1400000955, "216c0fcbf8e3053f5c4072d0640ffc98", 373, pubStr);
		    	if(checkResult.verifyResult == false)
		    	{
		    		System.out.println("i wrong: " + i + "\n" + result.urlSig);
		    		break;
		    	}
		    	
		    	write.println(result.urlSig);
		    	
		    	if(i ==999 )
		    	{
		    		System.out.println("All Right");
		    	}
		    }
    		write.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public static void Sign(){
		
	}

}
