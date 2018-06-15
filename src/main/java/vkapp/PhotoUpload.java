package vkapp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.util.List;

public class PhotoUpload {

    public static void upload(String upload_url, String path, String access_token) {
        StringBuilder sb = new StringBuilder();
        Integer groupId = 139401416;
        Integer APP_ID = 6177730;
        Connector adress = new Connector();
        JSONObject jsonObject = new JSONObject();


        try {

            MultipartUtility multipart = new MultipartUtility(upload_url, "UTF-8");
            multipart.addFilePart("photo", new File(path));
            List<String> respons = multipart.finish();

            for (String lin : respons) {
                sb.append(lin);
            }


            JSONParser parser = new JSONParser();
            Object obj = parser.parse(sb.toString());
            JSONObject jsonObj = (JSONObject) obj;

            Long server = (Long) jsonObj.get("server");
            String hash = (String) jsonObj.get("hash");
            String photo = (String) jsonObj.get("photo");


            String get_adress = "https://api.vk.com/method/photos.saveWallPhoto?group_id=139401416&groupId=" + groupId + "&server="+server+ "&photo="+ photo + "&hash=" +hash+ "&access_token=" + access_token + "&v=5.78";

            try {
                jsonObject = (JSONObject) adress.Connector(get_adress);
//                System.out.println(jsonObject.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }

            JSONArray array = new JSONArray();
            array = (JSONArray) jsonObject.get("response");
            JSONObject last_response = new JSONObject();
            last_response = (JSONObject) array.get(0);
            Long owner_id = (Long) last_response.get("owner_id");
            String id = last_response.get("id").toString();

            get_adress = "https://api.vk.com/method/wall.post?owner_id=-139401416&from_group=1&attachments=photo"+ owner_id + "_" + id + "&access_token=" + access_token + "&v=5.78";

            jsonObject = (JSONObject) adress.Connector(get_adress);

            System.out.println("Контент успешно загружен");

            } catch (Exception e){

            }

    }

}
