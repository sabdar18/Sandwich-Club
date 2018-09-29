package com.udacity.sandwichclub;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.udacity.sandwichclub.adapters.SandwichAdapter;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import com.udacity.sandwichclub.utils.SharedPrefsUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Sandwich> mSandwichList;
    private RecyclerView mRecyclerView;
    private SandwichAdapter mSandwichAdapter;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.sandwichesRV);
        mRecyclerView.setHasFixedSize(true);

        prepareSandwichItems();
        mSandwichAdapter = new SandwichAdapter(mSandwichList);
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        int defaultLayout = SharedPrefsUtils.getItemType(this);
        if (defaultLayout == SharedPrefsUtils.GRID_LAYOUT_ID) {
            setGridLayout();
        } else {
            setListLayout();
        }
        mRecyclerView.setAdapter(mSandwichAdapter);
        runLayoutAnimation();
    }


    private void prepareSandwichItems() {
        String[] sandwichList = getResources().getStringArray(R.array.sandwich_details);
        String[] sandwiches = getResources().getStringArray(R.array.sandwich_names);
        mSandwichList = new ArrayList<>();
        for (int i = 0; i < sandwiches.length; i++) {
            String json = sandwichList[i];
            Sandwich sandwich = JsonUtils.parseSandwichJson(json);
            mSandwichList.add(sandwich);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        mMenu = menu;
        updateMenu();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_list_item:
                setListLayout();
                break;
            case R.id.action_grid_item:
                setGridLayout();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setGridLayout() {
        SharedPrefsUtils.saveItemType(this, SharedPrefsUtils.GRID_LAYOUT_ID);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        updateMenu();
    }

    private void setListLayout() {
        SharedPrefsUtils.saveItemType(this, SharedPrefsUtils.LIST_LAYOUT_ID);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        updateMenu();
    }

    private void updateMenu() {
        if (mMenu == null) {
            return;
        }
        int defaultLayout = SharedPrefsUtils.getItemType(this);
        if (defaultLayout == SharedPrefsUtils.GRID_LAYOUT_ID) {
            mMenu.findItem(R.id.action_grid_item).setVisible(false);
            mMenu.findItem(R.id.action_list_item).setVisible(true);
        } else {
            mMenu.findItem(R.id.action_grid_item).setVisible(true);
            mMenu.findItem(R.id.action_list_item).setVisible(false);
        }
    }

    private void runLayoutAnimation(  ) {
        final Context context = mRecyclerView.getContext();
        final LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fall_down);
        mRecyclerView.setLayoutAnimation(controller);
        mRecyclerView.getAdapter().notifyDataSetChanged();
        mRecyclerView.scheduleLayoutAnimation();
    }


}
