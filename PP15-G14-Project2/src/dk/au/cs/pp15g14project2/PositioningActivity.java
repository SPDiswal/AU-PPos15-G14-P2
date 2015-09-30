package dk.au.cs.pp15g14project2;

import android.app.Activity;
import android.content.Context;
import android.hardware.*;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import dk.au.cs.pp15g14project2.loggers.*;
import dk.au.cs.pp15g14project2.reporters.*;

public class PositioningActivity extends Activity
{
    private Reporter reporter;
    private LocationManager locationManager;
    private SensorManager sensorManager;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        this.sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        
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
                    // TODO Allow variable distance interval.
                    reporter = new MotionReporter(sensorManager, 10);
                    Toast.makeText(this, "Using motion reporting", Toast.LENGTH_LONG).show();
                }
                break;
        }
        
        CompositeLogger logger = new CompositeLogger();
        logger.add(new ConsoleLogger());
        logger.add(new FileLogger());
        
        reporter.listenForUpdates(locationManager, logger);
    }
}
