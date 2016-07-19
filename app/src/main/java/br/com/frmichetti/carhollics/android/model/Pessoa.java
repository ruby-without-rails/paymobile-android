/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package br.com.frmichetti.carhollics.android.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public abstract class Pessoa extends Entidade{

    private static final long serialVersionUID = -402022521851330710L;

    @Expose
    @SerializedName("Nome")
    private String nome;

    @Expose
    @SerializedName("Telefone")
    private Long telefone ;

    @Expose
    @SerializedName("Endereco")
    private Endereco endereco = new Endereco();

    @Expose
    @SerializedName("Veiculo")
    private List<Veiculo> veiculo = new ArrayList<>();

    @Expose
    @SerializedName("Usuario")
    private Usuario usuario = new Usuario();

    public Pessoa() {

    }

    public String getNome() {
        return nome;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Veiculo> getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(List<Veiculo> veiculo) {
        this.veiculo = veiculo;
    }
}
