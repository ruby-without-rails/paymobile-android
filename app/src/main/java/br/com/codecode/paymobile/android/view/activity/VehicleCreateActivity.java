/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.codecode.paymobile.android.R;
import br.com.codecode.paymobile.android.model.compatibility.Vehicle;


public class VehicleCreateActivity extends BaseActivity {

    private EditText editTextName, editTextBrand;

    private Button buttonRegister;

    private Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vehicle_create);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

    }

    @Override
    public void doCastComponents() {

        editTextName = findViewById(R.id.editTextNomeVeículo);

        editTextBrand = findViewById(R.id.editTextMarcaVeiculo);

        buttonRegister = findViewById(R.id.buttonCadastrarVeiculo);

    }

    @Override
    public void doCreateListeners() {

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

/*
                TaskCreateAddress taskCreateVeiculo = new TaskCreateAddress(context, new AsyncResponse<Vehicle>() {

                    @Override
                    public void onSuccess(Vehicle output) {

                        selectedVehicle = output;

                        startActivity(new Intent(context, MainActivity.class)
                                .putExtra(VEHICLE_BUNDLE_KEY, selectedVehicle)
                                .putExtra(CUSTOMER_BUNDLE_KEY, customer)
                        );

                        finish();


                    }
                });
*/

                vehicle = doFillData();

//                taskCreateVeiculo.execute(vehicle);


            }
        });

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        if (actionBar != null) {
            actionBar.setSubtitle("Cadastrar Veículo");
        }

        vehicle = new Vehicle();

    }


    private Vehicle doFillData() {

        Vehicle v = new Vehicle();

        v.setModel(editTextName.getText().toString());

        v.setBrand(editTextBrand.getText().toString());

        return v;

    }

    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {

    }
}
