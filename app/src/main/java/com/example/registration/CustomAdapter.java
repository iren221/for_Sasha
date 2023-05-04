package com.example.registration;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    ArrayList<String> pet_name;
    ArrayList<String> pet_id;
    ArrayList<Uri> pet_photo;

    CustomAdapter(Context context,
                  ArrayList<String> pet_name, ArrayList<Uri> pet_photo, ArrayList<String> pet_id) {
        this.context = context;
        this.pet_name = pet_name;
        this.pet_photo = pet_photo;
        this.pet_id = pet_id;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.MyViewHolder holder, int position) {
        final Bitmap bitmap;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.context.getContentResolver(), pet_photo.get(position));
            holder.photo_pet.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        holder.name_pet.setText(String.valueOf(pet_name.get(position)));
        holder.id_pet.setText(String.valueOf(pet_id.get(position)));

        DBHelper dbase = new DBHelper(context.getApplicationContext());

        holder.photo_pet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context.getApplicationContext(), "You clicked" + holder.id_pet.getText().toString(), Toast.LENGTH_SHORT).show();
                Boolean chekpet = dbase.chekPet(holder.id_pet.getText().toString());
                if (chekpet) {
                    Toast.makeText(context.getApplicationContext(), "Успешно", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pet_name.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView name_pet, id_pet;
        ImageView photo_pet;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name_pet = itemView.findViewById(R.id.text_pet_name);
            id_pet = itemView.findViewById(R.id.id_pet);
            photo_pet = itemView.findViewById(R.id.image_photo_pet);
        }
    }
}
