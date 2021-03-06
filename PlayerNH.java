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
    		GameController gc=new GameController();
    		gc.queueResearch(UnitType.Worker);
    		for(int a=1;a<3;a++) {
    			gc.queueResearch(UnitType.Rocket);
    		}
    		for(int b=1;b<4;b++) {
    			gc.queueResearch(UnitType.Ranger);
    		}
    		for(int c=1;c<4;c++) {
    			gc.queueResearch(UnitType.Healer);
    		}
    		PlanetMap planetMap=gc.startingMap(Planet.Earth);
    	    ArrayList<Direction> directions=new ArrayList<Direction>();
    	    ArrayList<MapLocation> karboniteLocationsEarth=new ArrayList<MapLocation>();
    	    karboniteLocationsEarth=setKarboniteDestination(gc,karboniteLocationsEarth,planetMap);
    	    setDirectionsArrayList(gc,directions);
    	    Team myTeam=gc.team();
    	    Team enemyTeam;
    	    if(myTeam.equals(Team.Blue)) {
    	    enemyTeam=Team.Red;
    	    } else {
    	    enemyTeam=Team.Blue;
    	    }
    	    int[] rotationTries= {0,-1,1,-2,2,-3,3};
    	    ArrayList<Unit> unbuiltFactories=new ArrayList<Unit>();
    	    ArrayList<Unit> builtFactories=new ArrayList<Unit>();
    	    int canBlueprintFactory=1;
    	    int factoryCounter=4;
    	    int builtFactoryCount=0;
    	    int builtRocketCount=0;
    	    int canBlueprintRocket=1;
    	    int rocketCount = 0;
    	    ArrayList<Unit> workers=new ArrayList<Unit>();
        MapLocation stupidLocation=new MapLocation(Planet.Earth,-1,-1);
        ArrayList<Unit> unbuiltRockets=new ArrayList<Unit>();
	    ArrayList<Unit> builtRockets=new ArrayList<Unit>();
	    ArrayList<Unit> rangers=new ArrayList<Unit>();
	    int rangerCount=0;
    		while (true) {
    			workers.clear();
    			unbuiltFactories.clear();
    			builtFactories.clear();
    			unbuiltRockets.clear();
    			builtRockets.clear();
    			rangers.clear();
    			System.out.println("Current round:"+gc.round());
    			System.out.println("Karbonite:"+gc.karbonite());
    			try {
    			System.out.println("Get time left "+gc.getTimeLeftMs()+"ms");
    			} catch (Exception e) {
    				
    			}
    			VecUnit myUnits=gc.myUnits();
    			VecUnit enemyUnits=gc.senseNearbyUnitsByTeam(new MapLocation(Planet.Earth,(int)planetMap.getWidth()/2,(int)planetMap.getHeight()/2), ((planetMap.getHeight())*(planetMap.getWidth())), enemyTeam);
    			for(int d=0;d<myUnits.size();d++) {
    			    if(myUnits.get(d).unitType().equals(UnitType.Worker)) {
    			    		workers.add(myUnits.get(d));
    			    		if(2<gc.round()&&gc.round()<5) {
    			    			replicate(gc,myUnits.get(d),directions);
    			    		}
    			    		if (rocketCount < 3) {
    			    			int didBuild = blueprintTheRocket(gc, directions, myUnits.get(d));
    			    			if (didBuild == 1)
    			    				rocketCount++;
    			    		}
    			    				
    			    		}
    			    		if(canBlueprintFactory>0&&builtFactoryCount<4) {
    			    			if(blueprintTheFactory(gc,directions,myUnits.get(d))==1) {
    			    				canBlueprintFactory=0;
    			    			}
    			    		} /*else if(unbuiltRockets.size()==0&&unbuiltFactories.size()==0) {
    			    			checkForKarbonite(gc,myUnits.get(d),directions);
    			    			MapLocation possibleLocation=finalKarboniteDestination(gc,myUnits.get(d),karboniteLocationsEarth);
    			    			if(!possibleLocation.equals(stupidLocation)) {
    			    				pathFind(gc,possibleLocation,rotationTries,myUnits.get(d),directions);
    			    			}*/
    			    		
    			    		/*if(builtFactoryCount>=5) {
    			    			checkForKarbonite(gc,myUnits.get(d),directions);
    			    			MapLocation possibleLocation=finalKarboniteDestination(gc,myUnits.get(d),karboniteLocationsEarth);
    			    			if(!possibleLocation.equals(stupidLocation)) {
    			    				pathFind(gc,possibleLocation,rotationTries,myUnits.get(d),directions);
    			    			}
    			    		}*/
    			    		/*
    			    		 * Blueprint factory once factory is built
    			    		 */
    			    		/*if(factoryCounter<4) {
    			    			if(factoryNumber==0) {
    			    				int blueprintedFactory=blueprintTheFactory(gc,directions,myUnits.get(d));
    			    					if(blueprintedFactory==1) {
    			    						factoryCounter++;
    			    						factoryNumber++;
    			    					}
    			    			}
    			    		}
    			    		if(unbuiltFactories.size()>0) {
    			    			if(buildTheFactory(gc,unbuiltFactories.get(0),myUnits.get(d))==0) {
    			    				pathFind(gc,unbuiltFactories.get(0).location().mapLocation(),rotationTries,myUnits.get(d),directions);    				
    			    				//MapLocation finalMapLocation,int[] rotationTries,Unit unit,ArrayList<Direction> directions) {
    			    			}

    			    		}
			    			if(unbuiltFactories.get(0).structureIsBuilt()==1) {
			    				factoryNumber--;
			    				built++;
			    			}
    			    		if(built>=4) {
    			    			checkForKarbonite(gc,myUnits.get(d),directions);
    			    			MapLocation possibleLocation=finalKarboniteDestination(gc,myUnits.get(d),karboniteLocationsEarth);
    	    		            MapLocation stupidLocation=new MapLocation(Planet.Earth,-1,-1);
    			    			if(!possibleLocation.equals(stupidLocation)) {
    			    				pathFind(gc,possibleLocation,rotationTries,myUnits.get(d),directions);
    			    			}
    			    		}*/
    			    			/*
    			    			 *     		     if(factoryCounter>=4) {
    		        for(int a=0;a<workers.size();a++) {
    		            checkForKarbonite(gc,workers.get(a),directions);
    		            MapLocation possibleLocation=finalKarboniteDestination(gc,myUnits.get(a),karboniteLocationsEarth);
    		            MapLocation stupidLocation=new MapLocation(Planet.Earth,-1,-1);
    		            if(possibleLocation.equals(stupidLocation)) {
    		            		randomPathFind(gc,directions,workers.get(a));
    		            } else {
    		            		pathFind(gc,possibleLocation,rotationTries,workers.get(a),directions);
    		            }
    		        }
    		     }
    			    			 */

    			    
    			    if(myUnits.get(d).unitType().equals(UnitType.Factory)) {
    			    		if(myUnits.get(d).structureIsBuilt()==0) {
    			    			unbuiltFactories.add(myUnits.get(d));
    			    		} else {
    			    			builtFactories.add(myUnits.get(d));
    			    			/*if(rangerCount%6==5) {
    			    				if(buildHealers(gc,myUnits.get(d))==1) {
    			    					rangerCount++;
    			    				}
    			    			}*/
    			    			//if(gc.round()<200||builtRocketCount>=2) {
    			    			if(buildRangers(gc,myUnits.get(d))==1) {
    			    				rangerCount++;
    			    				rangers.add(myUnits.get(d));
    			    			}
    			    			//}
    			    		    unloadGarrison(gc,myUnits.get(d),directions);
    			    		}
    			    }
    			    if(myUnits.get(d).unitType().equals(UnitType.Ranger)) {
    			    		if(myUnits.get(d).location().isInGarrison()==false&&myUnits.get(d).location().isInSpace()==false) {
    			    			MapLocation maybe=finalEnemyDestination(gc,myUnits.get(d),enemyUnits);
    			    			if(!maybe.equals(stupidLocation)) {
    			    				pathFind(gc,maybe,rotationTries,myUnits.get(d),directions);
    			    			} else {
    			    				MapLocation center=new MapLocation(Planet.Earth,(int)planetMap.getWidth()/2,(int)planetMap.getHeight()/2);
    			    				pathFind(gc,center,rotationTries,myUnits.get(d),directions);
    			    			}
    			    		}
    			    }
    			    if(myUnits.get(d).unitType().equals(UnitType.Healer)) {
    			    		if(myUnits.get(d).location().isInGarrison()==false&&myUnits.get(d).location().isInSpace()==false) {
    			    			
    			    		}
    			    }
    			    if(myUnits.get(d).unitType().equals(UnitType.Rocket)) {
    			    		if(myUnits.get(d).structureIsBuilt()==0) {
    			    			unbuiltRockets.add(myUnits.get(d));
    			    		} else {
    			    			builtRockets.add(myUnits.get(d));
    			    		}
    			    	
    			    }
    			    
    			}
    			/*if(gc.round()<100) {
    		     if(workers.size()>=4) {
    		         workerCounter=4;
    		         }
    		         if(gc.round()<5 ) {
    		         if(workers.size()<4&&workers.size()>0) {
    		         int possiblyRandom=(int)(Math.random()*workers.size());
    		         replicate(gc,workers.get(possiblyRandom),directions);
    		         }
    		         }
    			if(builtFactories.size()>=4) {
    			     factoryCounter=4;
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
    		        	 		int found=pathFind(gc,unbuiltFactories.get(0).location().mapLocation(),rotationTries,workers.get(a),directions);
    		        	 		if(found==0) {
    		        	 			randomPathFind(gc,directions,workers.get(a));
    		        	 		}
    		        	 	}
    		         }
    		    }
    			}
    			
*/
	    		if(unbuiltFactories.size()>0) {
	    			for(int a=0;a<workers.size();a++) {
	    				if(buildTheFactory(gc,unbuiltFactories.get(0),workers.get(a))==0) {
	    					pathFind(gc,unbuiltFactories.get(0).location().mapLocation(),rotationTries,workers.get(a),directions);    				
	    				//MapLocation finalMapLocation,int[] rotationTries,Unit unit,ArrayList<Direction> directions) {
	    				}
	    			}
	    		}
	    	/*	if(builtFactories.size()>builtFactoryCount) {
	    			builtFactoryCount=builtFactories.size();
	    			canBlueprintFactory=1;
	    		} */
	    			for(int a=0;a<workers.size();a++) {
	    				if(unbuiltFactories.size()>0) {
		    				if(buildTheFactory(gc,unbuiltFactories.get(0),workers.get(a))==0) {
		    					pathFind(gc,unbuiltFactories.get(0).location().mapLocation(),rotationTries,workers.get(a),directions);    				
		    				//MapLocation finalMapLocation,int[] rotationTries,Unit unit,ArrayList<Direction> directions) {
		    				}
	    				} else if(unbuiltRockets.size()>0 ) {
	    					if(buildTheRocket(gc,unbuiltRockets.get(0),workers.get(a))==0) {
	    						pathFind(gc,unbuiltRockets.get(0).location().mapLocation(),rotationTries,workers.get(a),directions);
	    					} 
	    				} else {
	    	    			checkForKarbonite(gc,workers.get(a),directions);
	    	    			MapLocation possibleLocation=finalKarboniteDestination(gc,workers.get(a),karboniteLocationsEarth);
	    	    			if(!possibleLocation.equals(stupidLocation)) {
	    	    				pathFind(gc,possibleLocation,rotationTries,workers.get(a),directions);
	    	    			}
	    				}
	    			}

	    			if(builtRockets.size() > 0 && gc.planet() == Planet.Earth) {
		    			for (int a = 0; a < builtRockets.size(); a++) {
		    			MapLocation rocketLocation = builtRockets.get(a).location().mapLocation();
		    			VecUnit nearbyUnits = gc.senseNearbyUnits(rocketLocation, 1000);
		    			       if (nearbyUnits.size() == 0) 
		    			    	   nearbyUnits = gc.senseNearbyUnits(rocketLocation, 10000);
		    			       
		    			  for (int i = 0; i < nearbyUnits.size(); i++) {
		    				  if (gc.canLoad(builtRockets.get(a).id(), nearbyUnits.get(i).id())) {
		    					  gc.load(builtRockets.get(a).id(), nearbyUnits.get(i).id());
		    					  
		    				  }
		    				  else {
		    					  pathFind(gc, rocketLocation, rotationTries, nearbyUnits.get(i), directions);
		    				  }
		    			  }
		    			}
	    			}
		    			
		    			if (gc.round() == 250) {
		    				for (int i = 0; i < builtRockets.size(); i++) {
		    				
		    					MapLocation sendTo = canLaunch(gc, builtRockets.get(i));
		    				
		    					if (gc.canLaunchRocket(builtRockets.get(i).id(), sendTo)) {
		    						gc.launchRocket(builtRockets.get(i).id(), sendTo);
		    					}
		    				}
		    			}
		    			
		    	if (gc.round() > 300 && gc.planet().equals(Planet.Mars)) {
		    		for (int i = 0; i < builtRockets.size(); i++)
		    			unloadGarrison(gc, builtRockets.get(i), directions);
		    	}
		    			
    			gc.nextTurn();
    			/*
    			 * if(builtFactories.size()>builtFactoryCount) {
    			 * 	builtFactoryCount=builtFactories.size();
    			 * 	canBlueprintFactory=1;
    			 * }
    			 */
    		}
    		
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
    public static void replicate(GameController gc,Unit worker,ArrayList<Direction> directions) {
    for(int a=0;a<directions.size();a++) {
    if(gc.canReplicate(worker.id(), directions.get(a))) {
    gc.replicate(worker.id(), directions.get(a));
    }
    }
    
    }
    public static int blueprintTheFactory(GameController gc,ArrayList<Direction> directions,Unit worker) {
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
    public static int buildTheFactory(GameController gc,Unit unbuiltFactory,Unit worker) {
    if(gc.canBuild(worker.id(), unbuiltFactory.id())) {
    		gc.build(worker.id(), unbuiltFactory.id());
    		return 1;
    }
    return 0;
    }
    public static int pathFind(GameController gc,MapLocation finalMapLocation,int[] rotationTries,Unit unit,ArrayList<Direction> directions) {
    try {
    		Direction goTo=unit.location().mapLocation().directionTo(finalMapLocation);
    		for(int a=0;a<rotationTries.length;a++) {
			int index=(directions.indexOf(goTo)+a)%8;
    			if(gc.canMove(unit.id(), directions.get(index))&&gc.isMoveReady(unit.id())) {
    				gc.moveRobot(unit.id(), directions.get(index));
    				return 1;
    			}
    		}
    } catch (Exception e) {
    
    }
    //randomPathFind(gc,directions,unit);
    return 0;
    }
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
    public static MapLocation finalKarboniteDestination(GameController gc, Unit worker,ArrayList<MapLocation> karboniteLocationsEarth) {
    		long min=1000;
    		MapLocation optimalMapLocation=new MapLocation(Planet.Earth,-1,-1);
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
    public static int buildRangers(GameController gc,Unit builtFactory) {
    		if(gc.canProduceRobot(builtFactory.id(), UnitType.Ranger) ) {
    			gc.produceRobot(builtFactory.id(), UnitType.Ranger);
    			return 1;
    		}
    		return 0;   
    }
    public static void unloadGarrison(GameController gc,Unit builtFactory,ArrayList<Direction> directions) {
    		for(int d=0;d<directions.size();d++) {
    			if(gc.canUnload(builtFactory.id(), directions.get(d))) {
    				gc.unload(builtFactory.id(), directions.get(d));
    			}
    		}
    }
    public static MapLocation finalEnemyDestination(GameController gc, Unit ranger,VecUnit enemyUnits) {
		long min=1000;
		int badGuyIndex=-1;
		MapLocation optimalMapLocation=new MapLocation(Planet.Earth,-1,-1);
		for(int a=0;a<enemyUnits.size();a++) {
				long distance=ranger.location().mapLocation().distanceSquaredTo(enemyUnits.get(a).location().mapLocation());
					if(distance<min) {
						min=distance;
						badGuyIndex=a;
						optimalMapLocation=enemyUnits.get(a).location().mapLocation();
					}
		}
		if(badGuyIndex==-1) {
			return optimalMapLocation;
		}
		if(gc.canAttack(ranger.id(), enemyUnits.get(badGuyIndex).id())&&gc.isAttackReady(ranger.id())) {
			gc.attack(ranger.id(), enemyUnits.get(badGuyIndex).id());
			return optimalMapLocation;
		}
		return optimalMapLocation;
    }
 	public static int blueprintTheRocket(GameController gc, ArrayList<Direction> directions,Unit worker) {
    	  //System.out.println("Number of workers: "+workers.size());
      		  for(int b=0;b<directions.size();b++) {
      			  if(gc.canBlueprint(worker.id(), UnitType.Rocket, directions.get(b))) {
      				  gc.blueprint(worker.id(), UnitType.Rocket, directions.get(b));
      				  return 1;
      			  }
      		  }
  			  if(gc.canBlueprint(worker.id(), UnitType.Rocket, Direction.Center)) {
  				  gc.blueprint(worker.id(), UnitType.Rocket, Direction.Center);
  				  return 1;
  			  }
  	      	  return 0;
      	  }
 	public static int buildTheRocket(GameController gc,Unit unbuiltRocket,Unit worker) {
		 if(gc.canBuild(worker.id(), unbuiltRocket.id())) {
			 gc.build(worker.id(), unbuiltRocket.id());
			 return 1;
		 }
	
		 return 0;
 	}
    public static int buildHealers(GameController gc,Unit builtFactory) {
		if(gc.canProduceRobot(builtFactory.id(), UnitType.Healer) ) {
			gc.produceRobot(builtFactory.id(), UnitType.Healer);
			return 1;
		}
		return 0;   
}
    public static MapLocation canLaunch(GameController gc, Unit rocket)
 	{
 	/*	GameMap b = new GameMap();
 		PlanetMap m = b.getMars_map();
 		long height = m.getHeight();
 		long width = m.getWidth(); */
 		MapLocation land = new MapLocation(Planet.Mars, 0, 0);
 		land.setPlanet(Planet.Mars);
 		int z = 0;
 		
 		while(z == 0) {
 		{
 		int x = (int)(Math.random()* 100);
 		int y = (int)(Math.random()* 100);
 		land.setX(x);
 		land.setY(y);
 		
 		if (gc.canLaunchRocket(rocket.id(), land))
 			return land;
 		}
 		
 	}
      return null;	        	  
}
      	        	  
}
