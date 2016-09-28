package br.com.trasmontano.trasmontanoassociadomobile.DTO;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by rbarbosa on 22/09/2016.
 */

@Table("favoritos")
public class CredenciadosFavoritos  extends Model {
    @Key
    @Column("id")
    @AutoIncrement
    private long id;
    @Column("matricula")
    private String matricula;
    @Column("codigoCredenciado")
    private String codigoCredenciado;
    @Column("codigoFilial")
    private String codigoFilial;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMatricula() {
        return matricula;
    }

    public void setMatricula(String matricula) {
        this.matricula = matricula;
    }

    public String getCodigoCredenciado() {
        return codigoCredenciado;
    }

    public void setCodigoCredenciado(String codigoCredenciado) {
        this.codigoCredenciado = codigoCredenciado;
    }

    public String getCodigoFilial() {
        return codigoFilial;
    }

    public void setCodigoFilial(String codigoFilial) {
        this.codigoFilial = codigoFilial;
    }
}
