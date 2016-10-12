package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by mmaganha on 12/08/2016.
 */
public class DTOParametrosOrientador implements Serializable {
    @SerializedName("regiao")
    private String regiao;
    @SerializedName("cidade")
    private String cidade;
    @SerializedName("bairro")
    private String bairro;
    @SerializedName("especialidade")
    private String especialidade;
    @SerializedName("nomeFantasia")
    private String nomeFantasia;
    @SerializedName("tipoAtendimento")
    private String tipoAtendimento;
    @SerializedName("dsCategoria")
    private String dsCategoria;
    @SerializedName("ordem")
    private String ordem;
    @SerializedName("cNPJCPF")
    private String cNPJCPF;
    @SerializedName("cRM")
    private String cRM;
    @SerializedName("cdCategoria")
    private String cdCategoria;

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getNomeFantasia() {
        return nomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        this.nomeFantasia = nomeFantasia;
    }

    public String getTipoAtendimento() {
        return tipoAtendimento;
    }

    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
    }

    public String getDsCategoria() {
        return dsCategoria;
    }

    public void setDsCategoria(String dsCategoria) {
        this.dsCategoria = dsCategoria;
    }

    public String getOrdem() {
        return ordem;
    }

    public void setOrdem(String ordem) {
        this.ordem = ordem;
    }

    public String getcNPJCPF() {
        return cNPJCPF;
    }

    public void setcNPJCPF(String cNPJCPF) {
        this.cNPJCPF = cNPJCPF;
    }

    public String getcRM() {
        return cRM;
    }

    public void setcRM(String cRM) {
        this.cRM = cRM;
    }

    public String getCdCategoria() {
        return cdCategoria;
    }

    public void setCdCategoria(String cdCategoria) {
        this.cdCategoria = cdCategoria;
    }
}
