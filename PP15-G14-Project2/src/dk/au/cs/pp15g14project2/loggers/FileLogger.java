package dk.au.cs.pp15g14project2.loggers;

import android.os.Environment;
import android.util.Log;

import java.io.*;

public class FileLogger implements Logger
{
    private static final String OUTPUT_FILENAME_PREFIX = "pp15-g14-project2-";
    private static final String OUTPUT_FILENAME_SUFFIX = ".log";
    
    public void log(final String tag, final String location)
    {
        writeToFile(tag, location);
    }
    
    private boolean isExternalStorageWritable()
    {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }
    
    private void writeToFile(final String tag, final String output)
    {
        String outputFilename = OUTPUT_FILENAME_PREFIX + tag + OUTPUT_FILENAME_SUFFIX;
        
        if (isExternalStorageWritable())
        {
            try
            {
                File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                File file = new File(path, outputFilename);
                
                FileWriter writer = new FileWriter(file, true);
                writer.write(output + "\n");
                writer.close();
            }
            catch (IOException e)
            {
                Log.w(tag, "Cannot write to " + outputFilename, e);
            }
        }
        else
            Log.w(tag, "Cannot write to external storage.");
    }
}
