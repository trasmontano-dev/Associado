package br.com.trasmontano.trasmontanoassociadomobile.network;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.*;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by rbarbosa on 11/07/2016.
 */
public class APIClient {

    private static RestAdapter REST_ADAPTER;

    private static void createAdapterIfNeeded() {
        //http://webapi.trasmontano.com.br
        //http://m.trasmontano.srv.br:8888
        if (REST_ADAPTER == null) {
            REST_ADAPTER = new RestAdapter.Builder()
                    .setEndpoint("http://m.trasmontano.srv.br:8888")
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setClient(new OkClient())
                    .build();
        }
    }

    public APIClient() {
        createAdapterIfNeeded();
    }

    public RestServices getRestService() {
        return REST_ADAPTER.create(RestServices.class);
    }

    public interface RestServices {
        @GET("/acesso/autenticacaomobile")
        void getLoginAssociado(
                @Header("usuario") String usuario,
                @Header("senha") String senha,
                Callback<Login> callbackUsuario
        );
        @GET("/acesso/autenticacaomobilebiometria")
        void getLoginAssociadoBiometria(
                        @Header("usuario") String usuario,
                        @Header("telefone") String telefone,
                        Callback<Login> callbackUsuario
                );


        @GET("/associado/carencia/{matricula}/{dependente}")
        void getDadosCarteirinhaTemporaria(
                @Path("matricula") String matricula ,
                @Path("dependente") String dependente,
                Callback<List<DadosCarteirinha>> callbackCarteirinhaTemporaria
        );
        @GET("/associado/agendaMedicaAssociado/{matricula}/{dependente}/{tipo}")
        void getAngendaMedicaAssociado(
                @Path("matricula") String matricula ,
                @Path("dependente") String dependente,
                @Path("tipo") int tipo,
                Callback<List<AgendaMedicaAssociado>> callbackAgendaMedicaAssociado
        );
        @GET("/associado/agendamentoMedicoWebParametros/{tipo}/{cdEspecialidade}/{cdLocalidade}")
        void getAgendamentoMedicoWebParametros(
                @Path("tipo") byte tipo ,
                @Path("cdEspecialidade") int cdEspecialidade,
                @Path("cdLocalidade") int cdLocalidade,
                Callback<List<AgendamentoMedicoWebParametros>> callbackAgendamentoMedicoWebParametros
        );

        @GET("/associado/agendamentoMedicoWebDadosAssociado/{matricula}/{cdDependente}")
        void getAgendamentoMedicoWebDadosAssociado(
                @Path("matricula") String matricula ,
                @Path("cdDependente") String cdDependente,
                Callback<Associado> callbackAgendamentoMedicoWebDadosAssociado
        );
        @GET("/associado/agendamentoMedicoWeb/{Inicio}/{Fim}/{cdEspecialidade}/{cdLocalidade}/{cdMedico}/{sexo}/{idade}/{tipo}/{cdReferencia}")
        void getAgendamentoMedicoWeb(
                @Path("Inicio") String Inicio ,
                @Path("Fim") String Fim,
                @Path("cdEspecialidade") int cdEspecialidade,
                @Path("cdLocalidade") int cdLocalidade,
                @Path("cdMedico") int cdMedico,
                @Path("sexo") String sexo,
                @Path("idade") int idade,
                @Path("tipo") boolean tipo,
                @Path("cdReferencia") int cdReferencia,
                Callback<List<DadosConsulta>> callbackAgendamentoMedicoWeb
        );
        /*@POST("/associado/cancelamentoDeConsulta")
        void setCancelamentoDeConsulta(
                @Header("matricula") String matricula ,
                @Header("cdDependente") String cdDependente,
                @Header("id") int id,
                @Header("usuRede") String usuRede,
                @Header("usuSistema") String usuSistema,
                @Header("tipo") char tipo,
                @Header("solicitante") String solicitante,
                @Header("situacao") String situacao,
                @Header("horario") String horario,
                @Header("localidade") String localidade,
                @Header("especialidade") String especialidade,
                @Header("idAgenda") String idAgenda,
                Callback<String> callbackCancelamentoDeConsulta
        );*/


        @GET("/associado/cancelamentoDeConsulta")
        void setCancelamentoDeConsulta(
                @Header("matricula") String matricula ,
                @Header("cdDependente") String cdDependente,
                @Header("id") int id,
                @Header("usuRede") String usuRede,
                @Header("usuSistema") String usuSistema,
                @Header("tipo") char tipo,
                @Header("solicitante") String solicitante,
                @Header("situacao") String situacao,
                @Header("horario") String horario,
                @Header("localidade") String localidade,
                @Header("especialidade") String especialidade,
                @Header("idAgenda") String idAgenda,
                Callback<String> callbackCancelamentoDeConsulta
        );

