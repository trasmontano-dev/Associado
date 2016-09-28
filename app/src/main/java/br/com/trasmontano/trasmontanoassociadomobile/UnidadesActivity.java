package br.com.trasmontano.trasmontanoassociadomobile;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import java.util.List;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.UnidadeMedica;
import br.com.trasmontano.trasmontanoassociadomobile.adapter.UnidadeMedicaAdpter;
import br.com.trasmontano.trasmontanoassociadomobile.network.APIClient;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import se.emilsjolander.sprinkles.Query;

public class UnidadesActivity extends AppCompatActivity {

    private RecyclerView rwUnidades;
    private Callback<List<UnidadeMedica>> callbackListaUnidadeMedica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unidades);

        rwUnidades = (RecyclerView) findViewById(R.id.rwUnidades);
        rwUnidades.setLayoutManager(new LinearLayoutManager(this));
        rwUnidades.setHasFixedSize(true);
        ListaUnidadeMedicaCallback();
        new APIClient().getRestService().getUnidadeMedicaTodos(callbackListaUnidadeMedica);
        CarregarLista(this);
    }

    public void CarregarLista(Context context) {
        List<UnidadeMedica> lst = Query.all(UnidadeMedica.class).get().asList();
        rwUnidades.setAdapter(new UnidadeMedicaAdpter(this, lst, onClickUnidadeMedica()));
    }

    private UnidadeMedicaAdpter.UnidadeMedicaOnClickListener onClickUnidadeMedica() {
        return new UnidadeMedicaAdpter.UnidadeMedicaOnClickListener() {

            @Override
            public void OnClickUnidadeMedica(View view, int index) {
                TextView textViewCodigo =  (TextView) view.findViewById(R.id.twCodigo);
                Intent intentUnidadeDetalhes = new Intent(UnidadesActivity.this, UnidadesDetalhesActivity.class);
                intentUnidadeDetalhes.putExtra("IdUnidade", textViewCodigo.getText());
                startActivity(intentUnidadeDetalhes);
            }
        };
    }

    private void ListaUnidadeMedicaCallback()
    {
        callbackListaUnidadeMedica = new Callback<List<UnidadeMedica>>() {

            @Override
            public void success(List<UnidadeMedica> unidadeMedicas, Response response) {

                List<UnidadeMedica> lista = Query.all(UnidadeMedica.class).get().asList();

                for (UnidadeMedica item: lista) {
                    item.delete();
                }

                for (UnidadeMedica item: unidadeMedicas) {
                    item.save();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("Failure", "Error: " + error.getMessage());
            }
        };
    }
}
