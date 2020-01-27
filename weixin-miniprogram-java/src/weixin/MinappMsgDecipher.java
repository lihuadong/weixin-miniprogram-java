/**
 * 
 * 
 * MinappDecipher.java
 * Ver0.0.1
 * 2017年12月18日-下午7:41:36
 * 2017全智道(北京)科技有限公司-版权所有
 * 
 */
package weixin;

/**
 * 
 * MinappDecipher
 * 
 * 李华栋
 * 李华栋
 * 2017年12月18日 下午7:41:36
 * 
 * @version 0.0.1
 * 
 */


import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;


import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * MinappDecipher
 * 
 * 李华栋
 * 李华栋
 * 2017年5月8日 上午8:53:59
 * 
 * @version 0.0.1
 * 
 */
public class MinappMsgDecipher {

    public static boolean initialized = false;  
    private static Logger logger = Logger.getLogger(MinappMsgDecipher.class);   
	
    /**
	 * AES解密
	 * @param content 密文
	 * @return
	 * @throws InvalidAlgorithmParameterException 
	 * @throws NoSuchProviderException 
	 */
	public static byte[] decrypt(byte[] content, byte[] keyByte, byte[] ivByte) throws InvalidAlgorithmParameterException {
		
		initialize();
		try {
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
			Key sKeySpec = new SecretKeySpec(keyByte, "AES");
			
			cipher.init(Cipher.DECRYPT_MODE, sKeySpec, generateIV(ivByte));// 初始化 
			byte[] result = cipher.doFinal(content);
			return result;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();  
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();  
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}  
	
	public static void initialize(){  
        if(initialized){
        		return;  
        }else{
            Security.addProvider(new BouncyCastleProvider());  
            initialized = true;  
        }
    }
	
	//生成iv  
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");  
        params.init(new IvParameterSpec(iv));  
        return params;  
    }  

    public  String getDecryptInfo(String encryptedData,String iv,String sessionKey){
    	
    	String info = null; 
		try {
			
	        byte[] resultByte = decrypt(Base64.getDecoder().decode(encryptedData), Base64.getDecoder().decode(sessionKey), Base64.getDecoder().decode(iv));
	        if(null != resultByte && resultByte.length > 0){
	            info = new String(resultByte, "UTF-8");
	        }
	    } catch (InvalidAlgorithmParameterException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
		return info;
    }
    
	public JSONObject getUserInfoJSON(String encryptedData, String iv, String sessionKey) {

		logger.info("\n========================================================");
		logger.info("MinappMsgDecipher-getUserInfoJSON-encryptedData:" + encryptedData);
		logger.info("MinappMsgDecipher-getUserInfoJSON-iv:" + iv);
		logger.info("MinappMsgDecipher-getUserInfoJSON-sessionKey:" + sessionKey);

		String userInfo = getDecryptInfo(encryptedData, iv, sessionKey);
		logger.info("MinappMsgDecipher-getUserInfoJSON-decry userInfo:" + userInfo);

		JSONObject json4UserInfo =null;
		try {
			json4UserInfo = new JSONObject(userInfo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		logger.info("MinappMsgDecipher-getUserInfoJSON-decry userInfo:" + json4UserInfo);
		logger.info("========================================================\n");

		return json4UserInfo;
	}
    
    
    public  JSONObject getPhoneNumberJSON(String encryptedData,String iv,String sessionKey){
    	
       logger.info("\n========================================================");
       logger.info("MinappMsgDecipher-getPhoneNumberJSON-encryptedData:"+encryptedData);
       logger.info("MinappMsgDecipher-getPhoneNumberJSON-iv:"+iv);
       logger.info("MinappMsgDecipher-getPhoneNumberJSON-sessionKey:"+sessionKey);
       
 	   String userPhoneInfo = getDecryptInfo(encryptedData,iv,sessionKey); 

 	   logger.info("MinappMsgDecipher-getPhoneNumberJSON-decry userPhoneInfo:" + userPhoneInfo);
 	  logger.info("========================================================\n");
 	  
 	   JSONObject json4UserInfo = null;
	try {
		json4UserInfo = new JSONObject(userPhoneInfo);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
 	   
	   return json4UserInfo;
}
	


}
