package br.com.codecode.paymobile.android.dto;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import br.com.codecode.paymobile.android.model.compatibility.Order;

/**
 * Created by felipe on 28/01/18.
 */

public class OrderDTO implements Serializable{

    @SerializedName("order")
    public Order order;

    @SerializedName("message")
    public String message;

    @SerializedName("validation_errors")
    public List<ValidationErrorDTO> validationErrors;
}
