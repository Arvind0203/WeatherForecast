package app.com.weatherforecast.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import app.com.weatherforecast.CustomVo.weatherVo;
import app.com.weatherforecast.R;


public class WeatherViewFragment extends Fragment {
    TextView todayTemperature;
    TextView todayDescription;
    TextView todayWind;
    TextView todayPressure;
    TextView todayHumidity;
    TextView todayMinTemp;
    TextView todayMaxTemp;
    ImageView todayIcon;
    private List<weatherVo> weatherVos = new ArrayList<>();

    public WeatherViewFragment() {
    }

    public static String getWindDirectionString(Context context, weatherVo weather) {
        try {
            if (Double.parseDouble(weather.getWind()) != 0) {
                return weather.getWindDirection(8).getArrow(context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        weatherVos = (List<weatherVo>) bundle.getSerializable("weather_data");
        //weatherVo weatherInfo=(weatherVo) getArguments().getSerializable("weather_data");
        System.out.println("DAY " + bundle.getInt("day"));
        View view = inflater.inflate(R.layout.weather_layout, container, false);
        todayTemperature = (TextView) view.findViewById(R.id.todayTemperature);
        todayDescription = (TextView) view.findViewById(R.id.todayDescription);
        todayWind = (TextView) view.findViewById(R.id.todayWind);
        todayPressure = (TextView) view.findViewById(R.id.todayPressure);
        todayHumidity = (TextView) view.findViewById(R.id.todayHumidity);
        todayMinTemp = (TextView) view.findViewById(R.id.todayMinTemp);
        todayMaxTemp = (TextView) view.findViewById(R.id.todayMaxTemp);
        todayIcon = (ImageView) view.findViewById(R.id.todayIcon);
        loadData(weatherVos.get(bundle.getInt("day")));
        return view;
    }

    private void loadData(weatherVo wInfo) {
        todayWind.setText(getString(R.string.wind) + ": " + new DecimalFormat("#.0").format(Double.parseDouble(wInfo.getWind())) + " " +
                getResources().getString(R.string.speed_unit_mps) +
                (wInfo.isWindDirectionAvailable() ? " "
                        + getWindDirectionString(getActivity(), wInfo) : ""));
        todayPressure.setText(getString(R.string.pressure) + ": " + new DecimalFormat("#.0").format(Double.parseDouble(wInfo.getPressure())) + " " + getResources().getString(R.string.pressure_unit_hpa));
        todayHumidity.setText(getString(R.string.humidity) + ": " + wInfo.getHumidity() + " %");
        Glide.with(getActivity())
                .load(getResources().getString(R.string.image_base_url).concat(wInfo.getIcon()) + ".png")
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(todayIcon);
        todayTemperature.setText(new DecimalFormat("#.#").format(Double.parseDouble(wInfo.getTemperature())) + " °C");
        todayDescription.setText(wInfo.getDescription().substring(0, 1).toUpperCase() +
                wInfo.getDescription().substring(1));
        todayMinTemp.setText(getResources().getString(R.string.min_temp) + ": " + new DecimalFormat("#.#").format(Double.parseDouble(wInfo.getTemperature_min())) + " °C");
        todayMaxTemp.setText(getResources().getString(R.string.max_temp) + ": " + new DecimalFormat("#.#").format(Double.parseDouble(wInfo.getTemperature_max())) + " °C");
    }
}
