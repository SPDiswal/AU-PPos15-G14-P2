package dk.au.cs.pp15g14project2.loggers;

import android.location.Location;
import android.util.Log;

public class ConsoleLogger implements Logger
{
    public void log(String tag, Location location)
    {
        Log.d(tag, "> " + location);
    }
}
