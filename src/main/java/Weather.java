import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Weather {
    public static String getWeather(String message, Model model) throws IOException {
        URL url = new URL("http://api.openweathermap.org/data/2.5/weather?q="+message+"&appid=5533e9a85a9a50230e96aba594034aac");

        Scanner in = new Scanner((InputStream)url.getContent());
        String result ="";
        while (in.hasNext()){
            result=result+in.nextLine();
        }
        JSONObject object = new JSONObject(result);
        model.setName(object.getString("name"));

        JSONObject main = object.getJSONObject("main");
        model.setTemp(main.getDouble("temp"));
        model.setHumidity(main.getDouble("humidity"));

        JSONArray getArray = object.getJSONArray("weather");
        for(int i=0; i<getArray.length();i++){
            JSONObject obj = getArray.getJSONObject(i);
            model.setIcon((String) obj.get("icon"));
            model.setMain((String) obj.get("main"));
        }

        Double temperature = model.getTemp()-273;
        String info = "";
        String results = String.format("%.3f",temperature);
        if (temperature < 10) {
            info="Одевайся теплее, на улице холодно";
        }
        else if(temperature<26){
            info="На улице оптимальная температура";

        }
        else info="На улице жарко, рекомендуется одеть что нибудь легкое";
        return "Город: "+model.getName()+"\n"+
                "Температура: "+ results+"C - "+info+"\n"+
                "Влажность: "+model.getHumidity()+"%"+"\n"+
                "Погода: "+model.getMain()+"\n"+
                "http://openweathermap.org/img/wn/"+model.getIcon()+".png";
    }
}
