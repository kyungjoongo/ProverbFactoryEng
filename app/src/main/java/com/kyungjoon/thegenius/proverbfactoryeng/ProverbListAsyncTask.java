package com.kyungjoon.thegenius.proverbfactoryeng;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ProverbListAsyncTask extends AsyncTask<String, Void, String> {

    ArrayAdapter<String> mAdapter;

    public ProverbListAsyncTask(MainActivity androidAsyncTaskActivity, ArrayAdapter<String> pAdapter) {

        mAdapter = pAdapter;
    }

    protected String doInBackground(String... params) {
        // .0.3초간 정지
        try {
            Thread.sleep(250);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getDataFromHttpResponse(params[0]);


    }


    @Override
    protected void onPostExecute(String result) {

        try {
            JSONObject jsnobject = new JSONObject(result);
            JSONArray jsonArray = jsnobject.getJSONArray("arrList");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject explrObject = jsonArray.getJSONObject(i);


                Map<String, Object> contentMapOne = new Gson().fromJson(
                        String.valueOf(explrObject), new TypeToken<HashMap<String, Object>>() {
                        }.getType()
                );
                String contentOne = (String) contentMapOne.get("content");
                Double id = (Double) contentMapOne.get("id");
                //mAdapter.add(Double.toString(id) + "_" + contentOne);

                mAdapter.add(contentOne);
            }


        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static String getDataFromHttpResponse(String url) {
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if (inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while ((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

}