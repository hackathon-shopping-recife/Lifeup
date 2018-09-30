package com.lifeup.recpontos.ui.fragments;

import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lifeup.recpontos.R;
import com.lifeup.recpontos.model.domain.Promo;
import com.lifeup.recpontos.model.request.Request;

import java.util.List;


public class Discount extends Fragment {

    private ListView promoList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_discount, container, false);

        promoList = (ListView) v.findViewById(R.id.list_promo);
        Request.getInstance().getPromo(getActivity(), promoList);

        return v;
    }
}
