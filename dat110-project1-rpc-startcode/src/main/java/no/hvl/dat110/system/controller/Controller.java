package no.hvl.dat110.system.controller;

import no.hvl.dat110.TODO;
import no.hvl.dat110.rpc.RPCClient;
import no.hvl.dat110.rpc.RPCClientStopStub;

public class Controller  {
	
	private static int N = 5;
	
	public static void main (String[] args) {
		
		DisplayStub display;
		SensorStub sensor;
		
		RPCClient displayclient,sensorclient;
		
		System.out.println("Controller starting ...");
				
		// create RPC clients for the system
		displayclient = new RPCClient(Common.DISPLAYHOST,Common.DISPLAYPORT);
		sensorclient = new RPCClient(Common.SENSORHOST,Common.SENSORPORT);
		
		// setup stop methods in the RPC middleware
		RPCClientStopStub stopdisplay = new RPCClientStopStub(displayclient);
		RPCClientStopStub stopsensor = new RPCClientStopStub(sensorclient);
				
		// TODO - START
		
		// Opprett lokale stubber for display og sensor
		display = new DisplayStub(displayclient);
		sensor = new SensorStub(sensorclient);

		// Koble til sensor- og displayserverne
		displayclient.connect();
		sensorclient.connect();

		// Hent verdi fra sensoren N ganger og vis på displayet
		for (int i = 0; i < N; i++) {
			int temp = sensor.read(); // Les temperatur fra sensor
			display.write(Integer.toString(temp)); // Skriv temperatur til display

			try {
				Thread.sleep(1000); // Vent 1 sekund før neste måling
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// TODO - END

		
		stopdisplay.stop();
		stopsensor.stop();
	
		displayclient.disconnect();
		sensorclient.disconnect();
		
		System.out.println("Controller stopping ...");
		
	}
}
