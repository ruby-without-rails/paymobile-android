/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.codecode.paymobile.android.model;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.codecode.paymobile.android.model.compatibility.Product;

/**
 * ShoppingItem Model
 *
 * @author felipe
 * @since 1.0
 */
public class ShoppingItem implements Serializable {

    private Long productId;

    private Product service;

    private ShoppingItem() {
        super();
    }

    public ShoppingItem(Product product) {
        this.service = product;
        this.productId = product.getId();
    }

    public Product getProduct() {
        return service;
    }

    public BigDecimal getPrice() {
        return service.getPrice();
    }

    public Long getProductId() {
        return productId;
    }

    public BigDecimal getTotal(Integer quantity) {
        return getPrice().multiply(new BigDecimal(quantity));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((productId == null) ? 0 : productId.hashCode());
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
        if (productId == null) {
            if (other.productId != null)
                return false;
        } else if (!productId.equals(other.productId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return service.toString();
    }


}
