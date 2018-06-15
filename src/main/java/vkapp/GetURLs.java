package vkapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GetURLs {

    public static List<String> getURL (Integer page, Integer post) throws IOException{
        List<String> list = new ArrayList<>();

        String url = "https://www.resetera.com/threads/pc-screenshots-thread-2018.578/page-"+page;
        Document doc = Jsoup.connect(url).userAgent("Mozilla").get();
        Elements divElements = doc.select("li.message");

        for (Element i: divElements) {
            if (Integer.parseInt(i.select("div.postCount").text().split("#")[1]) == post) {
                for (Element z: i.select("img")) {
                    String str = z.attr("data-url");
                    if (!str.equals("")) {
                        list.add(str);
                    }
                }
                break;
            }
        }

        return list;
    }
}
