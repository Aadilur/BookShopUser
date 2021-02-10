package com.compactit.compactit.compactisolution.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.compactit.compactit.compactisolution.R;
import com.compactit.compactit.compactisolution.model.SliderItem;
import com.compactit.compactit.compactisolution.viewholder.SliderVH;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SliderAdapterExample extends
        SliderViewAdapter<SliderVH> {

     Context context;
     List<String> mSliderItems = new ArrayList<>();

    public SliderAdapterExample(Context context) {
    }

    public SliderAdapterExample(Context context, List<String> mSliderItems) {
        this.context = context;
        this.mSliderItems = mSliderItems;
    }

    @Override
    public SliderVH onCreateViewHolder(ViewGroup parent) {
        @SuppressLint("InflateParams") View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderVH viewHolder, int position) {
        String sliderItem = mSliderItems.get(position);

        Glide.with(viewHolder.itemView)
                .load(sliderItem)
                .fitCenter()
                .into(viewHolder.imageViewBackground);


    }


    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mSliderItems.size();
    }



}
