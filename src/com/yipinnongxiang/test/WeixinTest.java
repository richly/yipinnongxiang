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
        	   System.out.println("Ʊ��:"+token.getToken());
        	   System.out.println("��Чʱ��:"+token.getExpireIn());
        	   
        	   //String path="C:/Users/iRich/Desktop/bulldog.png";
        	   //String mediaId = WeixinUtil.upload(path, token.getToken(), "thumb");
        	   //System.out.println("mediaId:"+mediaId);
        	   String menu = JSONObject.fromObject(WeixinUtil.initMenu()).toString();
        	   int result = WeixinUtil.createMenu(token.getToken(),menu);
        	   if(result == 0){
        		   System.out.println("�����˵��ɹ�");
        	   }else{
        		   System.out.println("�����룺"+result);
        	   }
    	   }catch(Exception e){
    		   e.printStackTrace();
    	   }
    	   
       }
}
