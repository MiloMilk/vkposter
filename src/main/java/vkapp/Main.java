package vkapp;

import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.UserAuthResponse;

import org.json.simple.JSONObject;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException, ApiException, ClientException {

        List files = GetFiles.getFiles();

        TransportClient transportClient = HttpTransportClient.getInstance();
        VkApiClient vk = new VkApiClient(transportClient);

        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://oauth.vk.com/authorize?client_id=6177730&display=page&redirect_uri=https://oauth.vk.com/blank.html&scope=270340&response_type=code&v=5.78");

        WebElement element1 = driver.findElement(By.name("email"));
        WebElement element2 = driver.findElement(By.name("pass"));
        element1.sendKeys("**@gmail.com");
        element2.sendKeys("**");
        element1.submit();

        System.out.println("Page title is: " + driver.getTitle());

        // And now use this to visit Google
        // Now submit the form. WebDriver will find the form for us from the elemen
        try {
            WebElement loginButton = driver.findElement(By.xpath("//button[text()='Разрешить']"));
            loginButton.click();
        } catch (org.openqa.selenium.NoSuchElementException e) {
            System.out.println("You already have access");
        }

        Integer groupId = 139401416;
        Integer APP_ID = 6177730;
        String CLIENT_SECRET = "CtrCAleWjDC67JAGHhbv";
        String REDIRECT_URI = "https://oauth.vk.com/blank.html";

        String code = driver.getCurrentUrl().split("code=")[1];
        driver.close();

        UserAuthResponse authResponse = vk.oauth().userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code).execute();
        String access_token = authResponse.getAccessToken();
        UserActor actor = new UserActor(authResponse.getUserId(), access_token);

        String get_adress = "https://api.vk.com/method/photos.getWallUploadServer?group_id=139401416&access_token=" + access_token + "&v=5.78";
        Connector adress = new Connector();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject = (JSONObject) adress.Connector(get_adress);
        } catch (Exception e) {
            System.out.println("Something wrong");
            e.printStackTrace();
        }

        JSONObject response = (JSONObject) jsonObject.get("response");
        String upload_url = (String) response.get("upload_url");

        PhotoUpload.upload(upload_url, "C:\\Users\\Ivan\\Desktop\\1.png", access_token);


    }
}