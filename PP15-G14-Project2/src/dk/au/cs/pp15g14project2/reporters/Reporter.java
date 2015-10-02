package dk.au.cs.pp15g14project2.reporters;

public interface Reporter
{
    void startListeningForUpdates();
    
    void stopListeningForUpdates();
    
    String getTag();
    
    int getNumberOfGpsFixes();
    
    int getNumberOfLogs();
}
