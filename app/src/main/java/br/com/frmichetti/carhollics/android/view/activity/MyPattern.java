/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view.activity;

import android.content.Context;

public interface MyPattern {

    void doCastComponents();

    void doCreateListeners();

    void setupToolBar();

    void doConfigure();

    void doChangeActivity(Context context, Class clazz);

}
