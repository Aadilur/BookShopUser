package com.compactit.compactit.compactisolution.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.compactit.compactit.compactisolution.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderVH  extends SliderViewAdapter.ViewHolder {

       public View itemView;
       public ImageView imageViewBackground;
      public   ImageView imageGifContainer;
       public TextView textViewDescription;

        public SliderVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.iv_auto_image_slider);
            imageGifContainer = itemView.findViewById(R.id.iv_gif_container);
            textViewDescription = itemView.findViewById(R.id.tv_auto_image_slider);
            this.itemView = itemView;
        }
    }

