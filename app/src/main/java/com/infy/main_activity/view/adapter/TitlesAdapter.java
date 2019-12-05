package com.infy.main_activity.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.infy.R;
import com.infy.main_activity.model.TitilesModel;

import java.util.ArrayList;

public class TitlesAdapter extends RecyclerView.Adapter<TitlesAdapter.TitlesHolder> {

    Context context;
    ArrayList<TitilesModel.Row> rowArrayList;
    IOnRowClickListener listener;

    public interface IOnRowClickListener {
        void onRowClick(int position);
    }

    public TitlesAdapter(Context context, ArrayList<TitilesModel.Row> rowArrayList, IOnRowClickListener listener) {
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
    public void onBindViewHolder(@NonNull TitlesHolder holder, int position) {
        holder.mTvTitle.setText(rowArrayList.get(position).getTitle());
        holder.mTvItemDesc.setText(rowArrayList.get(position).getDescription());
        // example url "https://surrealhotels.com/wp-content/uploads/2014/10/Special_Offers2.jpg"
        String imgUrl = rowArrayList.get(position).getImageHref();
        try {
//            Picasso.with(context)
//                    .load("http://1.bp.blogspot.com/_VZVOmYVm68Q/SMkzZzkGXKI/AAAAAAAAADQ/U89miaCkcyo/s400/the_golden_compass_still.jpg")
//                    .into(holder.mImgItem);
            Glide.with(context).load(imgUrl).placeholder(R.mipmap.ic_launcher).into(holder.mImgItem);
        }catch (Exception e){
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public int getItemCount() {
        return rowArrayList.size();
    }

    public class TitlesHolder extends RecyclerView.ViewHolder {
        ImageView mImgItemDetails, mImgItem;
        TextView mTvTitle, mTvItemDesc;
        RelativeLayout mRelListItem;

        public TitlesHolder(@NonNull View itemView) {
            super(itemView);
//            ButterKnife.bind(this, itemView);
            mImgItemDetails = itemView.findViewById(R.id.mImgItemDetails);
            mTvTitle = itemView.findViewById(R.id.mTvTitle);
            mTvItemDesc = itemView.findViewById(R.id.mTvItemDesc);
            mImgItem = itemView.findViewById(R.id.mImgItem);
            mRelListItem = itemView.findViewById(R.id.mRelListItem);
        }
    }
}
