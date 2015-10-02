var express = require("express");
var router = express.Router({mergeParams: true});
var file = require("fs");

var logFilenamePrefix = __dirname + "/../public/pp15-g14-project2-";
var logFilenameSuffix = ".log";

router.get("/", function (request, response, next)
{
    var reporter = request.params.reporter;
    var logFilename = logFilenamePrefix + reporter + logFilenameSuffix;
    
    //console.log("-> Incoming GET request: " + JSON.stringify(request.params));
    
    var output;
    
    file.exists(logFilename, function (exists)
    {
        if (exists)
        {
            file.readFile(logFilename, "utf8", function (error, data)
            {
                if (error)
                {
                    throw error;
                }
                else
                {
                    var lines = data.split("\n");
                    
                    if (endsWith(reporter, "-Stats"))
                    {
                        output = lines[0];
                        response.header("Content-Type", "application/json").send(output);
                    }
                    else
                    {
                        output = kmlStart();
                        
                        for (var i in lines)
                        {
                            if (lines[i] !== "")
                            {
                                output += kmlElement(JSON.parse(lines[i]));
                            }
                        }
                        
                        output += kmlEnd();
                        response.header("Content-Type", "text/xml").send(output);
                    }
                }
            });
        }
        else
        {
            response.sendStatus(404);
        }
    });
});

router.post("/", function (request, response, next)
{
    var reporter = request.params.reporter;
    var output = {};
    
    if (!reporter)
    {
        response.sendStatus(400);
        return;
    }
    
    if (endsWith(reporter, "-Stats"))
    {
        var timespan = request.body["timespan"];
        var gpsFixes = request.body["gps-fixes"];
        var gpsFixesPerSecond = request.body["gps-fixes-per-second"];
        var logs = request.body["logs"];
        var logsPerSecond = request.body["logs-per-second"];
        
        if (!timespan || !gpsFixes || !gpsFixesPerSecond || !logs || !logsPerSecond)
        {
            response.sendStatus(400);
            return;
        }
        
        output["timespan"] = timespan;
        output["gps-fixes"] = gpsFixes;
        output["gps-fixes-per-second"] = gpsFixesPerSecond;
        output["logs"] = logs;
        output["logs-per-second"] = logsPerSecond;
    }
    else
    {
        var latitude = request.body["latitude"];
        var longitude = request.body["longitude"];
        var altitude = request.body["altitude"];
        var time = request.body["time"];
        
        if (!latitude || !longitude || !altitude || !time)
        {
            response.sendStatus(400);
            return;
        }
        
        output["latitude"] = latitude;
        output["longitude"] = longitude;
        output["altitude"] = altitude;
        output["time"] = time;
    }
    
    //console.log("-> Incoming POST request (" + reporter + "): " + JSON.stringify(request.body));
    
    var logFilename = logFilenamePrefix + reporter + logFilenameSuffix;
    
    file.appendFile(logFilename, JSON.stringify(output) + "\n", function ()
    {
        response.sendStatus(204);
    });
});

function kmlStart()
{
    return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
           "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n" +
           "\t<Document>\n";
}

function kmlElement(location)
{
    return "\t\t<Placemark>\n" +
           "\t\t<name>Simple placemark</name>\n" + // TODO
           "\t\t<description>An interesting location</description>\n" + // TODO
           "\t\t<Point>\n" +
           "\t\t\t<coordinates>" +
           location.longitude + "," + location.latitude + "," + location.altitude +
           "</coordinates>\n" +
           "\t\t</Point>\n" +
           "\t\t<TimeStamp>\n" +
           "\t\t\t<when>" +
           location.time +
           "</when>\n" +
           "\t\t</TimeStamp>\n" +
           "\t\t</Placemark>\n";
}

function kmlEnd()
{
    return "\t</Document>\n" +
           "</kml>";
}

function endsWith(str, suffix)
{
    return str.indexOf(suffix, str.length - suffix.length) !== -1;
}

module.exports = router;
