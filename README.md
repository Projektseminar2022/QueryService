# QUERY SERVICE
## Description of Requests

| Request Type | link                                              |                Request Parameter | Request Body |      Returns |
|:------------:|---------------------------------------------------|---------------------------------:|-------------:|-------------:|
|      Get     | /query/forecasts-by-coordinate                    |              Latitude, Longitude |            - |    Forecasts |
|      Get     | /query/forecast-by-coordinate-and-timeOffset      | Latitude, Longitude, Time Offset |            - |     Forecast |
|      Get     | /query/temperatures-by-coordinate                 |              Latitude, Longitude |            - | Temperatures |
|      Get     | /query/temperature-by-coordinate-and-timeOffset   | Latitude, Longitude, Time Offset |            - |  Temperature |
|      Get     | /query/forecasts-by-locationCode                  |                     LocationCode |            - |    Forecasts |
|      Get     | /query/forecast-by-locationCode-and-timeOffset    |        LocationCode, Time Offset |            - |     Forecast |
|      Get     | /query/temperatures-by-locationCode               |                     LocationCode |            - | Temperatures |
|      Get     | /query/temperature-by-locationCode-and-timeOffset |        LocationCode, Time Offset |            - |  Temperature |

## Description of Parameters

| Parameter Name | Type   | Example   |
|----------------|--------|-----------|
| Latitude       | double | 51.362432 |
| Longitude      | double | 51.280216 |
| Time Offset    | int    | 0-47      |
| Location Code  | String | any       |

Time Offset:  
All weather calls are for the current hour. A forecast is always sent for a time period of 48 hours.  
Therefore, if the forecast in 5 hours is needed, set the offset to 5.  
If the forecast for the current hour is needed, set the offset to 0. 

LocationCode:
The parameter will be checked with ".contains()" against the following attributes:
- Display Name
- City District
- City
- State
- Country
- Country Code

For detailed information about the actual Location definition that the LocationCode parameter is checked against, please see the DataAcquisition service. 

## Description of Returns

| Return Name | Type   | Example   |
|-------------|--------|-----------|
| Temperature | double | 284.271   |

Please note that the timestamps are in UTC format which is 1 hour late compared to the German timezone. 15:00 German time (CET) is 14:00 UTC.   
For more detailed information about the Forecast return, please see the DataAcquisition service.