/*
Assignment : InClass05_Group11
Viranchi Deshpande, Dharak Shah
 */
package com.example.viranchi.inclass05_group11;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Viranchi on 25-09-2017.
 */

public class RecepieUtil {

    static public class JSONRecepieParser{
        static ArrayList<Recepie> parseRecepie(String s) throws JSONException {

            ArrayList<Recepie> listRecepie = new ArrayList<Recepie>();

            JSONObject root = new JSONObject(s);
            JSONArray arrayRecepie = root.getJSONArray("results");

            for(int i=0;i<arrayRecepie.length();i++){
                JSONObject obj = arrayRecepie.getJSONObject(i);
                Recepie recepie = Recepie.createRecepie(obj);
                listRecepie.add(recepie);
            }

            return listRecepie;

        }
    }

}
