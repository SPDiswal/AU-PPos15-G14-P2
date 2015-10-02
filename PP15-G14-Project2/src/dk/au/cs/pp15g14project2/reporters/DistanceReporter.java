package dk.au.cs.pp15g14project2.reporters;

import android.location.*;
import android.os.Bundle;
import dk.au.cs.pp15g14project2.loggers.Logger;
import dk.au.cs.pp15g14project2.utilities.LocationPrinter;

public class DistanceReporter implements Reporter
{
    private static final String TAG = "DistanceReporter";
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    private final LocationManager locationManager;
    private final LocationListener listener;
    
    private int numberOfGpsFixes = 0;
    private int numberOfLogs = 0;
    
    public DistanceReporter(final LocationManager locationManager,
                            final Logger logger,
                            final int distanceThreshold /* metres */)
    {
        this.locationManager = locationManager;
        this.listener = new LocationListener()
        {
            private Location recentFix;
            
            public void onLocationChanged(final Location location)
            {
                numberOfGpsFixes++;
                
                if (recentFix == null || location.distanceTo(recentFix) >= distanceThreshold)
                {
                    recentFix = location;
                    numberOfLogs++;
                    logger.log(TAG, LocationPrinter.convertToString(location));
                }
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
        locationManager.requestLocationUpdates(GPS, 0, 0, listener);
    }

    @Override
    public void stopListeningForUpdates() {
        locationManager.removeUpdates(listener);
    }
    
    public String getTag()
    {
        return TAG;
    }
    
    public int getNumberOfGpsFixes()
    {
        return numberOfGpsFixes;
    }
    
    public int getNumberOfLogs()
    {
        return numberOfLogs;
    }
}
