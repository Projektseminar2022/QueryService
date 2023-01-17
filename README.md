# QUERY SERVICE
## Description of Requests

| Request Type | link                                        |         Request Parameter | Request Body |      Returns |
|:------------:|---------------------------------------------|--------------------------:|-------------:|-------------:|
|      Get     | /query/forecasts-by-coordinate              |       Latitude, Longitude |            - |    Forecasts |
|      Get     | /query/forecast-by-coordinate-and-time      | Latitude, Longitude, Time |            - |     Forecast |
|      Get     | /query/temperatures-by-coordinate           |       Latitude, Longitude |            - | Temperatures |
|      Get     | /query/temperature-by-coordinate-and-time   | Latitude, Longitude, Time |            - |  Temperature |
|      Get     | /query/forecasts-by-locationCode            |              LocationCode |            - |    Forecasts |
|      Get     | /query/forecast-by-locationCode-and-time    |        LocationCode, Time |            - |     Forecast |
|      Get     | /query/temperatures-by-locationCode         |              LocationCode |            - | Temperatures |
|      Get     | /query/temperature-by-locationCode-and-time |        LocationCode, Time |            - |  Temperature |

## Description of Parameters

| Parameter Name | Type                        | Example          |
|----------------|-----------------------------|------------------|
| Latitude       | double                      | 51.362432        |
| Longitude      | double                      | 51.280216        |
| Time           | String ("yyyy.MM.dd.HH.mm") | 1996.05.22.13.25 |
| LocationCode   | String                      | any              |

Please note: The LocationCode parameter will be checked with ".contains()" against the following attributes:
- Display Name
- City District
- City
- State
- Country
- Country Code

For detailed information about the actual Location definition, please see the DataAcquisition service. 

## Description of Returns

| Return Name | Type   | Example   |
|-------------|--------|-----------|
| Temperature | double | 284.271   |

For detailed information about the Forecast return, please see the DataAcquisition service.
