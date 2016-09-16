/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.model;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.frmichetti.carhollics.android.model.compatibility.Service;

/**
 * ShoppingItem Model
 *
 * @author felipe
 * @since 1.0
 */
public class ShoppingItem implements Serializable {

    private Long serviceId;

    private Service service;

    private ShoppingItem() {
        super();
    }

    public ShoppingItem(Service service) {
        this.service = service;
        this.serviceId = service.getId();
    }

    public Service getService() {
        return service;
    }

    public BigDecimal getPrice() {
        return service.getPrice();
    }

    public Long getProductId() {
        return serviceId;
    }

    public BigDecimal getTotal(Integer quantity) {
        return getPrice().multiply(new BigDecimal(quantity));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((serviceId == null) ? 0 : serviceId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        ShoppingItem other = (ShoppingItem) obj;
        if (serviceId == null) {
            if (other.serviceId != null)
                return false;
        } else if (!serviceId.equals(other.serviceId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return service.toString();
    }


}
