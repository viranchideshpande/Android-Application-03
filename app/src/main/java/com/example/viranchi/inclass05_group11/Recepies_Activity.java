/*
Assignment : InClass05_Group11
Viranchi Deshpande, Dharak Shah
 */
package com.example.viranchi.inclass05_group11;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Recepies_Activity extends AppCompatActivity {

    TextView txtTitleVal,txtURLVal,txtIngredients;
    ImageView imgRecepie;
    String url;
    ArrayList<Recepie> list;
    Button btnFinish;
    ImageButton btnFirst,btnLast,btnNext,btnPrev;
    int currentIndex = 0;
    ProgressBar progressBar;
    ConstraintLayout layout;
    TextView txtLoading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recepies_);

        txtTitleVal = (TextView) findViewById(R.id.txtTitleVal);
        txtURLVal = (TextView)findViewById(R.id.txtURLVal);
        imgRecepie = (ImageView) findViewById(R.id.imgRecepie);
        list = new ArrayList<Recepie>();
        txtIngredients = (TextView)findViewById(R.id.txtIngreVal);
        btnFinish = (Button)findViewById(R.id.btnFinish);
        btnFirst = (ImageButton) findViewById(R.id.imgBtnFirst);
        btnLast = (ImageButton)findViewById(R.id.imgBtnLast);
        btnNext = (ImageButton)findViewById(R.id.imgBtnNext);
        btnPrev = (ImageButton)findViewById(R.id.imgBtnPrev);
        progressBar = (ProgressBar)findViewById(R.id.progBar);
        txtLoading = (TextView) findViewById(R.id.txtLoading);

        txtURLVal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Intent.ACTION_VIEW, Uri.parse(txtURLVal.getText().toString()));
                startActivity(intent);
            }
        });


        if(getIntent().getExtras()!=null){
            url = getIntent().getExtras().getString("URL");
            layout = (ConstraintLayout) findViewById(R.id.displayRecepie);
            layout.setVisibility(View.INVISIBLE);
            new GetRecepie(Recepies_Activity.this).execute(url);
        }

        btnFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitleVal.setText(list.get(0).getTitle());
                txtURLVal.setText(list.get(0).getHref());
                txtIngredients.setText(list.get(0).getIngredients());
                new getData().execute(list.get(0).getThumbnail());
                currentIndex=0;
            }
        });

        btnLast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitleVal.setText(list.get(list.size()-1).getTitle());
                txtURLVal.setText(list.get(list.size()-1).getHref());
                txtIngredients.setText(list.get(list.size()-1).getIngredients());
                new getData().execute(list.get(list.size()-1).getThumbnail());
                currentIndex=list.size()-1;
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex--;
                if (currentIndex < 0)
                {
                    Toast.makeText(Recepies_Activity.this, "You have reached the First Result", Toast.LENGTH_LONG).show();
                    currentIndex++;
                }
                else {
                    txtTitleVal.setText(list.get(currentIndex).getTitle());
                    txtURLVal.setText(list.get(currentIndex).getHref());
                    txtIngredients.setText(list.get(currentIndex).getIngredients());
                    new getData().execute(list.get(currentIndex).getThumbnail());
                }
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex > list.size()-1)
                {
                    Toast.makeText(Recepies_Activity.this, "You have reached the Last Result", Toast.LENGTH_LONG).show();
                    currentIndex--;
                }else{
                    txtTitleVal.setText(list.get(currentIndex).getTitle());
                    txtURLVal.setText(list.get(currentIndex).getHref());
                    txtIngredients.setText(list.get(currentIndex).getIngredients());
                    new getData().execute(list.get(currentIndex).getThumbnail());
                }
            }
        });


        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setupData(ArrayList<Recepie> results){
        list = results;
        if(list.size()==0)
        {
            Intent intent = new Intent(Recepies_Activity.this,MainActivity.class);
            intent.putExtra("demo","demo");
            startActivity(intent);
        }
        else{

            txtTitleVal.setText(results.get(0).getTitle());
            txtURLVal.setText(results.get(0).getHref());
            txtIngredients.setText(results.get(0).getIngredients());
            new getData().execute(results.get(0).getThumbnail());
            layout.setVisibility(View.VISIBLE);
        }

    }

    private class getData extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {

            BufferedReader br = null;
            try{
                URL url = new URL(params[0]);
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                con.setRequestMethod("GET");
                Bitmap image = BitmapFactory.decodeStream(con.getInputStream());

                return image;


            }
            catch (Exception e){
                e.printStackTrace();
            }
            finally {
                try {
                    if(br !=null)
                        br.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(Bitmap s) {
            super.onPostExecute(s);
            if(s != null) {
                ImageView img = (ImageView) findViewById(R.id.imgRecepie);
                img.setImageBitmap(s);
            }
        }
    }

//    private boolean checkConnection(){
//        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo ni = cm.getActiveNetworkInfo();
//
//        if(ni!=null && ni.isConnected())
//            return true;
//        else
//            return false;
//    }
}

