package br.com.frmichetti.paymobile.android.adapter;

/**
 * Created by felipe on 02/01/18.
 */

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.frmichetti.paymobile.android.R;
import br.com.frmichetti.paymobile.android.listener.ItemAdapterListener;
import br.com.frmichetti.paymobile.android.model.compatibility.Product;


public abstract class SimpleCardItemAdapter extends RecyclerView.Adapter<SimpleCardItemAdapter.MyViewHolder> /* implements Filterable */ {

    private ItemAdapterListener itemAdapterListener;
    private Activity activity;
    private List<Product> completeList;
    public List<Product> completeListFiltered;


    public SimpleCardItemAdapter(List<Product> productList, Activity activity, ItemAdapterListener itemAdapterListener) {
        this.activity = activity;
        this.completeList = productList;
        this.completeListFiltered = completeList;
        this.itemAdapterListener = itemAdapterListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.simple_list_item, parent, false);

        // First step to show a custom context menu on text view
        activity.registerForContextMenu(itemView);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Product product = completeList.get(position);

        Glide.with(activity.getApplicationContext())
                .load(product.getImage())
                .into(holder.dirImage);


        holder.fileDirName.setText(product.getName());
    }

    @Override
    public int getItemCount() {
        return completeListFiltered.size();
    }

    /**
     * MyViewHolder
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView dirImage, moreActions;
        public TextView fileDirName;

        public MyViewHolder(View view) {
            super(view);
            this.cardView = view.findViewById(R.id.simple_item_card_view);
            this.dirImage = view.findViewById(R.id.item_image);
            this.moreActions = view.findViewById(R.id.more_actions);
            this.fileDirName = view.findViewById(R.id.item_name);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected item in onResult
                    itemAdapterListener.onItemSelected(completeListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
}