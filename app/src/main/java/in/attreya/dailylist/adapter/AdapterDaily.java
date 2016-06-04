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
import in.attreya.dailylist.beans.Daily;
import io.realm.RealmResults;

public class AdapterDaily extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    RealmResults<Daily> mResults;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;

    public AdapterDaily(Context context, RealmResults<Daily> results) {
        mInflater = LayoutInflater.from(context);
        update(results);
    }

    public void update(RealmResults<Daily> results) {
        mResults = results;
        notifyDataSetChanged();
    }

    @Override
    public HolderDaily onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View view = mInflater.inflate(R.layout.single_row, parent, false);
            return new HolderDaily(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = mInflater.inflate(R.layout.footer, parent, false);
            return new HolderDaily(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (mResults == null || position < mResults.size()) {
            return TYPE_ITEM;
        } else {
            return TYPE_FOOTER;
        }
     }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HolderDaily) {
            HolderDaily holderdaily = (HolderDaily) holder;
            Daily data = mResults.get(position);
            holderdaily.mTxtitem.setText(data.getWhat());
        }
    }

    @Override
    public int getItemCount() {
        return mResults.size()+1;
    }


    public class HolderDaily extends RecyclerView.ViewHolder {
        TextView mTxtitem;
        ImageView mStatus;

        public HolderDaily(View itemView) {
            super(itemView);
            mTxtitem = (TextView) itemView.findViewById(R.id.tv_item);
            mStatus = (ImageView) itemView.findViewById(R.id.iv_status);
        }
    }

    public class HolderFooter extends RecyclerView.ViewHolder {
        Button mFoot;

        public HolderFooter(View itemView) {
            super(itemView);
            mFoot = (Button) itemView.findViewById(R.id.btn_footer);

        }
    }
}
