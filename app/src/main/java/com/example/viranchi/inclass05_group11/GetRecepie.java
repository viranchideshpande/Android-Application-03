/*
Assignment : InClass05_Group11
Viranchi Deshpande, Dharak Shah
 */
package com.example.viranchi.inclass05_group11;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Viranchi on 25-09-2017.
 */

public class GetRecepie extends AsyncTask <String, Integer, ArrayList<Recepie>> {

    Recepies_Activity activity;
    ProgressBar progress;

    public GetRecepie(Recepies_Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progress = activity.progressBar;
        progress.setMax(100);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    protected ArrayList<Recepie> doInBackground(String... params) {

        BufferedReader br = null;

        try {
            publishProgress(1);
            URL url = new URL(params[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            publishProgress(2);
            con.setRequestMethod("GET");
            if(con.getResponseCode()==HttpURLConnection.HTTP_OK){
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                publishProgress(3);
                while(line!=null){
                    sb.append(line);
                    line = br.readLine();
                }
                ArrayList<Recepie> list =  RecepieUtil.JSONRecepieParser.parseRecepie(sb.toString());
                publishProgress(4);
                for(int i=5;i<=100;i++){
                    for(int j=0;j<100;j++)
                        publishProgress(i);
                }

                return list;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }finally {
            try{
                br.close();
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progress.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Recepie> recepies) {
        progress.setVisibility(View.GONE);
        activity.txtLoading.setVisibility(View.GONE);
        activity.setupData(recepies);
        super.onPostExecute(recepies);
    }
}
