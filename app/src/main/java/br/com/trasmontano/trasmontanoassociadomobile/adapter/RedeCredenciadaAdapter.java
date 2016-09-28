package br.com.trasmontano.trasmontanoassociadomobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.OrientadorMedicoDTOPesquisa;
import br.com.trasmontano.trasmontanoassociadomobile.R;

/**
 * Created by mmaganha on 17/08/2016.
 */
public class RedeCredenciadaAdapter extends RecyclerView.Adapter<RedeCredenciadaAdapter.RedeCredenciadaViewHolder> {
    private Context context;
    private List<OrientadorMedicoDTOPesquisa> lstRedeCredenciada;
    private final RedeCredenciadaOnClickListener onClickListener;

    public interface RedeCredenciadaOnClickListener {
        void OnClickVerMapa(View view, int index);
        void OnClickComoChegar(View view, int index);
        void OnClickInformacoes(View view, int index);
    }
    public RedeCredenciadaAdapter(Context context, List<OrientadorMedicoDTOPesquisa> lstRedeCredenciada, RedeCredenciadaOnClickListener onClickListener) {
        this.context = context;
        this.lstRedeCredenciada = (List<OrientadorMedicoDTOPesquisa>)lstRedeCredenciada;
        this.onClickListener = onClickListener;
    }

    @Override
    public RedeCredenciadaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_redecredenciada, parent, false);
        RedeCredenciadaViewHolder holder = new RedeCredenciadaViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RedeCredenciadaViewHolder holder,final int position) {
        OrientadorMedicoDTOPesquisa r = lstRedeCredenciada.get(position);
        String complemento = "";
        if (r.getComplemento() != null)
        {
            complemento = " - " + r.getComplemento();
        }
        holder.tiNomeFantasia.setText(r.getNomeFantasia());
        holder.tiNomeRazaoSocial.setText(r.getNomeDescritivo().toUpperCase());
        holder.tiTipoCredenciado.setText(r.getTipoAtendimento().toUpperCase());
        holder.tiEspecialidade.setText(r.getEspecialidade().toUpperCase());
        holder.tiEndereco.setText(r.getEndereco().toUpperCase() + "," + r.getNumero() + complemento);
        holder.tiBairro.setText(r.getBairro().toUpperCase());
        holder.tiContato.setText(r.getDDDFone1() + " - " + r.getFone1());
        holder.tiCidade.setText(r.getSomenteCidade().toUpperCase()+ "-" + r.getEstado());
        holder.tiCep.setText(r.getCep());
        holder.tiSite.setText(r.getSite());
        holder.tiCodigoCredenciado.setText(String.valueOf(r.getCodigoCredenciado()));
        holder.tiCodigoFilial.setText(String.valueOf(r.getCodigoFilial()));
        holder.tiLatitude.setText(r.getLatitude());
        holder.tiLongitude.setText(r.getLongitude());
        holder.tiCnpjCpf.setText(r.getCNPJ());

        //if (r.getCaminhoImagem() != null) {
        //    File file = new File(r.getCaminhoImagem());
        //   if (file.exists()) {
        //holder.ivAssociado.setImageURI(Uri.parse(r.getCaminhoImagem()));
        //}
        //}
        if (onClickListener != null) {
            holder.btVerMapa.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickVerMapa(holder.view, position);
                }
            });
            holder.btComoChegar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickComoChegar(holder.view, position);
                }
            });
            holder.btInformacoes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickInformacoes(holder.view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return lstRedeCredenciada.size();
    }

    public static class RedeCredenciadaViewHolder extends RecyclerView.ViewHolder {

        Button btVerMapa;
        Button btComoChegar;
        Button btInformacoes;
        TextView tiNomeFantasia;
        TextView tiNomeRazaoSocial;
        TextView tiTipoCredenciado;
        TextView tiEspecialidade;
        TextView tiEndereco;
        TextView tiBairro;
        TextView tiCidade;
        TextView tiCep;
        TextView tiContato;
        TextView tiSite;
        TextView tiCodigoCredenciado;
        TextView tiCodigoFilial;
        TextView tiLatitude;
        TextView tiLongitude;
        TextView tiCnpjCpf;
        private View view;

        public RedeCredenciadaViewHolder(View view) {
            super(view);
            this.view = view;
            this.tiNomeFantasia = (TextView) view.findViewById(R.id.tvNomeFantasia);
            this.tiNomeRazaoSocial = (TextView) view.findViewById(R.id.tvRazaoSocial);
            this.tiTipoCredenciado = (TextView) view.findViewById(R.id.tvTipoCredenciado);
            this.tiEspecialidade = (TextView) view.findViewById(R.id.tvEspecialidade);
            this.tiEndereco = (TextView) view.findViewById(R.id.tvEndereco);
            this.tiBairro = (TextView) view.findViewById(R.id.tvBairro);
            this.tiCidade = (TextView) view.findViewById(R.id.tvCidade);
            this.tiCep = (TextView) view.findViewById(R.id.tvCep);
            this.tiContato = (TextView) view.findViewById(R.id.tvContato);
            this.tiSite = (TextView) view.findViewById(R.id.tvSite);
            this.btVerMapa = (Button)view.findViewById(R.id.btVerMapa);
            this.btComoChegar = (Button)view.findViewById(R.id.btComoChegar);
            this.btInformacoes = (Button)view.findViewById(R.id.btInformacoes);
            this.tiCodigoCredenciado = (TextView)view.findViewById(R.id.tvCodigoCredenciado);
            this.tiCodigoFilial = (TextView)view.findViewById(R.id.tvCodigoFilial);
            this.tiLatitude = (TextView)view.findViewById(R.id.tvLatitude);
            this.tiLongitude = (TextView)view.findViewById(R.id.tvLongitude);
            this.tiCnpjCpf = (TextView)view.findViewById(R.id.tvCnpjCpf);
        }
    }
}

