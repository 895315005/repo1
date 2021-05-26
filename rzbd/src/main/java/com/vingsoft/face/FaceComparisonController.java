package com.vingsoft.face;

import java.util.ArrayList;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.baidu.aip.face.AipFace;
import com.baidu.aip.face.MatchRequest;

@RestController
@RequestMapping("mas/faceComparison")
public class FaceComparisonController {
	   //设置APPID/AK/SK
		// 百度控制台创建一个项目可生成AppID，API Key，Secret Key  https://console.bce.baidu.com/ai/?_=1592724281364&fromai=1#/ai/face/app/list
		
	    /**
	     * 人脸比对
	     * @return
	     * @throws 
	     */
		@ResponseStatus(HttpStatus.OK)
	    @RequestMapping(value = "/faceBd")
		public String faceBd(@RequestParam String image1,@RequestParam String image2,@RequestParam String app_id,@RequestParam String api_key,@RequestParam String secret_key) {
	
			 // 初始化一个AipFace
	        AipFace client = new AipFace(app_id, api_key, secret_key);

	        // 可选：设置网络连接参数
	        client.setConnectionTimeoutInMillis(2000);
	        client.setSocketTimeoutInMillis(60000);

	        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
			/*
			 * client.setHttpProxy("proxy_host", proxy_port); // 设置http代理
			 * client.setSocketProxy("proxy_host", proxy_port); // 设置socket代理
			 */
	        // 调用接口
	        String image = "取决于image_type参数，传入BASE64字符串或URL字符串或FACE_TOKEN字符串";
	        String imageType = "BASE64";
	    
	 	    MatchRequest req1 = new MatchRequest(image1, "BASE64");
		    MatchRequest req2 = new MatchRequest(image2, "BASE64");
		    ArrayList<MatchRequest> requests = new ArrayList<MatchRequest>();
		    requests.add(req1);
		    requests.add(req2);

		    JSONObject res = client.match(requests);
		  
		    String obj=res.toString();
		    return obj;

		}
	    
	
	
	
}
