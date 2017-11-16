package com.example.administrator.ccoupons.Things;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.ccoupons.R;

import java.util.List;

/**
 * Created by CZJ on 2017/11/15.
 */

public class ThingsCategoryAdpater extends ArrayAdapter<ThingsCategory> {
    private int resourceId;
    private int chosen;
    public ThingsCategoryAdpater(Context context, int textViewResourceId,
                                 List<ThingsCategory> objects, int chosen) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ThingsCategory cat = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);
        ImageView icon = (ImageView) view.findViewById(R.id.thing_cat_icon);
        TextView text = (TextView) view.findViewById(R.id.thing_cat_text);
        icon.setImageResource(cat.getResId());
        text.setText(cat.getProduct());
        if (position == chosen) {
            ImageView chosen = (ImageView) view.findViewById(R.id.thing_cat_chosen);
            chosen.setVisibility(View.VISIBLE);
            text.setTextColor(view.getResources().getColor(R.color.black));
        }
        return view;
    }

    public boolean setChosen(int chosen) {
        if(this.chosen == chosen)
            return false;
        else {
            this.chosen = chosen;
            return true;
        }
    }
}
