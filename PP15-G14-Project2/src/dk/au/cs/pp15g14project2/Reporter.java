package dk.au.cs.pp15g14project2;

import android.location.LocationManager;

public interface Reporter
{
    void listenForUpdates(LocationManager locationManager, Logger logger);
}
