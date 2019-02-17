package br.com.codecode.paymobile.android.adapter;

/**
 * Created by felipe on 06/01/18.
 */

import android.app.Activity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.listener.ItemAdapterListener;
import br.com.codecode.paymobile.android.model.compatibility.Product;

/**
 * RecyclerView adapter class to render items
 * This class can go into another separate class, but for simplicity
 */
public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.StoreItemViewHolder> implements Filterable {
    private Activity activity;
    private List<Product> completeList, completeListFiltered;
    private ItemAdapterListener itemAdapterListener;

    public StoreAdapter(List<Product> completeList, Activity activity, ItemAdapterListener itemAdapterListener) {
        this.activity = activity;
        this.completeList = completeList;
        this.completeListFiltered = completeList;
        this.itemAdapterListener = itemAdapterListener;
    }

    public void updateListData(List<Product> productList) {
        this.completeList.clear();
        this.completeListFiltered.clear();
        this.completeList.addAll(productList);
        this.completeListFiltered.addAll(productList);
        notifyDataSetChanged();
    }

    @Override
    public StoreItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.store_item_row, parent, false);

        return new StoreItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(StoreItemViewHolder holder, final int position) {
        final Product product = completeListFiltered.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(String.valueOf(product.getPrice()));

        Glide.with(activity.getApplicationContext())
                .load(product.getImage())
                .into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return completeListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    completeListFiltered = completeList;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product row : completeList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.getName().toLowerCase().contains(charString.toLowerCase()) || row.getName().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    completeListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = completeListFiltered;
                filterResults.count = completeListFiltered.size();
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                completeListFiltered = (List<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class StoreItemViewHolder extends RecyclerView.ViewHolder {
        public CardView cardView;
        public ImageView thumbnail;
        public TextView name, price;

        public StoreItemViewHolder(View view) {
            super(view);
            cardView = view.findViewById(R.id.card_view_store_item);
            thumbnail = view.findViewById(R.id.imv_thumbnail);

            name = view.findViewById(R.id.tv_name);
            price = view.findViewById(R.id.tv_price);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // send selected item in onResult
                    itemAdapterListener.onItemSelected(completeListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }
}
