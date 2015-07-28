package com.masum.recyclerviewexample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by masum on 28/07/15.
 */
public class InformationAdapter extends RecyclerView.Adapter<InformationAdapter.MyViewHolder> {

    private LayoutInflater inflater;
    private List<Information> data = Collections.emptyList();
    Context context;

    public  InformationAdapter(Context context, List<Information> data ){
        inflater = LayoutInflater.from(context);
        this.data = data;
        this.context = context;
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
        // view is the root view of of custom_row.xml meaning view is the RelativeLayout
        View view = inflater.inflate(R.layout.custom_row, viewGroup, false);
        final MyViewHolder holder = new MyViewHolder(view);
        //Log.i(MainActivity.TAG, "onCreateViewHolder called.");
        return holder;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void deleteItem(int position){
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder myViewHolder, final int position) {
        Information currentItem = data.get(position);
        myViewHolder.textView.setText(currentItem.title);
        myViewHolder.imageView.setImageResource(currentItem.iconId);
        /*
        First way to handle on item clik.
        myViewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "you clicked: "+position+" | text: " + myViewHolder.textView.getText(), Toast.LENGTH_LONG).show();
            }
        });
        */
        //Log.i(MainActivity.TAG, "onBindViewHolder called position: "+position);

    }
    // second way to handle on item click.
    class MyViewHolder extends RecyclerView.ViewHolder /*implements View.OnClickListener*/ {

        TextView textView;
        ImageView imageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.recyclerTextView);
            imageView = (ImageView) itemView.findViewById(R.id.recyclerImageViw);
            // second way to handle on item click.
            //imageView.setOnClickListener(this);
        }
        /*
        // second way to handle on item click.
        @Override
        public void onClick(View v) {
            // we must use getAdapterPosition to get current position. Coz position changes.
            Toast.makeText(context, "you clicked: "+ getAdapterPosition(), Toast.LENGTH_LONG).show();
            deleteItem(getAdapterPosition());
        }
        */
    }
}
