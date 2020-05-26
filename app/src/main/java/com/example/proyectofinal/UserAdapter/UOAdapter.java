package com.example.proyectofinal.UserAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectofinal.R;
import com.example.proyectofinal.clase.Offer;

import java.util.ArrayList;

public class UOAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Offer> listOffer;

    public UOAdapter (Activity activity, ArrayList<Offer> listOffer) {
        this.activity = activity;
        this.listOffer = listOffer;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View miVista = convertView;
        LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        miVista = inf.inflate(R.layout.activity_u_o_adapter, null);

        Offer offer =listOffer.get(position);

        TextView name = miVista.findViewById(R.id.OfferName);
        name.setText(offer.getType());

        return miVista;
    }


    @Override
    public int getCount() {
        return listOffer.size();
    }

    @Override
    public Object getItem(int position) {
        return listOffer.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}
