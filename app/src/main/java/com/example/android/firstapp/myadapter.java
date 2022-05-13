package com.example.android.firstapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.android.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>
{
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options)
    {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull final myviewholder holder, @SuppressLint("RecyclerView") final int position, @NonNull final model model)
    {
       holder.name.setText(model.getName());
       holder.departement.setText(model.getDepartement());
       holder.email.setText(model.getEmail());


                    holder.edit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            final DialogPlus dialogPlus=DialogPlus.newDialog(holder.img.getContext())
                                    .setContentHolder(new ViewHolder(R.layout.dialogcontent))
                                    .setExpanded(true,1100)
                                    .create();

                            View myview=dialogPlus.getHolderView();
                            final EditText name=myview.findViewById(R.id.uname);
                            final EditText departement=myview.findViewById(R.id.udepartement);
                            final EditText email=myview.findViewById(R.id.uemail);
                            Button submit=myview.findViewById(R.id.usubmit);


                            name.setText(model.getName());
                            departement.setText(model.getDepartement());
                            email.setText(model.getEmail());

                            dialogPlus.show();

                                submit.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Map<String,Object> map=new HashMap<>();
                                        map.put("name",name.getText().toString());
                                        map.put("email",email.getText().toString());
                                        map.put("departement",departement.getText().toString());

                                        FirebaseDatabase.getInstance().getReference().child("professeurs")
                                                .child(getRef(position).getKey()).updateChildren(map)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        dialogPlus.dismiss();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        dialogPlus.dismiss();
                                                    }
                                                });
                                    }
                                });


                        }
                    });


                    holder.delete.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            AlertDialog.Builder builder=new AlertDialog.Builder(holder.img.getContext());
                            builder.setTitle("Suppression");
                            builder.setMessage("Voulez vous effectuez cette suppression ?");

                            builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    FirebaseDatabase.getInstance().getReference().child("professeurs")
                                            .child(getRef(position).getKey()).removeValue();
                                }
                            });

                            builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });

                            builder.show();
                        }
                    });

    } // End of OnBindViewMethod

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
       View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
       return new myviewholder(view);
    }


    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        ImageView edit,delete;
        TextView name,departement,email;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
            img=(CircleImageView) itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
            departement=(TextView)itemView.findViewById(R.id.departementtext);
            email=(TextView)itemView.findViewById(R.id.emailtext);

            edit=(ImageView)itemView.findViewById(R.id.editicon);
            delete=(ImageView)itemView.findViewById(R.id.deleteicon);
        }
    }
}
