package dk.au.cs.pp15g14project2.utilities;

import android.location.Location;
import android.text.format.Time;

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
}
