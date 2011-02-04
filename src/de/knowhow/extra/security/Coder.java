package de.knowhow.extra.security;

public class Coder {

	private Decoder decoder;
	private Encoder encoder;
	private static Coder instance;

	private Coder() {
		// set defaul encoder/decoder
		decoder = new SwitchedHexDecoder();
		encoder = new SwitchedHexEncoder();
	}

	public void setDecoder(Decoder dec) {
		this.decoder = dec;
	}

	public void setEncoder(Encoder enc) {
		this.encoder = enc;
	}

	public String encode(String secret) {
		return encoder.encode(secret);
	}

	public String decode(String clear) {
		return decoder.decode(clear);
	}

	public synchronized static Coder getInstance() {
		if (instance == null) {
			instance = new Coder();
		}
		return instance;
	}

}
