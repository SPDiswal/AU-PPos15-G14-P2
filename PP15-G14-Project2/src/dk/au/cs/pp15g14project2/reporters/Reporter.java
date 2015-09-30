package dk.au.cs.pp15g14project2.reporters;

import android.location.LocationManager;
import dk.au.cs.pp15g14project2.loggers.Logger;

public interface Reporter
{
    void listenForUpdates(LocationManager locationManager, Logger logger);
}
