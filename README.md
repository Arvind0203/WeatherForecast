# WeatherForecast
----------------------
Version: 1.0

WeatherForecast is an Android App showing 5 days weather forecast information of Bangalore City.

Follow the below procedure to setup and build WeatherForecast
1) Download/Clone the source code from GitHub to local machine using the following link.
        git@github.com:Arvind0203/WeatherForecast.git

2) Import the Source into Android Studio.

3) Build Using Android Studio.

4) Push the .apk into the device using the following command
 adb push ./WeatherForecast/app/build/outputs/apk/app-debug.apk

5) Install the app onto Device.

Execution of Unit Test Cases
----------------------
1. Install both app-debug.apk and app-debug-androidTest.apk generated After building 
the code under "WeatherForecast\app\build\outputs\apk" Directory.

2. After Installation of both of the apk run the following commnad through terminal to execute test case.
$ adb shell am instrument -w -r   -e debug false -e class app.com.weatherforecast.ApplicationTest#TEST_WEATHER_FORECAST app.com.weatherforecast.test/android.support.test.runner.AndroidJUnitRunner

