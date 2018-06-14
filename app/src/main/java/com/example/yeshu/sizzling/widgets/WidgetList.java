package com.example.yeshu.sizzling.widgets;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.yeshu.sizzling.utils.HttpResponse;
import com.example.yeshu.sizzling.R;
import com.example.yeshu.sizzling.JsonData.ReceipeJsonDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class WidgetList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_widget_list);
        new WidgetData().execute();
    }
    @SuppressLint("StaticFieldLeak")
    public class WidgetData extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            String string=null;
            URL url;
            try {
                url=new URL(HttpResponse.ReceipeURL);
                string=HttpResponse.HttpResponsefromConnection(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return string;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            widget(s);
        }
        public void widget(String s){

            String[] ingredientName;
            final ReceipeJsonDate[] receipeJsonDates;
            try {
                JSONArray jsonArray=new JSONArray(s);
                ingredientName=new String[jsonArray.length()];
                receipeJsonDates=new ReceipeJsonDate[jsonArray.length()];
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    ReceipeJsonDate receipeJsonDate=new ReceipeJsonDate(jsonObject.getInt("id"),
                            jsonObject.getString("name"),
                            jsonObject.getJSONArray("ingredients").toString(),
                            jsonObject.getJSONArray("steps").toString());
                    receipeJsonDates[i]=receipeJsonDate;
                    ingredientName[i]=jsonObject.getString("name");
                }

                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(WidgetList.this);
                alertBuilder.setTitle(R.string.select_receipe);
                alertBuilder.setItems(ingredientName, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int ii) {
                        Intent intent;
                        switch (ii){
                            case 0:
                                intent=new Intent(RecipeWidget.widgetRECEIVER);
                                String string1=receipeJsonDates[0].getIngredients();
                                intent.putExtra("key",string1);
                                WidgetList.this.sendBroadcast(intent);
                                finish();
                                break;
                            case 1:
                                intent=new Intent(RecipeWidget.widgetRECEIVER);
                                String string2=receipeJsonDates[1].getIngredients();
                                intent.putExtra("key",string2);
                                WidgetList.this.sendBroadcast(intent);
                                finish();
                                break;
                            case 2:
                                intent=new Intent(RecipeWidget.widgetRECEIVER);
                                String string3=receipeJsonDates[2].getIngredients();
                                intent.putExtra("key",string3);
                                WidgetList.this.sendBroadcast(intent);
                                finish();
                                break;
                            case 3:
                                intent=new Intent(RecipeWidget.widgetRECEIVER);
                                String string4=receipeJsonDates[3].getIngredients();
                                intent.putExtra("key",string4);
                                WidgetList.this.sendBroadcast(intent);
                                finish();
                                break;
                        }
                    }
                });

                AlertDialog alertDialog=alertBuilder.create();
                alertDialog.show();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
