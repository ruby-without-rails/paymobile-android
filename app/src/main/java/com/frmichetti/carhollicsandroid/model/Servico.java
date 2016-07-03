/**
 *
 * @author Felipe Rodrigues Michetti
 * @see http://portfolio-frmichetti.rhcloud.com
 * @see http://codecode.com.br
 * @see mailto:frmichetti@gmail.com
 * */
package com.frmichetti.carhollicsandroid.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.math.BigDecimal;

public class Servico extends Entidade{
    
    private static final long serialVersionUID = 7750552704482144828L;

    @Expose
    @SerializedName("Nome")
    private String nome;

    @Expose
    @SerializedName("Descricao")
    private String descricao;

    @Expose
    @SerializedName("Observacao")
    private String observacao;

    @Expose
    @SerializedName("Duracao")
    private Long duracao;    

    @Expose
    @SerializedName("Preco")
    private BigDecimal preco;

    public Servico() {
	// TODO Auto-generated constructor stub
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Long getDuracao() {
        return duracao;
    }

    public void setDuracao(Long duracao) {
        this.duracao = duracao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

	@Override
	public String toString() {
		return nome ;
	}
    
    
    
}
