/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://www.codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.math.BigDecimal;
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

	@Expose
	@SerializedName("Veiculo")
	private Veiculo veiculo;	

	@Expose
	@SerializedName("Endereco")
	private Endereco endereco;

	public Pedido() {
		super();
	}

	public Pedido(Cliente cliente,Carrinho carrinho){
		this.setCliente(cliente);
		this.setEndereco(endereco);
		this.setVeiculo(veiculo);
		this.setCarrinho(carrinho.toJson());
		this.setTotal(carrinho.getTotal());
	}

	public Pedido(Cliente cliente,Endereco endereco,Veiculo veiculo,Carrinho carrinho){
		this.setCliente(cliente);		
		this.setEndereco(endereco);		
		this.setVeiculo(veiculo);
		this.setCarrinho(carrinho.toJson());
		this.setTotal(carrinho.getTotal());
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

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}


	public static Pedido fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Pedido>() {}.getType();

		Gson in = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();		 		

		return in.fromJson(gson, collectionType);  

	}

	@Override
	public String toString() {
		return uuid;
	}
}
