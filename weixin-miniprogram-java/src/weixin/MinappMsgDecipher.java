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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
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
			
	        byte[] resultByte = decrypt(Base64.decodeBase64(encryptedData), Base64.decodeBase64(sessionKey), Base64.decodeBase64(iv));
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

		JSONObject json4UserInfo = new JSONObject(userInfo);

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
 	  
 	   JSONObject  json4UserInfo = new JSONObject(userPhoneInfo);	
 	   
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


		//{"openId":"oDWH50ET2p9WQvV7XWu6qJk1D3Lc","nickName":"悟空来 |  Arthur李华栋","gender":1,"language":"zh_CN","city":"Haidian","province":"Beijing","country":"CN","avatarUrl":"http://wx.qlogo.cn/mmopen/vi_32/DYAIOgq83er0kFrHHQHHHF6V4OGkACFnBibYVqT2wmjvUZO4gXHYWmsfe8abKZUby7iaurmptS4zU7arYn5B5iajw/0","unionId":"oEoEjwSkoftwsyyicEAJAU4vKMiE","watermark":{"timestamp":1494078442,"appid":"wx86dfbc2c131a5b8f"}}
		MinappMsgDecipher   decipher  = new MinappMsgDecipher();

				
		String encryptedData = "m9U1ysYaxguFDi8bZcYkRRYGVOElwIsr2p9ZZdG+Rk8xwCbCaIe0mYL7ysgco6dDupGnKpJb0EjOcowJ2CtulOGXo15sorWa1GDIcqaGBXffGnvu1/YmovqS+F/BM6KFw96fVKHIkHaWE3tgzKRF/KtZa/sBAPZKZoApUKsf4cbaSicoQr7JUft/LUvXvh35cjTcvipGWE8+BjmafbV3pUmXkui9otXtP6Ww6RCyucvUXp9Ky4b8Bnq9PXjGVVQLCqZ5/0td9Q6ucht6CPgpne5x+OjtwCfFFP/E30/pYhxW3ksskt90NxTxZxB0lJRcbyyxPipiQOERqHp2oVzGCpGygAI90O9mPBaG3XDFBtDJ9hBVWzN1FEx6K4nTKnyKMRBfgnZ/cf7+o81+U5PQG1aTXv/y+ltx+60vlF5BruxqBV8w2LmpUmQTYBGNw3NHYpa4hw0TEXOko9ntN74ezAFGJvQdKLPrw24FaDac4pc5FasCuF7T0prcs1YrFxTqIfm+XH09tKl/wuo3XIhtge84RS/tDRnrVY+HXrJQzVrrs/ExWEjW9rO2d+MeYgCi";
		String iv ="1OhK5p9pDsfc6G5A+qOvSw==";
		String sessionKey ="QCF6QaAuQYY4KvxpQWihQA==";
		
		String str   = decipher.getDecryptInfo(encryptedData,iv,sessionKey);
		System.out.println("res1:"+str);
		
		JSONObject resJSON   = decipher.getUserInfoJSON(encryptedData,iv,sessionKey);
		System.out.println("res2:"+resJSON.toString());
		
	}

}
