package com.yipinnongxiang.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.yipinnongxiang.menu.Button;
import com.yipinnongxiang.menu.ClickButton;
import com.yipinnongxiang.menu.Menu;
import com.yipinnongxiang.menu.ViewButton;
import com.yipinnongxiang.po.Image;
import com.yipinnongxiang.po.ImageMessage;
import com.yipinnongxiang.po.Music;
import com.yipinnongxiang.po.MusicMessage;
import com.yipinnongxiang.po.News;
import com.yipinnongxiang.po.NewsMessage;
import com.yipinnongxiang.po.TextMessage;
import com.thoughtworks.xstream.XStream;

import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

public class MessageUtil {

	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_MUSIC = "music";
	public static final String MESSAGE_NEWS = "news";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "click";
	public static final String MESSAGE_VIEW = "view";
	public static final String MESSAGE_SCANCODE = "scancode_push";
	

	public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {

		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
		InputStream ins = request.getInputStream();
		Document doc = reader.read(ins);
		Element root = doc.getRootElement();
		List<Element> list = root.elements();
		for (Element e : list) {
			map.put(e.getName(), e.getText());
		}
		ins.close();
		return map;
	}

	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		return xstream.toXML(textMessage);
	}

	/**
	 * 主菜单
	 */
	public static String menuText() {
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎你加入一品农香，我们是一流的农产品特卖商城。安全的食品质量、快速高效的配送、高品质的产品，是我们的追求，我们立志成为中国最大的农产品特卖平台\n");
		sb.append("点击查看：\t");
		sb.append("<a href=\"http://yipinnongxiang.ngrok.cc/yipinnongxiang/firstMenu.jsp\">农香代理入门</a>\n\n");
		sb.append("点击查看：\t");
		sb.append("<a href=\"http://yipinnongxiang.ngrok.cc/yipinnongxiang/secondMenu.jsp\">农香学习资料</a>\n\n");
		sb.append("点击查看：\t");
		sb.append("<a href=\"http://yipinnongxiang.ngrok.cc/yipinnongxiang/thirdMenu.jsp\">农香天天抢购</a>\n\n");
		sb.append("点击查看：\t");
		sb.append("<a href=\"http://yipinnongxiang.ngrok.cc/yipinnongxiang/fourthMenu.jsp\">进入农香商城</a>\n\n");
		return sb.toString();
	}

	public static String initText(String toUserName,String fromUserName,String content) {
		TextMessage text = new TextMessage();
		text.setFromUserName(toUserName);
		text.setToUserName(fromUserName);
		text.setMsgType(MessageUtil.MESSAGE_TEXT);
		text.setCreateTime(new Date().getTime());
		text.setContent(content);
		return textMessageToXml(text);
	}

	public static String firstMenu() {
		StringBuffer sb = new StringBuffer();
		sb.append("功能暂未编写");
		return sb.toString();
	}
	/**
	 * 回复图文消息 
	 */
	public static String newsMessageToXml(NewsMessage newsMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", newsMessage.getClass());
		xstream.alias("item", new News().getClass());
		return xstream.toXML(newsMessage);
	}
	
	public static String imageMessageToXml(ImageMessage imageMessage){
		XStream xstream = new XStream();
		xstream.alias("xml", imageMessage.getClass());
		xstream.alias("Image", new Image().getClass());
		return xstream.toXML(imageMessage);
	}
	
	public static String musicMessageToXml(MusicMessage musicMessage){
		XStream xstream = new XStream();
		xstream.alias("xml",musicMessage.getClass());
		xstream.alias("Music", new Music().getClass());
		return xstream.toXML(musicMessage);
	}
	
    public static String initNewsMessage(String toUserName,String fromUserName){
    	 String message = null;
    	 List<News> newsList = new ArrayList<News>();
    	 NewsMessage newsMessage = new NewsMessage();
    	 
    	 News news = new News();
    	 news.setTitle("宝宝与法国斗牛犬的幸福生活");
    	 news.setDescription("最萌的宝宝和法国斗牛犬，没有之一~");
    	 news.setPicUrl("http://douniudashi.ngrok.cc/WechatDemo/images/baby&bulldog.jpg");
    	 news.setUrl("http://www.u148.net/article/76292.html");
    	 
    	 newsList.add(news);
    	 
    	 newsMessage.setToUserName(fromUserName);
    	 newsMessage.setFromUserName(toUserName);
    	 newsMessage.setMsgType(MessageUtil.MESSAGE_NEWS);
    	 newsMessage.setArticleCount(newsList.size());
    	 newsMessage.setArticles(newsList);
    	 message = newsMessageToXml(newsMessage);
    	 return message;
    }
    /**
     *组装图片消息 
     * 
     **/
    public static String initImageMessage(String toUserName,String fromUserName){
    	String message = null;
    	Image image = new Image();
    	image.setMediaId("_MAN_J5Ye7H0QGoy6nenLJXRntgqiJpJu5CyvLZ2CSoCuvlMQIe8YuW0PdkuCILG");
    	ImageMessage imageMessage = new ImageMessage();
    	imageMessage.setFromUserName(toUserName);
    	imageMessage.setToUserName(fromUserName);
    	imageMessage.setMsgType(MESSAGE_IMAGE);
    	imageMessage.setCreateTime(new Date().getTime());
    	imageMessage.setImage(image);
    	message = imageMessageToXml(imageMessage);
    	return message;
    }
    /**
     *组装音乐消息 
     * 
     **/
    public static String initMusicMessage(String toUserName,String fromUserName){
    	String message = null;
    	Music music = new Music();
    	music.setThumbMediaId("N32V-Dk0g__6t7OKfcGOFd2raqwI5hCLCbu8efdyM_B0ptU_JrguF5Nt5hz2xs0t");
    	music.setTitle("Merry Christmas,MR.Lawrence");
    	music.setDescription("坂本龙一-圣诞快乐,劳伦斯先生");
    	music.setMusicUrl("http://douniudashi.ngrok.cc/WechatDemo/resources/Merry Christmas Mr.Lawrence.mp3");
    	music.setHQMusicUrl("http://douniudashi.ngrok.cc/WechatDemo/resources/Merry Christmas Mr.Lawrence.mp3");
    	
    	MusicMessage musicMessage = new MusicMessage();
    	musicMessage.setFromUserName(toUserName);
    	musicMessage.setToUserName(fromUserName);
    	musicMessage.setMsgType(MESSAGE_MUSIC);
    	musicMessage.setCreateTime(new Date().getTime());
    	musicMessage.setMusic(music);
    	message = musicMessageToXml(musicMessage);
    	return message;
    }

    
}
