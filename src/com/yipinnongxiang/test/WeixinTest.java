package com.yipinnongxiang.test;

import java.io.IOException;
import java.text.ParseException;

import com.yipinnongxiang.po.AccessToken;
import com.yipinnongxiang.util.WeixinUtil;

import net.sf.json.JSONObject;

public class WeixinTest {
       public static void main(String[] args) throws IOException, ParseException{
    	   
    	   try{
    		   AccessToken token = WeixinUtil.getAccessToken();
        	   System.out.println("票据:"+token.getToken());
        	   System.out.println("有效时间:"+token.getExpireIn());
        	   
        	   //String path="C:/Users/iRich/Desktop/bulldog.png";
        	   //String mediaId = WeixinUtil.upload(path, token.getToken(), "thumb");
        	   //System.out.println("mediaId:"+mediaId);
        	   String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
        	   int result = WeixinUtil.createMenu(token.getToken(),menu);
        	   if(result == 0){
        		   System.out.println("创建菜单成功");
        	   }else{
        		   System.out.println("错误码："+result);
        	   }
    	   }catch(Exception e){
    		   e.printStackTrace();
    	   }
    	   
       }
}
