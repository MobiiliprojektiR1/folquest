package com.kantele.folquest;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Folquest
 * Created by Janne ( ͡° ͜ʖ ͡°) on 27.3.2017.
 */

public class QuestBoardAdapter extends BaseAdapter {


    private View view;

    public boolean visibility = true;

    private Context context;
    private ArrayList<Quest> questListValues;
    Button discardQuest, acceptQuest;
    public TextView goalText;
    LayoutInflater inflater;
    public QuestBoardAdapter(Context context, ArrayList<Quest> questListValues) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.questListValues = questListValues;
    }

    @Override
    public int getCount() {
        return questListValues.size();
    }

    @Override
    public Object getItem(int position) {
        return questListValues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return questListValues.indexOf(getItem(position));
    }

    public void deleteItem (int position) {
        questListValues.remove(position);
        this.notifyDataSetChanged();
    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    void toggleCheckBoxImage(int position){
        if(questListValues.get(position).isQuestActive()){
            acceptQuest.setBackgroundResource(R.mipmap.check_icon_104x96px);
        } else {
            acceptQuest.setBackgroundResource(R.mipmap.unchecked_icon_96x96px);
        }
        this.notifyDataSetChanged();
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup viewGroup) {
        LinearLayout view = (LinearLayout) convertView;
        if(view == null){
            view = (LinearLayout) inflater.inflate(R.layout.quest_board_quest, viewGroup, false);
        }

       /* TextView decriptionText = (TextView) view.findViewById(R.id.questDescriptionTextView);
        decriptionText.setText(questListValues.get(position).getDescription());*/

        goalText = (TextView) view.findViewById(R.id.questGoal);
        goalText.setText(questListValues.get(position).toString());

        discardQuest = (Button) view.findViewById(R.id.discardQuestButton);
        discardQuest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("ListView", position+"");
                deleteItem(position);


            }
        });

        acceptQuest = (Button) view.findViewById(R.id.acceptQuestButton);

        acceptQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ListView", position+"");

                //check that its the instance of quest board activity, to get the quest from quest board activity
                if(context instanceof QuestBoardActivity) {
                    if(questListValues.get(position).isQuestActive()) {
                        questListValues.get(position).setQuestActive(false);
                        ((QuestBoardActivity)context).removeQuest(questListValues.get(position));
                        toggleCheckBoxImage(position);
                    } else {
                        ((QuestBoardActivity)context).addQuest(position);
                        questListValues.get(position).setQuestActive(true);
                        toggleCheckBoxImage(position);
                    }
                }
            }
        });

        if(questListValues.get(position).isQuestActive()){
            acceptQuest.setBackgroundResource(R.mipmap.check_icon_104x96px);
        } else {
            acceptQuest.setBackgroundResource(R.mipmap.unchecked_icon_96x96px);
        }

        return view;
/*
        Log.d("QuestAdapter", "getView()");

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null) {
            listView = new View(context);
            listView = inflater.inflate(R.layout.quest_board_quest, null);

        } else {
            listView = (View) view;
        }


        TextView decriptionText = (TextView) listView.findViewById(R.id.questDescriptionTextView);
        decriptionText.setText(questListValues.get(position).getDescription());

        TextView goalText = (TextView) listView.findViewById(R.id.questGoal);
        goalText.setText(questListValues.get(position).toString());

        discardQuest = (Button) listView.findViewById(R.id.discardQuestButton);
        discardQuest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Log.d("ListView", position+"");
                deleteItem(position);


            }
        });

        acceptQuest = (CheckBox) listView.findViewById(R.id.acceptQuestButton);
        acceptQuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ListView", position+"");
                //check that its the instance of questboard activity, to get the quest ftom questboard activity
                if(context instanceof QuestBoardActivity) {
                    ((QuestBoardActivity)context).addQuest(position);
                    //deleteItem(position);
                }
            }
        });
        Log.d("QuestAdapter", this.getItemId(position) + " AcceptQuest button visibility = " + acceptQuest.getVisibility()+"");
        return listView;*/
    }

}
