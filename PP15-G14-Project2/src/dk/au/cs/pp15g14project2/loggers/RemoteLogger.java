package dk.au.cs.pp15g14project2.loggers;

import android.os.AsyncTask;
import android.util.Log;
import org.json.*;

import java.io.*;
import java.net.*;

public class RemoteLogger implements Logger
{
    private static final String REMOTE_PATH_PREFIX = "http://178.62.198.99:3000/";
    
    public void log(String tag, String message)
    {
        new RemoteTask().execute(tag, message);
    }
    
    private class RemoteTask extends AsyncTask<String, Void, Void>
    {
        public Void doInBackground(String... input)
        {
            String tag = input[0];
            String message = input[1];
            
            String[] tokens = message.split(" ");
            BufferedWriter writer = null;
            
            try
            {
                JSONObject json = new JSONObject().put("latitude", tokens[1])
                                                  .put("longitude", tokens[2])
                                                  .put("altitude", tokens[3])
                                                  .put("time", tokens[0]);
                
                String remotePath = REMOTE_PATH_PREFIX + tag + "/";
                URL url = new URL(remotePath);
                
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                
                writer = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream()));
                writer.write(json.toString());
                writer.flush();
                
                int statusCode = connection.getResponseCode();
                
                if (statusCode != 204) Log.w(tag, "Remote server responded with status code " + statusCode);
            }
            catch (JSONException e)
            {
                Log.w(tag, "Cannot post to remote server.");
            }
            catch (IOException e)
            {
                Log.w(tag, "Cannot post to remote server.");
            }
            finally
            {
                try
                {
                    if (writer != null) writer.close();
                }
                catch (IOException e)
                {
                    Log.w(tag, "Cannot close connection to remote server.");
                }
            }
            
            return null;
        }
    }
}
