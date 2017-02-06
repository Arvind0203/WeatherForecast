package app.com.weatherforecast;

import android.os.SystemClock;
import android.support.test.filters.SmallTest;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.UiObject;
import android.support.test.uiautomator.UiObjectNotFoundException;
import android.util.Log;
import android.widget.TextView;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
@RunWith(AndroidJUnit4.class)
@SmallTest
public class ApplicationTest{
    private TextView result;

    @Before
    public void init() {
        Log.d("TesdtCase", "Starts Executing");
    }{}

    @Test
    public void TEST_WEATHER_FORECAST() {
        try {
            Log.d("TesdtCase","Test Executing");
           Utils.openAppHavingText(Utils.AppName);
            SystemClock.sleep(2000);
            checkData();
        } catch (UiObjectNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void checkData() throws UiObjectNotFoundException {
        UiObject todatTemp = Utils.getObjectByRes(Utils.TODAY_TEMP);
        Assert.assertNotNull(todatTemp);
        Assert.assertNotSame(todatTemp.getText().toString(),null);

        UiObject todatDesc = Utils.getObjectByRes(Utils.TODAY_DESC);
        Assert.assertNotNull(todatDesc);
        Assert.assertNotSame(todatDesc.getText().toString(),null);

        UiObject todatWind = Utils.getObjectByRes(Utils.TODAY_WIND);
        Assert.assertNotNull(todatWind);
        Assert.assertNotSame(todatWind.getText().toString(),null);

        UiObject todatPress = Utils.getObjectByRes(Utils.TODAY_PRESS);
        Assert.assertNotNull(todatPress);
        Assert.assertNotSame(todatPress.getText().toString(),null);

        UiObject todatHumi = Utils.getObjectByRes(Utils.TODAY_HUMI);
        Assert.assertNotNull(todatHumi);
        Assert.assertNotSame(todatHumi.getText().toString(),null);

        UiObject todatMinTemp = Utils.getObjectByRes(Utils.TODAY_MIN_TEMP);
        Assert.assertNotNull(todatMinTemp);
        Assert.assertNotSame(todatMinTemp.getText().toString(),null);

        UiObject todatMaxTemp = Utils.getObjectByRes(Utils.TODAY_MAX_TEMP);
        Assert.assertNotNull(todatMaxTemp);
        Assert.assertNotSame(todatMaxTemp.getText().toString(),null);
    }
}