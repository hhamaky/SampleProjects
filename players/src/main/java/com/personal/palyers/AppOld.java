package com.personal.palyers;

import java.sql.Blob;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Hello world!
 *
 */
public class AppOld {
	public static void main(String[] args) {
		final BlockingQueue<String> bg;
		bg = new ArrayBlockingQueue<String>(1);
		final ExecutorService executor = Executors.newFixedThreadPool(2);
		int j = 0;
		Runnable initator = () -> {
			String msgReceived;
			String msgSent = "Hi";
			int counter = 0;
			do {
				try {
					if (bg.size() > 0) {
						msgReceived = bg.take();
						System.out.println("Initator Received " + msgReceived);
						counter = Integer.parseInt(String.valueOf(msgReceived.charAt(msgReceived.length() - 1)));

						msgSent = msgReceived + '_' + ++counter;

						// if(counter == 10){
						// executor.shutdown();
						// }
					}

					bg.put(msgSent);
					// bg.put(String.valueOf(counter));
					System.out.println("Initator Sent Hi");
				} catch (InterruptedException ie) {
				}
			} while (counter != 10);
			executor.shutdown();
		};
		executor.execute(initator);

		Runnable player = () -> {
			String msgReceived;
			String msgSent = "Hi";
			int counter = 0;
			do {
			try {
				msgReceived = bg.take();
				System.out.println("Player Received " + msgReceived);
				if (!msgReceived.equals(msgSent)) {
					counter = Integer.parseInt(String.valueOf(msgReceived.charAt(msgReceived.length() - 1)));
				}
				msgSent = msgReceived + "_" + ++counter;
				bg.put(msgSent);
				System.out.println("Player Sent " + msgSent);
			} catch (InterruptedException ie) {
			}
			} while (counter != 10);
		};

		executor.execute(player);
	}
}
