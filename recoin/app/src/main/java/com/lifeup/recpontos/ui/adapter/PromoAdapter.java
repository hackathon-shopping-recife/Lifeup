package com.lifeup.recpontos.ui.adapter;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.lifeup.recpontos.R;
import com.lifeup.recpontos.model.domain.Promo;

import java.util.List;

public class PromoAdapter extends ArrayAdapter<Promo> {

    private List<Promo> items;
    private Activity activity;
    private int resource;

    public PromoAdapter(@LayoutRes int resource, Activity activity, List<Promo> items) {
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

        Promo p = items.get(position);

        TextView title = view.findViewById(R.id.title);
        TextView description = view.findViewById(R.id.description);
        TextView points = view.findViewById(R.id.pontos);

        title.setText(p.getTitle());
        description.setText(p.getDescription());
        points.setText(p.getPrice());

        return view;
    }
}