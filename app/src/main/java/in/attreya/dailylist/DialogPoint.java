package in.attreya.dailylist;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class DialogPoint extends DialogFragment {
    TextView mQuote, mTask;
    ImageView mComplete;
    ImageButton mClose;
    SharedPreferences sharedpreferences;
    int compItems;

    public DialogPoint() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_point, container, false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogTheme);
    }

    private int getCompleteItems() {
        return ((ShowActivity) getActivity()).itemsComplete;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mClose = (ImageButton) view.findViewById(R.id.ib_close);
        mComplete = (ImageView) view.findViewById(R.id.complete_icon);
        mQuote = (TextView) view.findViewById(R.id.quote);
        mTask = (TextView) view.findViewById(R.id.tasks);


        compItems = getCompleteItems();

        mTask.setText("tasks completed - " + compItems);


        mClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}
