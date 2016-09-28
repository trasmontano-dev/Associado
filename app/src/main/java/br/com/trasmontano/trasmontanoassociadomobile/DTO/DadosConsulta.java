package br.com.trasmontano.trasmontanoassociadomobile.DTO;

/**
 * Created by rbarbosa on 03/08/2016.
 */
public class DadosConsulta {

    private String Bairro;
    private String Cbos;
    private String cdMedico;
    private String Cep;
    private String Cidade;
    private String DataHoraAgendamento;
    private String dsLocalidade;
    private String Endereco;

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getCbos() {
        return Cbos;
    }

    public void setCbos(String cbos) {
        Cbos = cbos;
    }

    public String getCdMedico() {
        return cdMedico;
    }

    public void setCdMedico(String cdMedico) {
        this.cdMedico = cdMedico;
    }

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getDataHoraAgendamento() {
        return DataHoraAgendamento;
    }

    public void setDataHoraAgendamento(String dataHoraAgendamento) {
        DataHoraAgendamento = dataHoraAgendamento;
    }

    public String getDsLocalidade() {
        return dsLocalidade;
    }

    public void setDsLocalidade(String dsLocalidade) {
        this.dsLocalidade = dsLocalidade;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIdAgenda() {
        return IdAgenda;
    }

    public void setIdAgenda(String idAgenda) {
        IdAgenda = idAgenda;
    }

    public String getLimiteConsAnual() {
        return LimiteConsAnual;
    }

    public void setLimiteConsAnual(String limiteConsAnual) {
        LimiteConsAnual = limiteConsAnual;
    }

    public String getNmMedico() {
        return nmMedico;
    }

    public void setNmMedico(String nmMedico) {
        this.nmMedico = nmMedico;
    }

    public String getNroLogr() {
        return NroLogr;
    }

    public void setNroLogr(String nroLogr) {
        NroLogr = nroLogr;
    }

    private String ID;
    private String IdAgenda;
    private String LimiteConsAnual;
    private String nmMedico;
    private String NroLogr;

}
