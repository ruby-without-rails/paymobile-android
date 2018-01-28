package br.com.codecode.paymobile.android.rest.dto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import br.com.codecode.paymobile.android.model.Token;
import br.com.codecode.paymobile.android.model.compatibility.Customer;
import br.com.codecode.paymobile.android.rest.BaseJson;

/**
 * Created by felipe on 01/01/18.
 */
public class CustomerDTO extends BaseJson{

    @SerializedName("customer")
    public Customer customer;

    @SerializedName("token")
    public Token token;

    @SerializedName("message")
    public String message;

    @SerializedName("validation_errors")
    public List<ValidationErrorDTO> validationErrors;
}
