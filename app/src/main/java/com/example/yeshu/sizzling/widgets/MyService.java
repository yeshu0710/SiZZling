package com.example.yeshu.sizzling.widgets;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.yeshu.sizzling.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * Created by Yeshu on 13-06-2018.
 */

public class MyService extends RemoteViewsService {
    public MyService() {
    }

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetView(this.getApplicationContext(),intent);
    }
    public class WidgetView implements RemoteViewsService.RemoteViewsFactory {

        String[] ingredientArray;
        int ingredientWidgetID;
        Context context;
        List<String> ingredientWidgetList= new ArrayList<>();

        WidgetView(Context applicationContext, Intent intent) {
            Bundle bundle=intent.getExtras();
            this.context=applicationContext;
            if (!bundle.isEmpty()){
                ingredientWidgetID=bundle.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID);
                ingredientArray=bundle.getStringArray("BundleKEY");
            }
        }

        @Override
        public void onCreate() {
            updateWidgetList();
        }

        @Override
        public void onDataSetChanged() {
            updateWidgetList();
        }

        @Override
        public void onDestroy() {
            ingredientWidgetList.clear();

        }

        @Override
        public int getCount() {
            return ingredientWidgetList.size();
        }

        @Override
        public RemoteViews getViewAt(int i) {
            RemoteViews remoteViews=new RemoteViews(context.getPackageName(), R.layout.widget_custome_dialogue);
            remoteViews.setTextViewText(R.id.widget_textView,ingredientWidgetList.get(i));
            return remoteViews;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        void updateWidgetList(){
            this.ingredientWidgetList= new ArrayList<>(Arrays.asList(ingredientArray));
        }
    }
}
