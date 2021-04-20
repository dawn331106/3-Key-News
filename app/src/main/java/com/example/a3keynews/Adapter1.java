package com.example.a3keynews;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.a3keynews.parameter.Articles;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.app.PendingIntent.getActivity;


public class Adapter1 extends RecyclerView.Adapter<Adapter1.ViewHolder>{
    Context context;
    List<String>url,title,published,urltoimage;
    public Adapter1(Context context, List<String> url,List<String>title,List<String>published,List<String>urltoimage){
        this.context=context;
        this.url=url;
        this.title=title;
        this.published=published;
        this.urltoimage=urltoimage;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {
      final String artUrl=url.get(position);
      final String arttitle=title.get(position);
      final String artPublished=published.get(position);
      final String artUrlToImage=urltoimage.get(position);
      holder.newsTitle.setText(arttitle);
      holder.newsDate.setText(artPublished);
      Picasso.get().load(artUrlToImage).into(holder.imageview);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,NewsDetailed.class);
                intent.putExtra("url",artUrl);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return url.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView newsTitle,newsDate;
        ImageView imageview;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsDate=itemView.findViewById(R.id.newDate);
            newsTitle=itemView.findViewById(R.id.newsTitle);
            imageview=itemView.findViewById(R.id.image);
            cardView=itemView.findViewById(R.id.cardview);
        }
    }

}
