package com.example.megha.hbd;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import worldline.com.foldablelayout.FoldableLayout;

/**
 * TODO: Add a class header comment!
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {

    private Map<Integer, Boolean> mFoldStates = new HashMap<>();
    private Context mContext;
    private int mDataSet[];

    public PhotoAdapter(Context context, int dataSet[]) {
        mContext = context;
        mDataSet = dataSet;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PhotoViewHolder(new FoldableLayout(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final PhotoViewHolder holder, int position) {

        Resources res = mContext.getResources();
        // Bind data
        Picasso.with(holder.mFoldableLayout.getContext()).load(mDataSet[position]).into(holder.mImageViewCover);
        Picasso.with(holder.mFoldableLayout.getContext()).load(mDataSet[position]).into(holder.mImageViewDetail);

        // Bind state
        if (mFoldStates.containsKey(position)) {
            if (mFoldStates.get(position) == Boolean.TRUE) {
                if (!holder.mFoldableLayout.isFolded()) {
                    holder.mFoldableLayout.foldWithoutAnimation();
                }
            } else if (mFoldStates.get(position) == Boolean.FALSE) {
                if (holder.mFoldableLayout.isFolded()) {
                    holder.mFoldableLayout.unfoldWithoutAnimation();
                }
            }
        } else {
            holder.mFoldableLayout.foldWithoutAnimation();
        }

        holder.mFoldableLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.mFoldableLayout.isFolded()) {
                    holder.mFoldableLayout.unfoldWithAnimation();
                } else {
                    holder.mFoldableLayout.foldWithAnimation();
                }
            }
        });
        holder.mFoldableLayout.setFoldListener(new FoldableLayout.FoldListener() {
            @Override
            public void onUnFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(5);
                }
            }

            @Override
            public void onUnFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(0);
                }
                mFoldStates.put(holder.getAdapterPosition(), false);
            }

            @Override
            public void onFoldStart() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(5);
                }
            }

            @Override
            public void onFoldEnd() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    holder.mFoldableLayout.setElevation(0);
                }
                mFoldStates.put(holder.getAdapterPosition(), true);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataSet.length ;
    }

    protected static class PhotoViewHolder extends RecyclerView.ViewHolder {

        protected FoldableLayout mFoldableLayout;

        @Bind(R.id.imageview_cover)
        protected ImageView mImageViewCover;

        @Bind(R.id.imageview_detail)
        protected ImageView mImageViewDetail;

        public PhotoViewHolder(FoldableLayout foldableLayout) {
            super(foldableLayout);
            mFoldableLayout = foldableLayout;
            foldableLayout.setupViews(R.layout.list_item_cover, R.layout.list_item_detail, R.dimen.card_cover_height, itemView.getContext());
            ButterKnife.bind(this, foldableLayout);
        }
    }
}
