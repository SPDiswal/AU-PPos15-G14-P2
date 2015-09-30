var express = require("express");
var router = express.Router();
var file = require("fs");

var logFilename = __dirname + "/../public/pp15-g14-project2.log";

router.get("/", function (request, response, next)
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
            var output = kmlStart();
            
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
    });
});

router.post("/", function (request, response, next)
{
    var latitude = request.body.latitude;
    var longitude = request.body.longitude;
    var altitude = request.body.altitude;
    var time = request.body.time;
    
    if (!latitude || !longitude || !altitude || !time)
    {
        response.sendStatus(400);
    }
    else
    {
        var output = {
            latitude:  latitude,
            longitude: longitude,
            altitude:  altitude,
            time:      time
        };
        
        file.appendFile(logFilename, JSON.stringify(output) + "\n", function ()
        {
            console.log("Wrote to " + __dirname + "/public/pp15-g14-project2.log");
            
            response.sendStatus(204);
        });
    }
});

function kmlStart()
{
    return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
           "<kml xmlns=\"http://www.opengis.net/kml/2.2\">\n";
}

function kmlElement(location)
{
    return "\t<Placemark>\n" +
           "\t<name>Simple placemark</name>\n" + // TODO
           "\t<description>An interesting location</description>\n" + // TODO
           "\t<Point>\n" +
           "\t\t<coordinates>" +
           location.latitude + "," + location.longitude + "," + location.altitude +
           "</coordinates>\n" +
           "\t</Point>\n" +
           "\t<TimeStamp>\n" +
           "\t\t<when>" +
           location.time +
           "</when>\n" +
           "\t</TimeStamp>\n" +
           "\t</Placemark>\n";
}

function kmlEnd()
{
    return "</kml>";
}

module.exports = router;
