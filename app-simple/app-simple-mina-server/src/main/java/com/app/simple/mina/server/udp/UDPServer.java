package com.app.simple.mina.server.udp;

import java.nio.charset.Charset;
import java.util.ResourceBundle;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;


/**
 * Udp端口监听服务
 * @author kent
 * @date 2014年8月11日 下午3:57:07
 *
 */
public class UDPServer extends IoHandlerAdapter{
	
	private Logger log = Logger.getLogger(UDPServer.class);
	@Autowired
	private MemcachedClient memcachedClient;
	/**
	 * 接收消息
	 * @author: kent
	 * @date 2014年8月14日 下午2:23:59
	 */
	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		System.out.println("udp in....bussiness handler");
		System.out.println("message:"+message.toString());
		//CardModel cardModel = (CardModel) message;
        
        IoBuffer buffer = IoBuffer.allocate(10).setAutoExpand(true);
		buffer.putString("OK",Charset.forName("UTF-8").newEncoder());
		buffer.flip();
        session.write(buffer);
        session.close(true);
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		log.info("UDP Server session closed......");
	}
    
}