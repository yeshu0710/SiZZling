package com.example.yeshu.sizzling;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.example.yeshu.sizzling.JsonData.IngredientsJsonData;
import com.example.yeshu.sizzling.JsonData.StepsJsonData;

import java.util.ArrayList;

public class ItemDetailActivity extends AppCompatActivity {

    StepsJsonData stepsJsonData;
    ArrayList<IngredientsJsonData> ingredientsJsonDataArrayList=new ArrayList<>();
    ItemDetailFragment itemDetailFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

       if (savedInstanceState!=null){
           itemDetailFragment=(ItemDetailFragment)getSupportFragmentManager().getFragment(savedInstanceState,"fragment");
       }else {
           itemDetailFragment=new ItemDetailFragment();
           Bundle bundle=new Bundle();
           if (getIntent().hasExtra("ingredients")){
               ingredientsJsonDataArrayList=getIntent().getParcelableArrayListExtra("ingredients");
               bundle.putParcelableArrayList("ingredients",ingredientsJsonDataArrayList);
           }
           if (getIntent().hasExtra("steps")){
               stepsJsonData=getIntent().getParcelableExtra("steps");
               bundle.putParcelable("steps",stepsJsonData);
           }
           itemDetailFragment.setArguments(bundle);
           getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container,itemDetailFragment).commit();
       }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("ingredients",getIntent().getParcelableArrayListExtra("ingredients"));
        outState.putParcelable("steps",getIntent().getParcelableExtra("steps"));
        getSupportFragmentManager().putFragment(outState,"fragment",itemDetailFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        stepsJsonData=savedInstanceState.getParcelable("steps");
        ingredientsJsonDataArrayList=savedInstanceState.getParcelableArrayList("ingredients");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            navigateUpTo(new Intent(this, ItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
