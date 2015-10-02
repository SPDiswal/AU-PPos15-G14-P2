package dk.au.cs.pp15g14project2.reporters;

import android.location.*;
import android.os.*;
import android.util.Log;
import dk.au.cs.pp15g14project2.loggers.Logger;
import dk.au.cs.pp15g14project2.utilities.LocationPrinter;

public class SpeedReporter implements Reporter
{
    private static final String TAG = "SpeedReporter";
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    private final int derivedTimeInterval;
    private final LocationManager locationManager;
    private final LocationListener listener;
    
    private boolean isRunning = false;
    
    private int numberOfGpsFixes = 0;
    private int numberOfLogs = 0;
    
    public SpeedReporter(final LocationManager locationManager,
                         final Logger logger,
                         final int distanceThreshold /* metres */,
                         final int maximumSpeed /* metres per second */)
    {
        if (maximumSpeed <= 0) throw new IllegalArgumentException("maximumSpeed must be greater than 0.");
    
        this.derivedTimeInterval = (int) (1000 * Math.ceil(distanceThreshold / maximumSpeed));
        this.locationManager = locationManager;
        this.listener = new LocationListener()
        {
            public void onLocationChanged(final Location location)
            {
                numberOfGpsFixes++;
                numberOfLogs++;
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
        isRunning = true;
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable()
        {
            public void run()
            {
                locationManager.requestSingleUpdate(GPS, listener, null);
                if (isRunning) handler.postDelayed(this, derivedTimeInterval);
            }
        }, derivedTimeInterval);
    }
    
    public void stopListeningForUpdates()
    {
        isRunning = false;
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
