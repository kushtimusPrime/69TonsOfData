// Nathan Hellstedt, Kush Hari, Gordon MacDonald

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
        int factoryCounter=0;
        int workerCounter=4;
        /*
         * Main method worker priority section
         * Workers Earth
         * 	Only 4
         * 	First, build factories in close proximity
         * 	Next, go to get karbonite
         * 	Go back and build rocket (20 rounds prior)
         * 	Stay near that area until death
         * Workers Mars
         * 	Replicate a crap ton
         * Factories
         * 	Build 4 factories
         * 	Blueprint, then immediately build, and repeat 4 times
         * Rockets
         * 	Build 2 rockets
         * 	Back to back after closest round	
         * 	Blueprint 10 rounds prior
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
        
        System.out.println(roundToLeave);
        
	        while (true) {
	        // Prints out current round and Karbonite number
	        	   workers.clear();
	        	   unbuiltFactories.clear();
	        	   builtFactories.clear();
	        	   unbuiltRockets.clear();
	        	   builtRockets.clear();
	        	   
	   	  	   VecUnit myUnits=gc.myUnits();
	   	  	   for(int a=0;a<myUnits.size();a++) {
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Worker)) {
	   	  			   workers.add(myUnits.get(a));


	   	  			  // blueprintTheFactory(gc,directions,myUnits.get(a));
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Knight)) {
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Ranger)) {
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Mage)) {
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Healer)) {
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Factory)) {
	   	  			   if(myUnits.get(a).structureIsBuilt()==0) {
	   	  				   unbuiltFactories.add(myUnits.get(a));
	   	  				   //buildTheFactory(gc,myUnits.get(a),workers);
	   	  			   } else {
	   	  				builtFactories.add(myUnits.get(a));
	   	  			          }
	   	  			   
	   	  		   }
	   	  		   if(myUnits.get(a).unitType().equals(UnitType.Rocket)) {
	   	  			   
	   	  			if(myUnits.get(a).structureIsBuilt()==0) {
	   	  				   unbuiltRockets.add(myUnits.get(a));
	   	  				   //buildTheFactory(gc,myUnits.get(a),workers);
	   	  			   } else {
	   	  				builtRockets.add(myUnits.get(a));
	   	  			   }
	   	  			   
	   	  		   }
	   	  	   }
	   	  	   if(builtFactories.size()>=4) {
	   	  		   factoryCounter=4;
	   	  	   }
	   	  	   if(workers.size()>=4) {
	   	  		   workerCounter=4;
	   	  	   }
	   	  	   if(gc.round()<5 ) {
	   	  	   if(workers.size()<4&&workers.size()>0) {
   	  			   int possiblyRandom=(int)(Math.random()*workers.size());
	   	  			replicate(gc,workers.get(possiblyRandom),directions); 
	   	  		   }
	   	  	   }
	   	  	   if(factoryCounter<4&&workers.size()>0) {
	   	  		   if(unbuiltFactories.size()==0) {
	   	  			   int possiblyRandom=(int)(Math.random()*workers.size());
	   	  			   blueprintTheFactory(gc,directions,workers.get(possiblyRandom));
	   	  		   }
	   	  	   }  
	   	  	   if(factoryCounter<4&&unbuiltFactories.size()>0) {
	   	  		   for(int a=0;a<workers.size();a++) {
	   	  			   int factoryBuilt=buildTheFactory(gc,unbuiltFactories.get(0),workers.get(a));
	   	  			   if(factoryBuilt==0) {
	   	  				   pathFind(gc,unbuiltFactories.get(0).location().mapLocation(),rotationTries,workers.get(a),directions);
	   	  			   }
	   	  		   }
	   	  	   } else if(factoryCounter>=4 && gc.round() < 225 || gc.round() > 300) {
	   	  		   for(int a=0;a<workers.size();a++) {
	  				   checkForKarbonite(gc,workers.get(a),directions);
	  				 MapLocation possibleLocation=finalKarboniteDestination(gc,myUnits.get(a),karboniteLocationsEarth);
	  				   MapLocation stupidLocation=new MapLocation(Planet.Earth,21,21);
	  				   if(possibleLocation.equals(stupidLocation)) {
   	  				   randomPathFind(gc,directions,workers.get(a));
	  				   } else {
	  				   pathFind(gc,possibleLocation,rotationTries,workers.get(a),directions);
	  				   }
	   	  		   }
	   	  	   }
	   	  	   
	   	  	   if (gc.round() == 225) {
	   	  		  int fun = blueprintTheRocket(gc, directions, workers);
	        }
	   	  	   
	   	  	   
	   	  	   if (gc.round() > 225 && gc.round() < 275) {
	   	  		   
	   	  		for(int a=0;a<workers.size();a++) {
	   	  			if (unbuiltRockets.size() > 0) {
	   	  			   int rocketBuilt = buildTheRocket(gc,unbuiltRockets.get(0),workers.get(a));
	   	  			   if(rocketBuilt==0) {
	   	  				   pathFind(gc,unbuiltRockets.get(0).location().mapLocation(),rotationTries,workers.get(a),directions);
	   	  			   }
	   	  		   }
	   	  		}
	   	  	   }
	   	  	   
	   	  	   if (builtRockets.isEmpty() == false) {
	   	  		   int counter = 0;
	   	  		   while (counter < 9) {
	   	  		   for (int i = 0; i < builtRockets.size(); i++) {
	   	  			   for (int a = 0; a < workers.size(); a++) {
	   	  				   loadRocketWithWorkers(gc, builtRockets.get(i), workers.get(a), rotationTries, directions);
	   	  				   counter++;
	   	  			   }
	   	  		   }
	   	  	   }
	   	  	   }
	   	  	   
	   	  	   if (gc.round() == 275) {
	   	  		   if (gc.canLaunchRocket(builtRockets.get(0).id(), new MapLocation(Planet.Mars, 5, 5)))
	   	  			   gc.launchRocket(builtRockets.get(0).isFactoryProducing(), new MapLocation(Planet.Mars, 5, 5));
	   	  	   }


	        	gc.nextTurn();

	   	  	   }
	        

	 }

	           
	//// End of the main method        
	//// Start of static methods      
	
	        
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
     	 public static int buildTheFactory(GameController gc,Unit unbuiltFactory,Unit worker) {
     				 if(gc.canBuild(worker.id(), unbuiltFactory.id())) {
     					 gc.build(worker.id(), unbuiltFactory.id());
     					 return 1;
     				 }
     				 return 0;
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
     		 MapLocation optimalMapLocation=new MapLocation(Planet.Earth,21,21);
     		 for(int a=0;a<karboniteLocationsEarth.size();a++) {
     			 try {
     			 if(gc.karboniteAt(karboniteLocationsEarth.get(a))>0) {
     			 long distance=worker.location().mapLocation().distanceSquaredTo(karboniteLocationsEarth.get(a));
     			 if(distance<min) {
     				 min=distance;
     				 optimalMapLocation=karboniteLocationsEarth.get(a);

     			 }
     			 }
     			 } catch (Exception e) {
     				 
     			 }
     		 }

     		 return optimalMapLocation;

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
     	 
     	public static int blueprintTheRocket(GameController gc, ArrayList<Direction> directions,ArrayList<Unit> workers) {
      	  //System.out.println("Number of workers: "+workers.size());
     		for(int a=0;a<workers.size();a++) {
        		  for(int b=0;b<directions.size();b++) {
        			  if(gc.canBlueprint(workers.get(a).id(), UnitType.Rocket, directions.get(b))) {
        				  gc.blueprint(workers.get(a).id(), UnitType.Rocket, directions.get(b));
        				  return 1;
        			  }
        		  }
        	  }
        	  
        	  return 0;
        	  
          }
      	  
     	
     	public static int buildTheRocket(GameController gc,Unit unbuiltRockets,Unit worker) {
			 if(gc.canBuild(worker.id(), unbuiltRockets.id())) {
				 gc.build(worker.id(), unbuiltRockets.id());
				 return 1;
			 }
		
		return 0;
 }
     	
     	public static void loadRocketWithWorkers (GameController gc, Unit rockets, Unit worker, int[] rotationTries, ArrayList<Direction> directions) {
     	  
     		if (gc.canLoad(rockets.id(), worker.id())) {
     			gc.load(rockets.id(), worker.id());
     		}
     		else {
     			pathFind(gc, rockets.location().mapLocation(), rotationTries, worker, directions);
     		}
     		
        }


}