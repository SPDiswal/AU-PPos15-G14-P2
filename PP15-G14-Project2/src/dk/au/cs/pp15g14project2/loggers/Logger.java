package dk.au.cs.pp15g14project2.loggers;

import android.location.Location;

public interface Logger
{
    void log(String tag, Location location);
}
