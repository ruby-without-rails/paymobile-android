/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.model;

import java.util.Date;

/**
 * Token Model
 *
 * @author felipe
 * @since 1.0
 * @version
 */
public class Token {

    private Date createdAt = new Date();

    private String key;

    public Token() {
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    private void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Token [createdAt=" + createdAt + ", key=" + key + "]";
    }

}
