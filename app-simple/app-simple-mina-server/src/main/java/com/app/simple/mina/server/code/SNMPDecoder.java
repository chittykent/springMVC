package com.app.simple.mina.server.code;

import java.nio.charset.Charset;

import org.apache.log4j.Logger;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

/**
 *	解码器
 * @author kent
 *
 */
public class SNMPDecoder extends CumulativeProtocolDecoder {

	private final static Logger log = Logger.getLogger(SNMPDecoder.class);
	private final Charset charset;

	public SNMPDecoder(Charset charset) {
		this.charset = charset;
	}

	public SNMPDecoder() {
		this(Charset.forName("utf-8"));
	}

	/**
	 * @Title: doDecode 所有报文长度都为18位
	 * @Description: 定位16进制数据包解析
	 * @param session
	 * @param in
	 * @param out
	 * @param @throws Exception
	 * @return
	 */
	@Override
	protected boolean doDecode(IoSession session, IoBuffer in,
			ProtocolDecoderOutput out) throws Exception {
		DecodeStrategy decodeStrategy = new DecodeStrategy();
		return decodeStrategy.doDecode(session, in, out);
	}
	
	public Charset getCharset() {
		return charset;
	}
	
	

}
