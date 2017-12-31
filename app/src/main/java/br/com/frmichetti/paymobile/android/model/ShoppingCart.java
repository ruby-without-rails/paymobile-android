/**
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 */
package br.com.frmichetti.paymobile.android.model;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class ShoppingCart implements Serializable {

    private Map<ShoppingItem, Integer> mapItems = new LinkedHashMap<>();

    public ShoppingCart() {
    }

    public Integer getQuantityOfItens(ShoppingItem shoppingItem) {

        if (!mapItems.containsKey(shoppingItem)) {
            mapItems.put(shoppingItem, 0);
        }
        return mapItems.get(shoppingItem);
    }

    public Integer getQuantity() {

        int accumulator = 0;

        for (int shoppingItem : mapItems.values()) {
            accumulator += shoppingItem;
        }

        /* TODO FIXME to java > 7
        return mapItems.values().stream()
				.reduce(0, (next,accumulator)-> next + accumulator);*/

        return accumulator;
    }

    public Collection<ShoppingItem> getList() {
        return new ArrayList<>(mapItems.keySet());
    }

    public BigDecimal getTotal(ShoppingItem shoppingItem) {
        return shoppingItem.getTotal(getQuantityOfItens(shoppingItem));
    }

    public BigDecimal getTotal() {
        BigDecimal total = BigDecimal.ZERO;

        for (ShoppingItem shoppingItem : mapItems.keySet()) {
            total = total.add(getTotal(shoppingItem));
        }
        return total;
    }

    public void add(ShoppingItem shoppingItem) {
        mapItems.put(shoppingItem, getQuantityOfItens(shoppingItem) + 1);
    }

    public void remove(ShoppingItem shoppingItem) {
        mapItems.remove(shoppingItem);
    }

    public boolean isEmpty() {
        return mapItems.isEmpty();
    }

    public void emptyCart() {
        mapItems.clear();
    }


    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mapItems == null) ? 0 : mapItems.hashCode());
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
        ShoppingCart other = (ShoppingCart) obj;
        if (mapItems == null) {
            if (other.mapItems != null)
                return false;
        } else if (!mapItems.equals(other.mapItems))
            return false;
        return true;
    }

    public String toJson() {

        JSONArray itens = new JSONArray();

        for (ShoppingItem shoppingItem : getList()) {

            try {
                itens.put(new JSONObject()
                        .put("title", shoppingItem.getService().getTitle())
                        .put("price", shoppingItem.getService().getPrice())
                        .put("quantity", getQuantityOfItens(shoppingItem).intValue())
                        .put("total", getTotal(shoppingItem))
                );
            } catch (JSONException e) {
                Log.e("JSON-CONVERSION", "Não foi possível criar objeto Json", e);
            }
        }

        return itens.toString();
    }
}