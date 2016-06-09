package in.attreya.dailylist;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import in.attreya.dailylist.beans.Daily;
import io.realm.Realm;
import io.realm.RealmResults;

public class DialogAdd extends android.support.v4.app.DialogFragment {
    ImageButton mAdd, mClose;
    TextView mTextAdd;
    EditText mWhat;
    Realm realm;
    int added;

    public DialogAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_add, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAdd = (ImageButton) view.findViewById(R.id.ib_add);
        mClose = (ImageButton) view.findViewById(R.id.ib_close);
        mTextAdd = (TextView) view.findViewById(R.id.tv_additem);
        mWhat = (EditText) view.findViewById(R.id.et_what);


        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Addaction();
                dismiss();
            }
        });
    }

    private void Addaction() {
        String data = mWhat.getText().toString();
        realm = Realm.getDefaultInstance();
        Daily daily = new Daily(data, false);
        realm.beginTransaction();
        realm.copyToRealm(daily);
        realm.commitTransaction();
        realm.close();

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        realm = Realm.getDefaultInstance();
        RealmResults<Daily> r = realm.where(Daily.class).findAll();
        int l = r.size();
        if (l == 1) {
            Intent i = new Intent(getContext(), ShowActivity.class);
            startActivity(i);

        }
    }
}
