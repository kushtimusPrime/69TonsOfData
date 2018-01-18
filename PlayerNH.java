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
import bc.VecUnitID;
import bc.bc;
import java.util.ArrayList;
import bc.Planet;


public class Player {


     
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
	  	ArrayList<Unit> unbuiltFactories=new ArrayList<Unit>();
	  	ArrayList<Direction> directions=new ArrayList<Direction>();
	  	ArrayList<Unit> workers=new ArrayList<Unit>();
	  	ArrayList<Unit> builtFactories=new ArrayList<Unit>();
	  	ArrayList<Unit> knights=new ArrayList<Unit>();
	  	ArrayList<Unit> unbuiltRockets =new ArrayList<Unit>();
	  	
        directions=setDirectionsArrayList(gc,directions);
        long buildHealer=0;
	  	
	        
	        while (true) {
	        unbuiltFactories.clear();
	        unbuiltRockets.clear();
	        builtFactories.clear();
	        rockets.clear();
	        // Prints out current round and Karbonite number
	           System.out.println("Current round: "+gc.round());
	           System.out.println("Karbonite " + gc.karbonite());
	           
	           VecUnit current = gc.myUnits();
	           
	           for(int a=0;a<current.size();a++) {
	        	   	if(current.get(a).unitType().equals(UnitType.Factory)) {
	        	   		if(current.get(a).structureIsBuilt()==0) {
	        	   			unbuiltFactories.add(current.get(a));
	        	   		} else {
	        	   			builtFactories.add(current.get(a));
	        	   		}
	     
	        	   	}
	        	   	if(current.get(a).unitType().equals(UnitType.Worker)) {
	        	   		workers.add(current.get(a));
	        	   	}
	        	   	if(current.get(a).unitType().equals(UnitType.Knight)) {
	        	   		workers.add(current.get(a));
	        	   	}
	           }
	           long factoryRound=gc.round()%50;
	           long karbonite=gc.karbonite();
	           if(factoryRound==1&&karbonite>=100) {
	        	   blueprintTheFactory(gc,directions,workers);
	        
	           }
	           buildTheFactory(gc,unbuiltFactories,workers);
	           karbonite=gc.karbonite();
	           long roundNumber=gc.round()%2;
	           if(karbonite>20&&roundNumber==1) {
	        	   	buildKnights(gc,builtFactories);	        	   
	           }
	           if(karbonite>20&&roundNumber==1) {
	        	   	buildHealers(gc,builtFactories);
	           }

	           unloadGarrison(gc,builtFactories,directions);
	           attack(gc,knights);
	           // Only occurs in the first round
	           if (gc.round() == 1 && gc.planet().equals(Planet.Earth)) {
	        	   for (int i = 0; i < current.size(); i++)
	        	   {
	        	    //blueprintFactory(i, gc, current);
	        	   }
	            }
	           
	           checkForKarbonite(gc, current);
	        	checkToReplicate(gc, workers, directions);
	          
	           for(int a=0;a<current.size();a++) {
	        	   	if(current.get(a).unitType().equals(UnitType.Rocket)) {
	        	   		if(current.get(a).structureIsBuilt()==0) {
	        	   			unbuiltRockets.add(current.get(a));
	        	   		} else {
	        	   			rockets.add(current.get(a));
	        	   		}
	        	   	}
	           }
	           
	           if (gc.round() == 125) {
	        	   blueprintTheRocket(gc, directions, workers);
	           }
	           
	           if (gc.round() > 125) {
	        	   for (int s = 0; s < current.size(); s++) {
	        		   if(current.get(s).unitType().equals(UnitType.Rocket)) {
		        	   		if(current.get(s).structureIsBuilt()==0) {
		        	   			unbuiltRockets.add(current.get(s));
		        	   		} else {
		        	   			rockets.add(current.get(s));
		        	   		}
			        		   
	        	   }
	              
	        	buildTheRocket(gc, unbuiltRockets, workers);
	            loadRocketWithWorkers(gc, rockets, workers);
	            
	           }
	           }
	        	   if (gc.round() == 250) {
	        	//	   gc.launchRocket(rockets.get(0).id(), new MapLocation(Planet.Mars, 5, 5));
	        		   for (int a = 0; a < current.size(); a++) {
	        			   if (current.get(a).unitType().equals(UnitType.Rocket))
	        				   gc.launchRocket(current.get(a).id(), new MapLocation(Planet.Mars, 5, 5));
	        		   }
	        	   }
	        	   
	        
	       gc.nextTurn();
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
	      public static void checkToReplicate(GameController gc, ArrayList<Unit> workers, ArrayList<Direction> directions) {
	    	  if (workers.size() <= 50) {
	    		  for(int a=0;a<workers.size();a++) {
	    			  for (int c = 0; c < directions.size(); c++){
	      				 if(gc.canReplicate(workers.get(a).id(), directions.get(c))) {
	      					 gc.replicate(workers.get(a).id(), directions.get(c));
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
	     

          public static int blueprintTheFactory(GameController gc,ArrayList<Direction> directions,ArrayList<Unit> workers) {
        	  System.out.println("Number of workers: "+workers.size());
        	  for(int a=0;a<workers.size();a++) {
        		  for(int b=0;b<directions.size();b++) {
        			  if(gc.canBlueprint(workers.get(a).id(), UnitType.Factory, directions.get(b))) {
        				  gc.blueprint(workers.get(a).id(), UnitType.Factory, directions.get(b));
        				  return 1;
        			  }
        		  }
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
     	 public static void buildTheFactory(GameController gc,ArrayList<Unit> unbuiltFactories,ArrayList<Unit> workers) {
     		 for(int a=0;a<workers.size();a++) {
     			 for(int b=0;b<unbuiltFactories.size();b++) {
     				 if(gc.canBuild(workers.get(a).id(), unbuiltFactories.get(b).id())) {
     					 gc.build(workers.get(a).id(), unbuiltFactories.get(b).id());
     				 }
     			 }
     		 } 
     	 }
     	 public static int buildKnights(GameController gc,ArrayList<Unit> builtFactories) {
     		 for(int a=0;a<builtFactories.size();a++) {
     			 if(gc.canProduceRobot(builtFactories.get(a).id(), UnitType.Knight) ) {
     				 gc.produceRobot(builtFactories.get(a).id(), UnitType.Knight);
     				 System.out.println("produced a knight");
     				 return 1;
     			 }

     		 }
     		 return 0;
     		 
     	 }
     	 public static int buildHealers(GameController gc,ArrayList<Unit> builtFactories) {
 			 System.out.println("Entering the method");
     		 for(int z=0;z<builtFactories.size();z++) {
     			 if(gc.canProduceRobot(builtFactories.get(z).id(), UnitType.Healer) ) {
     				 gc.produceRobot(builtFactories.get(z).id(), UnitType.Healer);
     				 System.out.println("produced a healer");
     				 return 1;
     			 } 

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
     	 public static void attack(GameController gc,ArrayList<Unit> knights) {
     		 
     		 
     	 }
     	 public static void destination(GameController gc) {
     		 
     	 } 
     	//returns location for rocket to land on mars
     	public static MapLocation rocketlandinglocationMars()
     	{
     		GameMap b = new GameMap();
     		PlanetMap m = b.getMars_map();
     		long height = m.getHeight();
     		long width = m.getWidth();
     		MapLocation land = new MapLocation(null, 0, 0);
     		land.setPlanet(Planet.Mars);
     		while(!m.onMap(land)&&m.isPassableTerrainAt(land) != 0)
     		{
     		int x = (int)(Math.random()*width);
     		int y = (int)(Math.random()*height);
     		land.setX(x);
     		land.setY(y);
     		}
     		return land;
     	}
     	// returns array list of  map locations with initial karbonite deposited
     	public static ArrayList<MapLocation> getKarboniteSpaces()
     	{
     		ArrayList<MapLocation> Karb = new ArrayList<MapLocation>();
     		GameMap b = new GameMap();
     		PlanetMap e = b.getEarth_map();
     		long height = e.getHeight();
     		long width = e.getWidth();
     		MapLocation a = new MapLocation(null, 0, 0);
     		a.setPlanet(Planet.Earth);  
     		int x = 0;
     		int y = 0;
     		while(x<=height)
     		{
     			while(y<=width)
     			{
     				a.setX(x);
     				a.setY(x);
     				if(e.initialKarboniteAt(a)>0)
     				{
     					Karb.add(a);
     				}
     				y++;
     			}
     			x++;
     		}
     		return Karb;
     	}

     	// returns array list of all impassable spaces on earth
     	public static ArrayList<MapLocation> getImpassableSpaces()

     	{
     		ArrayList<MapLocation> pass = new ArrayList<MapLocation>();
     		GameMap b = new GameMap();
     		PlanetMap e = b.getEarth_map();
     		long height = e.getHeight();
     		long width = e.getWidth();
     		MapLocation a = new MapLocation(null, 0, 0);
     		a.setPlanet(Planet.Earth);  
     		int x = 0;
     		int y = 0;
     		while(x<=height)
     		{
     			while(y<=width)
     			{
     				a.setX(x);
     				a.setY(x);
     				if(e.isPassableTerrainAt(a) != 0)
     				{
     					pass.add(a);
     				}
     				y++;
     			}
     			x++;
     		}
     		return pass;
     	}
     	
     	//Attempt to blueprint a rocket when called
        public static int blueprintTheRocket(GameController gc,ArrayList<Direction> directions,ArrayList<Unit> workers) {
      	 // System.out.println("Number of workers: "+workers.size());
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

        
        // Will Build a rocket if unbuiltRockets.size() > 0
        public static void buildTheRocket(GameController gc,ArrayList<Unit> unbuiltRockets,ArrayList<Unit> workers) {
    		 for(int a=0;a<workers.size();a++) {
    			 for(int b=0;b<unbuiltRockets.size();b++) {
    				 if(gc.canBuild(workers.get(a).id(), unbuiltRockets.get(b).id())) {
    					 gc.build(workers.get(a).id(), unbuiltRockets.get(b).id());
    				 }
    			 }
    		 } 
    	 
}

       public static void loadRocketWithWorkers (GameController gc, ArrayList<Unit> rockets, ArrayList<Unit> workers) {
    	   for (int i = 0; i < rockets.size(); i++) {
    		   for (int c = 0; c < workers.size(); c++) {
    			   if (gc.canLoad(rockets.get(i).id(), workers.get(c).id()))
    				    gc.load(rockets.get(i).id(), workers.get(c).id());
    		   }
    	   }
       }



}