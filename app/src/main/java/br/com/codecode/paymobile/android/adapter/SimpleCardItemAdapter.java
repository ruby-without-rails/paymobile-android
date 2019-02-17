package br.com.codecode.paymobile.android.adapter;

/**
 * Created by felipe on 02/01/18.
 */

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.listener.ItemAdapterListener;
import br.com.codecode.paymobile.android.listener.SimpleItemSelectionListener;
import br.com.codecode.paymobile.android.model.compatibility.Product;


public abstract class SimpleCardItemAdapter extends RecyclerView.Adapter<SimpleCardItemAdapter.MyViewHolder> implements Filterable {

    private ItemAdapterListener itemAdapterListener;
    private SimpleItemSelectionListener simpleItemSelectionListener;

    private Activity activity;
    private List<Product> completeList;
    private List<Product> completeListFiltered;
    private PopupMenu popup;


    public SimpleCardItemAdapter(List<Product> productList, Activity activity, ItemAdapterListener itemAdapterListener, SimpleItemSelectionListener simpleItemSelectionListener) {
        this.activity = activity;
        this.completeList = productList;
        this.completeListFiltered = productList;
        this.itemAdapterListener = itemAdapterListener;
        this.simpleItemSelectionListener = simpleItemSelectionListener;
    }

    public void updateListData(List<Product> productList) {
        this.completeList.clear();
        this.completeListFiltered.clear();
        this.completeList.addAll(productList);
        this.completeListFiltered.addAll(productList);
        notifyDataSetChanged();
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
    public class MyViewHolder extends RecyclerView.ViewHolder implements PopupMenu.OnMenuItemClickListener, PopupMenu.OnDismissListener {
        public ImageView dirImage, moreActions;
        public TextView fileDirName;
        private Product selectedProduct;

        public MyViewHolder(View view) {
            super(view);
            this.dirImage = view.findViewById(R.id.item_image);
            this.moreActions = view.findViewById(R.id.more_actions);
            this.fileDirName = view.findViewById(R.id.item_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected item in onResult
                    itemAdapterListener.onItemSelected(completeListFiltered.get(getAdapterPosition()));
                }
            });

            this.moreActions.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedProduct = completeListFiltered.get(getAdapterPosition());
                    showPopupMenu(v, selectedProduct);
                }
            });
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            int id = item.getItemId();
            Product selectedProduct = completeListFiltered.get(getAdapterPosition());

            // Handle the menu item selection
            if (id == R.id.action_details) {
                simpleItemSelectionListener.onDetails(selectedProduct);
                this.onDismiss(popup);
                return true;
            } else if (id == R.id.action_share) {
                simpleItemSelectionListener.onShare(selectedProduct, item);
                this.onDismiss(popup);
                return true;
            } else if (id == R.id.action_photo_library) {
                simpleItemSelectionListener.onGallery(selectedProduct);
                this.onDismiss(popup);
                return true;
            } else {
                return false;
            }
        }

        @Override
        public void onDismiss(PopupMenu menu) {
            menu.dismiss();
            popup = null;
        }

        /**
         * Showing popup menu when tapping on 3 dots
         */
        private void showPopupMenu(View view, Product product) {
            if (product != null) {
                if (popup == null) {
                    popup = new PopupMenu(activity, view);
                }
                // inflate menu
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.simple_item_context_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(this);
                popup.setOnDismissListener(this);
                popup.show();
            }
        }
    }
}