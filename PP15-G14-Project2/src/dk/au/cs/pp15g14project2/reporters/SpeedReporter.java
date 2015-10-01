package dk.au.cs.pp15g14project2.reporters;

import android.location.*;
import android.os.Bundle;
import dk.au.cs.pp15g14project2.loggers.Logger;
import dk.au.cs.pp15g14project2.utilities.LocationPrinter;

public class SpeedReporter implements Reporter
{
    private static final String TAG = "SpeedReporter";
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    private final int distanceInterval;
    private final int derivedTimeInterval;
    private final LocationManager locationManager;
    private final LocationListener listener;
    
    public SpeedReporter(final LocationManager locationManager,
                         final Logger logger,
                         final int distanceInterval /* metres */,
                         final int maximumSpeed /* metres per second */)
    {
        if (maximumSpeed <= 0) throw new IllegalArgumentException("maximumSpeed must be greater than 0.");
    
        this.distanceInterval = distanceInterval;
        this.derivedTimeInterval = (int) (1000 * Math.ceil(distanceInterval / maximumSpeed));
        this.locationManager = locationManager;
        this.listener = new LocationListener()
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
        };
    }
    
    public void startListeningForUpdates()
    {
        locationManager.requestLocationUpdates(GPS, derivedTimeInterval, distanceInterval, listener);
    }
    
    public void stopListeningForUpdates()
    {
        locationManager.removeUpdates(listener);
    }
}
