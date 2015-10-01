package dk.au.cs.pp15g14project2.utilities;

import android.location.Location;
import android.text.format.Time;
import dk.au.cs.pp15g14project2.Waypoint;

public class LocationPrinter
{
    private static final int ALTITUDE_OFFSET = 39 /* metres */;
    
    public static String convertToString(Location location)
    {
        Time timestamp = new Time();
        timestamp.setToNow();
        
        return timestamp.format("%H:%M:%S") + " "
               + location.getLatitude() + " "
               + location.getLongitude() + " "
               + (location.getAltitude() - ALTITUDE_OFFSET);
    }
    
    public static String convertToString(Waypoint waypoint)
    {
        return waypoint.getTimestamp().format("%H:%M:%S") + " "
               + waypoint.getLatitude() + " "
               + waypoint.getLongitude() + " "
               + (waypoint.getAltitude());
    }
}
