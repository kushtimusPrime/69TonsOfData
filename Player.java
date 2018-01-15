// Nathan Hellstedt, Kysh Hari, Charlie Little

import bc.Direction;
import bc.GameController;
import bc.MapLocation;
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
}

