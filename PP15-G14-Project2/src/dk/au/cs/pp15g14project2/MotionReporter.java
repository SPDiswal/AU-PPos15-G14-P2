package dk.au.cs.pp15g14project2;

import android.location.*;
import android.os.Bundle;

public class MotionReporter implements Reporter
{
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    public void listenForUpdates(final LocationManager locationManager, final Logger logger)
    {
        locationManager.requestLocationUpdates(GPS, 0, 0, new LocationListener()
        {
            public void onLocationChanged(final Location location)
            {
                logger.log(this.getClass().getName(), location);
                
                locationManager.removeUpdates(this);
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
