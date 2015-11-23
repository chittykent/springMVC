package com.app.simple.mina.server.tcp;

import java.nio.charset.Charset;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.SocketSessionConfig;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.simple.model.CardModel;
import com.app.simple.service.LoginService;


public class TCPServer extends IoHandlerAdapter {

	private Logger log = Logger.getLogger(TCPServer.class);
	@Autowired
	private MemcachedClient memcachedClient;
	//private ResourceBundle resourceBundle = ResourceBundle.getBundle("message");
	@Autowired
	private LoginService loginService;
	

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.out.println("session created..");
		try {
			SocketSessionConfig cfg = (SocketSessionConfig) session.getConfig();
			cfg.setReceiveBufferSize(1 * 1024);
			cfg.setReadBufferSize(1 * 1024);
			cfg.setKeepAlive(true);
			// cfg.setSoLinger(0); //这个是根本解决问题的设置
		} catch (Exception e) {
			log.error("TCP Server Session 打开异常：" + e.getMessage());
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		session.getConfig().setIdleTime(IdleStatus.BOTH_IDLE, 20);

	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		super.sessionIdle(session, status);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		super.exceptionCaught(session, cause);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		super.messageSent(session, message);
	}

	/**
	 * @Title: messageReceived
	 * @Description: 接收消息
	 * @param session
	 * @param message
	 * @throws Exception
	 * @return
	 * @author: kent
	 * @date 2014年8月14日 下午2:23:59
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		
		CardModel cardModel = (CardModel) message;
		//test
//		testMemCached();
//		testService();
		IoBuffer buffer = IoBuffer.allocate(10).setAutoExpand(true);
		buffer.putString("OK", Charset.forName("UTF-8").newEncoder());
		buffer.flip();
		session.write(buffer);
	}
//	private void testMemCached()throws Exception{
//		memcachedClient.set("test", 0, 11);
//		System.out.println("test:"+memcachedClient.get("test"));
//	}
//	private void testService()throws Exception{
//		System.out.println("login correct:"+loginService.login("admin", "admin"));
//		System.out.println("login error:"+loginService.login("admin", "111"));
//	}
	
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("TCP Server session closed......");
		System.out.println("closed in tcp");
	}


}
