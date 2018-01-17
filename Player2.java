// Nathan Hellstedt, Kysh Hari, Charlie Little

import bc.Direction;
import bc.GameController;
import bc.GameMap;
import bc.Location;
import bc.MapLocation;
import bc.Planet;
import bc.PlanetMap;
import bc.StartTurnMessage;
import bc.Unit;
import bc.UnitType;
import bc.VecUnit;
import bc.bc;
import java.util.ArrayList;
import java.util.Arrays;
import bc.Planet;


public class Player {
	
	private static int workerCount;

     
	 public static void main(String[] args) {

		 
	        GameController gc = new GameController();	       
	        
	        while (true) {
	           System.out.println("Current round: "+gc.round());
	           System.out.println("Karbonite " + gc.karbonite());
	           
	      
	           // Every round the units and location will be updated
	/*           for (int i = 0; i < gc.myUnits().size(); i++)
	        	   {
	        	   // Later, we will need to distinguish between robots on earth and mars
	        		units.add(gc.myUnits().get(i));
	        	   } */
	           VecUnit current = gc.myUnits();
	           
	           // Only occurs in the first round
	           if (gc.round() == 1 && gc.planet() == Planet.Earth) {
	        	   for (int i = 0; i < current.size(); i++)
	        	   {
	        	    blueprintFactory(i, gc);
	        	    if (current.get(i).unitType() == UnitType.Worker) 
	        	    	workerCount++;
	        	   }
	        	   //Queue research
	        	   gc.queueResearch(UnitType.Worker);
	        	   gc.queueResearch(UnitType.Rocket);
	        	   gc.queueResearch(UnitType.Rocket);
	        	   gc.queueResearch(UnitType.Rocket);
	            }
	           
	           for (int a = 0; a < current.size(); a++) {
	        	if (current.get(a).unitType() == UnitType.Factory && current.get(a).structureIsBuilt() == 0)
	        			checkToBuild(gc, current);
	           }
	        	checkForKarbonite(gc, current);
	        	checkToReplicate(gc, current);
	           
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
	      
	      	      
	      // This method will loop through all units to see if any workers can build factories
	      public static void checkToBuild(GameController gc, VecUnit current) {
	    	  
	    	  
	    	  for (int i = 0; i < current.size(); i++) {
	    		  Unit thisUnit = current.get(i);
	    		  
	    		  Location location = thisUnit.location();
	    		  
	    		  if (location.isOnMap() == true) {
	    			  VecUnit nearby = gc.senseNearbyUnits(location.mapLocation(), 2);
	    			  for (int c = 0; c < nearby.size(); c++)
	    				  if (thisUnit.unitType() == UnitType.Worker && gc.canBuild(thisUnit.id(), nearby.get(c).id())) { 
	    					  gc.build(thisUnit.id(), nearby.get(c).id());
	    					  System.out.println("Building");
	    				  }
	    			  
	    		  }
	    	  }
	      }
	        
	      // Checks for karbonite and mines karbonite if available
	      public static void checkForKarbonite(GameController gc, VecUnit currentVec) {
	    	  for (int i = 0; i < currentVec.size(); i++) {
	    		  Unit current = currentVec.get(i);
	    		  
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
	      
	     // Up to 50 workers, workers will replicate 
	      public static void checkToReplicate(GameController gc, VecUnit current) {
	    	  if (workerCount <= 50) {
	    	  for (int i = 0; i < current.size(); i++) {
	    		  if (current.get(i).unitType() == UnitType.Worker) {
	    		  if (gc.canReplicate(current.get(i).id(), Direction.North) && gc.karbonite() >= 150 && current.get(i).abilityHeat() < 10) {
	    			  gc.replicate(current.get(i).id(), Direction.North);
	    			  workerCount++;
	    		  }
	    		  else if (gc.canReplicate(current.get(i).id(), Direction.Northeast) && gc.karbonite() >= 150 && current.get(i).abilityHeat() < 10) {
	    			  gc.replicate(current.get(i).id(), Direction.Northeast);
	    			  workerCount++;
	    		  }
	    		  else if (gc.canReplicate(current.get(i).id(), Direction.Northwest) && gc.karbonite() >= 150 && current.get(i).abilityHeat() < 10) {
	    			  gc.replicate(current.get(i).id(), Direction.Northwest);
	    			  workerCount++;
	    		  }
	    		else  if (gc.canReplicate(current.get(i).id(), Direction.East) && gc.karbonite() >= 150 && current.get(i).abilityHeat() < 10) {
		    			  gc.replicate(current.get(i).id(), Direction.East);
		    			  workerCount++;
		    		  
	    		  }
	    		else  if (gc.canReplicate(current.get(i).id(), Direction.West) && gc.karbonite() >= 150 && current.get(i).abilityHeat() < 10) {
	    			  gc.replicate(current.get(i).id(), Direction.West);
	    			  workerCount++;
  		  }
	    		else  if (gc.canReplicate(current.get(i).id(), Direction.South) && gc.karbonite() >= 150 && current.get(i).abilityHeat() < 10) {
	    			  gc.replicate(current.get(i).id(), Direction.South);
	    			  workerCount++;
  		  }
	    		else  if (gc.canReplicate(current.get(i).id(), Direction.Southeast) && gc.karbonite() >= 150 && current.get(i).abilityHeat() < 10) {
	    			  gc.replicate(current.get(i).id(), Direction.Southeast);
	    			  workerCount++;
  		  }
	    		else  if (gc.canReplicate(current.get(i).id(), Direction.Southwest) && gc.karbonite() >= 150 && current.get(i).abilityHeat() < 10) {
	    			  gc.replicate(current.get(i).id(), Direction.Southwest);
	    			  workerCount++;
	    		}	  
  		  }
	    	  }
	      }
	      }
	      }


