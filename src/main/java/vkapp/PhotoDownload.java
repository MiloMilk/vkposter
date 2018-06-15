package vkapp;


import java.io.*;
import java.net.URL;

public class PhotoDownload {

    public static void download (String link, String path) throws IOException {
        File file = new File(path);
        URL url = new URL(link);
        InputStream in = new BufferedInputStream(url.openStream());
        FileOutputStream out = new FileOutputStream(file);
        byte[] buf = new byte[1024];
        int n = 0;
        while (-1!=(n=in.read(buf)))
        {
            out.write(buf, 0, n);
        }
        out.close();
        in.close();

    }

}
