package org.monday.weixin.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.liufeng.course.util.MessageUtil;
import org.monday.weixin.message.TextMessage;

//处理请求的业务逻辑类
public class CoreService {
         
	//处理请求的方法
	public static String processRequest(HttpServletRequest request){
		//以下为微信消息的处理
		String respXML=null;//定义一个返回给微信的内容
		//定义返回的消息
		TextMessage tm=new TextMessage();
		//1.解析微信传过来的XML内容
		try {
			HashMap<String,String> requestMap=MessageUtil.parseXML(request);
			//获取解析后需要的参数
			//用户的openID
			String fromUserName=requestMap.get("FromUserName");
			//公众号的原始ID;
			String toUserName=requestMap.get("ToUserName");
			//消息的类型
			String msgType=requestMap.get("MsgType");
			
			//定义一个给用户回复的文本消息
			//回复消息的时候fromUserName和toUserName要倒一倒
			//TextMessage tm=new TextMessage();
			tm.setFromUserName(toUserName);//给谁回复消息 发给用户
			tm.setToUserName(fromUserName);//谁发的 公众号
			tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);//发送文本消息
			tm.setCreateTime(new Date().getTime());
			
			//2.对消息类型判断，因为不同的消息类型回复的是不同内容
			//文本消息
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				tm.setContent("您发送的是文本消息 ");
			}
			//图片消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
				tm.setContent("您发送的是图片消息 ");
			}
			//链接消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
				tm.setContent("您发送的是链接消息 ");
			}
			//地理位置消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
				tm.setContent("您发送的是地理消息 ");
			}
			//视频消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
				tm.setContent("您发送的是视频消息 ");
			}
			//语音消息
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				tm.setContent("您发送的是语音消息 ");
			}
			//事件
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				//获取事件类型
				String eventType=requestMap.get("Event");
				//判断事件类型
				//关注事件
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					tm.setContent("您发送的是关注事件");
				}
				//取消关注
				else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					//tm.setContent("您发送的是取消事件 ");
				}
				//如果是菜单点击事件
				else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					//这里和weixin-26创建菜单有关
					//取得key内容
					String EventKey=requestMap.get("EventKey");
					
					//天气预报菜单
					if(EventKey.equals("KEY_WEATHER")){
						tm.setContent("这个是天气预报菜单");
					}
					//每日歌曲
					else if(EventKey.equals("KEY_MUSIC")){
						tm.setContent("这个是每日歌曲菜单");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//返回处理后的消息
		return MessageUtil.messageToXML(tm);
	}
	
}
