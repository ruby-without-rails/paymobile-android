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

import java.util.List;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.model.Servico;


public class ServicoAdapter extends RecyclerView.Adapter<ServicoAdapter.MyViewHolder> {

    private Context context;

    private List<Servico> servicos;

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


    public ServicoAdapter(Context context, List<Servico> servicos) {

        this.context = context;

        this.servicos = servicos;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.servico_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {

        Servico servico = servicos.get(position);

        holder.title.setText(servico.getNome());

        holder.price.setText(servico.getPreco() + " Preço");

        // loading servico cover using Glide library
       // Glide.with(context).load(servico.getThumbnail()).into(holder.thumbnail);

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

    @Override
    public int getItemCount() {
        return servicos.size();
    }
}