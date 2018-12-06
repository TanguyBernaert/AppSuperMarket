package com.projeta3.user.supermarket;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProductSectionGetter extends AsyncTask<String, String, String> {
    Auchan auchan;

    public ProductSectionGetter(Auchan auchan) {
        this.auchan = auchan;
    }

    @Override
    protected String doInBackground(String... params) {
        HttpJSonParser httpJsonParser = new HttpJSonParser();
        Map<String, String> httpParams = new HashMap<>();
        //Populating request parameters

        httpParams.put("produit", String.valueOf(this.auchan.produit));



        JSONObject jsonObject = httpJsonParser.makeHttpRequest(
                "https://tanguy-bernaert.000webhostapp.com/" + "sectiongetter.php", "POST", httpParams);
        int a=5;
        try {
            int success = jsonObject.getInt("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }


}

