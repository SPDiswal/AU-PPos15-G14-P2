package dk.au.cs.pp15g14project2;

import android.location.*;
import android.os.Bundle;

public class TimeReporter implements Reporter
{
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    private final int timeInterval;
    
    public TimeReporter(final int timeInterval /* seconds */)
    {
        this.timeInterval = 1000 * timeInterval;
    }
    
    public void listenForUpdates(final LocationManager locationManager, final Logger logger)
    {
        locationManager.requestLocationUpdates(GPS, timeInterval, 0, new LocationListener()
        {
            public void onLocationChanged(final Location location)
            {
                logger.log(this.getClass().getName(), location);
            }
            
            public void onStatusChanged(final String provider, final int status, final Bundle extras)
            {
            }
            
            public void onProviderEnabled(final String provider)
            {
            }
            
            public void onProviderDisabled(final String provider)
            {
            }
        });
    }
}
