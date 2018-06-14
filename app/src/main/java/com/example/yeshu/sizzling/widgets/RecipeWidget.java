package com.example.yeshu.sizzling.widgets;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RemoteViews;

import com.example.yeshu.sizzling.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/*
 * Created by Yeshu on 13-06-2018.
 */

public class RecipeWidget extends AppWidgetProvider {

    public static final String widgetRECEIVER="com.example.yeshu.sizzling.WIDGET";

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget_list);
        String[] strings={};
        if (Objects.equals(intent.getAction(), widgetRECEIVER)){
            String widgetData=intent.getStringExtra("key");
            try {
                JSONArray jsonArray=new JSONArray(widgetData);
                strings=new String[jsonArray.length()];
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    strings[i]=jsonObject.getString("ingredient")+" "+jsonObject.getString("quantity")+" "+jsonObject.getString("measure")+"/n";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            remoteViews.setViewVisibility(R.id.select_receipe, View.GONE);
            Intent receipeIntent=new Intent(context,MyService.class);
            Bundle bundle=new Bundle();
            bundle.putInt(AppWidgetManager.EXTRA_APPWIDGET_ID,remoteViews.getLayoutId());
            bundle.putStringArray("BundleKEY",strings);
            receipeIntent.putExtras(bundle);
            Uri uri=Uri.parse(receipeIntent.toUri(Intent.URI_INTENT_SCHEME));
            receipeIntent.setData(uri);
            remoteViews.setRemoteAdapter(R.id.widget_list,receipeIntent);
        }
        AppWidgetManager.getInstance(context).updateAppWidget(new ComponentName(context,RecipeWidget.class),remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int i:appWidgetIds){
            Intent intent=new Intent(context,WidgetList.class);
            PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,0);
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.widget_list);
            remoteViews.setOnClickPendingIntent(R.id.select_receipe,pendingIntent);
            appWidgetManager.updateAppWidget(i,remoteViews);
        }
    }
}
