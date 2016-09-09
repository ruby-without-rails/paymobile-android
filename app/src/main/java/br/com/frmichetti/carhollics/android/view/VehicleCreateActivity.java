/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.com.frmichetti.carhollics.android.R;
import br.com.frmichetti.carhollics.android.jobs.AsyncResponse;
import br.com.frmichetti.carhollics.android.jobs.TaskCreateVehicle;
import br.com.frmichetti.carhollics.android.model.compatibility.Customer;
import br.com.frmichetti.carhollics.android.model.compatibility.Vehicle;


public class VehicleCreateActivity extends BaseActivity {

    private EditText editTextName, editTextBrand;

    private Button buttonRegister;

    private Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_vehicle_create);

        doCastComponents();

        doCreateListeners();

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {

        super.onPostCreate(savedInstanceState);

        customer = (Customer) intent.getSerializableExtra("customer");

        doConfigure();

    }

    @Override
    public void doCastComponents() {

        editTextName = (EditText) findViewById(R.id.editTextNomeVeículo);

        editTextBrand = (EditText) findViewById(R.id.editTextMarcaVeiculo);

        buttonRegister = (Button) findViewById(R.id.buttonCadastrarVeiculo);

    }

    @Override
    public void doCreateListeners() {

        buttonRegister.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                TaskCreateVehicle taskCreateVeiculo = new TaskCreateVehicle(context, new AsyncResponse<Vehicle>() {

                    @Override
                    public void processFinish(Vehicle output) {

                        selectedVehicle = output;

                        startActivity(new Intent(context, MainActivity.class)
                                .putExtra("vehicle", selectedVehicle)
                                .putExtra("customer", customer)
                        );

                        finish();


                    }
                });


                vehicle = doFillData();

                taskCreateVeiculo.execute(vehicle);


            }
        });

    }

    @Override
    public void doConfigure() {

        super.doConfigure();

        actionBar.setSubtitle("Cadastrar Veículo");

        vehicle = new Vehicle();

    }


    private Vehicle doFillData() {

        Vehicle v = new Vehicle();

        v.setModel(editTextName.getText().toString());

        v.setBrand(editTextBrand.getText().toString());

        return v;

    }
}
