package weixin.login;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginTester {

	 public static void main(String []args) {
		 Login  login  = new Login();
		 String jsCode = "081At5xb0MZRLx130Fxb0riuxb0At5xe";
		 String appid = "wxfd3299108203da1e";
		 String secret = "382e8b9adde28bda1fe54a07c49def45";
		 
		 try {
			 JSONObject resJSON = login.getSessionKey(jsCode, appid, secret);
			 System.out.println(resJSON.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 }

}
