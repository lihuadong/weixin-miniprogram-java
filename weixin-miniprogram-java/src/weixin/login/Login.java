/**
 * 包打听全知道-微信H5版本
 * xiaochengxu.util
 * Login.java
 * Ver0.0.1
 * Sep 5, 2019-9:28:27 AM
 *  2019全智道(北京)科技有限公司-版权所有
 * 
 */
package weixin.login;

import org.json.JSONException;
import org.json.JSONObject;

import weixin.util.HTTPSDataManager;


/**
 * 
 * Login
 * 
 * 李华栋
 * Sep 5, 2019 9:28:27 AM
 * 
 * @version 0.0.1
 * 
 */
public class Login {
	
	 //单独的小程序使用(不使用)
	 public JSONObject getSessionKey(String js_code,String appid,String secret) throws JSONException{
		 			 
		 String  url ="https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+secret+"&js_code="+js_code+"&grant_type=authorization_code";
		 
		 String resStr   =  HTTPSDataManager.sendData(url, "");
		 JSONObject  resJSON   = new JSONObject(resStr);
		 
		 //MemcachManager  mc    = MemcachManager.getMemcacheManager();		 
		 //mc.put(appid+"session_key", resJSON.get("session_key"));
		 
		 return resJSON;
	 }
	 


}
