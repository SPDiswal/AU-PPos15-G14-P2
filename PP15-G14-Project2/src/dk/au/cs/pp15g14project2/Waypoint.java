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
        
        throw new UnsupportedOperationException();
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
