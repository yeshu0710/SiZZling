package com.example.yeshu.sizzling.utils;

/*
 * Created by Yeshu on 08-06-2018.
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class HttpResponse {
    public static final String ReceipeURL="https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";

    public static URL buildURL(){
        URL url=null;
        try {
            url=new URL(ReceipeURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static String HttpResponsefromConnection(URL url) throws IOException {
        HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
        Scanner scanner;
        boolean input;
        try {
            InputStream inputStream=httpURLConnection.getInputStream();
            scanner=new Scanner(inputStream);
            scanner.useDelimiter("\\A");
            input=scanner.hasNext();
            if (input){
                return scanner.next();
            }else {
                return null;
            }
        } finally {
            httpURLConnection.disconnect();
        }
    }
}
