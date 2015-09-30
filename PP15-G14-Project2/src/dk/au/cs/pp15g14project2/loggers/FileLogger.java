package dk.au.cs.pp15g14project2.loggers;

import android.location.Location;
import android.os.Environment;
import android.util.Log;

import java.io.*;

public class FileLogger implements Logger
{
    private static final String OUTPUT_FILENAME = "pp15-g14-project2.log";
    private final File file;
    
    public FileLogger()
    {
        File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        file = new File(path, OUTPUT_FILENAME);
    }
    
    public boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    
    public void writeToFile(final String tag, final String output)
    {
        if (isExternalStorageWritable())
        {
            try
            {
                FileWriter writer = new FileWriter(file, true);
                writer.write(tag + " :: " + output + "\n");
                writer.close();
            }
            catch (IOException e)
            {
                Log.w(tag, "Cannot write to " + file, e);
            }
        }
        else
            Log.w(tag, "Cannot write to external storage.");
    }
    
    public void log(final String tag, final Location location)
    {
        writeToFile(tag, location.toString());
    }
}
