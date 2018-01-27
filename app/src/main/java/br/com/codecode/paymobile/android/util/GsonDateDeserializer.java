/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.codecode.paymobile.android.helper.ApplicationDateFormater;

public class GsonDateDeserializer implements JsonDeserializer<Date> {

    @Override
    public Date deserialize(JsonElement element, Type arg1, JsonDeserializationContext arg2) throws JsonParseException {

        String date = element.getAsString();

        SimpleDateFormat formatter = ApplicationDateFormater.getDefaultFormater();

        try {

            return formatter.parse(date);

        } catch (ParseException e) {

            e.printStackTrace();

            return null;
        }
    }

}
