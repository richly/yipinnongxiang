package com.yipinnongxiang.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.yipinnongxiang.menu.Button;
import com.yipinnongxiang.menu.ClickButton;
import com.yipinnongxiang.menu.Menu;
import com.yipinnongxiang.menu.ViewButton;
import com.yipinnongxiang.po.AccessToken;

import net.sf.json.JSONObject;

public class WeixinUtil {
	private static final String APPID = "wxe59cec2e59779848";
	private static final String APPSECRET="5adce79792017c36e1316dc614c8b701";
	private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";
	private static final String UPLOAD_URL="https://api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=TYPE";
	private static final String CREATE_MENU_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

	
	public static String upload(String filePath,String accessToken,String type) throws IOException{
		File file = new File(filePath);
		if(!file.exists()||!file.isFile()){
			throw new IOException("�ļ�������");
		}
		String url = UPLOAD_URL.replace("ACCESS_TOKEN", accessToken).replace("TYPE", type);
		System.out.println("url:"+url);//
		URL urlObj = new URL(url);
		//����
		HttpURLConnection con =  (HttpURLConnection) urlObj.openConnection();
		con.setRequestMethod("POST");
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		System.out.println("con:"+con);//
		//��������ͷ��Ϣ
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		
		//���ñ߽�
		String BOUNDARY = "-----"+System.currentTimeMillis();
		con.setRequestProperty("Content-Type","multipart/form-data;boudary=" +BOUNDARY);
		
		StringBuilder sb = new StringBuilder();
		sb.append("--");
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition:form-data;name=\"file\";filename=\""+file.getName()+"\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		
		byte[] head = sb.toString().getBytes("utf-8");
		//��������
		OutputStream out = new DataOutputStream(con.getOutputStream());
		//�����ͷ
		out.write(head);
		
		//�ļ����Ĳ���
		//���ļ������ļ��ķ�ʽ���뵽url��
		DataInputStream in = new DataInputStream(new FileInputStream(file));
		int bytes = 0;
		byte[]  bufferOut = new byte[1024];
		while((bytes = in.read(bufferOut))!= -1){
			out.write(bufferOut,0,bytes);
		}
		in.close();
		//��β����
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");//����������ݷָ���
		out.write(foot);
		out.flush();
		out.close();
		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		String result = null;
		try{
			//����BufferedReader����������ȡURL����Ӧ
			reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			System.out.println("con.inputstream:"+con.getInputStream());//
			String line = null;
			while((line=reader.readLine())!=null){
				buffer.append(line);
			}
			System.out.println("buffer:"+buffer);//
			if(result == null ){
				result = buffer.toString();
			}
			System.out.println("result:"+result);//
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(reader!=null){
				reader.close();
			}
		}
		
		JSONObject jsonObj = JSONObject.fromObject(result);
		System.out.println("jsonObj:"+jsonObj);
		String typeName ="media_id";
		if(!"image".equals(type)){
			typeName = type + "_media_id";
		}
		String mediaId = jsonObj.getString(typeName);
		return mediaId;
	}
	
