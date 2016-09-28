package br.com.trasmontano.trasmontanoassociadomobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.AgendaMedicaAssociado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.Alarme;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.DadosConsulta;
import br.com.trasmontano.trasmontanoassociadomobile.R;

/**
 * Created by rbarbosa on 03/08/2016.
 */
public class ConsultaAdapter extends RecyclerView.Adapter<ConsultaAdapter.ConsultaViewHolder> {


    private Context context;
    private List<DadosConsulta> lstAgendaMedica;
    private final AgendarConsultaOnClickListener onClickListener;


    public interface AgendarConsultaOnClickListener {

        public void OnClickImageButtonAgendar(View view, int index);
    }

    public ConsultaAdapter(Context context, List<DadosConsulta> lstAgendaMedica, AgendarConsultaOnClickListener onClickListener) {
        this.context = context;
        this.lstAgendaMedica = lstAgendaMedica;
        this.onClickListener = onClickListener;

    }


    @Override
    public ConsultaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_consulta, parent, false);
        ConsultaViewHolder holder = new ConsultaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ConsultaViewHolder holder, final int position) {

        DadosConsulta a = lstAgendaMedica.get(position);
        String data = a.getDataHoraAgendamento().substring(8,10) + "/" + a.getDataHoraAgendamento().substring(5,7) + "/" + a.getDataHoraAgendamento().substring(0,4);
        String hora = a.getDataHoraAgendamento().substring(11,16);
        holder.tvDataHoraConsulta.setText((data + " " + hora));
        holder.tvNomeMedico.setText("Médico(a): " + a.getNmMedico());
        holder.tvIdConsulta.setText(a.getIdAgenda());
        holder.tvLocalidadeConsulta.setText(a.getDsLocalidade());
        holder.tvEnderecoConsulta.setText(a.getEndereco());

        if (onClickListener != null) {

            holder.imbAgendarConsulta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickImageButtonAgendar(holder.view, position);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return lstAgendaMedica.size();
    }

    public static class ConsultaViewHolder extends RecyclerView.ViewHolder {

        TextView tvNomeMedico;
        TextView tvIdConsulta;
        TextView tvDataHoraConsulta;
        TextView tvLocalidadeConsulta;
        TextView tvEnderecoConsulta;
        ImageButton imbAgendarConsulta;


        private View view;

        public ConsultaViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvNomeMedico = (TextView) view.findViewById(R.id.tvNomeMedico);
            this.tvIdConsulta = (TextView) view.findViewById(R.id.tvIdConsulta);
            this.tvDataHoraConsulta = (TextView) view.findViewById(R.id.tvDataHoraConsulta);
            this.tvLocalidadeConsulta = (TextView) view.findViewById(R.id.tvLocalidadeConsulta);
            this.tvEnderecoConsulta = (TextView) view.findViewById(R.id.tvEnderecoConsulta);
            this.imbAgendarConsulta = (ImageButton) view.findViewById(R.id.imbAgendarConsulta);


        }
    }
}
