package br.com.trasmontano.trasmontanoassociadomobile.DTO;

/**
 * Created by rbarbosa on 14/09/2016.
 */
public class Emergencia {

    public String NomeFantasia;
    public String Endereco;
    public String Numero;
    public String Complemento;
    public String Bairro;
    public String Cidade;
    public String Estado;
    public String Ddd;
    public String Telefone;
    public String Ddd1;
    public String Telefone1;


    public String getMatriculaAssociado() {
        return MatriculaAssociado;
    }

    public void setMatriculaAssociado(String matriculaAssociado) {
        MatriculaAssociado = matriculaAssociado;
    }

    public String MatriculaAssociado;

    public String getCodigoCredenciado() {
        return CodigoCredenciado;
    }

    public void setCodigoCredenciado(String codigoCredenciado) {
        CodigoCredenciado = codigoCredenciado;
    }

    public String getCodigoFilial() {
        return CodigoFilial;
    }

    public void setCodigoFilial(String codigoFilial) {
        CodigoFilial = codigoFilial;
    }

    public String Latitude;
    public String CodigoCredenciado;
    public String CodigoFilial;

    public String getCep() {
        return Cep;
    }

    public void setCep(String cep) {
        Cep = cep;
    }

    public String Cep;

    public String getNomeFantasia() {
        return NomeFantasia;
    }

    public void setNomeFantasia(String nomeFantasia) {
        NomeFantasia = nomeFantasia;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getNumero() {
        return Numero;
    }

    public void setNumero(String numero) {
        Numero = numero;
    }

    public String getComplemento() {
        return Complemento;
    }

    public void setComplemento(String complemento) {
        Complemento = complemento;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
    }

    public String getCidade() {
        return Cidade;
    }

    public void setCidade(String cidade) {
        Cidade = cidade;
    }

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getDdd() {
        return Ddd;
    }

    public void setDdd(String ddd) {
        Ddd = ddd;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public String getDdd1() {
        return Ddd1;
    }

    public void setDdd1(String ddd1) {
        Ddd1 = ddd1;
    }

    public String getTelefone1() {
        return Telefone1;
    }

    public void setTelefone1(String telefone1) {
        Telefone1 = telefone1;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String Longitude;
}
