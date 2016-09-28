package br.com.trasmontano.trasmontanoassociadomobile.DTO;

/**
 * Created by rbarbosa on 01/08/2016.
 */
public class AgendaMedicaAssociado {
    private  long ID;
    private String DataHoraAgendamento;
    private String Dependente;
    private String dsEspecialidade;
    private String dsLocalidade;
    private String dtAgendamento;
    private String IdAgenda;
    private String Matricula;
    private String NmMedico;
    private String Protocolo;
    private String Situacao;

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public String getDataHoraAgendamento() {
        return DataHoraAgendamento;
    }

    public void setDataHoraAgendamento(String dataHoraAgendamento) {
        DataHoraAgendamento = dataHoraAgendamento;
    }

    public String getDependente() {
        return Dependente;
    }

    public void setDependente(String dependente) {
        Dependente = dependente;
    }

    public String getDsEspecialidade() {
        return dsEspecialidade;
    }

    public void setDsEspecialidade(String dsEspecialidade) {
        this.dsEspecialidade = dsEspecialidade;
    }

    public String getDsLocalidade() {
        return dsLocalidade;
    }

    public void setDsLocalidade(String dsLocalidade) {
        this.dsLocalidade = dsLocalidade;
    }

    public String getDtAgendamento() {
        return dtAgendamento;
    }

    public void setDtAgendamento(String dtAgendamento) {
        this.dtAgendamento = dtAgendamento;
    }

    public String getIdAgenda() {
        return IdAgenda;
    }

    public void setIdAgenda(String idAgenda) {
        IdAgenda = idAgenda;
    }

    public String getMatricula() {
        return Matricula;
    }

    public void setMatricula(String matricula) {
        Matricula = matricula;
    }

    public String getNmMedico() {
        return NmMedico;
    }

    public void setNmMedico(String nmMedico) {
        NmMedico = nmMedico;
    }

    public String getProtocolo() {
        return Protocolo;
    }

    public void setProtocolo(String protocolo) {
        Protocolo = protocolo;
    }

    public String getSituacao() {
        return Situacao;
    }

    public void setSituacao(String situacao) {
        Situacao = situacao;
    }
}
