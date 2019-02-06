/*
Assignment : InClass05_Group11
Viranchi Deshpande, Dharak Shah
 */
package com.example.viranchi.inclass05_group11;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Viranchi on 25-09-2017.
 */

public class Recepie {

    String title,href,ingredients,thumbnail;

    public static Recepie createRecepie(JSONObject js) throws JSONException{

        Recepie rc = new Recepie();
        rc.setHref(js.getString("href"));
        rc.setIngredients(js.getString("ingredients"));
        rc.setThumbnail(js.getString("thumbnail"));
        rc.setTitle(js.getString("title"));

        return rc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}
