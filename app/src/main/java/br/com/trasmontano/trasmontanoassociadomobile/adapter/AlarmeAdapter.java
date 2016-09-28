package br.com.trasmontano.trasmontanoassociadomobile.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.DigitalClock;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mikhaellopez.circularimageview.CircularImageView;

import java.io.File;
import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Alarme;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Associado;
import br.com.trasmontano.trasmontanoassociadomobile.R;

/**
 * Created by rbarbosa on 19/07/2016.
 */
public class AlarmeAdapter extends RecyclerView.Adapter<AlarmeAdapter.AlarmeViewHolder> {

    private Context context;
    private List<Alarme> lstAlarme;
    private final AlarmeOnClickListener onClickListener;


    public interface AlarmeOnClickListener {
        public void OnClickToggle(View view, int index);

        public void OnClickView(View view, int index);

        public void OnClickLixeira(View view, int index);
    }


    public AlarmeAdapter(Context context, List<Alarme> lstAlarme, AlarmeOnClickListener onClickListener) {
        this.context = context;
        this.lstAlarme = lstAlarme;
        this.onClickListener = onClickListener;

    }


    @Override
    public AlarmeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_alarme, parent, false);
        AlarmeViewHolder holder = new AlarmeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AlarmeViewHolder holder, final int position) {
        Alarme a = lstAlarme.get(position);

        holder.swAtivo.setChecked(a.getAtivo() == 1);
        holder.tvNomePaciente.setText(a.getNomePaciente());
        holder.tvNomeMedicamento.setText("Medicamento:  " + a.getNomeMedicamento());
        holder.tvId.setText(String.valueOf(a.getId()));
        holder.tvDescricaoMedicamento.setText("Descrição: " + a.getDescricaoMedicamento());
        holder.tvFrequencia.setText("Frequencia " + a.getIntervaloDe() + " em " + a.getIntervaloDe() + " horas");
        holder.tvHorarios.setText("Horários: " + a.getHorarios());

        if (onClickListener != null) {
            holder.swAtivo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickToggle(holder.view, position);
                }
            });
            holder.imbDeleteAlarme.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickLixeira(holder.view, position);
                }
            });
            holder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickView(holder.view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lstAlarme.size();
    }

    public static class AlarmeViewHolder extends RecyclerView.ViewHolder {



        TextView tvNomePaciente;
        Switch swAtivo;
        TextView tvId;
        TextView tvNomeMedicamento;
        TextView tvDescricaoMedicamento;
        TextView tvHorarios;
        TextView tvFrequencia;
        ImageButton imbDeleteAlarme;

        private View view;

        public AlarmeViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvNomePaciente = (TextView) view.findViewById(R.id.tvNomePaciente);
            this.tvNomeMedicamento = (TextView) view.findViewById(R.id.tvNomeMedicamento);
            this.tvDescricaoMedicamento = (TextView) view.findViewById(R.id.tvDescricaoMedicamento);
            this.tvHorarios = (TextView) view.findViewById(R.id.tvHorarios);
            this.tvFrequencia = (TextView) view.findViewById(R.id.tvFrequencia);
            this.swAtivo = (Switch) view.findViewById(R.id.swAtivo);
            this.imbDeleteAlarme = (ImageButton) view.findViewById(R.id.imbDeleteAlarme);
            this.tvId = (TextView) view.findViewById(R.id.tvId);

        }
    }
}
