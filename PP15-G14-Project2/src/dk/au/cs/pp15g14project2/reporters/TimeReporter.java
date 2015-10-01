package dk.au.cs.pp15g14project2.reporters;

import android.location.*;
import android.os.*;
import android.util.Log;
import dk.au.cs.pp15g14project2.loggers.Logger;
import dk.au.cs.pp15g14project2.utilities.LocationPrinter;

public class TimeReporter implements Reporter
{
    private static final String TAG = "TimeReporter";
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    private final int timeInterval;
    private final LocationManager locationManager;
    private final LocationListener listener;
    
    private boolean isRunning = false;
    
    public TimeReporter(final LocationManager locationManager,
                        final Logger logger,
                        final int timeInterval /* seconds */)
    {
        this.timeInterval = 1000 * timeInterval;
        this.locationManager = locationManager;
        this.listener = new LocationListener()
        {
            public void onLocationChanged(final Location location)
            {
                if (isRunning)
                {
                    locationManager.removeUpdates(this);
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
        isRunning = true;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                if (isRunning)
                {
                    locationManager.requestLocationUpdates(GPS, 0, 0, listener);
                    handler.postDelayed(this, timeInterval);
                }
            }
        }, timeInterval);
    
        Log.e(TAG, "" + timeInterval);
    }
    
    public void stopListeningForUpdates()
    {
        isRunning = false;
    }
    
    public String getTag()
    {
        return TAG;
    }
}
