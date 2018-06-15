package vkapp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Connector extends JSONObject{

    public Connector () {
        super();
    }

    public JSONObject Connector(String url) {

        HttpURLConnection connection = null;
        String get_adress= url;
        StringBuilder sb=new StringBuilder();
        JSONParser parser;
//        Object obj;
        JSONObject jsonObject = null;

        try {

            connection=(HttpURLConnection) new URL(get_adress).openConnection();
            connection.connect();

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;

            while((line=in.readLine())!=null){
                sb.append(line);
            }
            //System.out.println(sb.toString());

            parser = new JSONParser();
            jsonObject = (JSONObject) parser.parse(sb.toString());
           // jsonObject = (JSONObject)obj;

        } catch (IOException e) {
            System.out.println("Connection Error");
        } catch (ParseException e) {
            System.out.println("Parsing Error");
        }
            return jsonObject;
        }

}
