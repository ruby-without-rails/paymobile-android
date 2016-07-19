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
import java.text.DateFormat;
import java.util.Date;
import java.util.UUID;


public class Pedido extends Entidade{	

	private static final long serialVersionUID = -439111737464114445L;

	@Expose
	@SerializedName("UUID")
	private String uuid;

	@Expose
	@SerializedName("Cliente")
	private Cliente cliente;

	@Expose
	@SerializedName("DataCompra")
	private Date dataCompra ;

	@Expose
	@SerializedName("Total")
	private BigDecimal total;

	@Expose
	@SerializedName("Carrinho")
	private String carrinho;

	public Pedido() {
		// TODO Auto-generated constructor stub
	}
	
	public Pedido(Cliente buyer,Carrinho shoppingCart){
		this.setCliente(buyer);
		this.setTotal(shoppingCart.getTotal());
		this.setCarrinho(shoppingCart.toGson());
	}

	public void generateUUID(){
		this.uuid = UUID.randomUUID().toString();		
		dataCompra = new Date();
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getCarrinho() {
		return carrinho;
	}

	public void setCarrinho(String carrinho) {
		this.carrinho = carrinho;
	}

	public Date getDataCompra() {
		return dataCompra;
	}

	public void setDataCompra(Date dataCompra) {
		this.dataCompra = dataCompra;
	}

	@Override
	public String toString() {
		return "uuid='" + uuid + "' dataCompra=" + dataCompra +	", total=" + total ;
	}
}
