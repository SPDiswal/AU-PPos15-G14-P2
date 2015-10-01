package dk.au.cs.pp15g14project2;

import android.text.format.Time;

import java.util.List;

public class Waypoint
{
    private String name;
    private double latitude;
    private double longitude;
    private double altitude;
    private Time timestamp;
    
    public Waypoint(String name, double latitude, double longitude, double altitude)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
    }
    
    public Waypoint(String name, double latitude, double longitude, double altitude, Time timestamp)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.timestamp = timestamp;
    }
    
    public static Waypoint interpolate(List<Waypoint> waypoints, Time time)
    {
        if(waypoints.get(0).getTimestamp().after(time)){
            return waypoints.get(0);
        }
        for (int i = 1; i < waypoints.size(); i++) {
            Waypoint secondWaypoint = waypoints.get(i);
            if(secondWaypoint.getTimestamp().after(time)){
                Waypoint firstWaypoint = waypoints.get(i - 1);
                double timeDifference1 = secondWaypoint.getTimestamp().toMillis(false) - firstWaypoint.getTimestamp().toMillis(false);
                double timeDifference2 = time.toMillis(false) - firstWaypoint.getTimestamp().toMillis(false);
                double differencePercentage = timeDifference2/timeDifference1;
                double latitudeDifference = firstWaypoint.getLatitude() - secondWaypoint.getLatitude();
                double longitudeDifference = firstWaypoint.getLongitude() - secondWaypoint.getLongitude();
                double altitudeDifference = firstWaypoint.getAltitude() - secondWaypoint.getAltitude();
                return new Waypoint("Interpolated",
                                        firstWaypoint.getLatitude() - latitudeDifference*differencePercentage,
                                        firstWaypoint.getLongitude() - longitudeDifference*differencePercentage,
                                        firstWaypoint.getAltitude() - altitudeDifference*differencePercentage, time);
            }
        }
        return waypoints.get(waypoints.size()-1);
    }
    
    public String getName()
    {
        return name;
    }
    
    public double getLatitude()
    {
        return latitude;
    }
    
    public double getLongitude()
    {
        return longitude;
    }
    
    public double getAltitude()
    {
        return altitude;
    }
    
    public Time getTimestamp()
    {
        return timestamp;
    }
    
    public void setTimestamp(Time timestamp)
    {
        this.timestamp = timestamp;
    }
    
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Waypoint waypoint = (Waypoint) o;
        
        return Double.compare(waypoint.latitude, latitude) == 0
               && Double.compare(waypoint.longitude, longitude) == 0
               && Double.compare(waypoint.altitude, altitude) == 0
               && !(name != null ? !name.equals(waypoint.name) : waypoint.name != null)
               && !(timestamp != null ? !timestamp.equals(waypoint.timestamp) : waypoint.timestamp != null);
    }
    
    public int hashCode()
    {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        temp = Double.doubleToLongBits(latitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(altitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        return result;
    }
    
    public String toString()
    {
        if (timestamp != null)
        {
            return "[" + timestamp.format("%H:%M:%S") + "] " 
                   + name + " (" + latitude + ", " + longitude + ", " + altitude + ")";
        }
        else
        { return name + " (" + latitude + ", " + longitude + ", " + altitude + ")"; }
    }
}
