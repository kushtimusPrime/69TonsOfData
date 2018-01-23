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
import bc.Team;
import bc.Unit;
import bc.UnitType;
import bc.VecUnit;
import bc.VecUnitID;
import bc.bc;
import java.util.ArrayList;
import java.util.Arrays;
import bc.Planet;


public class Player {
	
	
	 public static void main(String[] args) throws NullPointerException {
	   GameController gc = new GameController();
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
	  	ArrayList<Unit> unbuiltFactories=new ArrayList<Unit>();
	  	ArrayList<Direction> directions=new ArrayList<Direction>();
	  	ArrayList<Unit> workers=new ArrayList<Unit>();
	  	ArrayList<Unit> builtFactories=new ArrayList<Unit>();
	  	ArrayList<Unit> knights=new ArrayList<Unit>();
	  	ArrayList<Unit> healers=new ArrayList<Unit>();
	  	ArrayList<Unit> mages=new ArrayList<Unit>();
	  	ArrayList<Unit> rangers=new ArrayList<Unit>();
	  	ArrayList<Unit> unbuiltRockets=new ArrayList<Unit>();
	  	ArrayList<Unit> builtRockets=new ArrayList<Unit>();
	  	ArrayList<MapLocation> karboniteLocationsEarth=new ArrayList<MapLocation>();
	  	PlanetMap planetMap=gc.startingMap(gc.planet());
	  	ArrayList<Unit> structures=new ArrayList<Unit>();
	  	karboniteLocationsEarth=setKarboniteDestination(gc,karboniteLocationsEarth,planetMap);
        directions=setDirectionsArrayList(gc,directions);
        Team myTeam;
        long buildHealer=0;
        int[] rotationTries= {0,-1,1,-2,2,-3,3,-4,4};
        /*
         * Main method worker priority section
         * Workers Earth
         * 	Only 4
         * 	First, build factories in close proximity
         * 	Next, go to get karbonite
         * 	Go back and build rocket
         * 	Stay near that area until death
         * Workers Mars
         * 	Replicate a crap ton
         * Factories
         * 	
         * Factories
         * Blueprint one factory at a time
         * Workers stay and build
         * Make 4 factories
         * limit worker count to 4
         * Build rocket at round 250
         * Only one rocket builds, but then workers replicate a crap ton, no rangers are made in this process
         * 
         * 
         */
        
        long roundToLeave = getFirstLaunchRound(gc);
        
	        while (true) {
	        // Prints out current round and Karbonite number
	        	   workers.clear();
	        	   unbuiltFactories.clear();
	        	   
	           System.out.println("Current round: "+gc.round());
	           System.out.println("Karbonite " + gc.karbonite());
	   	  	   VecUnit myUnits=gc.myUnits();
	   	  	   for(int a=0;a<myUnits.size();a++) {
	   	  		   
	   	  		if(myUnits.get(a).unitType().equals(UnitType.Factory)) {
	   	  			   if(myUnits.get(a).structureIsBuilt()==0) {
	   	  				   unbuiltFactories.add(myUnits.get(a));
	   	  			   }
	   	  		}
	   	  	   
	   	  			   
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Worker)) {
	   	  			   
	   	  		   if (unbuiltFactories.isEmpty() != true) {
	   	  			   goToFactory(gc, unbuiltFactories.get(0).location().mapLocation(), rotationTries, myUnits.get(a), directions, unbuiltFactories);
	   	  			   System.out.println(unbuiltFactories.toString());
	   	  		   }
	   	  			 
	   	  		   else {
	   	  			      
	   	  			      checkForKarbonite(gc,myUnits.get(a),directions);
	   	  		   
	   	  				   MapLocation possibleLocation=finalKarboniteDestination(gc,myUnits.get(a),karboniteLocationsEarth);
	   	  				   MapLocation stupidLocation=new MapLocation(Planet.Earth,0,0);
	   	  				   if(possibleLocation.equals(stupidLocation)) {
		   	  				   randomPathFind(gc,directions,gc.myUnits().get(a));
	   	  				   } else {
	   	  				   pathFind(gc,possibleLocation,rotationTries,myUnits.get(a),directions);
	   	  				   }
	   	  			   //replicate(gc,myUnits.get(a),directions);
	   	  			  // blueprintTheFactory(gc,directions,myUnits.get(a));
	   	  		   
	   	  			   workers.add(myUnits.get(a));
	   	  		   }   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Knight)) {
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Ranger)) {
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Mage)) {
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Healer)) {
	   	  			   
	      
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Rocket)) {
	   	  			   
	   	  		   }
	   	  		   
	   	  		   if (unbuiltFactories.isEmpty() == true && gc.karbonite() >= 100)
	   	  			   for (int i = 0; i < workers.size(); i++)
	   	  				   if (blueprintTheFactory(gc, directions, workers.get(i)) == 1)
	   	  					   i = workers.size();
	   	  	   }
	   	  	   
	   	 
	   	  	   

	           /*
	           VecUnit current = gc.myUnits();
	           myTeam=gc.team();
	           for(int a=0;a<current.size();a++) {
	        	   	if(current.get(a).unitType().equals(UnitType.Factory)) {
	        	   		if(current.get(a).structureIsBuilt()==0) {
	        	   			System.out.println(unbuiltFactories.contains(current.get(a)));
	        	   			if(!unbuiltFactories.contains(current.get(a))) {
		        	   			unbuiltFactories.add(current.get(a));
	        	   			}
	        	   		} else {
	        	   			
	        	   			builtFactories.add(current.get(a));
	        	   		}
	     
	        	   	}
	        	   	if(current.get(a).unitType().equals(UnitType.Rocket)) {
	        	   		if(current.get(a).structureIsBuilt()==0) {
	        	   			unbuiltRockets.add(current.get(a));
	        	   		} else {
	        	   			builtRockets.add(current.get(a));
	        	   		}
	     
	        	   	}
	        	   	if(current.get(a).unitType().equals(UnitType.Worker)) {
	        	   		workers.add(current.get(a));
	        	   	}
	        	   	if(current.get(a).unitType().equals(UnitType.Knight)) {
	        	   		knights.add(current.get(a));
	        	   	}
	        	   	if(current.get(a).unitType().equals(UnitType.Healer)) {
	        	   		healers.add(current.get(a));
	        	   	}
	        	   	if(current.get(a).unitType().equals(UnitType.Mage)) {
	        	   		mages.add(current.get(a));
	        	   	}
	        	   	if(current.get(a).unitType().equals(UnitType.Ranger)) {
	        	   		rangers.add(current.get(a));
	        	   	}

	           }
	           structures.addAll(builtRockets);
	           structures.addAll(unbuiltRockets);
	           structures.addAll(builtFactories);
	           structures.addAll(unbuiltFactories);
	           System.out.println("Big test"+Arrays.toString(unbuiltFactories.toArray()));

	           long factoryRound=gc.round()%50;
	           long karbonite=gc.karbonite();
	           if(factoryRound==1&&karbonite>=100) {
	        	   blueprintTheFactory(gc,directions,workers,unbuiltFactories);
	           }
	           buildTheFactory(gc,unbuiltFactories,workers);
	           karbonite=gc.karbonite();
	           long roundNumber=gc.round()%2;
	           if(karbonite>20&&roundNumber==1) {
	        	   	buildKnights(gc,builtFactories);	        	   
	           }

	           unloadGarrison(gc,builtFactories,directions);
	        	checkForKarbonite(gc, workers,directions);

	   	  	if(gc.planet().equals(Planet.Earth)) {
	   		  	karboniteLocationsEarth=setKarboniteDestination(gc,karboniteLocationsEarth,planetMap);
	   		  	//for(int workerTracker=0;workerTracker<workers.size();workerTracker++) {
	   		  		MapLocation placeToGo=finalKarboniteDestination(gc,workers.get(0),karboniteLocationsEarth);
	   		  		pathFind(gc,placeToGo,rotationTries,workers.get(0));
	   		  	//}
	   		}
	       // 	checkToReplicate(gc, workers);*/
	        	gc.nextTurn();
	           

	           
	        }
	        }
  
	
	        
	      // Checks for karbonite and mines karbonite if available
	      public static void checkForKarbonite(GameController gc, Unit worker,ArrayList<Direction> directions) {
	    			  for(int a=0;a<directions.size();a++) {
	    				  if(gc.canHarvest(worker.id(), directions.get(a))) {
	    					  gc.harvest(worker.id(), directions.get(a));
	    				  }
	    			  }
	    			  if(gc.canHarvest(worker.id(), Direction.Center)) {
	    				  gc.harvest(worker.id(), Direction.Center);
	    			  }
	    	  }
	    		  
	    		/*  if (current.workerHasActed() == 0) {
	    			  
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

	    				  if (gc.canMove(current.id(), Direction.North) && current.movementHeat() < 10) {
	    					  System.out.println("Movement heat:"+current.movementHeat());
		    				  gc.moveRobot(current.id(), Direction.North);
	    				  }
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
	    		  }*/

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

          public static int blueprintTheFactory(GameController gc,ArrayList<Direction> directions,Unit worker) {
        	  //System.out.println("Number of workers: "+workers.size());
        		  for(int b=0;b<directions.size();b++) {
        			  if(gc.canBlueprint(worker.id(), UnitType.Factory, directions.get(b))) {
        				  gc.blueprint(worker.id(), UnitType.Factory, directions.get(b));
        				  return 1;
        			  }
        		  }
        		  if(gc.canBlueprint(worker.id(), UnitType.Factory, Direction.Center)) {
        			  gc.blueprint(worker.id(), UnitType.Factory, Direction.Center);
        			  return 1;
        		  }
        	  return 0;
        	  
          }
     	 public static ArrayList<Direction> setDirectionsArrayList(GameController gc,ArrayList<Direction> directions) {
 	        directions.add(Direction.North);
 	        directions.add(Direction.Northeast);
 	        directions.add(Direction.East);
 	        directions.add(Direction.Southeast);
 	        directions.add(Direction.South);
 	        directions.add(Direction.Southwest);
 	        directions.add(Direction.West);
 	        directions.add(Direction.Northwest);
 	        return directions;
 	 }
     	 public static void buildTheFactory(GameController gc,Unit unbuiltFactory,ArrayList<Unit> workers) {
     		 for(int a=0;a<workers.size();a++) {
     				 if(gc.canBuild(workers.get(a).id(), unbuiltFactory.id())) {
     					 gc.build(workers.get(a).id(), unbuiltFactory.id());
     				 }
     		 } 
     	 }

     	 public static void unloadGarrison(GameController gc,ArrayList<Unit> builtFactories,ArrayList<Direction> directions) {
     		 for(int b=0;b<builtFactories.size();b++) {
     				 for(int d=0;d<directions.size();d++) {
     					 if(gc.canUnload(builtFactories.get(b).id(), directions.get(d))) {
     						 gc.unload(builtFactories.get(b).id(), directions.get(d));
     					 }
     				 }


     		 }
    
     		 
     	 }
     	 public static ArrayList<MapLocation> setKarboniteDestination(GameController gc,ArrayList<MapLocation> karboniteLocationsEarth,PlanetMap planetMap) {
     		 MapLocation mapLocation;
     		 for(int a=0;a<planetMap.getWidth();a++) {
     			 for(int b=0;b<planetMap.getHeight();b++) {
     				  mapLocation=new MapLocation(Planet.Earth,a,b);
     				 try {
     				 if(planetMap.initialKarboniteAt(mapLocation)>0) {
     					 karboniteLocationsEarth.add(mapLocation);
     				 }
     				 } catch (Exception e) {
     					 
     				 }
     			 }
     		 }
     		 return karboniteLocationsEarth;
     	 }
     	 public static MapLocation finalKarboniteDestination(GameController gc, Unit worker,ArrayList<MapLocation> karboniteLocationsEarth) {
     		 long min=1000;
     		 MapLocation optimalMapLocation=new MapLocation(Planet.Earth,0,0);
     		 for(int a=0;a<karboniteLocationsEarth.size();a++) {
     			 try {
     			 if(gc.karboniteAt(karboniteLocationsEarth.get(a))>0) {
     			 long distance=worker.location().mapLocation().distanceSquaredTo(karboniteLocationsEarth.get(a));
     			 if(distance<min) {
     				 min=distance;
     				 optimalMapLocation=karboniteLocationsEarth.get(a);
     				 //System.out.println("Got the minimum "+min);
     				// System.out.println("Karbonite maplocation: "+optimalMapLocation);
     			 }
     			 }
     			 } catch (Exception e) {
     				 
     			 }
     		 }
     		 //System.out.println("MapLocation: "+optimalMapLocation);
     		// System.out.println("Worker location: "+worker.location().mapLocation());
     		 return optimalMapLocation;
     		 //gc.canMove(robot_id, direction)
     		// gc.isMoveReady(robot_id)
     	 }
     	 public static int pathFind(GameController gc,MapLocation finalMapLocation,int[] rotationTries,Unit unit,ArrayList<Direction> directions) {
     		 try {
     		 Direction goTo=unit.location().mapLocation().directionTo(finalMapLocation);
     			 for(int a=0;a<rotationTries.length;a++) {
     				 if(gc.canMove(unit.id(), goTo)&&gc.isMoveReady(unit.id())) {
     					 gc.moveRobot(unit.id(), goTo);
     					 return 1;
     				 }
     			 }
     		 } catch (Exception e) {
     			 
     		 }
     		 randomPathFind(gc,directions,unit);
     		 return 0;
     	 }
     	 public static void replicate(GameController gc,Unit worker,ArrayList<Direction> directions) {
     		 for(int a=0;a<directions.size();a++) {
     			 if(gc.canReplicate(worker.id(), directions.get(a))) {
     				 gc.replicate(worker.id(), directions.get(a));
     			 }
     		 }
     		 
     	 }
     	 public static int randomPathFind(GameController gc,ArrayList<Direction> directions, Unit robot) {
     		 if(gc.isMoveReady(robot.id())) {
     			 while(true) {
     				 int randomNumber=(int)(Math.random()*8);
     				 if(gc.canMove(robot.id(), directions.get(randomNumber))) {
     					 gc.moveRobot(robot.id(), directions.get(randomNumber));
     					 return 1;
     				 }
     			 }
     		 }
     		 return 0;
     	 }
     	 
     	 public static int goToFactory(GameController gc, MapLocation finalLocation, int[] rotationTries, Unit unit, ArrayList<Direction> directions, ArrayList<Unit> unbuiltFactories) {
     		 
     		if (gc.canBuild(unit.id(), unbuiltFactories.get(0).id())) {
     			gc.build(unit.id(), unbuiltFactories.get(0).id());
     			return 1;
     		}
     		else {
     			pathFind(gc, finalLocation, rotationTries, unit, directions);
     		}
     		 
     		 
     		 return 0;
     	 }
     	 /*
     	  * public static void pathFind(gc,finalMapLocation,rotationTries,Unit unit) {
     	  * 		Direction goTo=mapLocation.directionTo();
     	  * 		if(gc.isMoveReady(unit.id)==true)
     	  * 		for(int a=0;a<rotationTries.length;a++) {
     	  * 			if(gc.canMove(unit.id,goTo)) {
     	  * 				gc.Move(unit.id,goTto);
     	  * 
     	  * }
     	  */

}