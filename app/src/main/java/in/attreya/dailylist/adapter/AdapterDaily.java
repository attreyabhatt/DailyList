package in.attreya.dailylist.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import in.attreya.dailylist.R;
import in.attreya.dailylist.ShowActivity;
import in.attreya.dailylist.beans.Daily;
import io.realm.Realm;
import io.realm.RealmResults;

public class AdapterDaily extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements SwipeListener {

    private LayoutInflater mInflater;
    RealmResults<Daily> mResults;
    private Realm mRealm;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private AddListener mAddListener;
    private Context mContext;


    public AdapterDaily(Context context, Realm realm, RealmResults<Daily> results) {
        mInflater = LayoutInflater.from(context);
        mRealm = realm;
        update(results);
        this.mContext = context;
    }

    public void update(RealmResults<Daily> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    public void setAddListener(AddListener Listener) {
        mAddListener = Listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.single_row, parent, false);
            return new HolderDaily(view);
        } else {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            return new HolderFooter(view);
        }

    }

    @Override
    public int getItemViewType(int position) {

        if (position < mResults.size()) {
            return TYPE_ITEM;
        } else {
            return TYPE_FOOTER;
        }

    }

    @Override
    public long getItemId(int position) {
        if (position < mResults.size() && mResults.get(position).isValid()) {
            return mResults.get(position).getNow();
        }
        return RecyclerView.NO_ID;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderDaily) {
            HolderDaily holderdaily = (HolderDaily) holder;
            if (mResults.get(position).isValid()) {
                Daily data = mResults.get(position);
                holderdaily.setwhat(data.getWhat());
                holderdaily.setbackground(data.isCompleted());
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mResults == null || mResults.isEmpty()) {
            return 0;
        } else {
            return mResults.size() + 1;
        }
    }

    @Override
    public void onSwipe(int position) {

        if (mContext instanceof ShowActivity) {
            if(mResults.get(position).isCompleted())
            ((ShowActivity) mContext).increaseItemsComplete();
        }

        if (position < mResults.size()) {  //So that footer doesn't get swiped
            mRealm.beginTransaction();
            mResults.get(position).deleteFromRealm();
            mRealm.commitTransaction();
            notifyItemRemoved(position);
        }
        if (mContext instanceof ShowActivity) {
            ((ShowActivity) mContext).checkItemCount();
        }
    }

    public void markComplete(int position) {
        if (position < mResults.size()) {
            mRealm.beginTransaction();
            mResults.get(position).setCompleted(true);
            mRealm.commitTransaction();
        }
    }


    public class HolderDaily extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTxtitem;
        ImageView mStatus;

        public HolderDaily(View itemView) {
            super(itemView);
            mTxtitem = (TextView) itemView.findViewById(R.id.tv_item);
            mStatus = (ImageView) itemView.findViewById(R.id.iv_status);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            ((ShowActivity) mContext).showDialogMark(getAdapterPosition());
        }

        public void setwhat(String what) {
            mTxtitem.setText(what);
        }

        public void setbackground(boolean completed) {
            if (completed) {
                mStatus.setImageResource(R.drawable.ic_check);
            } else {
                mStatus.setImageResource(R.drawable.ic_minus);
            }
        }
    }

    public class HolderFooter extends RecyclerView.ViewHolder implements View.OnClickListener {
        Button mFoot;

        public HolderFooter(View itemView) {
            super(itemView);
            mFoot = (Button) itemView.findViewById(R.id.btn_footer);
            mFoot.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mAddListener.add();
        }
    }
}
