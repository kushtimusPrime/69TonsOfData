import bc.*;
import java.util.ArrayList;

public class Player {
	public static void main(String[] args) throws IllegalArgumentException {

		 
        GameController gc = new GameController();
        
        // These will help keep track of robots and the such
        ArrayList<Unit> units = new ArrayList<Unit>();
        ArrayList<Unit> factories=new ArrayList<Unit>();
	ArrayList<Unit> knights=new ArrayList<Unit>();
	ArrayList<Unit> healers = new ArrayList<Unit>();
	ArrayList<Direction> directions=new ArrayList<Direction>();
	units.clear();
	factories.clear();
	knights.clear();
	healers.clear();
	int[] rotationTries= {0,-1,1,-2,2};
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
        	   if (gc.myUnits().get(i).unitType().equals(UnitType.Factory)) {
        		   factories.add(gc.myUnits().get(i));
        	   }
		   	        	   if (gc.myUnits().get(i).unitType().equals(UnitType.Knight)) {
        		   knights.add(gc.myUnits().get(i));
        	   }
        	   		units.add(gc.myUnits().get(i));
        	   		
        	   // Later, we will need to distinguish between robots on earth and mars
        		
        	   }
           for (int i = 0; i < gc.myUnits().size(); i++)
     	   {
            if (gc.myUnits().get(i).unitType().equals(UnitType.Knight))
            {
             knightAttack(gc.myUnits().get(i), gc);
             
            }
     	   }
            gc.nextTurn();
                

        }
           
      /*     
            Only occurs in the first round
            if (gc.round() == 1 && gc.planet() == Planet.Earth) {
        	   for (int i = 0; i < gc.myUnits().size(); i++)
        	   {
        	    blueprintFactory(i, gc);
        	   }
	   }
		Unit fighter=searchForAttack(gc,knights);
	    try {
		if(!fighter.equals(null)) {
			int id=fighter.id();
			MapLocation locationOF=fighter.location().mapLocation();
			findPath(gc, locationOF, fighter, rotationTries, directions);
			KnightAttack( gc, knights,id);
			
		}
		} catch (Exception e ) {
			
		}
        checkToBuild(gc);
        	checkForKarbonite(gc);
        	checkToReplicate(gc);
		buildHealer(gc, factories, healers);
		buildKnights(gc,factories);
			
           
           */
  
           }
        


	//method returns an array of enemy units in vision range
	public static VecUnit AquireTargets( Unit a, GameController gc)
	{
		// gc is game controller
		Team enemy = null;
		MapLocation loco = a.location().mapLocation();
		if(a.team()==Team.Blue)
		{
			enemy = Team.Red;
		}
		if(a.team()==Team.Red)
		{
			enemy = Team.Blue;
		}
		VecUnit targets = gc.senseNearbyUnitsByTeam(loco, a.visionRange(), enemy);
		
		return targets;
		
		
	}
	
	// returns an array list of unit ids in range with factories and rockets at end
	public static ArrayList<Integer> targetsID(Unit a, GameController gc)
	{
ArrayList<Integer> robotID = new ArrayList<Integer>();
VecUnit targets = AquireTargets(a, gc);

for(long x = 0; x<targets.size(); x++)
{
	Unit b = targets.get(x);
	if(b.unitType()!=UnitType.Factory && b.unitType()!=UnitType.Rocket)
	{
		robotID.add(b.id(), 0);
	}
	else
	{
		robotID.add(b.id());
	}
	
}
return robotID;
	}
	// has knights attack starting with enemy units who have crit damage
public static void knightAttack(Unit a, GameController gc)
{
	ArrayList<Integer> nowID = targetsID(a, gc);
	VecUnit now = AquireTargets(a, gc);
	for(int x =0; x<now.size(); x++)
	{
		if(now.get(x).health()<= 60)
		{
			if(gc.isJavelinReady(a.id()))
			{
				gc.javelin(a.id(),now.get(x).id());
			}
			else
			{
				if(gc.canAttack(a.id(),now.get(x).id()))
				{
					gc.attack(a.id(),now.get(x).id());
				}
			}
		}
	}
	for(int x =0; x<now.size(); x++)
	{
			if(gc.isJavelinReady(a.id()))
			{
				gc.javelin(a.id(),now.get(x).id());
			}
			else
			{
				if(gc.canAttack(a.id(),now.get(x).id()))
				{
					gc.attack(a.id(),now.get(x).id());
				}
			}
	}
	
}

//returns Vec unit of friendlys in healers range
public static VecUnit AquireHeals( Unit a, GameController gc)
{
	// gc is game controller
	Team us = null;
	MapLocation loco = a.location().mapLocation();
	if(a.team()==Team.Blue)
	{
		us = Team.Blue;
	}
	if(a.team()==Team.Red)
	{
		us = Team.Red;
	}
	VecUnit targets = gc.senseNearbyUnitsByTeam(loco, 30, us);
	
	return targets;
	
}

//healer will heal unit in range missing the most amount of health
public static void healerDoesHeal(Unit a, GameController gc)
{
	VecUnit couldheal = AquireHeals(a, gc);
	int spot = -1;
	long healthdifference = 0;
	for(int x = 0; x<couldheal.size(); x++)
	{
		Unit b = couldheal.get(x);
		if(b.maxHealth()-b.health()>healthdifference)
		{
			healthdifference = b.maxHealth()-b.health();
			spot = x;
		}
	}
	if(gc.canHeal(a.id(),couldheal.get(spot).id()))
	{
		gc.heal(a.id(),couldheal.get(spot).id());
	}
}
// ranger attack, first with crit health
public static void rangerAttack(Unit a, GameController gc)
{
	ArrayList<Integer> nowID = targetsID(a, gc);
	VecUnit now = AquireTargets(a, gc);
	for(int x =0; x<now.size(); x++)
	{
		if(now.get(x).health()<= 40)
		{
				if(gc.canAttack(a.id(),now.get(x).id()))
				{
					gc.attack(a.id(),now.get(x).id());
				}
			
		}
	}
	for(int x =0; x<now.size(); x++)
	{
		
				if(gc.canAttack(a.id(),now.get(x).id()))
				{
					gc.attack(a.id(),now.get(x).id());
				}
			
	}
	
}


}


