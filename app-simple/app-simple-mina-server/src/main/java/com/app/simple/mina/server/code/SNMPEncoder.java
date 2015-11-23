package com.app.simple.mina.server.code;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;


/**
 * 编码器
 * @author kent
 *	
 */
public class SNMPEncoder extends ProtocolEncoderAdapter  {

	private Charset charset;
	
	public SNMPEncoder() {
		this(Charset.forName("utf-8")); 
	}

	public SNMPEncoder(Charset charset) {
		this.charset = charset;
	}

	@Override
	public void encode(IoSession session, Object message,
			ProtocolEncoderOutput out) throws Exception {
		System.out.println("encode....");
		byte[] msgBytes=message.toString().getBytes();
		IoBuffer buf = IoBuffer.allocate(msgBytes.length);  
	    buf.put(msgBytes);
	    buf.flip();  
	    out.write(buf);  
	}

	public Charset getCharset() {
		return charset;
	}
}

