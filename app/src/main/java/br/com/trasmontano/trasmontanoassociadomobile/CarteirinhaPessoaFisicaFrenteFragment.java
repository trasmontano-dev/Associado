package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosCarteirinha;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CarteirinhaPessoaFisicaFrenteFragment extends Fragment {
    private Callback<List<DadosCarteirinha>> callbackCarteirinhaTemporaria;
    private TextView tvNomeCarteirinhaPFisica;
    private TextView tvRgPFisica;
    private TextView tvAdmissaoPFisica;
    private TextView tvPlanoPFisica;
    private TextView tvCatPfisica;
    private TextView tvAbrangenciaPFisica;
    private TextView tvMatricula;
    private TextView tvInvidualOuFamiliar;


    private  String Matricula;
    private  String Dependente;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_carteirinha_pessoa_fisica_frente, container, false);

        tvNomeCarteirinhaPFisica = (TextView) view.findViewById(R.id.tvNomeCarteirinhaPFisica);
        tvRgPFisica = (TextView) view.findViewById(R.id.tvRgPFisica);
        tvAdmissaoPFisica = (TextView) view.findViewById(R.id.tvAdmissaoPFisica);
        tvPlanoPFisica = (TextView) view.findViewById(R.id.tvPlanoPFisica);
        tvCatPfisica = (TextView) view.findViewById(R.id.tvCatPFisica);
        tvAbrangenciaPFisica = (TextView) view.findViewById(R.id.tvAreaAbrangenciaPFisica);
        tvMatricula = (TextView) view.findViewById(R.id.tvMatriculaPFisica);
        tvInvidualOuFamiliar = (TextView) view.findViewById(R.id.tvInvidualOuFamiliar);


        SharedPreferences prefs = this.getActivity().getSharedPreferences("DADOS_LOGIN", Context.MODE_PRIVATE);
        if (prefs.contains("CodigoUsuario")) {
            Matricula = prefs.getString("CodigoUsuario", "");

            if (Matricula.length() > 6) {

                Dependente = Matricula.substring(6, 8);
            } else {
                Dependente = "00";
            }
        }


        configureInformacaoAssociadoCallback();

        new APIClient().getRestService().getDadosCarteirinhaTemporaria(Matricula.substring(0, 6),
                Dependente, callbackCarteirinhaTemporaria);

        return view;
    }

    private void configureInformacaoAssociadoCallback() {
        callbackCarteirinhaTemporaria = new Callback<List<DadosCarteirinha>>() {

            @Override
            public void success(List<DadosCarteirinha> dadosCarteirinha, Response response) {
                DadosCarteirinha c = dadosCarteirinha.get(0);

                if(c.getTipo() == "1")
                {
                    tvInvidualOuFamiliar.setText("Individual");
                }
                else
                {
                    tvInvidualOuFamiliar.setText("Familiar");
                }

                String admissao = c.getAdmissao().substring(0, 10);
                String[] admParts = admissao.split("-");

                String iniVigencia = c.getAdmissao().substring(0, 10);
                String[] vigenciaParts = iniVigencia.split("-");
                String nm = c.getNome();
                tvNomeCarteirinhaPFisica.setText(nm);
                tvRgPFisica.setText(c.getRG());
                tvAdmissaoPFisica.setText(admParts[2] + "/" + admParts[1] + "/" + admParts[0]);
                tvPlanoPFisica.setText(c.getDescricaoPlano());

                if (c.getAcomodacao().contains("apartamento") || c.getAcomodacao().contains("apto")) {
                    tvCatPfisica.setText(c.getCdCategoria() + " " + c.getDsCategoria() + " " + "Apto   " + c.getNumRegAns());
                } else {
                    tvCatPfisica.setText(c.getCdCategoria() + " " + c.getDsCategoria() + " " + "Enf   Reg.:" + c.getNumRegAns());
                }

                tvAbrangenciaPFisica.setText(c.getAbrangencia());
                tvMatricula.setText(Matricula.substring(0, 6) + "." + Dependente);

            }

            public void failure(RetrofitError error) {
                Log.d("ERRO-------->", error.getMessage().toString());
                Toast.makeText(getContext(), "Falha ao conectar no servidor", Toast.LENGTH_LONG).show();
            }

        };
    }

}
