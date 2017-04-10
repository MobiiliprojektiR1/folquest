package com.kantele.folquest;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Folquest
 * Created by Janne ( ͡° ͜ʖ ͡°) on 27.3.2017.
 */

public class QuestBoardAdapter extends BaseAdapter{

    private Context context;
    private ArrayList<Quest> questListValues;

    public QuestBoardAdapter(Context context, ArrayList<Quest> questListValues) {
        this.context = context;
        this.questListValues = questListValues;
    }

    @Override
    public int getCount() {
        return questListValues.size();
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

        View listView;

        if(view == null) {
            listView = new View(context);
            listView = inflater.inflate(R.layout.quest_board_quest, null);

            TextView decriptionText = (TextView) listView.findViewById(R.id.questDescriptionTextView);
            decriptionText.setText(questListValues.get(position).getDescription());

            TextView goalText = (TextView) listView.findViewById(R.id.questGoal);
            goalText.setText(questListValues.get(position).toString());


        } else {
            listView = (View) view;
        }

        return listView;
    }
}
