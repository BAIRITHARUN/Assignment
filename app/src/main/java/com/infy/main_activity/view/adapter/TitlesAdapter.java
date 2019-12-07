package com.infy.main_activity.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.infy.R;
import com.infy.main_activity.model.Row;
import com.infy.main_activity.model.TitilesModel;

import java.util.ArrayList;

public class TitlesAdapter extends RecyclerView.Adapter<TitlesAdapter.TitlesHolder> {

    Context context;
    ArrayList<Row> rowArrayList;
    IOnRowClickListener listener;

    public interface IOnRowClickListener {
        void onRowClick(int position);
    }

    public TitlesAdapter(Context context, ArrayList<Row> rowArrayList, IOnRowClickListener listener) {
        this.context = context;
        this.rowArrayList = rowArrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TitlesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.inflater_titiles_list_item, parent, false);
        return new TitlesHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final TitlesHolder holder, final int position) {
        holder.mTvTitle.setText(rowArrayList.get(position).getTitle());
        holder.mTvItemDesc.setText(rowArrayList.get(position).getDescription());
        // example url "https://surrealhotels.com/wp-content/uploads/2014/10/Special_Offers2.jpg"
        String imgUrl = rowArrayList.get(position).getImageHref();
        if (imgUrl!=null && imgUrl.contains("http://")){
            imgUrl = imgUrl.replace("http://", "https://");
        }
        try {
            Glide.with(context)
                    .load(imgUrl)
                    .error(R.drawable.error)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                    Target<Drawable> target, boolean isFirstResource) {
                            holder.mPBLoadImage.setVisibility(View.GONE);
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model,
                                                       Target<Drawable> target, DataSource dataSource,
                                                       boolean isFirstResource) {
                            holder.mPBLoadImage.setVisibility(View.GONE);
                            return false;
                        }
                    }).into(holder.mImgItem);

        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        holder.mRelListItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onRowClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rowArrayList.size();
    }

    public class TitlesHolder extends RecyclerView.ViewHolder {
        ImageView mImgItemDetails, mImgItem;
        TextView mTvTitle, mTvItemDesc;
        RelativeLayout mRelListItem;
        ProgressBar mPBLoadImage;

        public TitlesHolder(@NonNull View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            mImgItemDetails = itemView.findViewById(R.id.mImgItemDetails);
            mTvTitle = itemView.findViewById(R.id.mTvTitle);
            mTvItemDesc = itemView.findViewById(R.id.mTvItemDesc);
            mImgItem = itemView.findViewById(R.id.mImgItem);
            mRelListItem = itemView.findViewById(R.id.mRelListItem);
            mPBLoadImage = itemView.findViewById(R.id.mPBLoadImage);
        }
    }
}
