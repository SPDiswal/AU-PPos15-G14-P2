package dk.au.cs.pp15g14project2.loggers;

import android.text.format.Time;
import dk.au.cs.pp15g14project2.Waypoint;

import java.util.*;

public class InMemoryProxyLogger implements Logger
{
    private Map<String, List<Waypoint>> loggedWaypoints = new HashMap<String, List<Waypoint>>();
    
    public void log(String tag, String message)
    {
        String[] tokens = message.split(" ");
    
        double latitude = Double.parseDouble(tokens[1]);
        double longitude = Double.parseDouble(tokens[2]);
        double altitude = Double.parseDouble(tokens[3]);
    
        String[] timestamp = tokens[0].split(":");
        
        Time time = new Time();
        time.setToNow();
    
        int hours = Integer.parseInt(timestamp[0]);
        int minutes = Integer.parseInt(timestamp[1]);
        int seconds = Integer.parseInt(timestamp[2]);
        
        time.hour = hours;
        time.minute = minutes;
        time.second = seconds;
        
        if (!loggedWaypoints.containsKey(tag))
            loggedWaypoints.put(tag, new ArrayList<Waypoint>());
        
        loggedWaypoints.get(tag).add(new Waypoint("Measured", latitude, longitude, altitude, time));
    }
    
    public List<Waypoint> getLoggedWaypoints(String tag)
    {
        return loggedWaypoints.get(tag);
    }
}
