package dk.au.cs.pp15g14project2;

import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

public class PositioningActivity extends Activity
{
    private Reporter reporter;
    private LocationManager locationManager;
    
    private SensorManager sensorManager;
    private Sensor linearAccelerationSensor;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        
        sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        linearAccelerationSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        
        setContentView(R.layout.main);
    }
    
    public void onChoosingReporter(View view)
    {
        boolean isChecked = ((RadioButton) view).isChecked();
        
        switch (view.getId())
        {
            case R.id.timeReporter:
                if (isChecked)
                {
                    // TODO Allow variable time interval.
                    reporter = new TimeReporter(5);
                    Toast.makeText(this, "Using time reporting", Toast.LENGTH_LONG).show();
                }
                break;
            
            case R.id.distanceReporter:
                if (isChecked)
                {
                    // TODO Allow variable distance interval.
                    reporter = new DistanceReporter(10);
                    Toast.makeText(this, "Using distance reporting", Toast.LENGTH_LONG).show();
                }
                break;
            
            case R.id.speedReporter:
                if (isChecked)
                {
                    // TODO Allow variable distance interval.
                    reporter = new SpeedReporter(10, 2);
                    Toast.makeText(this, "Using speed reporting", Toast.LENGTH_LONG).show();
                }
                break;
            
            case R.id.motionReporter:
                if (isChecked)
                {
                    reporter = new MotionReporter();
                    Toast.makeText(this, "Using motion reporting", Toast.LENGTH_LONG).show();
                }
                break;
        }
        
        reporter.listenForUpdates(locationManager, new ConsoleLogger());
    }
}
