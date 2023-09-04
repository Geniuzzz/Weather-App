# Eliq Weather App
## Overview
Welcome to the Eliq Weather App. This guide will guide you on how the application works.

## Details
1. The application's UI is built with Jetpack compose
2. MVVM architecture is used

## How to use the application
1. Install the application on an android device
2. The application will require user location and internet so ensure to connect to an active network and grant the requested location permissions.
3. With the Last known location, the application will fetch weather information from a rest API and render the data on the UI
4. A Location can also be queried for
   1. With the help of a geocoding api, suggestions are fetch with their location details
   2. A suggestion that best matches the required location can be selected
5. Finally, weather information for the selected location is fetched and rendered on the UI