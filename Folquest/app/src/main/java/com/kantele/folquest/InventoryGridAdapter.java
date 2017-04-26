package com.kantele.folquest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Folquest
 * Created by Janne ( ͡° ͜ʖ ͡°) on 27.3.2017.
 */

public class InventoryGridAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Item> gridValues;

    PlayerController controller;

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

            controller = (PlayerController) context.getApplicationContext();

            ImageView imageView = (ImageView) gridView.findViewById(R.id.itemImageButton);

            String tab = controller.getChosenTab();

            if (Objects.equals("" + tab, "Accessory")) {

                int accID = context.getResources().getIdentifier(controller.ownedAccessoryItems.get(position).getItemId() + "_icon", "mipmap", context.getPackageName());
                imageView.setImageResource(accID);

                Log.i("ICONID", "accID: " + accID);
            }

            if (Objects.equals("" + tab, "Head")) {

                int headID = context.getResources().getIdentifier(controller.ownedHeadItems.get(position).getItemId() + "_icon", "mipmap", context.getPackageName());
                imageView.setImageResource(headID);

                Log.i("ICONID", "headID: " + headID);
            }

            if (Objects.equals("" + tab, "Torso")) {

                int torsoID = context.getResources().getIdentifier(controller.ownedTorsoItems.get(position).getItemId() + "_icon", "mipmap", context.getPackageName());
                imageView.setImageResource(torsoID);
                Log.i("ICONID", "torsoID: " + torsoID);
            }

            if (Objects.equals("" + tab, "Bottom")) {

                int bottomID = context.getResources().getIdentifier(controller.ownedBottomItems.get(position).getItemId() + "_icon", "mipmap", context.getPackageName());
                imageView.setImageResource(bottomID);
                Log.i("ICONID", "bottomID: " + bottomID);
            }

            if (Objects.equals("" + tab, "Feet")) {

                int feetID = context.getResources().getIdentifier(controller.ownedFeetItems.get(position).getItemId() + "_icon", "mipmap", context.getPackageName());
                imageView.setImageResource(feetID);
                Log.i("ICONID", "feetID: " + feetID);
            }

        } else {
            gridView = (View) view;
        }

        return gridView;
    }
}
