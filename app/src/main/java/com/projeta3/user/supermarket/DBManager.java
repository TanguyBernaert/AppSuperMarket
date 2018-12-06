package com.projeta3.user.supermarket;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DBManager extends AsyncTask<String, Void, Auchan[]> {
    InputStream is = null;
    String result = "";
    String link = "https://tanguy-bernaert.000webhostapp.com/connexion.php";
    private Context context;
    public GoogleMap map;

    public DBManager(Context context) {
        this.context = context;

    }

    @Override
    protected Auchan[] doInBackground(String... strings) {
        try{

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(link);

            //httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
        }catch(Exception e){
            Log.e("log_tag", "Error in http connection " + e.toString());
        }
        try{
            BufferedReader reader = new BufferedReader(new InputStreamReader(is,"UTF-8"),8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");

            }
            is.close();
            result=sb.toString();

        }catch(Exception e){
            Log.e("log_tag", "Error converting result " + e.toString());
        }
        // Parse les données JSON

        Auchan[] auchan = null;
        String returnString = null;
        try{
            JSONArray jArray = new JSONArray(result);
            auchan = new Auchan[jArray.length()];
            System.out.println(jArray.length());
            for(int i=0;i<jArray.length();i++){
                JSONObject json_data = jArray.getJSONObject(i);

                auchan[i] = new Auchan();
                auchan[i].produit = json_data.getString("Produit");
                auchan[i].produit = json_data.getString("Produit");

                auchan[i].id = json_data.getInt("ID");
                returnString += "\n\t" + jArray.getJSONObject(i);
            }
        }catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

        return auchan;

    }

    @Override
    protected void onPostExecute(Auchan[] auchan) {
        if(auchan!=null){
            for(int i=0; i<auchan.length; i++)
            {
                Log.e("dataTanguy", auchan[i].produit);
            }
        }
    }
}

