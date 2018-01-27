package br.com.codecode.paymobile.android.dto;

import com.google.gson.annotations.SerializedName;

/**
 * Created by felipe on 07/01/18.
 */

public class ValidationErrorDTO {
    @SerializedName("field")
    public String field;

    @SerializedName("error")
    public String error;

}
