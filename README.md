# ficticious-clean-api
Project for TOTVS recruitment process.

**USAGE**
1. Import project to your favorite IDE;
2. Run/execute project;
3. There are three endpoints available: 
    * http://localhost:8080/api/vehicles (GET): This endpoint is responsible to retrieve all vehicles from database;
    * http://localhost:8080/api/vehicles (POST): This endpoint is responsible to create/save vehicles;
    * http://localhost:8080/api/vehicles/fuel-cost-forecast (GET): This endpoint is responsible to forecast fuel cost and returns a vehicle ordered list in descending order by fuel cost according to the parameters.

**http://localhost:8080/api/vehicles (GET)**
1. Make a GET request (no parameters needed) to retrieve all vehicles.

**http://localhost:8080/api/vehicles (POST)**
1. Make a POST request with **Content-Type: application/json** header and this JSON as body example:
```
{
  "name": "Lightning McQueen",  
  "make": "Volkswagen",
  "model": "Fusca",
  "productionYear": 1970,
  "cityAverageFuelConsumption": 8.6,
  "highwayAverageFuelConsumption": 5.5
}
```

**http://localhost:8080/api/vehicles/fuel-cost-forecast (GET)**
1. Make a GET request with these URL parameters as example:
`
?fuelPrice=5.5&cityRouteDistance=10&highwayRouteDistance=1
`
2. So, the final URL is: http://localhost:8080/api/vehicles/fuel-cost-forecast?fuelPrice=5.5&cityRouteDistance=10&highwayRouteDistance=1


**POSTMAN JSON EXAMPLE**
[TOTVS Ficticous Clean API.postman_collection.txt](https://github.com/lucas-dc/ficticious-clean-api/files/11130507/TOTVS.Ficticous.Clean.API.postman_collection.txt)
