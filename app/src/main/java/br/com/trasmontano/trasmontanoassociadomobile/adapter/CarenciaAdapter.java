package br.com.trasmontano.trasmontanoassociadomobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosCarteirinha;
import br.com.trasmontano.trasmontanoassociadomobile.R;

/**
 * Created by rbarbosa on 14/07/2016.
 */
public class CarenciaAdapter extends RecyclerView.Adapter<CarenciaAdapter.CarenciaViewHolder>  {
    private Context context;
    private List<DadosCarteirinha> lstCarencias;

    public CarenciaAdapter(Context context, List<DadosCarteirinha> lstCarencias) {
        this.context = context;
        this.lstCarencias = lstCarencias;
    }

    @Override
    public CarenciaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_carencia, parent, false);
        CarenciaViewHolder holder = new CarenciaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(CarenciaViewHolder holder, int position) {

            DadosCarteirinha d = lstCarencias.get(position);
            holder.tiItemCarencia.setText(String.valueOf(position + 1) + " - " + d.getDescricaoCarencia());
            holder.tiDtLiberacao.setText(d.getSituacao());

    }

    @Override
    public int getItemCount() {
      return lstCarencias.size();
    }

    public static class CarenciaViewHolder  extends RecyclerView.ViewHolder {
        TextView tiItemCarencia;
        TextView tiDtLiberacao;
        private View view;

        public CarenciaViewHolder(View view) {
            super(view);
            this.view = view;
            this.tiItemCarencia = (TextView) view.findViewById(R.id.tvItemCarencia);
            this.tiDtLiberacao = (TextView) view.findViewById(R.id.tvDtLiberacao);
        }
    }
}
