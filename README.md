# AirQualityMonitoring
This Android app uses data from ws://city-ws.herokuapp.com and creates 2 use cases
1. Show AQI for different cities in a table that also shows last updated time
2. Show historical data for each city in a line graph

We are using the MVVM architecture here. There is a service that continuously monitor the socket for latest data and updates database. The data is dynamically fetched from the db as and when it is updated from server.
We are using Room library for database operations and ohHttp library for socket related tasks. The two use cases are implemented using two different Activities. 

Assumptions:
1. Data is added to db only if there is a change is AQI score and with interval of 30 seconds.
2. The updated time is the time when data is added to DB.

Future enhancements:
1. Have a single activity and multiple fragments. Implement Navigation using Android Navigation.
2. Write unit test cases.
