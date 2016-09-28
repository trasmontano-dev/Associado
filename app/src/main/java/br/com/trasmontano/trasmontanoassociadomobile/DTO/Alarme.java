package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by rbarbosa on 19/07/2016.
 */
@Table("alarme")
public class Alarme extends Model {


    @Key
    @Column("id")
    @AutoIncrement
    private long id;
    @Column("nomeMedicamento")
    private String nomeMedicamento;
    @Column("nomePaciente")
    private String nomePaciente;
    @Column("nomeMusica")
    private String nomeMusica;
    @Column("descricaoMedicamento")
    private String descricaoMedicamento;
    @Column("dosagem")
    private String dosagem;
    @Column("quantidade")
    private String quantidade;
    @Column("dataInicio")
    private String dataInicio;
    @Column("horaInicio")
    private String horaInicio;
    @Column("intervaloDe")
    private String intervaloDe;
    @Column("ativo")
    private int ativo;
    @Column("vibrar")
    private int vibrar;
    @Column("horarios")
    private String horarios;
    @Column("tocar")
    private int tocar;

    public int getTocar() {
        return tocar;
    }

    public void setTocar(int tocar) {
        this.tocar = tocar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNomeMedicamento() {
        return nomeMedicamento;
    }

    public void setNomeMedicamento(String nomeMedicamento) {
        this.nomeMedicamento = nomeMedicamento;
    }

    public String getDescricaoMedicamento() {
        return descricaoMedicamento;
    }

    public void setDescricaoMedicamento(String descricaoMedicamento) {
        this.descricaoMedicamento = descricaoMedicamento;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(String quantidade) {
        this.quantidade = quantidade;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public String getIntervaloDe() {
        return intervaloDe;
    }

    public void setIntervaloDe(String intervaloDe) {
        this.intervaloDe = intervaloDe;
    }

    public int getAtivo() {
        return ativo;
    }

    public void setAtivo(int ativo) {
        this.ativo = ativo;
    }


    public String getHorarios() {
        return horarios;
    }

    public void setHorarios(String horarios) {
        this.horarios = horarios;
    }


    public int getVibrar() {
        return vibrar;
    }

    public void setVibrar(int vibrar) {
        this.vibrar = vibrar;
    }

    public String getNomeMusica() {
        return nomeMusica;
    }

    public void setNomeMusica(String nomeMusica) {
        this.nomeMusica = nomeMusica;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }


}
