package weixin;

import org.json.JSONObject;

public class MinappMsgDecipherTester {

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
