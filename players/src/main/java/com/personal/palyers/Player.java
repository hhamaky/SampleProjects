package com.personal.palyers;

import java.util.HashMap;
import java.util.Map;

public class Player implements Runnable {
 
	String name;
	
	Map<Player, Integer> players =new HashMap<>();
	
	public Player(String name) {
		this.name = name;
	}


	public synchronized int getCounter(Player p) {
		return players.get(p);
	}

	public void receiveMsg(String msg, String type, Player p) {
		synchronized (this) {
			Integer i = players.get(p);
			if(i !=null){
				players.put(p,i++);
			}else{
				players.put(p, 1);
			}
			
		}
		if(!type.equals("ack")){
			System.out.println("Received message " + msg);
			sendAck(msg, p);
		}
	}
	
	

	public void sendAck(String msg, Player p) {
//		if( getCounter() <10){
			System.out.println("Sending Ack " + msg + " " + getCounter(p));
			p.receiveMsg(msg + getCounter(p), "ack",this);
//		}
	}

	@Override
	public void run() {
		
	}

}