	public static JSONObject doGetStr(String url) throws IOException{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(url);
		JSONObject jsonObject = null;
		try{
			HttpResponse response = httpClient.execute(httpGet);
		    HttpEntity entity = response.getEntity();
		    if(entity != null){
		    	String result = EntityUtils.toString(entity,"UTF-8");
		    	jsonObject = JSONObject.fromObject(result);
		    }
		}catch(ClientProtocolException e){
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static JSONObject doPostStr(String url,String outStr) throws ClientProtocolException, IOException{
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		JSONObject jsonObject = null;
		try{
			httpPost.setEntity(new StringEntity(outStr,"UTF-8"));
			HttpResponse response = httpClient.execute(httpPost);
			String result = EntityUtils.toString(response.getEntity(),"UTF-8");
			jsonObject =  JSONObject.fromObject(result);
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		return jsonObject;
	}
	
	public static AccessToken getAccessToken() throws IOException{
		AccessToken token = new AccessToken();
		String url = ACCESS_TOKEN_URL.replace("APPID",APPID).replace("APPSECRET", APPSECRET);
		JSONObject jsonObject = doGetStr(url);
		if(jsonObject != null){
			token.setToken(jsonObject.getString("access_token"));
			token.setExpireIn(jsonObject.getInt("expires_in"));
		}
		return token;
	}
    /**
     * ��װ�˵���ť
     */
	
	public static Menu initMenu(){
		Menu menu = new Menu();
		
		
		ViewButton btn1 = new ViewButton();
		btn1.setName("�����̳�");
		btn1.setType("view");
	    btn1.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/1.1enterMall.jsp");
	    
	    ViewButton btn21 = new ViewButton();
	    btn21.setName("��������");
	    btn21.setType("view");
	    btn21.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/2.1todayPurchase.jsp");
	    
	    ViewButton btn22 = new ViewButton();
	    btn22.setName("������Ʒ");
		btn22.setType("view");
		btn22.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/2.2freshEveryday.jsp");
	    
	    ViewButton btn23 = new ViewButton();
	    btn23.setName("�����ز�");
	    btn23.setType("view");
	    btn23.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/2.3superniorSpecialty.jsp");
	    
	    ViewButton btn24 = new ViewButton();
	    btn24.setName("ʱ��ˮ��");
	    btn24.setType("view");
	    btn24.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/2.4freshFruit.jsp");
	    
	    
		Button btn2 = new Button();
		btn2.setName("��������");
		btn2.setSub_button(new Button[]{btn21,btn22,btn23,btn24});
		
		ViewButton btn31 = new ViewButton();
		btn31.setName("��������");
		btn31.setType("view");
		btn31.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/3.1personalCenter.jsp");
	    
	    ViewButton btn32 = new ViewButton();
	    btn32.setName("��˾����");
	    btn32.setType("view");
	    btn32.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/3.2companyDescription.jsp");
	    
	    ViewButton btn33 = new ViewButton();
	    btn33.setName("����˵��");
	    btn33.setType("view");
	    btn33.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/3.3merchantStatement.jsp");
	    
	    ViewButton btn34 = new ViewButton();
	    btn34.setName("�ۺ����");
	    btn34.setType("view");
	    btn34.setUrl("http://yipinnongxiang.ngrok.cc/yipinnongxiang/3.4saleSupport.jsp");
	    
		Button btn3 = new Button();
		btn3.setName("��������");
		btn3.setSub_button(new Button[]{btn31,btn32,btn33,btn34});
		
		menu.setButton(new Button[]{btn1,btn2,btn3});
		return menu;
	}
	
	
	
	
//    public static Menu initMenu(){
//    	
//    	Menu menu = new Menu();
//    	//���ø���
// 	  	Button[] btns = new Button[]{};
// 	  	
//    	String[][] btnNames ={{"�����̳�"},{"��������","��������","ÿ����Ʒ","�羳��ױ","ʱ��Ůװ","Ӫ������"},{"��������","��������","��˾ʹ��","����˵��","��˰��FAQ","�ۺ����"}};
//     	
//     	for(int btnIndex = 0; btnIndex < btnNames.length; btnIndex++){
//            System.out.println(btnNames[1].length);
//     	  	btns[btnIndex].setName(btnNames[btnIndex][0]);
//     	  	
//     	  	//�����Ӽ�
//     	  	String[] tempArr = btnNames[btnIndex];
//     	  	ViewButton[] subBtns = new ViewButton[]{};
//     	  	for(int subBtnIndex = 1; subBtnIndex < tempArr.length; subBtnIndex++){
//     	  		subBtns[subBtnIndex].setName(tempArr[subBtnIndex]);
//     	  		subBtns[subBtnIndex].setType("view");
//     	  		subBtns[subBtnIndex].setUrl("http://www.imooc.com/");
//     	  	}
//     	  	
//     	  	btns[btnIndex].setSub_button(subBtns);
//     		
//     	}
//    	return menu;
//    }
    
    
    public static int createMenu(String token,String menu) throws ClientProtocolException, IOException{
     int result = 0;
     String errMsg ="";
     String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
     JSONObject jsonObject = doPostStr(url,menu);
     if(jsonObject != null){
    	 result = jsonObject.getInt("errcode");
    	 errMsg = jsonObject.getString("errmsg");
    	 System.out.println(errMsg);
     }
     return result;
    }
    
//    public static JSONObject queryMenu(String token){
//    	String url = CREATE_MENU_URL.replace("ACCESS_TOKEN", token);
//    	JSONObject jsonObject = doPostStr(url,menu);
//    }
}
