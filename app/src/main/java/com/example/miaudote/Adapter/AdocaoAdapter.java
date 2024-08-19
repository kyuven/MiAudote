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

import com.example.miaudote.Models.AnimalModel;
import com.example.miaudote.PetInfo.AdoptionPet_Info;
import com.example.miaudote.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AdocaoAdapter extends RecyclerView.Adapter<MyViewHolderAdocao> {

    private Context context;
    private List<AnimalModel> animalModelList;

    public AdocaoAdapter(Context context, List<AnimalModel> animalModelList) {
        this.context = context;
        this.animalModelList = animalModelList;
    }

    @NonNull
    @Override
    public MyViewHolderAdocao onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_adoption_card, parent, false);
        return new MyViewHolderAdocao(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderAdocao holder, int position) {
        AnimalModel animalModel = animalModelList.get(position);
        Picasso.with(context).load(animalModel.getImgAnimal()).resize(110, 90).into(holder.imgAnimal);
        holder.txtCidadeAnimal.setText(animalModel.getCidadeAnimal());
        holder.txtUfAnimal.setText(animalModel.getUfAnimal());
        holder.txtNomeAnimal.setText(animalModel.getNomeAnimal());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AdoptionPet_Info.class);
                i.putExtra("Imagem Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getImgAnimal());
                i.putExtra("Nome Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getNomeAnimal());
                i.putExtra("Descrição Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getDescAnimal());
                i.putExtra("UF Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getUfAnimal());
                i.putExtra("Cidade Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getCidadeAnimal());
                i.putExtra("Bairro Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getBairroAnimal());
                i.putExtra("Logradouro Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getLograAnimal());
                i.putExtra("Latitude Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getLatAnimal());
                i.putExtra("Longitude Animal Adoção", animalModelList.get(holder.getBindingAdapterPosition()).getLngAnimal());
                i.putExtra("User ID", animalModelList.get(holder.getBindingAdapterPosition()).getUserId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalModelList.size();
    }
}

class MyViewHolderAdocao extends RecyclerView.ViewHolder {

    ImageView imgAnimal;
    TextView txtCidadeAnimal, txtUfAnimal, txtNomeAnimal;
    CardView cardView;

    public MyViewHolderAdocao(@NonNull View itemView) {
        super(itemView);

        imgAnimal = itemView.findViewById(R.id.imgAdpt_Animal);
        txtCidadeAnimal = itemView.findViewById(R.id.txtAdpt_CdAnimal);
        txtUfAnimal = itemView.findViewById(R.id.txtAdpt_UfAnimal);
        txtNomeAnimal = itemView.findViewById(R.id.txtAdpt_nomeAnimal);
        cardView = itemView.findViewById(R.id.card_adptAnimal);
    }
}