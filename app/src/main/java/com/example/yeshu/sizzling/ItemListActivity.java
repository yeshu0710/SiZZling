package com.example.yeshu.sizzling;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.example.yeshu.sizzling.JsonData.IngredientsJsonData;
import com.example.yeshu.sizzling.JsonData.ReceipeJsonDate;
import com.example.yeshu.sizzling.JsonData.StepsJsonData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ItemListActivity extends AppCompatActivity {

    ReceipeJsonDate receipeJsonDate;
    ArrayList<IngredientsJsonData> ingredientsJsonDataArrayList=new ArrayList<>();
    ArrayList<StepsJsonData> stepsJsonDataArrayList=new ArrayList<>();
    String title;
    String ingredients=null;
    String steps=null;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Intent intent=getIntent();
        if (intent.getParcelableExtra("results")!=null){
            receipeJsonDate=intent.getParcelableExtra("results");
            title=receipeJsonDate.getName();
            ingredients=receipeJsonDate.getIngredients();
            steps=receipeJsonDate.getSteps();
        }
        if (ingredients!=null){
            try {
                JSONArray jsonArrayIngredients=new JSONArray(ingredients);
                for (int i=0;i<jsonArrayIngredients.length();i++){
                    JSONObject jsonObjectIngredients=jsonArrayIngredients.getJSONObject(i);
                    ingredientsJsonDataArrayList.add(new IngredientsJsonData(jsonObjectIngredients.getString("quantity"),
                            jsonObjectIngredients.getString("measure"),
                            jsonObjectIngredients.getString("ingredient")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (steps != null){
            try {
                JSONArray jsonArraySteps=new JSONArray(steps);
                for (int j=0;j<jsonArraySteps.length();j++){
                    JSONObject jsonObjectSteps=jsonArraySteps.getJSONObject(j);
                    stepsJsonDataArrayList.add(new StepsJsonData(jsonObjectSteps.getInt("id"),
                            jsonObjectSteps.getString("shortDescription"),
                            jsonObjectSteps.getString("description"),
                            jsonObjectSteps.getString("videoURL"),
                            jsonObjectSteps.getString("thumbnailURL")));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
        if (findViewById(R.id.item_detail_container)==null){
            mTwoPane=false;
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title",receipeJsonDate.getName());
        outState.putString("ingredients",receipeJsonDate.getIngredients());
        outState.putString("steps",receipeJsonDate.getSteps());
        outState.putBoolean("mTwo",mTwoPane);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        title=savedInstanceState.getString("title");
        ingredients=savedInstanceState.getString("ingredients");
        steps=savedInstanceState.getString("steps");
        mTwoPane=savedInstanceState.getBoolean("mTwo");
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, stepsJsonDataArrayList,ingredientsJsonDataArrayList, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final ItemListActivity mParentActivity;
        private final List<StepsJsonData> mValues;
        private final ArrayList<IngredientsJsonData> mIngredients;
        private final boolean mTwoPane;

        SimpleItemRecyclerViewAdapter(ItemListActivity itemListActivity, List<StepsJsonData> stepsJsonDataArrayList, ArrayList<IngredientsJsonData> ingredientsJsonDataArrayList, boolean twoPane) {
            mParentActivity=itemListActivity;
            mValues=stepsJsonDataArrayList;
            mIngredients=ingredientsJsonDataArrayList;
            mTwoPane=twoPane;
        }


        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            if (position == 0){
                holder.mContentView.setText(R.string.ingredients_list);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (mTwoPane){
                            Bundle bundle=new Bundle();
                            bundle.putParcelableArrayList("ingredients",mIngredients);
                            ItemDetailFragment itemDetailFragment=new ItemDetailFragment();
                            itemDetailFragment.setArguments(bundle);
                            mParentActivity.getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.item_detail_container,itemDetailFragment).commit();
                        }else {
                            Context context=view.getContext();
                            Intent intent=new Intent(context,ItemDetailActivity.class);
                            intent.putExtra("ingredients",mIngredients);
                            context.startActivity(intent);
                        }
                    }
                });
            }else {
                if (position==1)
                    holder.mContentView.setText(mValues.get(position-1).getDescription());
                else {
                    holder.mContentView.setText("Step " + mValues.get(position - 1).getId() + " : " + mValues.get(position - 1).getShortDescription());
                }
                holder.itemView.setTag(mValues.get(position-1));
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                        public void onClick(View view) {
                            StepsJsonData stepsJsonDataItem=(StepsJsonData)view.getTag();
                            if (mTwoPane){
                                Bundle bundle=new Bundle();
                                bundle.putParcelable("steps",stepsJsonDataItem);
                                ItemDetailFragment itemDetailFragment=new ItemDetailFragment();
                                itemDetailFragment.setArguments(bundle);
                                mParentActivity.getSupportFragmentManager()
                                        .beginTransaction().replace(R.id.item_detail_container,itemDetailFragment)
                                        .commit();
                            }else {
                                Context context=view.getContext();
                                Intent intent=new Intent(context,ItemDetailActivity.class);
                                intent.putExtra("steps",stepsJsonDataItem);
                                context.startActivity(intent);
                            }
                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return mValues.size()+1;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mContentView=view.findViewById(R.id.ingredients_steps);

            }
        }
    }
}
