package org.monday.weixin.service;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.liufeng.course.util.MessageUtil;
import org.monday.weixin.message.TextMessage;

//���������ҵ���߼���
public class CoreService {
         
	//��������ķ���
	public static String processRequest(HttpServletRequest request){
		//����Ϊ΢����Ϣ�Ĵ���
		String respXML=null;//����һ�����ظ�΢�ŵ�����
		//���巵�ص���Ϣ
		TextMessage tm=new TextMessage();
		//1.����΢�Ŵ�������XML����
		try {
			HashMap<String,String> requestMap=MessageUtil.parseXML(request);
			//��ȡ��������Ҫ�Ĳ���
			//�û���openID
			String fromUserName=requestMap.get("FromUserName");
			//���ںŵ�ԭʼID;
			String toUserName=requestMap.get("ToUserName");
			//��Ϣ������
			String msgType=requestMap.get("MsgType");
			
			//����һ�����û��ظ����ı���Ϣ
			//�ظ���Ϣ��ʱ��fromUserName��toUserNameҪ��һ��
			//TextMessage tm=new TextMessage();
			tm.setFromUserName(toUserName);//��˭�ظ���Ϣ �����û�
			tm.setToUserName(fromUserName);//˭���� ���ں�
			tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);//�����ı���Ϣ
			tm.setCreateTime(new Date().getTime());
			
			//2.����Ϣ�����жϣ���Ϊ��ͬ����Ϣ���ͻظ����ǲ�ͬ����
			//�ı���Ϣ
			if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)){
				tm.setContent("�����͵����ı���Ϣ ");
			}
			//ͼƬ��Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)){
				tm.setContent("�����͵���ͼƬ��Ϣ ");
			}
			//������Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)){
				tm.setContent("�����͵���������Ϣ ");
			}
			//����λ����Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)){
				tm.setContent("�����͵��ǵ�����Ϣ ");
			}
			//��Ƶ��Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)){
				tm.setContent("�����͵�����Ƶ��Ϣ ");
			}
			//������Ϣ
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)){
				tm.setContent("�����͵���������Ϣ ");
			}
			//�¼�
			else if(msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)){
				//��ȡ�¼�����
				String eventType=requestMap.get("Event");
				//�ж��¼�����
				//��ע�¼�
				if(eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)){
					tm.setContent("�����͵��ǹ�ע�¼�");
				}
				//ȡ����ע
				else if(eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)){
					//tm.setContent("�����͵���ȡ���¼� ");
				}
				//����ǲ˵�����¼�
				else if(eventType.equals(MessageUtil.EVENT_TYPE_CLICK)){
					//�����weixin-26�����˵��й�
					//ȡ��key����
					String EventKey=requestMap.get("EventKey");
					
					//����Ԥ���˵�
					if(EventKey.equals("KEY_WEATHER")){
						tm.setContent("���������Ԥ���˵�");
					}
					//ÿ�ո���
					else if(EventKey.equals("KEY_MUSIC")){
						tm.setContent("�����ÿ�ո����˵�");
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//���ش�������Ϣ
		return MessageUtil.messageToXML(tm);
	}
	
}
