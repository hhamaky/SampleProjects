package com.personal.palyers;

public class App {
	public static void main(String[] args) {
		Player p1 = new Player("hassan");
		Player p2 = new Player("Yaaasaaameeen");
		p1.run();
		p2.run();
		System.out.println("P1 Sending Message ");
		p2.receiveMsg("Hi","msg", p1);
	}
}
