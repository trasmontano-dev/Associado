package br.com.trasmontano.trasmontanoassociadomobile.DTO;

/**
 * Created by rbarbosa on 03/08/2016.
 */
public class AgendaMedica {

    private String Inicio;
    private String Fim;
    private int cdEspecialidade;
    private int cdLocalidade;
    private int cdMedico;
    private String sexo;
    private int idade;
    private boolean tipo;
    private int cdReferencia;

    public String getInicio() {
        return Inicio;
    }

    public void setInicio(String inicio) {
        Inicio = inicio;
    }

    public String getFim() {
        return Fim;
    }

    public void setFim(String fim) {
        Fim = fim;
    }

    public int getCdEspecialidade() {
        return cdEspecialidade;
    }

    public void setCdEspecialidade(int cdEspecialidade) {
        this.cdEspecialidade = cdEspecialidade;
    }

    public int getCdLocalidade() {
        return cdLocalidade;
    }

    public void setCdLocalidade(int cdLocalidade) {
        this.cdLocalidade = cdLocalidade;
    }

    public int getCdMedico() {
        return cdMedico;
    }

    public void setCdMedico(int cdMedico) {
        this.cdMedico = cdMedico;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    public boolean isTipo() {
        return tipo;
    }

    public void setTipo(boolean tipo) {
        this.tipo = tipo;
    }

    public int getCdReferencia() {
        return cdReferencia;
    }

    public void setCdReferencia(int cdReferencia) {
        this.cdReferencia = cdReferencia;
    }
}
