/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import br.com.frmichetti.paymobile.android.R;

public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerAdapter.NavigationItemViewHolder> {

    private List<NavDrawerItem> data = Collections.emptyList();

    private LayoutInflater inflater;

    private Context context;

    public NavigationDrawerAdapter(Context context, List<NavDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public NavigationItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_drawer_row, parent, false);
        return new NavigationItemViewHolder(view);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(NavigationItemViewHolder holder, int position) {
        NavDrawerItem current = data.get(position);
        holder.title.setText(current.getTitle());
        holder.imageView.setBackground(current.getResourceId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class NavigationItemViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView imageView;

        private NavigationItemViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            imageView = itemView.findViewById(R.id.imv_nav);

        }
    }
}
