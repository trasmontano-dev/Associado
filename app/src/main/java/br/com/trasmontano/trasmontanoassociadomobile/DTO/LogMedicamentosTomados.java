package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by rbarbosa on 28/07/2016.
 */
@Table("logMedicamentosTomados")
public class LogMedicamentosTomados extends Model {

    @Key
    @Column("id")
    @AutoIncrement
    private long id;
    @Column("idAlarme")
    private long idAlarme;
    @Column("usuario")
    private String usuario;
    @Column("dataTomouMedicamento")
    private String dataTomouMedicamento;
    @Column("horaTomouMedicamento")
    private String horaTomouMedicamento;
    @Column("nomeMedicamento")
    private String nomeMedicamento;
    @Column("tomei")
    private int tomei;

    public int getTomei() {
        return tomei;
    }

    public void setTomei(int tomei) {
        this.tomei = tomei;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdAlarme() {
        return idAlarme;
    }

    public void setIdAlarme(long idAlarme) {
        this.idAlarme = idAlarme;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getDataTomouMedicamento() {
        return dataTomouMedicamento;
    }

    public void setDataTomouMedicamento(String dataTomouMedicamento) {
        this.dataTomouMedicamento = dataTomouMedicamento;
    }

    public String getHoraTomouMedicamento() {
        return horaTomouMedicamento;
    }

    public void setHoraTomouMedicamento(String horaTomouMedicamento) {
        this.horaTomouMedicamento = horaTomouMedicamento;
    }
}
