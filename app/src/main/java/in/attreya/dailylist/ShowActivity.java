package in.attreya.dailylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import in.attreya.dailylist.adapter.AdapterDaily;
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

        if (r == null) {
            Intent j = new Intent(ShowActivity.this, MainActivity.class);
            startActivity(j);
        }


        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecycler.setLayoutManager(manager);

        mAdapter = new AdapterDaily(this, r);
        mRecycler.setAdapter(mAdapter);

        setSupportActionBar(mToolbar);
        initBackgroundImage();

    }

    private void initBackgroundImage() {
        ImageView background = (ImageView) findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(background);
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
}
