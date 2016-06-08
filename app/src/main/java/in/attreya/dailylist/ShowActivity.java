package in.attreya.dailylist;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.dgreenhalgh.android.simpleitemdecoration.linear.DividerItemDecoration;

import in.attreya.dailylist.adapter.AdapterDaily;
import in.attreya.dailylist.adapter.AddListener;
import in.attreya.dailylist.adapter.SimpleTouchCallback;
import in.attreya.dailylist.beans.Daily;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class ShowActivity extends AppCompatActivity {
    Toolbar mToolbar;
    RecyclerView mRecycler;
    AdapterDaily mAdapter;
    Realm showRealm;
    RealmResults<Daily> r;

    private AddListener mAddListener = new AddListener() {
        @Override
        public void add() {
            showDialogAdd();
        }
    };

    RealmChangeListener mChangeListener = new RealmChangeListener() {
        @Override
        public void onChange(Object element) {
            mAdapter.update(r);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecycler = (RecyclerView) findViewById(R.id.rv_list);

        showRealm = Realm.getDefaultInstance();
        r = showRealm.where(Daily.class).findAllAsync();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);

        mRecycler.addItemDecoration(new DividerItemDecoration(dividerDrawable));
        mAdapter = new AdapterDaily(this,showRealm, r);

        mAdapter.setAddListener(mAddListener);
        mRecycler.setAdapter(mAdapter);

        SimpleTouchCallback callback = new SimpleTouchCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(mRecycler);


        setSupportActionBar(mToolbar);


    }

    public void showDialogAdd() {
        DialogAdd dialog = new DialogAdd();
        dialog.show(getSupportFragmentManager(), "Addshow");
    }

    @Override
    protected void onStart() {
        super.onStart();
        r.addChangeListener(mChangeListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        r.removeChangeListener(mChangeListener);
    }

    public void checkItemCount() {
        //Log.d("Attreya", "checkItemCount: "+ mAdapter.getItemCount());
        // == 2 as lastitem that is swiped + (footer)
        if(mAdapter.getItemCount() == 2){
            Intent i = new Intent(ShowActivity.this, MainActivity.class);
            startActivity(i);
        }
    }
}
