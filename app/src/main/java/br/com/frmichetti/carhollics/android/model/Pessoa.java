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

import java.util.ArrayList;
import java.util.List;


public abstract class Pessoa extends Entidade{

	private static final long serialVersionUID = -402022521851330710L;

	@Expose
	@SerializedName("Nome")
	private String nome; 

	@Expose
	@SerializedName("Telefone")
	private Long telefone;

	@Expose
	@SerializedName("Endereco")
	private List<Endereco> endereco = new ArrayList<Endereco>();

	@Expose
	@SerializedName("Veiculo")
	private List<Veiculo> veiculo = new ArrayList<Veiculo>();

	@Expose
	@SerializedName("Usuario")
	private Usuario usuario = new Usuario();

	public Pessoa() {
		super();
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}	

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public List<Endereco> getEndereco() {
		return endereco;
	}

	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}

	public void addEndereco(Endereco endereco){	    
		this.endereco.add(endereco);	    
	}

	public void removeEndereco(Endereco endereco){	    
		this.endereco.remove(endereco);	    
	}

	public List<Veiculo> getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(List<Veiculo> veiculo) {
		this.veiculo = veiculo;
	}

	public void addVeiculo(Veiculo veiculo){	
		this.veiculo.add(veiculo);
	}

	public void removeVeiculo(Veiculo veiculo){
		this.veiculo.remove(veiculo);
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}  


	public static Pessoa fromGson(String gson){	

		java.lang.reflect.Type collectionType = new TypeToken<Pessoa>() {}.getType();

		Gson in = new GsonBuilder()
				.excludeFieldsWithoutExposeAnnotation()
				.enableComplexMapKeySerialization()
				.setDateFormat("dd/MM/yyyy")
				.setPrettyPrinting()
				.create();				

		return in.fromJson(gson, collectionType);  



	}

}
