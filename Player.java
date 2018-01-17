// Nathan Hellstedt, Kush Hari, Charlie Little, Gordon MacDonald

import bc.Direction;
import bc.GameController;
import bc.GameMap;
import bc.Location;
import bc.MapLocation;
import bc.OrbitPattern;
import bc.Planet;
import bc.PlanetMap;
import bc.Unit;
import bc.UnitType;
import bc.VecUnit;
import bc.bc;

import java.awt.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Set;
import bc.Planet;


public class Player {

     
	 public static void main(String[] args) throws IllegalArgumentException {

		 
	        GameController gc = new GameController();
	        
	        // These will help keep track of robots and the such
	        ArrayList<Unit> units = new ArrayList<Unit>();
	        ArrayList<Unit> factories=new ArrayList<Unit>();
		ArrayList<Unit> knights=new ArrayList<Unit>();
	        gc.queueResearch(UnitType.Worker);
	    	  

	  		for (int y=0;y<3;y++)
	  		{
	  		gc.queueResearch(UnitType.Rocket);
	  	
	  	}
	  	for (int x=0;x<3;x++)
	  	{
	  	    gc.queueResearch(UnitType.Knight);
        }
	  	for (int x=0;x<3;x++)
	  	{
	  		gc.queueResearch(UnitType.Healer);
	  	}
	  	for (int x=0;x<3;x++)
	  	{
	  		gc.queueResearch(UnitType.Ranger);
	  	}
	       
	        
	        while (true) {
	           System.out.println("Current round: "+gc.round());
	           System.out.println("Karbonite " + gc.karbonite());
	           
	      
	           // Every round the units and location will be updated
	           for (int i = 0; i < gc.myUnits().size(); i++)
	        	   {
	        	   if (gc.myUnits().get(i).unitType() == UnitType.Factory) {
	        		   factories.add(gc.myUnits().get(i));
	        	   }
			   	        	   if (gc.myUnits().get(i).unitType() == UnitType.Knight) {
	        		   knights.add(gc.myUnits().get(i));
	        	   }
	        	   		units.add(gc.myUnits().get(i));
	        	   		
	        	   // Later, we will need to distinguish between robots on earth and mars
	        		
	        	   }
	           
	           
	           // Only occurs in the first round
	           if (gc.round() == 1 && gc.planet() == Planet.Earth) {
	        	   for (int i = 0; i < gc.myUnits().size(); i++)
	        	   {
	        	    blueprintFactory(i, gc);
	        	   }
	            }
	           
	        	checkToBuild(gc);
	        	checkForKarbonite(gc);
	        	checkToReplicate(gc);
	           
	           
	           
	         
	          
	           
	           gc.nextTurn();
	        }
  
	 }
	 
	 // This method will check and see if a specified worker can set a factory blueprint around him/her. If yes, it will lay the blueprint
	 
