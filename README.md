# Weather-API-Client

A Java program that collects the hourly forecast for Garden City, NY (40.726944 N, 73.649722 W) and uses that data to computer the average temperature that is expected in the next 12 hours.

Program Flow:

1.Opens an HTTP connection to the National Weather Service API documented at https://www.weather.gov/documentation/services-web-api

2.Makes sure that it gets an appropriate HTTP Status Code

3.Parses the returned JSON and builds a data structure that can hold the forecast

4.Computes the average.

A test run of the program can look like:

$ java -classpath .:javax.json-1.0.jar WeatherAPI

14 => 41 F
15 => 42 F
16 => 43 F
17 => 42 F
18 => 42 F
19 => 41 F
20 => 39 F
21 => 37 F
22 => 36 F
23 => 35 F
00 => 34 F
01 => 34 F

Average temperature over the next 12 hours: 38.83 F
