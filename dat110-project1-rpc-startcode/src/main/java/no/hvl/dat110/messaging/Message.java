package no.hvl.dat110.messaging;

public class Message {

	// Meldingen kan inneholde opptil 127 byte med data (payload)
	private byte[] data;

	// Konstruktør for å lage en melding med gitt data
	public Message(byte[] data) {
		
		// Sjekk at data ikke er null og ikke lengre enn 127 byte
		if (data == null || data.length > 127) {
			throw new IllegalArgumentException("Data må være mellom 1 og 127 byte.");
		}

		// Lagre data
		this.data = data;
	}

	// Hent data fra meldingen
	public byte[] getData() {
		return this.data; 
	}
}
