# QUERY SERVICE
## Description of Requests

| Request Type | link                                           |         Request Parameter | Request Body |      Returns |
|:------------:|------------------------------------------------|--------------------------:|-------------:|-------------:|
|      Get     | /query/find-forecasts-by-coordinates           |       Latitude, Longitude |            - |    Forecasts |
|      Get     | /query/find-forecast-by-coordinates-and-time   | Latitude, Longitude, Time |            - |     Forecast |
|      Get     | /query/find-temperatures-by-coordinates        |       Latitude, Longitude |            - | Temperatures |
|      Get     | /query/find-temperature-by-coordinate-and-time | Latitude, Longitude, Time |            - |  Temperature |
|      Get     | /query/find-forecasts-by-location              |              LocationCode |            - |    Forecasts |
|      Get     | /query/find-forecast-by-location-and-time      |        LocationCode, Time |            - |     Forecast |
|      Get     | /query/find-temperatures-by-location           |              LocationCode |            - | Temperatures |
|      Get     | /query/find-temperature-by-location-and-time   |        LocationCode, Time |            - |  Temperature |

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
