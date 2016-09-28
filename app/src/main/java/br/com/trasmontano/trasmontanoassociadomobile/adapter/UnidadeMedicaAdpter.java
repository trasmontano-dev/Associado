package br.com.trasmontano.trasmontanoassociadomobile.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.trasmontano.trasmontanoassociadomobile.DTO.UnidadeMedica;
import br.com.trasmontano.trasmontanoassociadomobile.R;
import br.com.trasmontano.trasmontanoassociadomobile.Util.Base64Util;

public class UnidadeMedicaAdpter extends RecyclerView.Adapter<UnidadeMedicaAdpter.UnidadeMedicaViewHolder> {

    private Context context;
    private List<UnidadeMedica> unidadeMedicaList;
    private final UnidadeMedicaOnClickListener onClickListener;

    public interface  UnidadeMedicaOnClickListener {
        void OnClickUnidadeMedica(View view, int index);
    }

    public UnidadeMedicaAdpter(Context context, List<UnidadeMedica> unidadeMedicaList, UnidadeMedicaOnClickListener onClickListener) {
        this.context = context;
        this.unidadeMedicaList = unidadeMedicaList;
        this.onClickListener = onClickListener;
    }

    @Override
    public UnidadeMedicaAdpter.UnidadeMedicaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.activity_item_unidade, parent, false);
        UnidadeMedicaViewHolder holder = new UnidadeMedicaViewHolder(view);
        return  holder;
    }

    @Override
    public void onBindViewHolder(final UnidadeMedicaAdpter.UnidadeMedicaViewHolder holder, final int position) {
        UnidadeMedica unidadeMedica = unidadeMedicaList.get(position);
        holder.textViewCodigo.setText(String.valueOf(unidadeMedica.getCodigo()));
        holder.textViewLocalidade.setText(unidadeMedica.getLocalidade());
        holder.textViewCodigo.setVisibility(View.GONE);

        if (unidadeMedica.getImagem() != null)
            holder.imageViewLocalidade.setImageBitmap(Base64Util.decodeBase64(unidadeMedica.getImagem()));

        if (onClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.OnClickUnidadeMedica(holder.view, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return unidadeMedicaList.size();
    }

    public static class UnidadeMedicaViewHolder extends RecyclerView.ViewHolder {
        TextView textViewCodigo;
        TextView textViewLocalidade;
        ImageView imageViewLocalidade;
        private View view;

        public UnidadeMedicaViewHolder(View view) {
            super(view);
            this.view = view;
            this.textViewCodigo = (TextView) view.findViewById(R.id.twCodigo);
            this.textViewLocalidade = (TextView) view.findViewById(R.id.twLocalidade);
            this.imageViewLocalidade = (ImageView) view.findViewById(R.id.imgLocalidade);
        }
    }
}