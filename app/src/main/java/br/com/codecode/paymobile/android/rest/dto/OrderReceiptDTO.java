package br.com.codecode.paymobile.android.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.codecode.paymobile.android.model.compatibility.Order;
import br.com.codecode.paymobile.android.rest.BaseJson;

public class OrderReceiptDTO extends BaseJson {

    @SerializedName("order")
    public Order order;

    @SerializedName("message")
    public String message;

    @SerializedName("validation_errors")
    public List<ValidationErrorDTO> validationErrors;
}