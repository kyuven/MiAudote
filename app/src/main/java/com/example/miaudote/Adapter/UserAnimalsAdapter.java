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
import com.example.miaudote.PetInfo.Actv_EdtDadosAnimal;
import com.example.miaudote.PetInfo.AdoptionPet_Info;
import com.example.miaudote.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UserAnimalsAdapter extends RecyclerView.Adapter<MyViewHolderUser> {

    private Context context;
    private List<AnimalModel> animalUserList;

    public UserAnimalsAdapter(Context context, List<AnimalModel> animalUserList) {
        this.context = context;
        this.animalUserList = animalUserList;
    }

    @NonNull
    @Override
    public MyViewHolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.animal_adoption_card, parent, false);
        return new MyViewHolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolderUser holder, int position) {
        AnimalModel animalModel = animalUserList.get(position);
        Picasso.get().load(animalModel.getImgAnimal()).resize(110, 90).into(holder.imgAnimal);
        holder.txtCidadeAnimal.setText(animalModel.getCidadeAnimal());
        holder.txtUfAnimal.setText(animalModel.getUfAnimal());
        holder.txtNomeAnimal.setText(animalModel.getNomeAnimal());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, Actv_EdtDadosAnimal.class);
                i.putExtra("Imagem Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getImgAnimal());
                i.putExtra("Nome Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getNomeAnimal());
                i.putExtra("Descrição Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getDescAnimal());
                i.putExtra("UF Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getUfAnimal());
                i.putExtra("Cidade Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getCidadeAnimal());
                i.putExtra("Bairro Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getBairroAnimal());
                i.putExtra("Logradouro Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getLograAnimal());
                i.putExtra("Latitude Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getLatAnimal());
                i.putExtra("Longitude Animal User", animalUserList.get(holder.getBindingAdapterPosition()).getLngAnimal());
                i.putExtra("Animal ID", animalUserList.get(holder.getBindingAdapterPosition()).getAnimalId());
                i.putExtra("User ID", animalUserList.get(holder.getBindingAdapterPosition()).getUserId());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return animalUserList.size();
    }
}

class MyViewHolderUser extends RecyclerView.ViewHolder {

    ImageView imgAnimal;
    TextView txtCidadeAnimal, txtUfAnimal, txtNomeAnimal;
    CardView cardView;

    public MyViewHolderUser(@NonNull View itemView) {
        super(itemView);

        imgAnimal = itemView.findViewById(R.id.imgAdpt_Animal);
        txtCidadeAnimal = itemView.findViewById(R.id.txtAdpt_CdAnimal);
        txtUfAnimal = itemView.findViewById(R.id.txtAdpt_UfAnimal);
        txtNomeAnimal = itemView.findViewById(R.id.txtAdpt_nomeAnimal);
        cardView = itemView.findViewById(R.id.card_adptAnimal);
    }
}