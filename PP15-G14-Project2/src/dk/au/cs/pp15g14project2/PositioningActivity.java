package dk.au.cs.pp15g14project2;

import android.app.Activity;
import android.content.Context;
import android.hardware.SensorManager;
import android.location.LocationManager;
import android.os.*;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.*;
import dk.au.cs.pp15g14project2.loggers.*;
import dk.au.cs.pp15g14project2.reporters.*;
import org.json.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class PositioningActivity extends Activity
{
    private Reporter reporter;
    private LocationManager locationManager;
    private SensorManager sensorManager;
    private CompositeLogger logger;
    
    private Queue<Waypoint> waypoints;
    private List<Waypoint> finishedWaypoints;
    
    private static final String WAYPOINTS_PATH = "http://178.62.198.99:3000/waypoints.json";
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        this.locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        this.sensorManager = (SensorManager) this.getSystemService(Context.SENSOR_SERVICE);
        
        this.logger = new CompositeLogger();
        this.logger.add(new ConsoleLogger());
        this.logger.add(new FileLogger());
        this.logger.add(new RemoteLogger());
        this.logger.add(new InMemoryProxyLogger());
        
        new WaypointTask().execute();
        
        setContentView(R.layout.main);
    }
    
    public void onChoosingReporter(View view)
    {
        if (reporter != null) reporter.stopListeningForUpdates();
        boolean isChecked = ((RadioButton) view).isChecked();
        
        switch (view.getId())
        {
            case R.id.timeReporter:
                if (isChecked)
                {
                    // TODO Allow variable time interval.
                    reporter = new TimeReporter(locationManager, logger, 5);
                    Toast.makeText(this, "Using time reporting", Toast.LENGTH_LONG).show();
                }
                break;
            
            case R.id.distanceReporter:
                if (isChecked)
                {
                    // TODO Allow variable distance interval.
                    reporter = new DistanceReporter(locationManager, logger, 10);
                    Toast.makeText(this, "Using distance reporting", Toast.LENGTH_LONG).show();
                }
                break;
            
            case R.id.speedReporter:
                if (isChecked)
                {
                    // TODO Allow variable distance interval.
                    reporter = new SpeedReporter(locationManager, logger, 10, 2);
                    Toast.makeText(this, "Using speed reporting", Toast.LENGTH_LONG).show();
                }
                break;
            
            case R.id.motionReporter:
                if (isChecked)
                {
                    // TODO Allow variable distance interval.
                    reporter = new MotionReporter(sensorManager, locationManager, logger, 10);
                    Toast.makeText(this, "Using motion reporting", Toast.LENGTH_LONG).show();
                }
                break;
        }
        
        if (reporter != null) reporter.startListeningForUpdates();
    }
    
    private class WaypointTask extends AsyncTask<Void, Void, Queue<Waypoint>>
    {
        private static final String TAG = "WaypointTask";
        
        public Queue<Waypoint> doInBackground(Void... input)
        {
            BufferedReader reader = null;
            Queue<Waypoint> waypoints = new LinkedList<Waypoint>();
            
            try
            {
                URL url = new URL(WAYPOINTS_PATH);
                
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                
                int statusCode = connection.getResponseCode();
                
                if (statusCode != 200)
                {
                    Log.w(TAG, "Remote server responded with status code " + statusCode);
                }
                else
                {
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    
                    while ((line = reader.readLine()) != null)
                    {
                        builder.append(line).append("\n");
                    }
                    
                    JSONArray jsonWaypoints = new JSONArray(builder.toString());
                    
                    for (int i = 0; i < jsonWaypoints.length(); i++)
                    {
                        JSONObject jsonWaypoint = jsonWaypoints.getJSONObject(i);
                        String name = jsonWaypoint.getString("name");
                        double latitude = jsonWaypoint.getDouble("latitude");
                        double longitude = jsonWaypoint.getDouble("longitude");
                        double altitude = jsonWaypoint.getDouble("altitude");
                        
                        if (i > 0 && i < jsonWaypoints.length() - 1)
                        {
                            waypoints.add(new Waypoint(name + " (ankomst)", latitude, longitude, altitude));
                            waypoints.add(new Waypoint(name + " (afgang)", latitude, longitude, altitude));
                        }
                        else if (i == 0)
                        {
                            waypoints.add(new Waypoint(name + " (afgang)", latitude, longitude, altitude));
                        }
                        else
                        {
                            waypoints.add(new Waypoint(name + " (ankomst)", latitude, longitude, altitude));
                        }
                    }
                }
            }
            catch (IOException e)
            {
                Log.w(TAG, "Cannot get waypoints from remote server.");
            }
            catch (JSONException e)
            {
                Log.w(TAG, "Cannot get waypoints from remote server.");
            }
            finally
            {
                try
                {
                    if (reader != null) reader.close();
                }
                catch (IOException e)
                {
                    Log.w(TAG, "Cannot close connection to remote server.");
                }
            }
            
            return waypoints;
        }
        
        public void onPostExecute(Queue<Waypoint> waypoints)
        {
            updateWaypoints(waypoints);
        }
    }
    
    public void updateWaypoints(Queue<Waypoint> waypoints)
    {
        this.waypoints = waypoints;
        this.finishedWaypoints = new ArrayList<Waypoint>();
        
        TextView current = (TextView) findViewById(R.id.mostRecent);
        current.setText("Loaded - not started yet");
        
        TextView next = (TextView) findViewById(R.id.next);
        next.setText(waypoints.peek().getName());
    }
    
    public void onClickingForNextWaypoint(View view)
    {
        Time timestamp = new Time();
        timestamp.setToNow();
        
        if (!waypoints.isEmpty())
        {
            waypoints.peek().setTimestamp(timestamp);
            finishedWaypoints.add(waypoints.peek());
    
            TextView mostRecent = (TextView) findViewById(R.id.mostRecent);
            mostRecent.setText(waypoints.poll().getName());
            
            if (!waypoints.isEmpty())
            {
                TextView next = (TextView) findViewById(R.id.next);
                next.setText(waypoints.peek().getName());
            }
            else
            {
                TextView next = (TextView) findViewById(R.id.next);
                next.setText("Done");
                
                
            }
        }
    }
}
