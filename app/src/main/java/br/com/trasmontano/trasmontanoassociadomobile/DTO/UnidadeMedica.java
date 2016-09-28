package br.com.trasmontano.trasmontanoassociadomobile.DTO;


import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

@Table("tbLocalidade")
public class UnidadeMedica extends Model {

    /*@Key
    @AutoIncrement
    @Column("Id")
    private long Id;*/
    @Key
    @Column("Codigo")
    private int Codigo;
    @Column("Localidade")
    private String Localidade;
    @Column("Endereco")
    private String Endereco;
    @Column("Numero")
    private String Numero;
    @Column("Complemento")
    private String Complemento;
    @Column("Cep")
    private String Cep;
    @Column("Bairro")
    private String Bairro;
    @Column("Cidade")
    private String Cidade;
    @Column("Estado")
    private String Estado;
    @Column("Imagem")
    private String Imagem;
    @Column("Latitude")
    private String Latitude;
    @Column("Longitude")
    private String Longitude;

    /*public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }*/

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int codigo) {
        Codigo = codigo;
    }

    public String getLocalidade() {
        return Localidade;
    }

    public void setLocalidade(String localidade) {
        Localidade = localidade;
    }

    public String getEndereco() {
        return Endereco;
    }

    public void setEndereco(String endereco) {
        Endereco = endereco;
    }

    public String getBairro() {
        return Bairro;
    }

    public void setBairro(String bairro) {
        Bairro = bairro;
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

    public String getEstado() {
        return Estado;
    }

    public void setEstado(String estado) {
        Estado = estado;
    }

    public String getImagem() {
        return Imagem;
    }

    public void setImagem(String imagem) {
        Imagem = imagem;
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

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
}
