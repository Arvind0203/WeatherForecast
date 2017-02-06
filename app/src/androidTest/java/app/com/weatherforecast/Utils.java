package app.com.weatherforecast;

import android.os.RemoteException;
import android.support.test.InstrumentationRegistry;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.support.test.uiautomator.UiScrollable;
import android.support.test.uiautomator.UiSelector;

/**
 * Created by hp-hp on 06-02-2017.
 */

public class Utils {
    public static String AppName="WeatherForecast";
    public static String TODAY_TEMP="app.com.weatherforecast:id/todayTemperature";
    public static String TODAY_DESC="app.com.weatherforecast:id/todayDescription";
    public static String TODAY_WIND="app.com.weatherforecast:id/todayWind";
    public static String TODAY_PRESS="app.com.weatherforecast:id/todayPressure";
    public static String TODAY_HUMI="app.com.weatherforecast:id/todayHumidity";
    public static String TODAY_MIN_TEMP="app.com.weatherforecast:id/todayMinTemp";
    public static String TODAY_MAX_TEMP="app.com.weatherforecast:id/todayMaxTemp";

    public final static UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry
            .getInstrumentation());

    public static boolean openAppHavingText(String appName) throws UiObjectNotFoundException {

        try {
            mDevice.wakeUp();
            mDevice.pressHome();
            Thread.sleep(2000);
        } catch (InterruptedException | RemoteException e) {
            e.printStackTrace();
        }
        UiObject allAppsButton = mDevice.findObject(new UiSelector().description("Apps"));
        if (allAppsButton.exists()) {
            allAppsButton.click();
        }
        UiScrollable scrollableList = new UiScrollable(new UiSelector().scrollable(true));
        scrollableList.scrollIntoView(new UiSelector().text(appName));
        UiObject app = mDevice.findObject(new UiSelector().descriptionContains(appName));
        if (app.exists()) {
            app.click();
            return true;
        } else return false;
    }
    public static UiObject getObjectByRes(String txt) {
        return mDevice.findObject(new UiSelector().resourceId(txt));
    }
}
