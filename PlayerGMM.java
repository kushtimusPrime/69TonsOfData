
public class Player
{
//returns location for rocket to land on mars
public static MapLocation rocketlandinglocationMars()
{
	PlanetMap m = getMars_map();
	long height = m.getHeight();
	long width = m.getWidth();
	MapLocation land = new MapLocation();
	land.setPlanet(Planet.Mars);
	while(!m.onMap(land)&&!m.isPassableTerrainAt(land))
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
	PlanetMap e = getEarth_map();
	long height = e.getHeight();
	long width = e.getWidth();
	MapLocation a = new MapLocation();
	a.setPlanet(Planet.Earth);  
	int x = 0;
	int y = 0;
	while(x<=height)
	{
		while(y<=width)
		{
			a.setX(x);
			a.setY(x)
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
	PlanetMap e = getEarth_map();
	long height = e.getHeight();
	long width = e.getWidth();
	MapLocation a = new MapLocation();
	a.setPlanet(Planet.Earth);  
	int x = 0;
	int y = 0;
	while(x<=height)
	{
		while(y<=width)
		{
			a.setX(x);
			a.setY(x)
			if(!e.isPassableTerrainAt(a))
			{
				pass.add(a);
			}
			y++;
		}
		x++;
	}
	return pass;
}



}
