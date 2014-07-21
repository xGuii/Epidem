package com.faculdade.epidem.controllers;

import com.faculdade.epidem.R;
import com.faculdade.epidem.model.Coordenada;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;



public class CoordListActivity extends BaseAdapter{
	public Activity activity;
	public ArrayList<Coordenada> values;
	
	public CoordListActivity(Activity activity, ArrayList<Coordenada> values){		
		this.activity = activity;
		this.values = values;
	}
	
	public View getView(int position, View convertView, ViewGroup parent){
		View rowView = convertView;
		
		if(convertView == null){
			LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_layout, null);
		}
		TextView txtId = (TextView) rowView.findViewById(R.id.Id);
		TextView txtLatitude = (TextView) rowView.findViewById(R.id.Latitude);
		TextView txtLongitude = (TextView) rowView.findViewById(R.id.Longitude);
		TextView txtDescricao = (TextView) rowView.findViewById(R.id.Descricao);
		
		Coordenada coordenada = values.get(position);
		
		txtId.setText("ID: "+ String.valueOf(coordenada.getId()));
		txtLatitude.setText("Latitude: "+ String.valueOf(coordenada.getLatitude()));
		txtLongitude.setText(", Longitude: "+ String.valueOf(coordenada.getLongitude()));
		txtDescricao.setText("Desc: "+coordenada.getDescricao());
		
		return rowView;
	}
	

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return values.size();
	}
	
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return values.get(position);
	}
	

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
}
