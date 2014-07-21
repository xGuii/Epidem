package com.faculdade.epidem;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.faculdade.epidem.interfaces.OnTaskCompleted;
import com.faculdade.epidem.model.Coordenada;
import com.faculdade.epidem.model.Opcao;
import com.faculdade.epidem.resources.ServiceTaskCoordenadas;
import com.faculdade.epidem.resources.ServiceTaskOpcoes;

public class Coordinates extends Activity implements OnClickListener, OnTaskCompleted {
	
	private ServiceTaskCoordenadas stc = null;
	private EditText descricao;
	private Spinner spnOpcoes;
	private float lat, lon;
	private Button btnSend;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_coordinates);

		Bundle b = getIntent().getExtras();
		lat = (float) b.getDouble("LATITUDE");
		lon = (float) b.getDouble("LONGITUDE");
		
		descricao = (EditText) findViewById(R.id.descriptionTextIns);
		spnOpcoes = (Spinner) findViewById(R.id.spinnerIns);
		btnSend = (Button) findViewById(R.id.btnIns);
		
		btnSend.setOnClickListener(this);
		
		ServiceTaskOpcoes sto = new ServiceTaskOpcoes(this, "", this);
		sto.execute();
		
		if (savedInstanceState == null) {
			
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.coordinates, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void onTaskCompleted(Object result) {
		if(stc == null) {
			ArrayAdapter<Opcao> adapter = new ArrayAdapter<Opcao>(this, android.R.layout.simple_spinner_item, (ArrayList<Opcao>) result);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spnOpcoes.setAdapter(adapter);	
		} else {
			String resultMessage = "Falha ao salvar a localização.";
			if(Boolean.parseBoolean(result.toString())){
				resultMessage = "Dados salvos com sucesso!";
			}
			
			Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	public void onClick(View v) {
		Opcao opcao = (Opcao) spnOpcoes.getSelectedItem();
		
		Coordenada coordenada = new Coordenada();
		coordenada.setDescricao(descricao.getText().toString());
		coordenada.setLatitude(lat);
		coordenada.setLongitude(lon);
		coordenada.setTipo(opcao.getId());
		
		stc = new ServiceTaskCoordenadas(this, "Salvando dados...", this, coordenada);
		stc.execute();
	}
	
}
