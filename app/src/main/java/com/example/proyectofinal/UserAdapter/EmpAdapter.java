package com.example.proyectofinal.UserAdapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.example.proyectofinal.R;
import com.example.proyectofinal.clase.Emp;

import java.util.ArrayList;


public class EmpAdapter extends BaseAdapter {

    protected Activity activity;
    protected ArrayList<Emp> listEmp;

    public EmpAdapter(Activity activity, ArrayList<Emp> listEmp) {
        this.activity = activity;
        this.listEmp = listEmp;
    }

    @Override
    public int getCount() {
        return listEmp.size();
    }

    @Override
    public Object getItem(int position) {
        return listEmp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View miVista = convertView;
        LayoutInflater inf = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        miVista = inf.inflate(R.layout.activity_emp_adapter, null);

        Emp emp =listEmp.get(position);

        TextView name = miVista.findViewById(R.id.EmpName);
        name.setText(emp.getName());

        TextView des = miVista.findViewById(R.id.empName);
        des.setText(emp.getDescription());
        return miVista;
    }
}
