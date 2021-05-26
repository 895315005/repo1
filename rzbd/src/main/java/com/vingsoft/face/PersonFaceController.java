package com.vingsoft.face;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


 

@Controller
@RequestMapping("mas/personFace")
public class PersonFaceController {
    private static final Log logger = LogFactory.getLog(PersonFaceController.class);
    
    @Autowired
    private  FaceComparisonController   faceComparison;
   
    //调用人脸识别页面
    @RequestMapping("/faceshibie")
    public String faceshibie(){
    	
        return "mas/face-sb";
    } 
     //调用人脸比对
    @ResponseBody
    @RequestMapping("/facebd")
    @CrossOrigin
    @ResponseStatus(HttpStatus.OK)
    public Boolean fwpj(@RequestBody Map mapp){
    	
    	String msgIdcard = mapp.get("idCardPhoto1").toString();//身份证照片 base64
    	String idNumber1 = mapp.get("idNumber1").toString(); //身份证号码
    	String peopleFace1 = mapp.get("peopleFace1").toString();//人脸抓拍照片base64
    	String idinfo1 = mapp.get("idinfo1").toString(); //读取的身份证信息
	    // 百度控制台创建一个项目可生成AppID，API Key，Secret Key
	  	  String app_id = "20517948";
	  	  String api_key = "TwzhZuTHMha844bsXwc6BWM8";
	  	  String secret_key = "dxb70oG2slUa3KvoXMHLKt2U95PK6BR7";
  	 
	  	  //接口需要的参数
	  	  Map paramsMap=new HashMap();
		 paramsMap.put("app_id", app_id);
		 paramsMap.put("api_key",api_key);
		 paramsMap.put("secret_key",secret_key);
		 paramsMap.put("image1",msgIdcard);
		 logger.info("人脸抓拍照片base64"+paramsMap);
		Boolean issuccess=false;
		String doPost = faceComparison.faceBd(msgIdcard, peopleFace1, app_id, api_key, secret_key);
		JSONObject parseObject = JSONObject.parseObject(doPost);
		logger.info(parseObject);
				
		try{
				Object obj = parseObject.get("result");
				String json = JSON.toJSONString(obj);
				JSONObject resutt = JSONObject.parseObject(json);
				if(resutt != null) {
					Object object = resutt.get("score");
					System.out.println("比对分数"+object.toString());
					float parseFloat = Float.parseFloat(object.toString());
					if(parseFloat>=60) {
						issuccess=true;
			    	    String uuid=	UUID.randomUUID().toString().replace("-", "");

					}
				}
				
			 
	     }catch (Exception e){
	        e.printStackTrace();
	    }

    	
    	return issuccess;
    } 
  
}
