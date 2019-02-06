/*
Assignment : InClass05_Group11
Viranchi Deshpande, Dharak Shah
 */

package com.example.viranchi.inclass05_group11;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText textIn;
    ImageButton buttonAdd;
    LinearLayout container;
    ArrayList<String> wordList;
    int keywordCount = 0;
    EditText editText;
    StringBuilder sb = new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sb.append("http://www.recipepuppy.com/api/?i=");

        if(getIntent().getExtras()!=null){
            Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_LONG).show();
        }

        textIn = (EditText)findViewById(R.id.textin);
        buttonAdd = (ImageButton)findViewById(R.id.add);
        container = (LinearLayout)findViewById(R.id.container);
        wordList = new ArrayList<String>();
        editText = (EditText) findViewById(R.id.editText);


        buttonAdd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (keywordCount <= 4) {
                    LayoutInflater layoutInflater =
                            (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View addView = layoutInflater.inflate(R.layout.row, null);
                    final TextView textOut = (TextView) addView.findViewById(R.id.textout);
                    textOut.setText(textIn.getText().toString());
                    wordList.add(textIn.getText().toString());
                    textIn.setText("");
                    ImageButton buttonRemove = (ImageButton) addView.findViewById(R.id.remove);
                    if(keywordCount==4){
                        textIn.setVisibility(View.GONE);
                        buttonAdd.setVisibility(View.GONE);
                    }

                    final View.OnClickListener thisListener = new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            ((LinearLayout) addView.getParent()).removeView(addView);
                            wordList.remove(textOut.getText().toString());
                            textIn.setVisibility(View.VISIBLE);
                            buttonAdd.setVisibility(View.VISIBLE);

                            listAllAddView();
                            keywordCount--;
                        }
                    };

                    buttonRemove.setOnClickListener(thisListener);
                    container.addView(addView);

                    listAllAddView();
                    keywordCount++;
                }
            }
        });

        findViewById(R.id.btnSerach).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i=0;i<wordList.size();i++){

                    if(i == wordList.size()-1)
                        sb.append(wordList.get(i));
                    else
                        sb.append(wordList.get(i)+",");
                }

                sb.append("&q="+editText.getText().toString());
                Intent intent = new Intent(MainActivity.this,Recepies_Activity.class);
                intent.putExtra("URL",sb.toString());
                startActivity(intent);
            }

        });
    }


    private void listAllAddView(){
        int childCount = container.getChildCount();
        for(int i=0; i<childCount; i++){
            View thisChild = container.getChildAt(i);
        }
    }

}
