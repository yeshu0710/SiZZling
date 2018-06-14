package com.example.yeshu.sizzling;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.yeshu.sizzling.JsonData.ReceipeJsonDate;
import com.example.yeshu.sizzling.utils.HttpResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class HomeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>{

    @BindView(R.id.HomeActivityRecycleview) RecyclerView recyclerView;

    public static final int LoadID=50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);
        ConnectivityManager connectivityManager=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfoWiFi=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo networkInfoData=connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((networkInfoWiFi!=null & networkInfoData!=null)&&(networkInfoWiFi.isConnected() | networkInfoData.isConnected())) {
            getSupportLoaderManager().initLoader(LoadID, null, this);
        }else {
            new AlertDialog.Builder(HomeActivity.this).setTitle(R.string.app_name).
                    setMessage("No Internet Connection").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).show();
        }
    }


    @NonNull
    @SuppressLint("StaticFieldLeak")
    @Override
    public android.support.v4.content.Loader<String> onCreateLoader(int id, Bundle args) {
        return new android.support.v4.content.AsyncTaskLoader<String>(this) {
            @Nullable
            @Override
            public String loadInBackground() {
                URL url= HttpResponse.buildURL();
                String result=null;
                try {
                    result= HttpResponse.HttpResponsefromConnection(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return result;
            }

            @Override
            protected void onStartLoading() {
                super.onStartLoading();
                forceLoad();
            }
        };
    }

    @Override
    public void onLoadFinished(@NonNull android.support.v4.content.Loader<String> loader, String data) {
        ArrayList<ReceipeJsonDate> receipeJsonDates =new ArrayList<>();
        JSONArray jsonArray=null;
        try {
            jsonArray=new JSONArray(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonArray != null) {
            for (int i=0;i<jsonArray.length();i++){
                try {
                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                    receipeJsonDates.add(new ReceipeJsonDate(jsonObject.getInt("id"),
                            jsonObject.getString("name"),
                            jsonObject.getJSONArray("ingredients").toString(),
                            jsonObject.getJSONArray("steps").toString()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        }
        recyclerView.setAdapter(new ReceipeAdaptor(this,receipeJsonDates));
    }

    @Override
    public void onLoaderReset(@NonNull android.support.v4.content.Loader<String> loader) {

    }

    public Activity getActivity(){
        Context context=this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity) context;
            }
            context=((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
