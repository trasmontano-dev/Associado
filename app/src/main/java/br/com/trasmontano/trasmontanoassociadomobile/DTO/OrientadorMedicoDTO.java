package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import se.emilsjolander.sprinkles.annotations.Column;

/**
 * Created by mmaganha on 04/08/2016.
 */
public class OrientadorMedicoDTO {

    private  String CdCategoria;
    private String DsCategoria;

    public String toString() {
        return (this.getDsCategoria());
    }

    public String getCdCategoria() {
        return CdCategoria;
    }

    public void setCdCategoria(String cdCategoria) {
        CdCategoria = cdCategoria;
    }

    public String getDsCategoria() {
        return DsCategoria;
    }

    public void setDsCategoria(String dsCategoria) {
        DsCategoria = dsCategoria;
    }
}
