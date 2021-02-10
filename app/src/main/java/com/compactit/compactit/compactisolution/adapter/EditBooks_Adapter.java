package com.compactit.compactit.compactisolution.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.compactit.compactit.compactisolution.R;
import com.compactit.compactit.compactisolution.activity.BookDetails;
import com.compactit.compactit.compactisolution.model.Books_Model;
import com.compactit.compactit.compactisolution.viewholder.AllBooks_VH;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class EditBooks_Adapter extends RecyclerView.Adapter<AllBooks_VH> {
    Context context;
    List<Books_Model> li;
    long count = 0;


    DatabaseReference databaseBookRef;
    StorageReference storageReference;

    public EditBooks_Adapter() {

    }

    public EditBooks_Adapter(Context context, List<Books_Model> data) {
        this.context = context;
        this.li = data;
    }

    @NonNull
    @Override
    public AllBooks_VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_books, parent, false);
        return new AllBooks_VH(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull AllBooks_VH holder, int position) {
        final Books_Model data = li.get(position);

        if (data.getCover_img() != null && !data.getCover_img().equals("")) {
            Picasso.get().load(data.getCover_img()).placeholder(R.drawable.place_holder).error(R.drawable.place_holder).into(holder.cover);
        } else Picasso.get().load(R.drawable.place_holder).into(holder.cover);

        holder.book.setText(data.getBook_name());
        holder.author.setText(data.getAuthor());

        holder.price.setText("BDT " + data.getNew_price() + "/=");
        holder.page.setText("Total Page: " + data.getPage() + "p");
        if (data.getGenre().size() >= 1 && !data.getGenre().get(0).equals("")) {
            holder.g1.setText(data.getGenre().get(0));
        } else holder.g1.setVisibility(View.GONE);
        if (data.getGenre().size() >= 2 && !data.getGenre().get(1).equals("")) {
            holder.g2.setText(data.getGenre().get(1));
        } else holder.g2.setVisibility(View.GONE);
        if (data.getGenre().size() >= 3 && !data.getGenre().get(2).equals("")) {
            holder.g3.setText(data.getGenre().get(2));
        } else holder.g3.setVisibility(View.GONE);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String category = TextUtils.join(",", data.getCategory());
                String genre = TextUtils.join(",", data.getGenre());
                String keyword = TextUtils.join(",", data.getKeyword());

                Intent intent = new Intent(context, BookDetails.class);
                intent.putExtra("id", data.getId());
                intent.putExtra("cover",data.getCover_img());
                intent.putExtra("bName", data.getBook_name());
                intent.putExtra("author", data.getAuthor());
                intent.putExtra("price", data.getPrice());
                intent.putExtra("newPrice", data.getNew_price());
                intent.putExtra("page", data.getPage());
                intent.putExtra("condition", data.getCondition());
                intent.putExtra("description", data.getDescription());
                intent.putExtra("category", category);
                intent.putExtra("genre", genre);
                intent.putExtra("keyword", keyword);
                intent.putExtra("status", data.getStatus());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);
            }
        });


    }


    @Override
    public int getItemCount() {
        return li.size();
    }


    private void deleteBook(String id) {
        new AlertDialog.Builder(context)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?")
                // Specifying a listener allows you to take an action before dismissing the dialog.
                // The dialog is automatically dismissed when a dialog button is clicked.
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        databaseBookRef = FirebaseDatabase.getInstance().getReference().child("Books");
                        storageReference = FirebaseStorage.getInstance().getReference().child("Images");

                        databaseBookRef.child(id).child("preview_img");
                        databaseBookRef.child(id).child("preview_img").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        String path = (String) dataSnapshot.getValue();
                                        System.out.println("=================================== " + path);
                                        if (path != null && !path.equals("")) {
                                            StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(path);
                                            reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @SuppressLint("SetTextI18n")
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    dataSnapshot.getRef().removeValue();
                                                }
                                            });
                                        }
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                            }
                        });

                        StorageReference coverImage = storageReference.child("cover_img").child(id).child("coverImg.png");
                        coverImage.delete();
                        StorageReference authorImage = storageReference.child("author_img").child(id).child("authorImg.png");
                        authorImage.delete();
                        databaseBookRef.child(id).removeValue();
                        Toast.makeText(context, "Delete Successful", Toast.LENGTH_SHORT).show();
                    }
                })
                // A null listener allows the button to dismiss the dialog and take no further action.
                .setNegativeButton("No", null)
                .setIcon(R.drawable.ic_right)
                .show();


//        databaseBookRef.child(id).child("preview_img").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        String path = (String) dataSnapshot.getValue();
//                        assert path != null;
//                        StorageReference reference = FirebaseStorage.getInstance().getReferenceFromUrl(path);
//                        reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @SuppressLint("SetTextI18n")
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                dataSnapshot.getRef().removeValue();
//
//
//                            }
//                        });
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }

}
