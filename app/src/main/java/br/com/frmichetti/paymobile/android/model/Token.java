/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.model;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import br.com.frmichetti.paymobile.android.helper.ApplicationDateFormater;

/**
 * Token Model
 *
 * @author felipe
 * @since 1.0
 */
public class Token implements Serializable {

    //@SerializedName("expires_at")
    private Date expiresAt;

    @SerializedName("key")
    private String key;

    public Token() {
    }

    public Token(JSONObject jsonObject) {
        try {
            this.key = jsonObject.has("key") ? jsonObject.getString("key") : jsonObject.getString("token");
            SimpleDateFormat formatter = ApplicationDateFormater.getDefaultFormater();
            this.expiresAt = jsonObject.has("expires_at") ? formatter.parse(jsonObject.getString("expires_at")) : new Date();
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getExpiresAt() {
        return expiresAt;
    }

    private void setExpiresAt(Date expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Token [expiresAt=" + expiresAt + ", key=" + key + "]";
    }

}
