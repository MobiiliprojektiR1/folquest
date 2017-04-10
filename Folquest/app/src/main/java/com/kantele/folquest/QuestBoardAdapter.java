package com.kantele.folquest;

import android.content.Context;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Folquest
 * Created by Janne ( ͡° ͜ʖ ͡°) on 27.3.2017.
 */

public class QuestBoardAdapter extends BaseAdapter implements ListAdapter{


    private View listView;

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


    public void deleteItem (int position) {
        questListValues.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);



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
        Button discardQuest = (Button) listView.findViewById(R.id.discardQuestButton);
        discardQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ListView", position+"");
                deleteItem(position);
            }
        });

        return listView;
    }
}
