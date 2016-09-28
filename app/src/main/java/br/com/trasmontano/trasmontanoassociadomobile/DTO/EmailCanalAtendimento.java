package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rbarbosa on 16/08/2016.
 */
public class EmailCanalAtendimento {

    @SerializedName("nome")
    private String nome;
    @SerializedName("matricula")
    private String matricula;
    @SerializedName("cdDependente")
    private String cdDependente;
    @SerializedName("email")
    private String email;
    @SerializedName("emailDestinatario")
    private String emailDestinatario;
    @SerializedName("assunto")
    private String assunto;
    @SerializedName("mensagem")
    private String mensagem;

    public String getEmailDestinatario() {
        return emailDestinatario;
    }

    public void setEmailDestinatario(String emailDestinatario) {
        this.emailDestinatario = emailDestinatario;
    }



    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCdDependente() {
        return cdDependente;
    }

    public void setCdDependente(String cdDependente) {
        this.cdDependente = cdDependente;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


}
