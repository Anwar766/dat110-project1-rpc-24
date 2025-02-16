package no.hvl.dat110.rpc;

import java.nio.ByteBuffer;
import java.util.Arrays;

public class RPCUtils {
	
	public static byte[] encapsulate(byte rpcid, byte[] payload) {
		// Opprett en ny byte-array med plass til rpcid + payload
		byte[] rpcmsg = new byte[payload.length + 1];

		// Første byte = rpcid
		rpcmsg[0] = rpcid;

		// Kopier payload inn i rpcmsg, start på indeks 1
		System.arraycopy(payload, 0, rpcmsg, 1, payload.length);

		return rpcmsg;
	}
	
	public static byte[] decapsulate(byte[] rpcmsg) {
		// Fjern første byte (rpcid) og returner resten som payload
		return Arrays.copyOfRange(rpcmsg, 1, rpcmsg.length);
	}

	// Konverterer String til byte-array
	public static byte[] marshallString(String str) {
		return str.getBytes();
	}

	// Konverterer byte-array til String
	public static String unmarshallString(byte[] data) {
		return new String(data);
	}

	// Konverterer void (ingen returverdi) til en tom byte-array
	public static byte[] marshallVoid() {
		return new byte[0];
	}

	// Ingen dekoding nødvendig for void (ingen returverdi)
	public static void unmarshallVoid(byte[] data) {
	}

	// Konverterer int til byte-array (4 bytes)
	public static byte[] marshallInteger(int x) {
		return ByteBuffer.allocate(4).putInt(x).array();
	}

	// Konverterer byte-array til int
	public static int unmarshallInteger(byte[] data) {
		return ByteBuffer.wrap(data).getInt();
	}

	// 🔹 Konverterer boolean til en byte-array (1 byte)
	public static byte[] marshallBoolean(boolean b) {
		byte[] encoded = new byte[1];
		encoded[0] = (byte) (b ? 1 : 0);
		return encoded;
	}

	// 🔹 Konverterer byte-array til boolean
	public static boolean unmarshallBoolean(byte[] data) {
		return data[0] == 1;
	}
}
