package com.clinic.mobile;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class ConsumeJsonActivity extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		new DownloadJsonAsyncTask()
				.execute("http://10.0.0.21:3000/clients/clientlist");
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);

		Client client = (Client) l.getAdapter().getItem(position);

		Intent intent = new Intent(this, DailySchedule.class);
		intent.putExtra("client", client);
		startActivity(intent);
	}

	class DownloadJsonAsyncTask extends AsyncTask<String, Void, List<Client>> {
		ProgressDialog dialog;

		//Exibe pop-up indicando que está sendo feito o download do JSON
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = ProgressDialog.show(ConsumeJsonActivity.this, "Wait",
					"Connecting to the server...");
		}

		//Acessa o serviço do JSON e retorna a lista de clientes
		@Override
		protected List<Client> doInBackground(String... params) {
			String urlString = params[0];
			HttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet(urlString);
			try {
				HttpResponse response = httpclient.execute(httpget);
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					InputStream instream = entity.getContent();
					String json = getStringFromInputStream(instream);
					instream.close();
					List<Client> clients = getClients(json);
					return clients;
				}
			} catch (Exception e) {
				Log.e("Error", "Failed to access Web service", e);
			}
			return null;
		}


		//Depois de executada a chamada do serviço
		@Override
		protected void onPostExecute(List<Client> result) {
			super.onPostExecute(result);
			dialog.dismiss();
			if (result.size() > 0) {
				ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(
						ConsumeJsonActivity.this,
						android.R.layout.simple_list_item_1, result);
				setListAdapter(adapter);
			} else {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						ConsumeJsonActivity.this)
						.setTitle("Error")
						.setMessage("Unable to access the information!!")
						.setPositiveButton("OK", null);
				builder.create().show();
			}
		}

		//Retorna uma lista de clientes com as informações retornadas do JSON
		private List<Client> getClients(String jsonString) {
			List<Client> clients = new ArrayList<Client>();
			try {
				JSONArray clientsJson = new JSONArray(jsonString);
				JSONObject client;

				for (int i = 0; i < clientsJson.length(); i++) {
					client = new JSONObject(clientsJson.getString(i));
					Log.i("CUSTOMER FOUND: ",
							"fullname=" + client.getString("fullname"));

					Client objetoClient = new Client();
					objetoClient.setFullname(client.getString("fullname"));
					objetoClient.setPhone(client.getString("phone"));
					objetoClient.setHealthPlan(client.getString("healthPlan"));
					objetoClient.setHealthPlanNumber(client.getString("healthPlanNumber"));
					objetoClient.setSchedulingDate(client.getString("schedulingDate"));
					objetoClient.setSchedulingHour(client.getString("schedulingHour"));
					clients.add(objetoClient);
				}

			} catch (JSONException e) {
				Log.e("Error", "Error in the JSON parsing", e);
			}
			return clients;
		}


		//Converte objeto InputStream para String
		private String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return sb.toString();

		}

	}
}
