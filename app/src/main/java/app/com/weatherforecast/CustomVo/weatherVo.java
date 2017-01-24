package app.com.weatherforecast.CustomVo;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.Date;

import app.com.weatherforecast.R;

public class weatherVo implements Serializable {

    private String city;
    private String country;
    private Date date;
    private String temperature;
    private String temperature_min;
    private String temperature_max;
    private String description;
    private Double windDirectionDegree;
    private String wind;
    private String pressure;
    private String humidity;
    private String icon;
    private String lastUpdated;

    public weatherVo(JSONObject jsonObject, int i) {
        try {
            JSONArray list = jsonObject.getJSONArray("list");
            JSONObject city = jsonObject.getJSONObject("city");
            this.city = city.getString("name");
            this.country = city.getString("country");
            JSONObject listItem = list.getJSONObject(i);
            JSONObject main = listItem.getJSONObject("main");
            this.pressure = main.getString("pressure");
            this.humidity = main.getString("humidity");
            this.temperature = main.getString("temp");
            this.temperature_min = main.getString("temp_min");
            this.temperature_max = main.getString("temp_max");
            JSONObject windObj = listItem.optJSONObject("wind");
            this.wind = windObj.getString("speed");
            this.windDirectionDegree = windObj.getDouble("deg");
            this.date = new Date(Long.parseLong(listItem.getString("dt")) * 1000);
            JSONArray weather = listItem.getJSONArray("weather");
            this.description = weather.getJSONObject(0).getString("description");
            this.icon = weather.getJSONObject(0).getString("icon");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public weatherVo() {
    }

    public WindDirection getWindDirection() {
        return WindDirection.byDegree(windDirectionDegree);
    }

    public WindDirection getWindDirection(int numberOfDirections) {
        return WindDirection.byDegree(windDirectionDegree, numberOfDirections);
    }

    public boolean isWindDirectionAvailable() {
        return windDirectionDegree != null;
    }


    public String getTemperature_min() {
        return temperature_min;
    }


    public String getTemperature_max() {
        return temperature_max;
    }

    public void setTemperature_max(String temperature_max) {
        this.temperature_max = temperature_max;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getWind() {
        return wind;
    }

    public void setWind(String wind) {
        this.wind = wind;
    }

    public String getPressure() {
        return pressure;
    }

    public void setPressure(String pressure) {
        this.pressure = pressure;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public enum WindDirection {
        // don't change order
        NORTH, NORTH_NORTH_EAST, NORTH_EAST, EAST_NORTH_EAST,
        EAST, EAST_SOUTH_EAST, SOUTH_EAST, SOUTH_SOUTH_EAST,
        SOUTH, SOUTH_SOUTH_WEST, SOUTH_WEST, WEST_SOUTH_WEST,
        WEST, WEST_NORTH_WEST, NORTH_WEST, NORTH_NORTH_WEST;

        public static WindDirection byDegree(double degree) {
            return byDegree(degree, WindDirection.values().length);
        }

        public static int windDirectionDegreeToIndex(double degree, int numberOfDirections) {
            // to be on the safe side
            degree %= 360;
            if (degree < 0) degree += 360;

            degree += 180 / numberOfDirections; // add offset to make North start from 0

            int direction = (int) Math.floor(degree * numberOfDirections / 360);

            return direction % numberOfDirections;
        }

        public static WindDirection byDegree(double degree, int numberOfDirections) {
            WindDirection[] directions = WindDirection.values();
            int availableNumberOfDirections = directions.length;

            int direction = windDirectionDegreeToIndex(degree, numberOfDirections)
                    * availableNumberOfDirections / numberOfDirections;

            return directions[direction];
        }

        public String getLocalizedString(Context context) {
            // usage of enum.ordinal() is not recommended, but whatever
            return context.getResources().getStringArray(R.array.windDirections)[ordinal()];
        }

        public String getArrow(Context context) {
            // usage of enum.ordinal() is not recommended, but whatever
            return context.getResources().getStringArray(R.array.windDirectionArrows)[ordinal() / 2];
        }
    }
}


