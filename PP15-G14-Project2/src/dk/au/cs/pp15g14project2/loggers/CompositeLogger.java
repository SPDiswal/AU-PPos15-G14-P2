package dk.au.cs.pp15g14project2.loggers;

import android.location.Location;

import java.util.*;

public class CompositeLogger implements Logger
{
    private Collection<Logger> loggers = new ArrayList<Logger>();
    
    public void add(final Logger logger)
    {
        loggers.add(logger);
    }
    
    public void log(final String tag, final Location location)
    {
        for (Logger logger : loggers)
        {
            logger.log(tag, location);
        }
    }
}
