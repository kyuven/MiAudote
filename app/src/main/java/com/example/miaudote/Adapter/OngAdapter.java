package com.example.miaudote.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miaudote.Models.OngModel;
import com.example.miaudote.ONGInfo.ONG_Page;
import com.example.miaudote.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OngAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<OngModel> ongModelList;

    public OngAdapter(Context context, List<OngModel> ongModelList) {
        this.context = context;
        this.ongModelList = ongModelList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_ong, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        OngModel ongModel = ongModelList.get(position);
        Picasso.get().load(ongModel.getImgOng()).resize(140, 94).into(holder.imgOngLogo);
        holder.txtCidadeOng.setText(ongModel.getCidadeOng());
        holder.txtUfOng.setText(ongModel.getUfOng());
        holder.txtNomeOng.setText(ongModel.getNomeOng());

        holder.cardOng.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v) {
               Intent i = new Intent(context, ONG_Page.class);
               i.putExtra("Imagem ONG", ongModelList.get(holder.getAdapterPosition()).getImgOng());
               i.putExtra("UF ONG", ongModelList.get(holder.getAdapterPosition()).getUfOng());
               i.putExtra("Cidade ONG", ongModelList.get(holder.getAdapterPosition()).getCidadeOng());
               i.putExtra("Bairro ONG", ongModelList.get(holder.getBindingAdapterPosition()).getBairroOng());
               i.putExtra("Logradouro ONG", ongModelList.get(holder.getBindingAdapterPosition()).getLograOng());
               i.putExtra("Nome ONG", ongModelList.get(holder.getAdapterPosition()).getNomeOng());
               i.putExtra("Descrição ONG", ongModelList.get(holder.getBindingAdapterPosition()).getDescOng());
               i.putExtra("Website ONG", ongModelList.get(holder.getBindingAdapterPosition()).getWebsiteOng());
               i.putExtra("Telefone ONG", ongModelList.get(holder.getBindingAdapterPosition()).getTelOng());
               i.putExtra("Instagram ONG", ongModelList.get(holder.getBindingAdapterPosition()).getInstaOng());
               i.putExtra("Email ONG", ongModelList.get(holder.getBindingAdapterPosition()).getEmailOng());
               i.putExtra("Twitter ONG", ongModelList.get(holder.getBindingAdapterPosition()).getTwitterOng());

               context.startActivity(i);
            }
        });

        holder.btnSaibaMaisOng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ONG_Page.class);
                i.putExtra("Imagem ONG", ongModelList.get(holder.getAdapterPosition()).getImgOng());
                i.putExtra("UF ONG", ongModelList.get(holder.getAdapterPosition()).getUfOng());
                i.putExtra("Cidade ONG", ongModelList.get(holder.getAdapterPosition()).getCidadeOng());
                i.putExtra("Bairro ONG", ongModelList.get(holder.getBindingAdapterPosition()).getBairroOng());
                i.putExtra("Logradouro ONG", ongModelList.get(holder.getBindingAdapterPosition()).getLograOng());
                i.putExtra("Nome ONG", ongModelList.get(holder.getAdapterPosition()).getNomeOng());
                i.putExtra("Descrição ONG", ongModelList.get(holder.getBindingAdapterPosition()).getDescOng());
                i.putExtra("Website ONG", ongModelList.get(holder.getBindingAdapterPosition()).getWebsiteOng());
                i.putExtra("Telefone ONG", ongModelList.get(holder.getBindingAdapterPosition()).getTelOng());
                i.putExtra("Instagram ONG", ongModelList.get(holder.getBindingAdapterPosition()).getInstaOng());
                i.putExtra("Email ONG", ongModelList.get(holder.getBindingAdapterPosition()).getEmailOng());
                i.putExtra("Twitter ONG", ongModelList.get(holder.getBindingAdapterPosition()).getTwitterOng());

                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ongModelList.size();
    }

}

class MyViewHolder extends RecyclerView.ViewHolder {

    // WIDGETS DO CARD
    ImageView imgOngLogo;
    TextView txtCidadeOng, txtUfOng, txtNomeOng;
    CardView cardOng;
    AppCompatButton btnSaibaMaisOng;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        imgOngLogo = itemView.findViewById(R.id.imgOngCard_logo);
        txtCidadeOng = itemView.findViewById(R.id.txtOngCard_CddOng);
        txtUfOng = itemView.findViewById(R.id.txtOngCard_UfOng);
        txtNomeOng = itemView.findViewById(R.id.txtOngCard_nome);
        cardOng = itemView.findViewById(R.id.cardView_ong);
        btnSaibaMaisOng = itemView.findViewById(R.id.btnOngCard_info);
    }
}