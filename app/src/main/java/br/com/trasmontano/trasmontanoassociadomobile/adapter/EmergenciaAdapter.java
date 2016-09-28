package br.com.trasmontano.trasmontanoassociadomobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.CredenciadosFavoritos;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosConsulta;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Emergencia;
import br.com.trasmontano.trasmontanoassociadomobile.R;
import se.emilsjolander.sprinkles.Query;

/**
 * Created by rbarbosa on 14/09/2016.
 */
public class EmergenciaAdapter extends RecyclerView.Adapter<EmergenciaAdapter.EmergenciaViewHolder> {


    private Context context;
    private List<Emergencia> lstEmergencia;
    private final EmergenciaOnClickListener onClickListener;

    public EmergenciaAdapter(Context context, List<Emergencia> lstEmergencia, EmergenciaOnClickListener onClickListener) {
        this.context = context;
        this.lstEmergencia = lstEmergencia;
        this.onClickListener = onClickListener;
    }


    public interface EmergenciaOnClickListener {

        public void OnClickButtonComoChegar(View view, int index);
        public void OnClickButtonLigar(View view, int index);
        public void OnClickButtonFavoritos(View view, int index);
    }


    @Override
    public EmergenciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_emergencia, parent, false);
        EmergenciaViewHolder holder = new EmergenciaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final EmergenciaViewHolder holder, final int position) {
        Emergencia e = lstEmergencia.get(position);

        holder.tvNomeFantasia.setText(e.getNomeFantasia());
        holder.tvEndereco.setText(e.getEndereco() + ", " + e.getNumero());
        holder.tvBairro.setText(e.getBairro());
        holder.tvCidade.setText(e.getCidade());
        holder.tvEstado.setText(e.getEstado());
        holder.tvCep.setText(e.getCep());
        holder.tvTelefone.setText(e.getDdd() + " " + e.getTelefone());
        holder.tvTelefone1.setText(e.getDdd1() + " " + e.getTelefone1());
        holder.tvLatitude.setText(e.getLatitude());
        holder.tvLongitude.setText(e.getLongitude());
        holder.tvCodigoCredenciado.setText(e.getCodigoCredenciado());
        holder.tvCodigoFilial.setText(e.getCodigoFilial());
        holder.tvMatricula.setText(e.getMatriculaAssociado());

        String credenciados = "";

        List<CredenciadosFavoritos> lst = Query.many(CredenciadosFavoritos.class, "select * from favoritos where matricula=?", e.getMatriculaAssociado()).get().asList();
        for (CredenciadosFavoritos c: lst) {
            if(c.getCodigoCredenciado().equals(e.getCodigoCredenciado()) && c.getCodigoFilial().equals(e.getCodigoFilial())) {
                holder.shineButtonFavoritos.setChecked(true);
            }
        }

        if (onClickListener != null) {

            holder.btComoChegar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickButtonComoChegar(holder.view, position);
                }
            });
            holder.btLigar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickButtonLigar(holder.view, position);
                }
            });
            holder.shineButtonFavoritos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickButtonFavoritos(holder.view, position);
                }
            });




        }

    }

    @Override
    public int getItemCount() {
        return lstEmergencia.size();
    }

    public static class EmergenciaViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomeFantasia;
        TextView tvEndereco;
        TextView tvBairro;
        TextView tvCidade;
        TextView tvEstado;
        TextView tvCep;
        TextView tvTelefone;
        TextView tvTelefone1;
        TextView tvLatitude;
        TextView tvLongitude;
        Button btComoChegar;
        Button btLigar;
        TextView tvCodigoCredenciado;
        TextView tvCodigoFilial;
        TextView tvMatricula;
        ShineButton shineButtonFavoritos;


        private View view;

        public EmergenciaViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvNomeFantasia = (TextView) view.findViewById(R.id.tvNomeFantasia);
            this.tvEndereco = (TextView) view.findViewById(R.id.tvEndereco);
            this.tvBairro = (TextView) view.findViewById(R.id.tvBairro);
            this.tvCidade = (TextView) view.findViewById(R.id.tvCidade);
            this.tvEstado = (TextView) view.findViewById(R.id.tvEstado);
            this.tvCep = (TextView) view.findViewById(R.id.tvCep);
            this.tvTelefone = (TextView) view.findViewById(R.id.tvTelefone);
            this.tvTelefone1 = (TextView) view.findViewById(R.id.tvTelefone1);
            this.tvLatitude = (TextView) view.findViewById(R.id.tvLatitude);
            this.tvLongitude = (TextView) view.findViewById(R.id.tvLongitude);
            this.btComoChegar = (Button) view.findViewById(R.id.btComoChegar);
            this.btLigar = (Button) view.findViewById(R.id.btLigar);
            this.tvCodigoCredenciado = (TextView) view.findViewById(R.id.tvCodigoCredenciado);
            this.tvCodigoFilial = (TextView) view.findViewById(R.id.tvCodigoFilial);
            this.tvMatricula = (TextView) view.findViewById(R.id.tvMatricula);
            this.shineButtonFavoritos = (ShineButton) view.findViewById(R.id.shineButtonFavoritos);


        }
    }
}
