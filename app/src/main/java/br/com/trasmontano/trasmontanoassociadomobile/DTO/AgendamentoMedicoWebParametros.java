package br.com.trasmontano.trasmontanoassociadomobile.DTO;

/**
 * Created by rbarbosa on 02/08/2016.
 */
public class AgendamentoMedicoWebParametros {
    private String CBOS;
    private String cdDescricao;

    public String getDsDescricao() {
        return dsDescricao;
    }

    public void setDsDescricao(String dsDescricao) {
        this.dsDescricao = dsDescricao;
    }

    public String getCdDescricao() {
        return cdDescricao;
    }

    public void setCdDescricao(String cdDescricao) {
        this.cdDescricao = cdDescricao;
    }

    public String getCBOS() {
        return CBOS;
    }

    public void setCBOS(String CBOS) {
        this.CBOS = CBOS;
    }

    private String dsDescricao;



}
