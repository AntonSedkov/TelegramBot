import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;

public class Weather {

    //token from openweathermap = 5d2f4b204da89451b6c8de6620f80436
    public static String getWeather(String message, WeatherApiModel model) throws IOException {

        URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q=" + message + "&units=metric&appid=5d2f4b204da89451b6c8de6620f80436");

        Scanner in = new Scanner((InputStream) url.getContent());
        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }

        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setPressure(main.getDouble("pressure"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray weather = object.getJSONArray("weather");
        for (int i = 0; i < weather.length(); i++) {
            JSONObject objectTMP = weather.getJSONObject(i);
            model.setMain(objectTMP.getString("main"));
            model.setIcon(objectTMP.getString("icon"));         
        }

        return "City: " + model.getName() + "\n" +
                "Temperature: " + model.getTemp() + "C" + "\n" +
                "Pressure: " + model.getPressure() + " mbar" + "\n" +
                "Humidity: " + model.getHumidity() + "%" + "\n" +
                "Weather: " + model.getMain() + "\n" +
                "http://openweathermap.org/img/w/" + model.getIcon() + ".png";
    }
}

