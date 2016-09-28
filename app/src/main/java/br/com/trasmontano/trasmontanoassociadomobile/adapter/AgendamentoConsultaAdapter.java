package br.com.trasmontano.trasmontanoassociadomobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sackcentury.shinebuttonlib.ShineButton;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.AgendaMedicaAssociado;
import br.com.trasmontano.trasmontanoassociadomobile.DTO.LogMedicamentosTomados;
import br.com.trasmontano.trasmontanoassociadomobile.R;

/**
 * Created by rbarbosa on 02/08/2016.
 */
public class AgendamentoConsultaAdapter extends RecyclerView.Adapter<AgendamentoConsultaAdapter.AgendamentoConsultaViewHolder> {


    private Context context;
    private List<AgendaMedicaAssociado> lstAgendaMedica;
    private final DeleteConsultaOnClickListener onClickListener;


    public interface DeleteConsultaOnClickListener {

        public void OnClickImageButtonDelete(View view, int index);
    }

    public AgendamentoConsultaAdapter(Context context, List<AgendaMedicaAssociado> lstAgendaMedica, DeleteConsultaOnClickListener onClickListener) {
        this.context = context;
        this.lstAgendaMedica = lstAgendaMedica;
        this.onClickListener = onClickListener;

    }


    @Override
    public AgendamentoConsultaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_agendamento_consulta, parent, false);
        AgendamentoConsultaViewHolder holder = new AgendamentoConsultaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final AgendamentoConsultaViewHolder holder, final int position) {
        AgendaMedicaAssociado a = lstAgendaMedica.get(position);

        holder.tvId.setText(String.valueOf(a.getID()));
        holder.tvIdAgenda.setText(String.valueOf(a.getIdAgenda()));
        String data = a.getDataHoraAgendamento().substring(8,10) + "/" + a.getDataHoraAgendamento().substring(5,7) + "/" + a.getDataHoraAgendamento().substring(0,4);
        String hora = a.getDataHoraAgendamento().substring(11,16);
        holder.tvDataHora.setText("Data: " + data + " " + hora);
        holder.tvEspecialidade.setText("Especialidade: " + a.getDsEspecialidade());
        holder.tvLocalidade.setText("Localidade: " + a.getDsLocalidade());
        holder.tvMedico.setText("Médico(a): " + a.getNmMedico());
        holder.tvSituacao.setText("Situação: " + a.getSituacao());

        if (onClickListener != null) {
            holder.imbDeleteAgendamentoConsulta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickImageButtonDelete(holder.view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lstAgendaMedica.size();
    }

    public static class AgendamentoConsultaViewHolder extends RecyclerView.ViewHolder {


        TextView tvDataHora;
        TextView tvMedico;
        TextView tvEspecialidade;
        TextView tvLocalidade;
        TextView tvSituacao;
        TextView tvId;
        TextView tvIdAgenda;
        ImageButton imbDeleteAgendamentoConsulta;


        private View view;

        public AgendamentoConsultaViewHolder(View view) {
            super(view);
            this.view = view;
            this.tvDataHora = (TextView) view.findViewById(R.id.tvDataHora);
            this.tvMedico = (TextView) view.findViewById(R.id.tvMedico);
            this.tvEspecialidade = (TextView) view.findViewById(R.id.tvEspecialidade);
            this.tvLocalidade = (TextView) view.findViewById(R.id.tvLocalidade);
            this.tvSituacao = (TextView) view.findViewById(R.id.tvSituacao);
            this.tvId = (TextView) view.findViewById(R.id.tvId);
            this.tvIdAgenda = (TextView) view.findViewById(R.id.tvIdAgenda);
            this.imbDeleteAgendamentoConsulta = (ImageButton) view.findViewById(R.id.imbDeleteAgendamentoConsulta);


        }
    }
}
