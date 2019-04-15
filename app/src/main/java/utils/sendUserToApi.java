package utils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Papis Ndiaye on 09/03/2016.
 */
public class sendUserToApi {
    private HttpURLConnection conn;
    public static final int CONNECTION_TIMEOUT = 18 * 1000;
    public static final String apiKey="Y77CL20xKn0QhEz";
    private static final String api="BUtgBzxYfg6Ud5gmoJ4dAQ==:QV1Z6osHGZC9CiCe580FHJoGoRHO4mWkmUPwx1yPjSw=";

    public JSONObject sendRequest(String link, HashMap<String, String> values) {

        JSONObject object = null;
        try {
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(CONNECTION_TIMEOUT);
            conn.setConnectTimeout(CONNECTION_TIMEOUT);
            conn.setDoInput(true);

            if (values != null) {
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestProperty("Accept", "application/json");
                byte[] postDats=getPostData(values).getBytes("UTF-8");
                conn.setRequestProperty("Content-Length", String.valueOf(postDats.length));
                conn.connect();
                OutputStream os = conn.getOutputStream();
                OutputStreamWriter osWriter = new OutputStreamWriter(os, "UTF-8");
                BufferedWriter writer = new BufferedWriter(osWriter);
                os.write(postDats);
                os.flush();

                os.close();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                    BufferedReader reader = new BufferedReader(isReader);

                    String result = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                    if (result.trim().length() > 2) {
                        object = new JSONObject(result);
                    }
                }
            }
            else {
                conn.connect();
                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream is = conn.getInputStream();
                    InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
                    BufferedReader reader = new BufferedReader(isReader);

                    String result = "";
                    String line = "";
                    while ((line = reader.readLine()) != null) {
                        result += line;
                    }
                    if (result.trim().length() > 2) {
                        object = new JSONObject(result);
                    }
                }
            }
            conn.disconnect();
            conn=null;
        }
        catch (MalformedURLException e) {
            Log.d("exceptionUrl",e.getMessage());
            e.printStackTrace();
            conn=null;

        }
        catch (IOException e) {
            e.printStackTrace();
            conn.disconnect();
            conn=null;
        }
        catch (JSONException e) {
            Log.d("exceptionJS",e.getMessage());
            e.printStackTrace();
            conn.disconnect();
            conn=null;
        }
        //Log.d("objetReturned",object.toString());
        return object;
    }
    public String getPostData(HashMap<String, String> values) {
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : values.entrySet()) {
                try {
                    builder.append("&");
                    builder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
                    builder.append("=");
                    builder.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
                }
                catch (UnsupportedEncodingException e) {

                }
            }
        return builder.toString();
    }
    public String getApi(String name) throws Exception {
        MyCripto cripto=new MyCripto();
        String encripted_api=cripto.decrypt(api,crpt);
        return encripted_api+name;
    }
    private static final String crpt="BD1E9A1117D6596FE4B4276D2B154BBDDC22DE2AA17545E1B4A1811F2EF322E06896D546F581FD26F78DCF5ACF5FC9E2";
}
