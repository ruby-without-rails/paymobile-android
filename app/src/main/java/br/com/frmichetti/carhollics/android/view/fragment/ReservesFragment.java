/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.frmichetti.carhollics.android.R;

public class ReservesFragment extends BaseFragment {

    public ReservesFragment(){}

    @Override
    protected void doCastComponents(View rootView) {

    }

    @Override
    protected void doCreateListeners() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_reserve, container, false);


        // Inflate the layout for this fragment
        return rootView;
    }


}
