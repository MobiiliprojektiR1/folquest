package com.kantele.folquest;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Folquest
 * Created by Janne ( ͡° ͜ʖ ͡°) on 27.3.2017.
 */

public class InventoryGridAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Item> gridValues;

    public InventoryGridAdapter(Context context, ArrayList<Item> gridValues) {
        this.context = context;
        this.gridValues = gridValues;
    }

    @Override
    public int getCount() {
        return gridValues.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if(view == null) {
            gridView = new View(context);
            gridView = inflater.inflate(R.layout.inventory_grid_item, null);

            ImageView imageView = (ImageView) gridView.findViewById(R.id.itemImageButton);
            imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            gridView = (View) view;
        }

        return gridView;
    }
}
