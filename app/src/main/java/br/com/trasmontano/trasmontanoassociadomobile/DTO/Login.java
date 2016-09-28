package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import java.util.Date;

import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by rbarbosa on 11/07/2016.
 */
@Table("login")
public class Login {
    public int Bloqueado;
    public int TentativasAcesso;
    public int ExpiraEm;
    public boolean UsuarioValido;
    public boolean SenhaValida;
    public int StatusAcesso;
    public String DataUltimoAcesso;
    public String CodigoUsuario;
    public String NomeUsuario;
    public String Email;
    public String PerfilUsuario;
    public String TipoPlano;

    public String getSituacao() {
        return Situacao;
    }

    public void setSituacao(String situacao) {
        Situacao = situacao;
    }

    public String Situacao;

    public int getBloqueado() {
        return Bloqueado;
    }

    public void setBloqueado(int bloqueado) {
        Bloqueado = bloqueado;
    }

    public int getTentativasAcesso() {
        return TentativasAcesso;
    }

    public void setTentativasAcesso(int tentativasAcesso) {
        TentativasAcesso = tentativasAcesso;
    }

    public int getExpiraEm() {
        return ExpiraEm;
    }

    public void setExpiraEm(int expiraEm) {
        ExpiraEm = expiraEm;
    }

    public boolean isUsuarioValido() {
        return UsuarioValido;
    }

    public void setUsuarioValido(boolean usuarioValido) {
        UsuarioValido = usuarioValido;
    }

    public boolean isSenhaValida() {
        return SenhaValida;
    }

    public void setSenhaValida(boolean senhaValida) {
        SenhaValida = senhaValida;
    }

    public int getStatusAcesso() {
        return StatusAcesso;
    }

    public void setStatusAcesso(int statusAcesso) {
        StatusAcesso = statusAcesso;
    }

    public String getDataUltimoAcesso() {
        return DataUltimoAcesso;
    }

    public void setDataUltimoAcesso(String dataUltimoAcesso) {
        DataUltimoAcesso = dataUltimoAcesso;
    }

    public String getCodigoUsuario() {
        return CodigoUsuario;
    }

    public void setCodigoUsuario(String codigoUsuario) {
        CodigoUsuario = codigoUsuario;
    }

    public String getNomeUsuario() {
        return NomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        NomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPerfilUsuario() {
        return PerfilUsuario;
    }

    public void setPerfilUsuario(String perfilUsuario) {
        PerfilUsuario = perfilUsuario;
    }

    public String getTipoPlano() {
        return TipoPlano;
    }

    public void setTipoPlano(String tipoPlano) {
        TipoPlano = tipoPlano;
    }

    public int getQtdeBloquear() {
        return QtdeBloquear;
    }

    public void setQtdeBloquear(int qtdeBloquear) {
        QtdeBloquear = qtdeBloquear;
    }

    public int getTipo() {
        return Tipo;
    }

    public void setTipo(int tipo) {
        Tipo = tipo;
    }

    public int getCdCategoria() {
        return CdCategoria;
    }

    public void setCdCategoria(int cdCategoria) {
        CdCategoria = cdCategoria;
    }

    public int QtdeBloquear;
    public int Tipo;
    public int CdCategoria;


}
