// Nathan Hellstedt, Kysh Hari, Charlie Little

import bc.Direction;
import bc.GameController;
import bc.GameMap;
import bc.Location;
import bc.MapLocation;
import bc.OrbitPattern;
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
	private static int rocketCount;

     
	 public static void main(String[] args) {

		 
	        GameController gc = new GameController();
	        
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
	  	
	  	ArrayList<Unit> rockets = new ArrayList<Unit>();
	  	
	        
	        while (true) {
	        // Prints out current round and Karbonite number
	           System.out.println("Current round: "+gc.round());
	           System.out.println("Karbonite " + gc.karbonite());
	           
	           VecUnit current = gc.myUnits();
	           
	           // Only occurs in the first round
	           if (gc.round() == 1 && gc.planet() == Planet.Earth) {
	        	   for (int i = 0; i < current.size(); i++)
	        	   {
	        	    blueprintFactory(i, gc, current);
	        	    if (current.get(i).unitType() == UnitType.Worker) 
	        	    	workerCount++;
	        	   }
	            }
	           
	           for (int a = 0; a < current.size(); a++) {
	        	if (current.get(a).unitType() == UnitType.Factory && current.get(a).structureIsBuilt() == 0)
	        			checkToBuildFactory(gc, current);
	           }
	        	checkForKarbonite(gc, current);
	        	checkToReplicate(gc, current);
	        	
	        	if (gc.round() == 125 && gc.planet() == Planet.Earth) {
	        		rocketCount = 0;
	        		System.out.println(blueprintRocket(gc, current));
	        	}
	        	
	        	if (gc.round() == 126) {
	        		
	        		for (int s = 0; s < current.size(); s++) {
	        			if (current.get(s).unitType() == UnitType.Rocket) {
	        				rockets.add(current.get(s));
	        				System.out.println(current.get(s));
	        		}
	        		System.out.println(rockets);
	        		}
	        		}
	        	
	        	if (gc.round() > 125) 
	        	for (int h = 0; h < current.size(); h++) 
	        	if (current.get(h).unitType() == UnitType.Rocket && current.get(h).structureIsBuilt() == 0)
        			checkToBuildRocket(gc, rockets);
	        	else if (current.get(h).unitType() == UnitType.Rocket && current.get(h).structureIsBuilt() != 0)
	        	{
	        		rockets.add(current.get(h));
	        	}
	        	
	        	if (rockets.size() > 0) {
	        		for (int z = 0; z < rockets.size(); z++) {
	        			for (int x = 0; x < current.size(); x++) {
	        				if (gc.canLoad(rockets.get(z).id(), current.get(x).id()) == true) {
	        					gc.load(rockets.get(z).id(), current.get(x).id());
	        				}
	        			}
	        			}
	        		}
	        	
	        	
           if (gc.round() == 250) {

        	   for (int i = 0; i < current.size(); i++) {
        		   if (current.get(i).unitType() == UnitType.Rocket) {
        			   System.out.println(current.get(i));
        	   if (gc.canLaunchRocket(current.get(i).id(), new MapLocation(Planet.Mars, 5, 5))) {
        	         gc.launchRocket(current.get(i).id(), new MapLocation(Planet.Mars, 5, 5));
        	         System.out.println("Rocket is launched");
           }
           }
        	   }
           }
	           gc.nextTurn();
	        }
	        }
  
	 
	 
	 // This method will check and see if a specified worker can set a factory blueprint around him/her. If yes, it will lay the blueprint
	 
	      public static boolean blueprintFactory (int i, GameController gc, VecUnit current)
	      {  
	    	  
	    	  if (gc.canBlueprint(current.get(i).id(), UnitType.Factory, Direction.North) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(current.get(i).id(), UnitType.Factory, Direction.North);
      		   	return true;
	    	  }
      	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Factory, Direction.Northeast) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(current.get(i).id(), UnitType.Factory, Direction.Northeast);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Factory, Direction.East) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(current.get(i).id(), UnitType.Factory, Direction.East);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Factory, Direction.Northwest) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(current.get(i).id(), UnitType.Factory, Direction.Northwest);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Factory, Direction.Southeast) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(current.get(i).id(), UnitType.Factory, Direction.Southeast);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Factory, Direction.Southwest) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(current.get(i).id(), UnitType.Factory, Direction.Southwest);
      		   	return true;
      	   }
      	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Factory, Direction.South) == true && gc.karbonite() >= 100) {
      		   	gc.blueprint(current.get(i).id(), UnitType.Factory, Direction.South);
      		   	return true;
      	   }
			   else if (gc.canBlueprint(current.get(i).id(), UnitType.Factory, Direction.West) == true && gc.karbonite() >= 100) {
				   	gc.blueprint(current.get(i).id(), UnitType.Factory, Direction.West);
				   return true;
			   }
	    	  
	    	  return false;
	      }
	      
	      	      
	      // This method will loop through all units to see if any workers can build factories
	      public static void checkToBuildFactory(GameController gc, VecUnit current) {
	    	  
	    	  
	    	  for (int i = 0; i < current.size(); i++) {
	    		  Unit thisUnit = current.get(i);
	    		  
	    		  Location location = thisUnit.location();
	    		  
	    		  if (location.isOnMap() == true) {
	    			  VecUnit nearby = gc.senseNearbyUnits(location.mapLocation(), 2);
	    			  for (int c = 0; c < nearby.size(); c++)
	    				  if (thisUnit.unitType() == UnitType.Worker && gc.canBuild(thisUnit.id(), nearby.get(c).id())) { 
	    					  gc.build(thisUnit.id(), nearby.get(c).id());
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
	      
	      public static boolean blueprintRocket(GameController gc, VecUnit current) {
	    	 
	    		  
	    	  for (int i = 0; i < current.size(); i++) {
	    		  
	    		  if (rocketCount < 2) {
	    			  
	    		  if (gc.canBlueprint(current.get(i).id(), UnitType.Rocket, Direction.North) == true && gc.karbonite() >= 500) {
	        		   	gc.blueprint(current.get(i).id(), UnitType.Rocket, Direction.North);
	        		   	rocketCount++;
	        		   	return true;
	  	    	  }
	        	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Rocket, Direction.Northeast) == true && gc.karbonite() >= 500) {
	        		   	gc.blueprint(current.get(i).id(), UnitType.Rocket, Direction.Northeast);
	        		   	rocketCount++;
	        		   	return true;
	        	   }
	        	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Rocket, Direction.East) == true && gc.karbonite() >= 500) {
	        		   	gc.blueprint(current.get(i).id(), UnitType.Rocket, Direction.East);
	        		   	rocketCount++;
	        		   	return true;
	        	   }
	        	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Rocket, Direction.Northwest) == true && gc.karbonite() >= 500) {
	        		   	gc.blueprint(current.get(i).id(), UnitType.Rocket, Direction.Northwest);
	        		   	rocketCount++;
	        		   	return true;
	        	   }
	        	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Rocket, Direction.Southeast) == true && gc.karbonite() >= 500) {
	        		   	gc.blueprint(current.get(i).id(), UnitType.Rocket, Direction.Southeast);
	        		   	rocketCount++;
	        		   	return true;
	        	   }
	        	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Rocket, Direction.Southwest) == true && gc.karbonite() >= 500) {
	        		   	gc.blueprint(current.get(i).id(), UnitType.Rocket, Direction.Southwest);
	        		   	rocketCount++;
	        		   	return true;
	        	   }
	        	   else if (gc.canBlueprint(current.get(i).id(), UnitType.Rocket, Direction.South) == true && gc.karbonite() >= 500) {
	        		   	gc.blueprint(current.get(i).id(), UnitType.Rocket, Direction.South);
	        		   	rocketCount++;
	        		   	return true;
	        	   }
	  			   else if (gc.canBlueprint(current.get(i).id(), UnitType.Rocket, Direction.West) == true && gc.karbonite() >= 500) {
	  				   	gc.blueprint(current.get(i).id(), UnitType.Rocket, Direction.West);
	  				  rocketCount++;
	  				   return true;
	  			   }
	    	  }  
	  	    	  
	    	  }
	    	  return false;
	      }


          public static void checkToBuildRocket(GameController gc, ArrayList<Unit> rockets) {
	    	  
	    	  if (rocketCount < 2 && rockets.get(0).structureIsBuilt() == 0) {
	    	  for (int i = 0; i < rockets.size(); i++) {
	    		  Unit thisUnit = rockets.get(i);
	    		  
	    		  Location location = thisUnit.location();
	    		  
	    		  if (location.isOnMap() == true) {
	    			  VecUnit nearby = gc.senseNearbyUnits(location.mapLocation(), 2);
	    			  for (int c = 0; c < nearby.size(); c++)
	    				  if (thisUnit.unitType() == UnitType.Worker && gc.canBuild(thisUnit.id(), nearby.get(c).id())) { 
	    					  gc.build(thisUnit.id(), nearby.get(c).id());
	    				  }
	    		  }  
	    		  }
	    	  }
	      }
}
