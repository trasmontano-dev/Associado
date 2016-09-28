package br.com.trasmontano.trasmontanoassociadomobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.Alarme;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.LogMedicamentosTomados;
import br.com.trasmontano.trasmontanoassociadomobile.R;

/**
 * Created by rbarbosa on 28/07/2016.
 */
public class LogAlarmeAdapter extends RecyclerView.Adapter<LogAlarmeAdapter.LogAlarmeViewHolder> {


    private Context context;
    private List<LogMedicamentosTomados> lstLogMedicamentosTomados;
    private final LogAlarmeOnClickListener onClickListener;


    public interface LogAlarmeOnClickListener {

        public void OnClickView(View view, int index);

        public void OnClickShineButton(View view, int index);
    }


    public LogAlarmeAdapter(Context context, List<LogMedicamentosTomados> lstLogMedicamentosTomados, LogAlarmeOnClickListener onClickListener) {
        this.context = context;
        this.lstLogMedicamentosTomados = lstLogMedicamentosTomados;
        this.onClickListener = onClickListener;

    }


    @Override
    public LogAlarmeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_log_alarme, parent, false);
        LogAlarmeViewHolder holder = new LogAlarmeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final LogAlarmeViewHolder holder, final int position) {
        LogMedicamentosTomados l = lstLogMedicamentosTomados.get(position);

        holder.tvId.setText(String.valueOf(l.getId()));
        holder.tvData.setText(l.getDataTomouMedicamento());
        if(l.getHoraTomouMedicamento().equalsIgnoreCase("+ 10 min"))
        {
            holder.shineButtonTomei.setChecked(false);
            holder.shineButtonTomei.setClickable(false);

        }
        else
        {
            if (onClickListener != null) {
                holder.shineButtonTomei.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onClickListener.OnClickShineButton(holder.view, position);
                    }
                });

            }
        }

        holder.tvHora.setText(l.getHoraTomouMedicamento());
        if(l.getTomei() == 1)
            holder.shineButtonTomei.setChecked(true);


    }

    @Override
    public int getItemCount() {
        return lstLogMedicamentosTomados.size();
    }



    public static class LogAlarmeViewHolder extends RecyclerView.ViewHolder {


        TextView tvData;
        TextView tvHora;
        TextView tvId;
        ShineButton shineButtonTomei;


        private View view;

        public LogAlarmeViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvData = (TextView) view.findViewById(R.id.tvData);
            this.tvHora = (TextView) view.findViewById(R.id.tvHora);
            this.tvId = (TextView) view.findViewById(R.id.tvId);
            this.shineButtonTomei = (ShineButton) view.findViewById(R.id.shineButtonTomei);

        }
    }
}
