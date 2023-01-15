# QUERY SERVICE

| Request Type | link                                    | Parameter Parameter | Request Body |
|:------------:|-----------------------------------------|--------------------:|-------------:|
|      Get     | /query/find-by-location-and-time        |   Location and Time |            - |
|      Get     | /query/find-by-location-at-current-time |            Location |            - |
|      Get     | /query/find-by-user                     |                   - |         User |




User:
    Location
    LocalDateTime (yyyy.MM.dd.HH.mm)

Location:
    double Longitude
    double Latitude
