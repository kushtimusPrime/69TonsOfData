// Nathan Hellstedt, Kush Hari, Charlie Little, Gordon MacDonald

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import bc.Direction;
import bc.GameController;
import bc.MapLocation;
import bc.OrbitPattern;
import bc.Planet;
import bc.Unit;
import bc.VecUnit;
import bc.bc;


public class Player {

	 public static void main(String[] args) {

	        GameController gc = new GameController();
	
	        Direction[] directions = Direction.values();
	
	        while (true) {
	           System.out.println("Current round: "+gc.round());
	           gc.nextTurn();
	        }
	     
	    
	 }
	 //This method will determine which round the rocket should launch in order to reach Mars the earliest 
	 public static long getFirstLaunchRound(GameController gc) {
		 //currentPattern is the orbit pattern of the map
		 OrbitPattern currentPattern=gc.orbitPattern();
		 //marsArrivals is an arrayList that records when a rocket would arrive on Mars based on which round it launched
		 //marsArrivals keeps track of rounds 125-750 launches
		 ArrayList<Long> marsArrivals=new ArrayList<Long>();
		 //Puts the arrival round number into the ArrayList
		 for(int a=125;a<=750;a++) {
			 long duration=currentPattern.duration(a);
			 long arrival=duration+a;
			 marsArrivals.add(arrival);
		 }
		 //min is the lowest Mars arrival time
		 long min=marsArrivals.get(0);
		 //leaveTime is a round number that determines when a rocket should leave Earth in order to get to Mars the quickest
		 int leaveTime=125;
		 //The loop determines the min value and the leaveTime value
		 for(int c=1;c<marsArrivals.size();c++) {
			 long potentialLaunch=marsArrivals.get(c);
			 if(potentialLaunch<min) {
				 min=potentialLaunch;
				 leaveTime=c+125;
			 }
		 }
		 return leaveTime;
	 }
}
