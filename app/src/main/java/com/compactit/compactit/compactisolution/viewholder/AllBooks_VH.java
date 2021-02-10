package com.compactit.compactit.compactisolution.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.compactit.compactit.compactisolution.R;

public class AllBooks_VH extends RecyclerView.ViewHolder {


    public ConstraintLayout constraintLayout;
    public ImageView cover, view_all;
    public TextView book, author, price, page, g1, g2, g3;

    public AllBooks_VH(@NonNull View itemView) {
        super(itemView);

        constraintLayout = itemView.findViewById(R.id.vh_layout1);
        cover = itemView.findViewById(R.id.vh_coverImg1);
        book = itemView.findViewById(R.id.vh_bookName);
        author = itemView.findViewById(R.id.vh_authorName);
        price = itemView.findViewById(R.id.vh_price);
        page = itemView.findViewById(R.id.vh_page);
        g1 = itemView.findViewById(R.id.vh_g1);
        g2 = itemView.findViewById(R.id.vh_g2);
        g3 = itemView.findViewById(R.id.vh_g3);

        view_all = itemView.findViewById(R.id.view_all);
    }
}
