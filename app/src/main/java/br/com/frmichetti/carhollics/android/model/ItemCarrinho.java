/**
 *
 * @author frmichetti
 * <Felipe Rodrigues Michetti at http://portfolio-frmichetti.rhcloud.com/>
 * Contact frmichetti@gmail.com
 */
package br.com.frmichetti.carhollics.android.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ItemCarrinho implements Serializable{

	private static final long serialVersionUID = -3480230394888070089L;

	private Long servicoId;

	private Servico servico;

	public ItemCarrinho(Servico servico) {
		this.servico = servico;
		this.servicoId = servico.getId();
	}

	public static ItemCarrinho shoppingItem() {
		Servico servico = new Servico();
		servico.setPreco(BigDecimal.ZERO);
		return new ItemCarrinho(servico);
	}

	public Servico getServico() {
		return servico;
	}

	public BigDecimal getPrice(){
		return servico.getPreco();
	}

	public Long getProductId(){
		return servicoId;
	}

	public BigDecimal getTotal(Integer quantity) {
		return getPrice().multiply(new BigDecimal(quantity));
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((servicoId == null) ? 0 : servicoId.hashCode());
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
		ItemCarrinho other = (ItemCarrinho) obj;
		if (servicoId == null) {
			if (other.servicoId != null)
				return false;
		} else if (!servicoId.equals(other.servicoId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return servico.toString();
	}
}
