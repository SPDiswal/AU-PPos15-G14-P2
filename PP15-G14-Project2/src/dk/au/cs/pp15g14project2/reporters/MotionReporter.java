package dk.au.cs.pp15g14project2.reporters;

import android.hardware.*;
import android.location.*;
import android.os.Bundle;
import dk.au.cs.pp15g14project2.loggers.Logger;
import dk.au.cs.pp15g14project2.utilities.LocationPrinter;

public class MotionReporter implements Reporter
{
    private static final String TAG = "MotionReporter";
    private static final String GPS = LocationManager.GPS_PROVIDER;
    
    private final SensorManager sensorManager;
    private final int distanceInterval;
    
    public MotionReporter(SensorManager sensorManager, int distanceInterval)
    {
        this.sensorManager = sensorManager;
        this.distanceInterval = distanceInterval;
    }
    
    public void listenForUpdates(final LocationManager locationManager, final Logger logger)
    {
        // TODO Get speed estimate (e.g. by retrieving three GPS fixes initially and using location.getSpeed()).
        
        Sensor linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    
        // TODO Listen for motion changes with linear acceleration and derive acceleration (m/s^2).
        
        sensorManager.registerListener(new SensorEventListener()
        {
            public void onSensorChanged(SensorEvent event)
            {
                throw new UnsupportedOperationException();
            }
            
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {
                throw new UnsupportedOperationException();
            }
        }, linearAccelerationSensor, SensorManager.SENSOR_DELAY_NORMAL);
        
        // TODO Derive 'optimal' time and distance interval from speed and acceleration.
        // TODO Listen for position changes until deceleration.
        
        locationManager.requestLocationUpdates(GPS, 0, 0, new LocationListener()
        {
            public void onLocationChanged(final Location location)
            {
                logger.log(TAG, LocationPrinter.convertToString(location));
                
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