	      public static boolean blueprintFactory (int i, GameController gc)
	      {  
	    	  
	    	  if (gc.canBlueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.North) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.North);
      		   	return true;
	    	  }
      	   else if (gc.canBlueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.Northeast) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.Northeast);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.East) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.East);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.Northwest) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.Northwest);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.Southeast) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.Southeast);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.Southwest) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.Southwest);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.South) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.South);
      		   	return true;
      	   }
			   else if (gc.canBlueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.West) == true && gc.karbonite() >= 100) {
				   	gc.blueprint(gc.myUnits().get(i).id(), UnitType.Factory, Direction.West);
				   return true;
			   }
	    	  
	    	  return false;
	      }
	      
	      // Research Queue
	     
	      
	      // This method will work with the activeFactoryBuilds tree map to allow workers to build on a blueprint
	      public static void buildFactory(Unit a, Unit b, GameController gc) {
	    	  
	    	  gc.build(a.id(), b.id());
	      }
	      
	      
	      // Might not need this....update coming
	      public static int getFactoryID(ArrayList<Integer> ID, GameController gc)
	      {
	    	  for (int i = 0; i < gc.myUnits().size(); i++) {
	    		  if (gc.myUnits().get(i).unitType() == UnitType.Factory)
	    			  return i;
	    	  }
	    	  return 0;
	      }
	      
	      // This method will loop through all units to see if any workers can build factories
	      public static void checkToBuild(GameController gc) {
	    	  
	    	  
	    	  for (int i = 0; i < gc.myUnits().size(); i++) {
	    		  Unit current = gc.myUnits().get(i);
	    		  
	    		  Location location = current.location();
	    		  
	    		  if (location.isOnMap() == true) {
	    			  VecUnit nearby = gc.senseNearbyUnits(location.mapLocation(), 2);
	    			  for (int c = 0; c < nearby.size(); c++)
	    				  if (current.unitType() == UnitType.Worker && gc.canBuild(current.id(), nearby.get(c).id())) { 
	    					  gc.build(current.id(), nearby.get(c).id());
	    					  System.out.println("Building");
	    				  }
	    			  
	    		  }
	    	  }
	      }
	           
	      public static void checkForKarbonite(GameController gc) {
	    	  for (int i = 0; i < gc.myUnits().size(); i++) {
	    		  Unit current = gc.myUnits().get(i);
	    		  
	    		  if (current.unitType() == UnitType.Worker && current.workerHasActed() == 0) {
	    			  
	    			  if (gc.canHarvest(current.id(), Direction.North))
	    				  gc.harvest(current.id(), Direction.North);
	    			  else if (gc.canHarvest(current.id(), Direction.Northeast))
	    				  gc.harvest(current.id(), Direction.Northeast);
	    			  else if (gc.canHarvest(current.id(), Direction.Northwest))
	    				  gc.harvest(current.id(), Direction.Northwest);
	    			  else if (gc.canHarvest(current.id(), Direction.East))
	    				  gc.harvest(current.id(), Direction.East);
	    			  else if (gc.canHarvest(current.id(), Direction.Southeast))
	    				  gc.harvest(current.id(), Direction.Southeast);
	    			  else if (gc.canHarvest(current.id(), Direction.Southwest))
	    				  gc.harvest(current.id(), Direction.Southwest);
	    			  else if (gc.canHarvest(current.id(), Direction.West))
	    				  gc.harvest(current.id(), Direction.West);
	    			  else if (gc.canHarvest(current.id(), Direction.South))
	    				  gc.harvest(current.id(), Direction.South);
	    			  else {
	    				  if (gc.canMove(current.id(), Direction.North) && current.movementHeat() < 10)
		    				  gc.moveRobot(current.id(), Direction.North);
		    			  else if (gc.canMove(current.id(), Direction.Northeast) && current.movementHeat() < 10)
		    				  gc.moveRobot(current.id(), Direction.Northeast);
		    			  else if (gc.canMove(current.id(), Direction.Northwest) && current.movementHeat() < 10)
		    				  gc.moveRobot(current.id(), Direction.Northwest);
		    			  else if (gc.canMove(current.id(), Direction.East) && current.movementHeat() < 10)
		    				  gc.moveRobot(current.id(), Direction.East);
		    			  else if (gc.canMove(current.id(), Direction.Southeast) && current.movementHeat() < 10)
		    				  gc.moveRobot(current.id(), Direction.Southeast);
		    			  else if (gc.canMove(current.id(), Direction.Southwest) && current.movementHeat() < 10)
		    				  gc.moveRobot(current.id(), Direction.Southwest);
		    			  else if (gc.canMove(current.id(), Direction.West) && current.movementHeat() < 10)
		    				  gc.moveRobot(current.id(), Direction.West);
		    			  else if (gc.canMove(current.id(), Direction.South) && current.movementHeat() < 10)
		    				  gc.moveRobot(current.id(), Direction.South);
	    			  }
	    		  }
	    	  }
	      }
	      
	      public static void checkToReplicate(GameController gc) {
	    	  for (int i = 0; i < gc.myUnits().size(); i++) {
	    		  if (gc.myUnits().get(i).unitType() == UnitType.Worker) {
	    		  if (gc.canReplicate(gc.myUnits().get(i).id(), Direction.North) && gc.karbonite() >= 50 && gc.myUnits().get(i).abilityHeat() < 10) {
	    			  gc.replicate(gc.myUnits().get(i).id(), Direction.North);
	    		  }
	    		  else if (gc.canReplicate(gc.myUnits().get(i).id(), Direction.Northeast) && gc.karbonite() >= 50 && gc.myUnits().get(i).abilityHeat() < 10) {
	    			  gc.replicate(gc.myUnits().get(i).id(), Direction.Northeast);
	    		  }
	    		  else if (gc.canReplicate(gc.myUnits().get(i).id(), Direction.Northwest) && gc.karbonite() >= 50 && gc.myUnits().get(i).abilityHeat() < 10) {
	    			  gc.replicate(gc.myUnits().get(i).id(), Direction.Northwest);
	    		  }
	    		else  if (gc.canReplicate(gc.myUnits().get(i).id(), Direction.East) && gc.karbonite() >= 50 && gc.myUnits().get(i).abilityHeat() < 10) {
		    			  gc.replicate(gc.myUnits().get(i).id(), Direction.East);
		    		  
	    		  }
	    		else  if (gc.canReplicate(gc.myUnits().get(i).id(), Direction.West) && gc.karbonite() >= 50 && gc.myUnits().get(i).abilityHeat() < 10) {
	    			  gc.replicate(gc.myUnits().get(i).id(), Direction.West);
	    		  
  		  }
	    		else  if (gc.canReplicate(gc.myUnits().get(i).id(), Direction.South) && gc.karbonite() >= 50 && gc.myUnits().get(i).abilityHeat() < 10) {
	    			  gc.replicate(gc.myUnits().get(i).id(), Direction.South);
	    		  
  		  }
	    		else  if (gc.canReplicate(gc.myUnits().get(i).id(), Direction.Southeast) && gc.karbonite() >= 50 && gc.myUnits().get(i).abilityHeat() < 10) {
	    			  gc.replicate(gc.myUnits().get(i).id(), Direction.Southeast);
	    		  
  		  }
	    		else  if (gc.canReplicate(gc.myUnits().get(i).id(), Direction.Southwest) && gc.karbonite() >= 50 && gc.myUnits().get(i).abilityHeat() < 10) {
	    			  gc.replicate(gc.myUnits().get(i).id(), Direction.Southwest);
	    		}	  
  		  }
	    	  }
	      }
	      //Have to implement a better version of this later, but this is a pathfinding method
	      public static int findPath(GameController gc, MapLocation destination, Unit unit, int[] rotationTries, ArrayList<Direction> directions) {
	 		 Direction moveHere=unit.location().mapLocation().directionTo(destination);
	 		 for(int a=0;a<rotationTries.length;a++) {
	 			 int optimalIndex=directions.indexOf(moveHere);
	 			 int actualIndex= (optimalIndex+a)%8;
	 			 Direction actualDirection=directions.get(actualIndex);
	 			 if(gc.canMove(unit.id(), actualDirection)) {
	 				 gc.moveRobot(unit.id(), actualDirection);
	 				 return 1;
	 			 }
	 		 }
	 		 return 0;
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
		 public static void buildKnights(GameController gc,ArrayList<Unit> factories) {
		 for(int a=0;a<factories.size();a++) {
			 if(gc.canProduceRobot(factories.get(a).id(),UnitType.Knight)) {
				 gc.produceRobot(factories.get(a).id(), UnitType.Knight);
			 }
		 }
	 }
		 public static Unit searchForAttack(GameController gc, ArrayList<Unit> knights) {
		 Team myTeam=knights.get(0).team();
		 Team enemy;
		 if(myTeam.equals(Team.Blue)) {
			 enemy=Team.Red;
		 } else {
			 enemy=Team.Blue;
		 }
		 for(int a=0;a<knights.size();a++) {
			 VecUnit opponents=gc.senseNearbyUnitsByTeam(knights.get(a).location().mapLocation(), knights.get(a).visionRange(), enemy);
			 if(opponents.size()>1) {
				 Unit opponent=opponents.get(0);
				 return opponent;
			 }
		 }
		 return null;
	 }
	      
	      }

