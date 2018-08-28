package com.example.eugen.groshi;
        import android.os.AsyncTask;
        import org.json.JSONException;
        import org.json.JSONObject;
        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

public class LoadTask extends AsyncTask<String, Void, JSONObject> {

    InputStream is = null;
    String res = "";
    BufferedReader reader;
    JSONObject jsonObject = null;

    @Override
    protected JSONObject doInBackground(String... params) {
        try {
            URL url = new URL(params[0]);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.connect();
            InputStream is = connection.getInputStream();

            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                is.close();
                res = sb.toString();
                jsonObject = new JSONObject(res);
                return jsonObject;
            } catch(Exception e1) {
                return null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    protected void onPostExecute(JSONObject result){
        //сюда у нас приходит JSON объект
        try {
            MainActivity.self.response(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

