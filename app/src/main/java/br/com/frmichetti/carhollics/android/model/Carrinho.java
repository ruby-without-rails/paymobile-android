/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model;

import android.annotation.TargetApi;
import android.os.Build;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BinaryOperator;


public class Carrinho implements Serializable {

    private static final long serialVersionUID = -2804186734546390662L;

    @Expose
    @SerializedName("ItensCarrinho")
    private Map<ItemCarrinho, Integer> mapItems = new LinkedHashMap<>();

    public Carrinho(){
        super();
    }

    public void add(ItemCarrinho shoppingItem) {

        System.out.println(shoppingItem.getServico().getNome() + "Adicionado ao Carrinho " );

        mapItems.put(shoppingItem, getQuantidade(shoppingItem) + 1);
    }

    public Integer getQuantidade(ItemCarrinho shoppingItem) {

        if(!mapItems.containsKey(shoppingItem)){
            mapItems.put(shoppingItem, 0);
        }
        return mapItems.get(shoppingItem);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public Integer getQuantidade(){
        int quant = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

        /*    quant = mapItems.values().stream()
                    .reduce(0, (next,accumulator)-> next + accumulator);*/

        }else{

            quant = mapItems.values().stream().reduce(0, new BinaryOperator<Integer>() {

                @Override
                public Integer apply(Integer next, Integer accumulator) {
                    return next + accumulator;
                }
            });
        }
        return quant;
    }

    public Collection<ItemCarrinho> getList(){
        return new ArrayList<ItemCarrinho>(mapItems.keySet());
    }

    public BigDecimal getTotal(ItemCarrinho shoppingItem){
        return shoppingItem.getTotal(getQuantidade(shoppingItem));
    }

    public BigDecimal getTotal(){
        BigDecimal total = BigDecimal.ZERO;

        for(ItemCarrinho shoppingItem : mapItems.keySet()){
            total = total.add(getTotal(shoppingItem));
        }
        return total;
    }

    public void remove(ItemCarrinho shoppingItem) {
        mapItems.remove(shoppingItem);
    }

    public boolean isEmpty(){
        return mapItems.isEmpty();
    }

    public void emptyCart(){
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
        Carrinho other = (Carrinho) obj;
        if (mapItems == null) {
            if (other.mapItems != null)
                return false;
        } else if (!mapItems.equals(other.mapItems))
            return false;
        return true;
    }

    public String toJson() {

        JsonArray itens = new JsonArray();

        JsonObject object = new JsonObject();

        for (ItemCarrinho itemCarrinho : getList()){

            object.addProperty("nome", itemCarrinho.getServico().getNome());
            object.addProperty("preco", itemCarrinho.getServico().getPreco());
            object.addProperty("quantidade", getQuantidade(itemCarrinho));
            object.addProperty("total", getTotal(itemCarrinho));

            itens.add(object);


        }

        return itens.toString();
    }

    public static Carrinho fromGson(String gson){

        java.lang.reflect.Type collectionType = new TypeToken<Carrinho>() {}.getType();

        Gson in = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .enableComplexMapKeySerialization()
                .setDateFormat("dd/MM/yyyy")
                .setPrettyPrinting()
                .create();

        return in.fromJson(gson, collectionType);

    }

}
