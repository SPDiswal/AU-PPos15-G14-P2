package dk.au.cs.pp15g14project2.reporters;

import android.location.*;
import android.os.Bundle;
import android.util.Log;
import dk.au.cs.pp15g14project2.loggers.Logger;
import dk.au.cs.pp15g14project2.utilities.LocationPrinter;

public class DistanceReporter implements Reporter
{
    private static final String TAG = "DistanceReporter";
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    private final LocationManager locationManager;
    private final LocationListener listener;

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
                if (recentFix == null || location.distanceTo(recentFix) >= distanceThreshold)
                {
                    if (recentFix != null)
                        Log.d(TAG, "" + location.distanceTo(recentFix));
                    
                    recentFix = location;
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
}
