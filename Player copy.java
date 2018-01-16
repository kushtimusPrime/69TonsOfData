// Nathan Hellstedt, Kysh Hari, Charlie Little

import bc.Direction;
import bc.GameController;
import bc.Location;
import bc.MapLocation;
import bc.Planet;
import bc.Unit;
import bc.UnitType;
import bc.VecUnit;
import bc.bc;
import java.util.ArrayList;
import java.util.TreeMap;
import bc.Planet;


public class Player {

	 public static void main(String[] args) {

		 
	        GameController gc = new GameController();
	
	        Direction[] directions = Direction.values();
	        
	        TreeMap<Integer, Location> unitLocations = new TreeMap<Integer, Location>();
	        ArrayList<Integer> ID = new ArrayList<Integer>();
	        TreeMap<Integer, Integer> activeFactoryBuilds = new TreeMap<Integer, Integer>();
	
	        
	        while (true) {
	           System.out.println("Current round: "+gc.round());
	           System.out.println("Karbonite " + gc.karbonite());
	      
	           for (int i = 0; i < gc.myUnits().size(); i++)
	        	   {
	        		unitLocations.put(gc.myUnits().get(i).id() ,gc.myUnits().get(i).location());
	        		ID.add(gc.myUnits().get(i).id());
	        	   }
	           
	           if (gc.round() == 1 && gc.planet() == Planet.Earth) {
	        	    blueprintFactory(0, ID, gc);
	        	    long size = gc.myUnits().size() - 1;
	        	    activeFactoryBuilds.put(gc.myUnits().get(0).id(), gc.myUnits().get(size).id());
	        	     }
	           
	           gc.nextTurn();
	        }
	 
	 }           
	      public static void blueprintFactory (int i, ArrayList<Integer> ID, GameController gc)
	      {
	    	  if (gc.canBlueprint(ID.get(i), UnitType.Factory, Direction.North) == true)
      		   	gc.blueprint(ID.get(i), UnitType.Factory, Direction.North);
      	   else if (gc.canBlueprint(ID.get(i), UnitType.Factory, Direction.Northeast) == true)
      		   	gc.blueprint(ID.get(i), UnitType.Factory, Direction.Northeast);
      	   else if (gc.canBlueprint(ID.get(i), UnitType.Factory, Direction.East) == true)
      		   	gc.blueprint(ID.get(i), UnitType.Factory, Direction.East);
      	   else if (gc.canBlueprint(ID.get(i), UnitType.Factory, Direction.Northwest) == true)
      		   	gc.blueprint(ID.get(i), UnitType.Factory, Direction.Northwest);
      	   else if (gc.canBlueprint(ID.get(i), UnitType.Factory, Direction.Southeast) == true)
      		   	gc.blueprint(ID.get(i), UnitType.Factory, Direction.Southeast);
      	   else if (gc.canBlueprint(ID.get(i), UnitType.Factory, Direction.Southwest) == true)
      		   	gc.blueprint(ID.get(i), UnitType.Factory, Direction.Southwest);
      	   else if (gc.canBlueprint(ID.get(i), UnitType.Factory, Direction.South) == true)
      		   	gc.blueprint(ID.get(i), UnitType.Factory, Direction.South);
			   else if (gc.canBlueprint(ID.get(i), UnitType.Factory, Direction.West) == true)
				   	gc.blueprint(ID.get(i), UnitType.Factory, Direction.West);
	      }
	      
	      public static void buildFactory(int worker, int factory, GameController gc) {
	    	  
	    	  gc.build(worker, factory);
	      }
	      
	      public static int getFactoryID(ArrayList<Integer> ID, GameController gc)
	      {
	    	  for (int i = 0; i < gc.myUnits().size(); i++) {
	    		  if (gc.myUnits().get(i).unitType() == UnitType.Factory)
	    			  return i;
	    	  }
	    	  return 0;
	      }
	           
}

