package com.lifeup.recpontos.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.lifeup.recpontos.R;
import com.lifeup.recpontos.model.domain.Compra;

import java.util.List;

public class ComprasAdapter extends ArrayAdapter<Compra> {

    private List<Compra> items;
    private Activity activity;
    private int resource;

    public ComprasAdapter(@LayoutRes int resource, Activity activity, List<Compra> items) {
        super(activity.getApplicationContext(), resource, items);
        this.resource = resource;
        this.activity = activity;
        this.items = items;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = activity.getLayoutInflater().from(activity.getApplicationContext()).inflate(this.resource, null);
        }

        Compra p = items.get(position);
        TextView title = view.findViewById(R.id.title);
        title.setText(p.getName());
        TextView pontos = view.findViewById(R.id.pontos);
        pontos.setText("Você conseguiu arrecadar " + Integer.toString(p.getTotalValue()) + " este mês!");
        return view;
    }

}
