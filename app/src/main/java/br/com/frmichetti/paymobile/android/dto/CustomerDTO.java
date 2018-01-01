package br.com.frmichetti.paymobile.android.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import br.com.frmichetti.paymobile.android.model.compatibility.Customer;

/**
 * Created by felipe on 01/01/18.
 */
public class CustomerDTO implements Serializable{
    @SerializedName("customer")
    public Customer customer;
}
