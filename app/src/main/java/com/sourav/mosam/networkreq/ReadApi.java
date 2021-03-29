package com.sourav.mosam.networkreq;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
public class ReadApi {
    private URL url = null ;
    private String data  =null;
    public ReadApi(String API) {
        //TODO set a link string
        this.url = creaturl(API);
        try {
            data =  this.connecttohttp();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public String getData()
    {
        if(data!=null)
            return data ;

        return "No data" ;
    }
    private URL creaturl(String API){

        try {
            URL tmp = new URL(API);
            return tmp ;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null ;
    }
    private String connecttohttp() throws IOException {
        String jsonString = null;
        HttpURLConnection connection ;
        if(this.url == null)
            return null;

        connection = (HttpURLConnection) this.url.openConnection();
        connection.setRequestMethod("GET");
        connection.setConnectTimeout(10000);
        connection.connect();
        int c = connection.getResponseCode() ;
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            jsonString = getfromstream(connection.getInputStream());
        }
        connection.disconnect();
        return jsonString ;
    }
    private String getfromstream(InputStream inputStream){
        StringBuilder output = new StringBuilder();
        InputStreamReader streamreader = new InputStreamReader(inputStream, Charset.defaultCharset()) ;
        BufferedReader reader = new BufferedReader(streamreader) ;
        try {
            String buffer  = reader.readLine() ;
            while (buffer!=null){

                output.append(buffer) ;
                buffer= reader.readLine();
            }
            return output.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null ;
    }
}
