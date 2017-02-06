package app.com.weatherforecast;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import app.com.weatherforecast.Adapter.ViewPagerAdapter;
import app.com.weatherforecast.CustomVo.weatherVo;
import app.com.weatherforecast.Fragment.WeatherViewFragment;

public class MainActivity extends AppCompatActivity {
    TextView todayTemperature;
    TextView todayDescription;
    TextView todayWind;
    TextView todayPressure;
    TextView todayHumidity;
    TextView todayMinTemp;
    TextView todayMaxTemp;
    TextView lastUpdate;
    ImageView todayIcon;
    ViewPager viewPager;
    TabLayout tabLayout;
    View appView;
    ProgressDialog progressDialog;
    private String tag_json_obj = "jobj_req";
    private ProgressDialog pDialog;
    private List<weatherVo> weatherVos = new ArrayList<>();
    public static String getWindDirectionString(Context context, weatherVo weather) {
        try {
            if (Double.parseDouble(weather.getWind()) != 0) {
                return weather.getWindDirection().getLocalizedString(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pDialog = new ProgressDialog(MainActivity.this);
        appView = findViewById(R.id.viewApp);
        todayTemperature = (TextView) findViewById(R.id.todayTemperature);
        todayDescription = (TextView) findViewById(R.id.todayDescription);
        todayWind = (TextView) findViewById(R.id.todayWind);
        todayPressure = (TextView) findViewById(R.id.todayPressure);
        todayHumidity = (TextView) findViewById(R.id.todayHumidity);
        todayMinTemp = (TextView) findViewById(R.id.todayMinTemp);
        todayMaxTemp = (TextView) findViewById(R.id.todayMaxTemp);
        lastUpdate = (TextView) findViewById(R.id.lastUpdate);
        todayIcon = (ImageView) findViewById(R.id.todayIcon);
        // Initialize viewPager
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        makeJsonObjReq();
        progressDialog = new ProgressDialog(MainActivity.this);
    }

    private void updateLongTermWeatherUI() {
        setCurrentData();
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        Bundle bundleDay1 = new Bundle();
        bundleDay1.putInt("day", 0);
        bundleDay1.putSerializable("weather_data", (Serializable) weatherVos);
        WeatherViewFragment recyclerViewDay1 = new WeatherViewFragment();
        recyclerViewDay1.setArguments(bundleDay1);
        viewPagerAdapter.addFragment(recyclerViewDay1, getDay(1));
        Bundle bundleDay2 = new Bundle();
        bundleDay2.putInt("day", 1);
        bundleDay2.putSerializable("weather_data", (Serializable) weatherVos);
        WeatherViewFragment recyclerViewDay2 = new WeatherViewFragment();
        recyclerViewDay2.setArguments(bundleDay2);
        viewPagerAdapter.addFragment(recyclerViewDay2, getDay(2));
        Bundle bundleDay3 = new Bundle();
        bundleDay3.putInt("day", 2);
        bundleDay3.putSerializable("weather_data", (Serializable) weatherVos);
        WeatherViewFragment recyclerViewDay3 = new WeatherViewFragment();
        recyclerViewDay3.setArguments(bundleDay3);
        viewPagerAdapter.addFragment(recyclerViewDay3, getDay(3));
        Bundle bundleDay4 = new Bundle();
        bundleDay4.putInt("day", 3);
        bundleDay4.putSerializable("weather_data", (Serializable) weatherVos);
        WeatherViewFragment recyclerViewDay4 = new WeatherViewFragment();
        recyclerViewDay4.setArguments(bundleDay4);
        viewPagerAdapter.addFragment(recyclerViewDay4, getDay(4));
        int currentPage = viewPager.getCurrentItem();
        viewPagerAdapter.notifyDataSetChanged();
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        currentPage = 0;
        viewPager.setCurrentItem(currentPage, false);
    }

    private void setCurrentData() {
        getSupportActionBar().setTitle(weatherVos.get(0).getCity() + (weatherVos.get(0).getCountry().isEmpty() ? "" : ", " + weatherVos.get(0).getCountry()));
        todayWind.setText(getString(R.string.wind) + ": " + new DecimalFormat("#.0").format(Double.parseDouble(weatherVos.get(0).getWind())) + " " +
                getResources().getString(R.string.speed_unit_mps) +
                (weatherVos.get(0).isWindDirectionAvailable() ? " "
                        + getWindDirectionString(this, weatherVos.get(0)) : ""));
        todayPressure.setText(getString(R.string.pressure) + ": " + new DecimalFormat("#.0").format(Double.parseDouble(weatherVos.get(0).getPressure())) + " " + getResources().getString(R.string.pressure_unit_hpa));
        todayHumidity.setText(getString(R.string.humidity) + ": " + weatherVos.get(0).getHumidity() + " %");
        Glide.with(MainActivity.this)
                .load(getResources().getString(R.string.image_base_url).concat(weatherVos.get(0).getIcon()) + ".png")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(todayIcon);
        todayTemperature.setText(new DecimalFormat("#.#").format(Double.parseDouble(weatherVos.get(0).getTemperature())) + " °C");
        todayDescription.setText(weatherVos.get(0).getDescription().substring(0, 1).toUpperCase() +
                weatherVos.get(0).getDescription().substring(1));
        todayMinTemp.setText(getResources().getString(R.string.min_temp) + ": " + new DecimalFormat("#.#").format(Double.parseDouble(weatherVos.get(0).getTemperature_min())) + " °C");
        todayMaxTemp.setText(getResources().getString(R.string.max_temp) + ": " + new DecimalFormat("#.#").format(Double.parseDouble(weatherVos.get(0).getTemperature_max())) + " °C");
        lastUpdate.setText(getString(R.string.last_update, getLastUpdate()));
    }

    private String getDay(int item) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, item);
        Date date = calendar.getTime();
        return new SimpleDateFormat("EE", Locale.ENGLISH).format(date.getTime());
    }

    private void makeJsonObjReq() {
        showProgressDialog();
        JsonObjectRequest jsonobj = new JsonObjectRequest(Request.Method.GET, "http://api.openweathermap.org/data/2.5/forecast/city?id=1277333&mode=json&units=metric&cnt=5&APPID=f279ee6273c82ce2e30067a8d9511fae", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        JSONArray dataArray = response.getJSONArray("list");
                        for (int i = 0; i < dataArray.length(); i++) {
                            weatherVo weathervo = new weatherVo(response, i);
                            weatherVos.add(weathervo);
                        }
                        updateLongTermWeatherUI();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                hideProgressDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideProgressDialog();
            }
        });
        AppController.getInstance().addToRequestQueue(jsonobj,
                tag_json_obj);
    }

    private void showProgressDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideProgressDialog() {
        if (pDialog.isShowing())
            pDialog.hide();
    }

    private String getLastUpdate() {
        Date date = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("hh:mm a");
        return formatDate.format(date);
    }
}
