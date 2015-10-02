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
    
    private static final double GRAVITY = 9.81;
    private static final int STEP_THRESHOLD = 3;
    private static final double STEP_LENGTH = 0.75;
    
    private final SensorManager sensorManager;
    private final SensorEventListener stepListener;
    
    private boolean hasStepped = false;
    private int stepCount = 0;
    
    private int numberOfGpsFixes = 0;
    private int numberOfLogs = 0;
    
    public MotionReporter(final SensorManager sensorManager,
                          final LocationManager locationManager,
                          final Logger logger,
                          final int distanceThreshold)
    {
        this.sensorManager = sensorManager;
        
        this.stepListener = new SensorEventListener()
        {
            public void onSensorChanged(SensorEvent event)
            {
                float x = event.values[0];
                float y = event.values[1];
                float z = event.values[2];
                
                double norm = Math.sqrt(x * x + y * y + z * z);
                double normWithoutGravity = norm - GRAVITY;
                
                if (normWithoutGravity > STEP_THRESHOLD && !hasStepped)
                {
                    hasStepped = true;
                    stepCount++;
                    
                    if (stepCount * STEP_LENGTH >= distanceThreshold)
                    {
                        stepCount = 0;
                        locationManager.requestSingleUpdate(GPS, new LocationListener()
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
                        }, null);
                    }
                }
                
                if (normWithoutGravity < 0)
                {
                    hasStepped = false;
                }
            }
            
            public void onAccuracyChanged(Sensor sensor, int accuracy)
            {
            }
        };
    }
    
    public void startListeningForUpdates()
    {
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(stepListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    public void stopListeningForUpdates()
    {
        sensorManager.unregisterListener(stepListener);
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
