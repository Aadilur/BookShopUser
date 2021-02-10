package com.compactit.compactit.compactisolution.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.compactit.compactit.compactisolution.R;

public class userOrder_VH extends RecyclerView.ViewHolder {

    public ImageView cover;
    public TextView tnxid,bname,aname, quantity,price,status,date;

    public userOrder_VH(@NonNull View itemView) {
        super(itemView);
        cover = itemView.findViewById(R.id.userCover);
        tnxid = itemView.findViewById(R.id.userTxnID);
        bname = itemView.findViewById(R.id.userBookName);
        aname = itemView.findViewById(R.id.userAuthor);
        quantity = itemView.findViewById(R.id.userQuantity);
        price = itemView.findViewById(R.id.userTotalPrice);
        status = itemView.findViewById(R.id.userStatus);
        date = itemView.findViewById(R.id.userOrderDate);
    }
}