        @POST("/associado/enviaEmail")
        void enviaEmail(@Body EmailCanalAtendimento email, Callback<String> callback);

        @GET("/associado/verificaUsuarioExiste")
        void getUsuarioExiste(
                @Header("matricula") String matricula ,
                @Header("cdDependente") String cdDependente,
                @Header("dataNascimento") String usuRede,
                Callback<String> callbackUsuarioExiste
        );

        @GET("/associado/verificaSituacaoAssociado")
        void getSituacaoAssociado(
                @Header("matricula") String matricula ,
                @Header("cdDependente") String cdDependente,
                Callback<String> callbackSituacaoAssociado
        );
        @GET("/associado/descricaoDosPlanos")
        void getDescricaoPlanos(
                Callback<List<OrientadorMedicoDTO>> callbackDescricaoPlanos
        );
        @GET("/associado/descricaoDasRegioes/{codPlano}")
        void getDescricaoDasRegioes(
                @Path("codPlano") String codPlano,
                Callback<List<OrientadorMedicoDTO>> callbackDescricaoDasRegioes
        );
        @GET("/associado/descricaoDosMunicipios/{codPlano}/{codRegiao}")
        void getDescricaoDosMunicipios(
                @Path("codPlano") String codPlano,
                @Path("codRegiao") String codRegiao,
                Callback<List<OrientadorMedicoDTO>> callbackDescricaoDosMunicipios
        );
        @GET("/associado/descricaoDasEspecialidade")
        void getDescricaoDasEspecialidade(
                Callback<List<OrientadorMedicoDTO>> callbackDescricaoDasEspecialidade
        );
        //@POST("/associado/carregarGridOrientadorMedicoMobile")
        //void getGridOrientadorMedicoMobile(
        //@Body DTOParametrosOrientador orientador,
        //Callback<List<OrientadorMedicoDTOPesquisa>> callbackOrientadorWeb);
        @POST("/associado/carregarGridOrientadorMedicoMobile")
        void getGridOrientadorMedicoMobile(@Body DTOParametrosOrientador orientador, Callback<List<OrientadorMedicoDTOPesquisa>> callback);

        @GET("/associado/agendamentoDeConsulta")
        void setAgendamentoDeConsulta(
                @Header("matricula") String matricula ,
                @Header("cdDependente") String cdDependente,
                @Header("dataParaAgendar") String dataParaAgendar,
                @Header("especialidade") String especialidade,
                @Header("localidade") String localidade,
                @Header("cdEspecialidade") String cdEspecialidade,
                @Header("cbos") String cbos,
                @Header("cdMedico") String cdMedico,
                @Header("idAgendaMedica") String idAgendaMedica,
                @Header("nmMedico") String nmMedico,
                @Header("idAgenda") String idAgenda,
                @Header("perfilUsuario") String perfilUsuario,
                @Header("limiteConsAnual") String limiteConsAnual,
                Callback<String> callbackAgendamentoDeConsulta

        );

        @GET("/associado/setLoginAssociado")
        void setLoginAssociado(
                @Header("senha") String senha ,
                @Header("repetirSenha") String repetirSenha,
                @Header("lembrarSenha") String lembrarSenha,
                @Header("dataNascimento") String dataNascimento,
                @Header("matricula") String matricula,
                @Header("email") String email,
                @Header("repetirEmail") String repetirEmail,
                @Header("cpf") String cpf,
                @Header("cdDependente") String cdDependente,
                Callback<String> callbackLoginAssociado
        );
        @GET("/associado/getHospitaisEmergenciaMobile")
        void getHospitaisEmergenciaMobile(
                @Header("matricula") String matricula ,
                @Header("latitude") String latitude,
                @Header("longitude") String longitude,
                Callback<List<Emergencia>> callbackEmergencia
        );

        @GET("/unidademedica/todos")
        void getUnidadeMedicaTodos(Callback<List<UnidadeMedica>> callbackListaUnidadeMedica);

        @GET("/associado/getCredenciadosFavoritosMobile")
        void getCredenciadosFavoritosMobile(
                @Header("matricula") String matricula ,
                @Header("credenciadosFavoritos") String credenciadosFavoritos,
                Callback<List<Emergencia>> callbackFavoritos
        );
    }
}
