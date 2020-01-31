/**
 * 包打听全知道-微信H5版本
 * xiaochengxu.user
 * MsgDecipher.java
 * Ver0.0.1
 * 2017年12月21日-下午5:27:38
 *  2017全智道(北京)科技有限公司-版权所有
 * 
 */
package weixin;

import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 
 * MsgDecipher
 * 
 * 李华栋
 * 李华栋
 * 2017年12月21日 下午5:27:38
 * 
 * @version 0.0.1
 * 
 */
public class MsgDecipher {

    public static boolean initialized = false;  
	
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
        if (initialized) return;  
        Security.addProvider(new BouncyCastleProvider());  
        initialized = true;  
    }
	//生成iv  
    public static AlgorithmParameters generateIV(byte[] iv) throws Exception{  
        AlgorithmParameters params = AlgorithmParameters.getInstance("AES");  
        params.init(new IvParameterSpec(iv));  
        return params;  
    }  

    public static String getUserInfo(String encryptedData,String iv,String sessionKey){
    	
    	    String userInfo = null; 
		try {
			
	        byte[] resultByte = decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
	        if(null != resultByte && resultByte.length > 0){
	            userInfo = new String(resultByte, "UTF-8");
	        }
	    } catch (InvalidAlgorithmParameterException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
		return userInfo;
    }
    
    public static JSONObject getUserInfoJSON(String encryptedData,String iv,String sessionKey){
    	
    	   String userInfo = getUserInfo(encryptedData,iv,sessionKey); 
    	   JSONObject json4UserInfo = null;
		try {
			json4UserInfo = new JSONObject(userInfo);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	   return json4UserInfo;
}
    
    public static JSONObject getPhoneNumberJSON(String encryptedData,String iv,String sessionKey){
    	
 	   String userInfo = getUserInfo(encryptedData,iv,sessionKey); 
 	   JSONObject json4UserInfo = null;
	try {
		json4UserInfo = new JSONObject(userInfo);
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	   return json4UserInfo;
   }
	/**
	 * main(这里用一句话描述这个方法的作用)
	 * (这里描述这个方法适用条件 – 可选)
	 * @param args 
	 *void
	 * @exception 
	 * @since  0.0.1
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
