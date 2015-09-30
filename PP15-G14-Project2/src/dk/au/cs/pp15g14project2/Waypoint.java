package dk.au.cs.pp15g14project2;

public class Waypoint
{
    private String name;
    private double latitude;
    private double longitude;
    
    public Waypoint(String name, double latitude, double longitude)
    {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    public double getLatitude()
    {
        return latitude;
    }
    
    public double getLongitude()
    {
        return longitude;
    }
    
    public String getName()
    {
        return name;
    }
    
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
    
        Waypoint waypoint = (Waypoint) o;
        return Double.compare(waypoint.latitude, latitude) == 0 && Double.compare(waypoint.longitude, longitude) == 0;
    }
    
    public int hashCode()
    {
        int result;
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
    
    public String toString()
    {
        return name + " (" + latitude + ", " + longitude + ")";
    }
}
