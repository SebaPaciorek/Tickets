/*
 * Created by Sebastian Paciorek on 6.3.2019
 * Copyright (c) 2019.  All rights reserved.
 * Last modified 06.03.19 23:54
 */

package buying.tickets.choosemethod;
/**
 * Created by Sebastian Paciorek
 */
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import buying.tickets.R;

public class ChooseMethodAdapter extends PagerAdapter {

    private Context context;
    private ArrayList<ChooseMethodItem> chooseMethodItems;

    public static int screenHeight;

    public ChooseMethodAdapter(Context context, ArrayList<ChooseMethodItem> chooseMethodItems) {
        this.context = context;
        this.chooseMethodItems = chooseMethodItems;
    }

    @Override
    public int getCount() {
        return chooseMethodItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.choosemethod_item, container, false);

        ChooseMethodItem chooseMethodItem = chooseMethodItems.get(position);

        ImageView imageView = itemView.findViewById(R.id.choose_methodImageView);
        imageView.setImageResource(chooseMethodItem.getImageID());
        if (screenHeight < 1920) imageView.getLayoutParams().height = 250;

        TextView titleTextView = itemView.findViewById(R.id.choose_method_titleTextView);
        titleTextView.setText(chooseMethodItem.getTitle());

        TextView descriptionTextView = itemView.findViewById(R.id.choose_method_descriptionTextView);
        descriptionTextView.setText(chooseMethodItem.getDescription());

        container.addView(itemView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               ChooseMethodActivity.getInstance().startChosenMethodActivity();
            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}
