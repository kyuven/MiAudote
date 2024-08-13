package com.example.miaudote.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.miaudote.Models.OngModel;
import com.example.miaudote.ONGInfo.ONG_Page;
import com.example.miaudote.R;

import java.util.List;

public class OngAdapter extends RecyclerView.Adapter<OngAdapter.MyViewHolder> {

    public OngAdapter(Context context, List<OngModel> ongModelList) {
        this.context = context;
        this.ongModelList = ongModelList;
    }

    private Context context;
    private List<OngModel> ongModelList;

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ong, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OngModel ongModel = ongModelList.get(position);

        Glide.with(context).load(ongModel.getImgOng());
        holder.txtCidadeOng.setText(ongModel.getCidadeOng());
        holder.txtUfOng.setText(ongModel.getUfOng());
        holder.txtNomeOng.setText(ongModel.getNomeOng());

        // holder.cardOng.setOnClickListener(new View.OnClickListener() {
           // @Override
            //public void onClick(View v) {
                //Intent i = new Intent(context, ONG_Page.class);
                //i.putExtra("Imagem", ongModelList.get(holder.getAdapterPosition()).getImgOng());
                //i.putExtra("Cidade", ongModelList.get(holder.getAdapterPosition()).getCidadeOng());
                //i.putExtra("UF", ongModelList.get(holder.getAdapterPosition()).getUfOng());
                //i.putExtra("Nome Ong", ongModelList.get(holder.getAdapterPosition()).getNomeOng());

               // context.startActivity(i);
           // }
        //});
    }

    @Override
    public int getItemCount() {
        return ongModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // WIDGETS DO CARD
        ImageView imgOngLogo;
        TextView txtCidadeOng, txtUfOng, txtNomeOng;
        CardView cardOng;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgOngLogo = itemView.findViewById(R.id.imgOngCard_logo);
            txtCidadeOng = itemView.findViewById(R.id.txtOngCard_CddOng);
            txtUfOng = itemView.findViewById(R.id.txtOngCard_UfOng);
            txtNomeOng = itemView.findViewById(R.id.txtOngCard_nome);
        }
    }
}