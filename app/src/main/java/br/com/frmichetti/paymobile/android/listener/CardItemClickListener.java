package br.com.frmichetti.paymobile.android.listener;

/**
 * Created by felipe on 06/01/18.
 */

import android.view.View;

public interface CardItemClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}