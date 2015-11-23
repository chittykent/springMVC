package com.app.simple.mina.server.udp;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.service.IoConnector;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.transport.socket.nio.NioDatagramConnector;

public class UDPClinetTest extends IoHandlerAdapter {
private final static Logger log = Logger.getLogger(UDPClinetTest.class);

	
    
	@Override
	public void sessionCreated(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("sessionCreated....");
		super.sessionCreated(session);
	}



	@Override
	public void sessionOpened(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("sessionOpened....");
		super.sessionOpened(session);
	}



	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("sessionClosed....");
		super.sessionClosed(session);
	}



	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		// TODO Auto-generated method stub
		super.sessionIdle(session, status);
	}



	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		session.close(true);
	}



	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		IoBuffer buffer = (IoBuffer) message;
		String msg = buffer.getString(3,
		Charset.forName("UTF-8").newDecoder());
//		String msg = message.toString();
		log.info("The message received is [" + msg + "]");
	}



	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("messageSent...");
		super.messageSent(session, message);
	}

	

	public static void main(String[] args) {
		try {
			IoConnector connector = new NioDatagramConnector();
			connector.setHandler(new UDPClinetTest());
//			connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(
//					new TextLineCodecFactory(Charset.forName("UTF-8"),
//					LineDelimiter.WINDOWS.getValue(),
//					LineDelimiter.WINDOWS.getValue()), ""));
			ConnectFuture connFuture = connector.connect(new InetSocketAddress("localhost", 3088));
			connFuture.awaitUninterruptibly();
			IoSession session = connFuture.getSession();
			IoBuffer buffer = IoBuffer.allocate(3);
			buffer.putString("OK!",
			Charset.forName("UTF-8").newEncoder());
			buffer.flip();
			WriteFuture future = session.write(buffer);
			//WriteFuture future = session.write(" send test");
			future.awaitUninterruptibly();
			connector.dispose();
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
