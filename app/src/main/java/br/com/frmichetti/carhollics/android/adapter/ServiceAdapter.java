package br.com.frmichetti.carhollics.android.adapter;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.compatibility.Service;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private Context context;

    private List<Service> services;

    public ServiceAdapter(Context context, List<Service> services) {

        this.context = context;

        this.services = services;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.servico_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Service service = services.get(position);

        holder.title.setText(service.getTitle());

        holder.price.setText(service.getPrice() + " Preço");

        // loading service cover using Glide library
        Glide.with(context).load(service.getThumbnail()).into(holder.thumbnail);

        holder.overflow.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow);
            }
        });
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view) {

        // inflate menu
        PopupMenu popup = new PopupMenu(context, view);

        MenuInflater inflater = popup.getMenuInflater();

        inflater.inflate(R.menu.menu_album, popup.getMenu());

        popup.setOnMenuItemClickListener(new MyMenuItemClickListener());

        popup.show();
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView title, price;

        public ImageView thumbnail, overflow;

        public MyViewHolder(View view) {

            super(view);

            title = (TextView) view.findViewById(R.id.title);

            price = (TextView) view.findViewById(R.id.count);

            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);

            overflow = (ImageView) view.findViewById(R.id.overflow);
        }
    }

    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        public MyMenuItemClickListener() {
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {

            switch (menuItem.getItemId()) {

                case R.id.action_add_to_cart:

                    Toast.makeText(context, "Add to favourite", Toast.LENGTH_SHORT).show();

                    return true;

                case R.id.action_see_more:

                    Toast.makeText(context, "Play next", Toast.LENGTH_SHORT).show();

                    return true;

                default:
            }
            return false;
        }
    }
}