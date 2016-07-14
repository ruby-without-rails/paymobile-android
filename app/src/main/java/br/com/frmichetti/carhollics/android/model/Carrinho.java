/**
 *
 * @author frmichetti
 * <Felipe Rodrigues Michetti at http://portfolio-frmichetti.rhcloud.com/>
 * Contact frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Carrinho extends Entidade{
	
	private static final long serialVersionUID = -3029522377450022549L;

	@Expose
	@SerializedName("ItensCarrinho")
	private Map<ItemCarrinho, Integer> mapItems = new LinkedHashMap<>();

	public void add(ItemCarrinho shoppingItem) {
		System.out.println(shoppingItem.getServico().getNome() + " Adicionado ao Carrinho" );
		mapItems.put(shoppingItem, getQuantity(shoppingItem) + 1);		
	}

	public void remove(ItemCarrinho shoppingItem) {
		System.out.println(shoppingItem.getServico().getNome() + " Removido do Carrinho" );
		mapItems.remove(shoppingItem);
	}

	public Integer getQuantity(ItemCarrinho shoppingItem) {
		if(!mapItems.containsKey(shoppingItem)){
			mapItems.put(shoppingItem, 0);
		}
		return mapItems.get(shoppingItem);
	}

	public Integer getQuantity(){
		//TODO FIXME downgrade to JAVA 6
		//return mapItems.values().stream().reduce(0, (next,accumulator)-> next + accumulator);
		return 0;
	}

	public Collection<ItemCarrinho> getList(){	
		return new ArrayList<>(mapItems.keySet());
	}

	public BigDecimal getTotal(ItemCarrinho shoppingItem){
		return shoppingItem.getTotal(getQuantity(shoppingItem));
	}

	public BigDecimal getTotal(){

		BigDecimal total = BigDecimal.ZERO;

		for(ItemCarrinho shoppingItem : mapItems.keySet()){
			total = total.add(getTotal(shoppingItem));
		}

		return total;
	}


	public boolean isEmpty(){
		return mapItems.isEmpty();
	}

	public void emptyCart(){
		mapItems.clear();		
	}
	
	public Carrinho() {
		System.out.println("[Criando Instancia de " + this.getClass().getSimpleName() + "]");
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

	@Override
	public String toString() {
		return mapItems.toString() ;
	}

	public String toJson() {

		//JsonArrayBuilder itens = Json.createArrayBuilder();

		StringBuilder sb = new StringBuilder();

		for (ItemCarrinho shoppingItem : getList()){

			sb.append("Nome")
					.append(shoppingItem.getServico().getNome())
					.append("Preco")
					.append(shoppingItem.getServico().getPreco())
					.append("Quantidade")
					.append(getQuantity(shoppingItem).intValue())
					.append("Total")
                    .append(getTotal(shoppingItem));

		/*	itens.add(Json.createObjectBuilder()
					.add("nome", shoppingItem.getServico().getNome())
					.add("preco", shoppingItem.getServico().getPreco())
					.add("quantidade", getQuantity(shoppingItem).intValue())
					.add("total", getTotal(shoppingItem))
			);
			*/
		}

        return sb.toString();
		//return itens.build().toString();

	}
}
