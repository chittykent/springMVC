package com.app.simple.mina.server.code;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

public class SNMPCodecFactory implements ProtocolCodecFactory{
	
	private SNMPEncoder snmpEncoder;
	private SNMPDecoder snmpDecoder;

	
	
	public SNMPCodecFactory() {
		this(Charset.defaultCharset());
	}
	
	

	public SNMPCodecFactory(Charset charset) {
		this.snmpEncoder = new SNMPEncoder(charset);
		this.snmpDecoder = new SNMPDecoder(charset);
	}



	@Override
	public ProtocolEncoder getEncoder(IoSession session) throws Exception {
		return snmpEncoder;
	}

	@Override
	public ProtocolDecoder getDecoder(IoSession session) throws Exception {
		return snmpDecoder;
	}



	public SNMPEncoder getSnmpEncoder() {
		return snmpEncoder;
	}



	public void setSnmpEncoder(SNMPEncoder snmpEncoder) {
		this.snmpEncoder = snmpEncoder;
	}



	public SNMPDecoder getSnmpDecoder() {
		return snmpDecoder;
	}



	public void setSnmpDecoder(SNMPDecoder snmpDecoder) {
		this.snmpDecoder = snmpDecoder;
	}


	

}
