package com.study.jsp.controller;
import java.io.IOException;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

/**
 * Sessions가 각 호출 시 마다 생성되므로 static으로 정해 줘야 한다.
 * 브라우져가 websocket을 지원 해야 한다.
 * 웹 소켓 연결 후 별도 close 동작 없이 브라우져를 닫을 경우 자동으로 OnClose가 호출된다
 */
@ServerEndpoint("/websocketendpoint")
public class WsServer2{
	private static final java.util.Set<Session> sessions =
			java.util.Collections.synchronizedSet(new java.util.HashSet<Session>());
	
	@OnOpen
	public void onOpen(Session session) {
		System.out.println("Open session id : " + session.getId());
		
		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText("msg/연결되었습니다. 재밌게 노세요!");
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}		
		
		sessions.add(session);
	}
	
	@OnClose
	public void onClose(Session session) {
		System.out.println("Session " + session.getId() + " has ended");			
		sessions.remove(session);
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("Message from " + session.getId() + " : " + message);
		
		try {
			final Basic basic = session.getBasicRemote();
			basic.sendText("msg/" + message);
			sendAllUserNickname(session);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		
		sendAllSessiontoMessage(session,message);
	}
	
	// 모든 사용자에게 메시지를 전달
	public void sendAllSessiontoMessage(Session self, String message) {		
		try {
			for(Session session : WsServer2.sessions) {
				if(!(self.getId().equals(session.getId()))) {
					session.getBasicRemote().sendText("msg/" + message);
				}
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
	
	// 모든 사용자에게 닉네임 정보를 전달 <- 구현중
		public void sendAllUserNickname(Session self) {	
			System.out.println("실행됨");
			try {
				for(Session session : WsServer2.sessions) {
					if(!(self.getId().equals(session.getId()))) {
						session.getBasicRemote().sendText("user/" + session.getId());
					}
				}
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	
	@OnError
	public void onError(Throwable e, Session session) {
		
	}
}
