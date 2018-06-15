package vkapp;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GetFiles {

    public static List<String> getFiles() throws IOException{

        Integer curr_page;
        Integer curr_post;

        File post_info = new File("C:\\Users\\Ivan\\Desktop\\resetera.txt");
        String save_path = "C:\\Users\\Ivan\\Desktop\\";

        Scanner scanner = new Scanner(post_info);

        scanner.nextLine();
        curr_page = Integer.parseInt(scanner.nextLine());
        scanner.nextLine();
        curr_post = Integer.parseInt(scanner.nextLine());


        List<String> list = GetURLs.getURL(curr_page , curr_post);
        if (curr_post%50 == 0){
            curr_page = curr_page + 1;
        }
        while (list.isEmpty()){
            curr_post = curr_post + 1;
            if (curr_post%50 == 0){
                curr_page = curr_page + 1;
            }
            list = GetURLs.getURL(curr_page , curr_post);
        }

        List<String> files = new ArrayList<>();
        int file_num = 0;
        for (String i: list){
            String path = save_path + file_num + ".png";

            PhotoDownload.download(i, path);
            files.add(path);

            file_num = file_num + 1;
        }


        FileWriter writer = new FileWriter(post_info);
        curr_post = curr_post + 1;
        writer.write("page\r\n" + curr_page + "\r\n" + "post\r\n" + curr_post);
        writer.flush();
        writer.close();
        scanner.close();

        return files;

    }

}
