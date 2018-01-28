package br.com.codecode.paymobile.android.rest.dto;

import com.google.gson.annotations.SerializedName;

import br.com.codecode.paymobile.android.rest.BaseJson;

/**
 * Created by felipe on 07/01/18.
 */

public class ValidationErrorDTO extends BaseJson {
    @SerializedName("field")
    public String field;

    @SerializedName("error")
    public String error;

}
