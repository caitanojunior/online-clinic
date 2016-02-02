package rest.controlpoint.cliniconline;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        new HttpRequestTask().execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            new HttpRequestTask().execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    private class HttpRequestTask extends AsyncTask<Void, Void, Clientlist> {
        @Override
        protected Clientlist doInBackground(Void... params) {
            try {
                final String url = "http://localhost:3000/clients/clientlist/";
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                Clientlist clientlist = restTemplate.getForObject(url, Clientlist.class);
                return clientlist;
            } catch (Exception e) {
                Log.e("MainActivity", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(Clientlist clientlist) {
            TextView clientIdText = (TextView) findViewById(R.id._id_value);
            TextView clientFullnameText = (TextView) findViewById(R.id.fullname_value);
            TextView clientPhoneText = (TextView) findViewById(R.id.phone_value);
            TextView clientHealthPlanText = (TextView) findViewById(R.id.healthPlan_value);
            TextView clientHealthPlanNumberText = (TextView) findViewById(R.id.healthPlanNumber_value);
            TextView clientSchedulingDateText = (TextView) findViewById(R.id.schedulingDate_value);
            TextView clientSchedulingHourText = (TextView) findViewById(R.id.schedulingHour_value);

            clientIdText.setText(clientlist.get_id());
            clientFullnameText.setText(clientlist.getFullname());
            clientPhoneText.setText(clientlist.getPhone());
            clientHealthPlanText.setText(clientlist.getHealthPlan());
            clientHealthPlanNumberText.setText(clientlist.getHealthPlanNumber());
            clientSchedulingDateText.setText(clientlist.getSchedulingDate());
            clientSchedulingHourText.setText(clientlist.getSchedulingHour());
        }

    }

}
