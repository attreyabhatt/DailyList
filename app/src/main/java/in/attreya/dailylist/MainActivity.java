package in.attreya.dailylist;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import in.attreya.dailylist.beans.Daily;
import io.realm.Realm;
import io.realm.RealmResults;

//TODO Make transition between activities smoother

public class MainActivity extends AppCompatActivity {

    RealmResults<Daily> r;
    Realm thisRealm;
    Button btn_add;
    String TAG = "yolo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "OnCreate");
        setContentView(R.layout.activity_main);
        btn_add = (Button) findViewById(R.id.btn_makelist);

        thisRealm = Realm.getDefaultInstance();
        r = thisRealm.where(Daily.class).findAll();
        int l = r.size();

        if (l != 0) {
            Intent i = new Intent(MainActivity.this, ShowActivity.class);
            startActivity(i);
        }

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addaction();
                Log.d(TAG, "onClick: ");
            }
        });
        initBackgroundImage();
    }

    private void Addaction() {
        showDialogAdd();
    }

    public void showDialogAdd() {
        DialogAdd dialog = new DialogAdd();
        dialog.show(getSupportFragmentManager(), "Add");
    }

    private void initBackgroundImage() {
        ImageView background = (ImageView) findViewById(R.id.iv_background);
        Glide.with(this)
                .load(R.drawable.background)
                .centerCrop()
                .into(background);
    }


}
