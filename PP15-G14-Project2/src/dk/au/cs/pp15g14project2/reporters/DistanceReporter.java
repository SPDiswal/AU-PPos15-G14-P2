package dk.au.cs.pp15g14project2.reporters;

import android.location.*;
import android.os.Bundle;
import dk.au.cs.pp15g14project2.loggers.Logger;
import dk.au.cs.pp15g14project2.utilities.LocationPrinter;

public class DistanceReporter implements Reporter
{
    private static final String TAG = "DistanceReporter";
    private static final int FIXED_TIME_INTERVAL = 5000;
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    private final int distanceInterval;
    
    public DistanceReporter(final int distanceInterval /* metres */)
    {
        this.distanceInterval = distanceInterval;
    }
    
    public void listenForUpdates(final LocationManager locationManager, final Logger logger)
    {
        locationManager.requestLocationUpdates(GPS, FIXED_TIME_INTERVAL, distanceInterval, new LocationListener()
        {
            public void onLocationChanged(final Location location)
            {
                logger.log(TAG, LocationPrinter.convertToString(location));
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
