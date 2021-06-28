package aguashorizonte.com.br.aguashorizonte;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.InputStream;
import java.net.*;

public class MainActivity extends AppCompatActivity {

    ProgressDialog pDialog;
    EditText campoNome, campoTelefone, campoPedido;
    Button Enviar;
    boolean status;
    String nome, telefone, pedido;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        campoNome = (EditText) findViewById(R.id.nome);
        campoTelefone = (EditText) findViewById(R.id.telefone);
        campoPedido = (EditText) findViewById(R.id.pedido);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Enviar = (Button) findViewById(R.id.enviar);

        Enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nome = campoNome.getText().toString();
                telefone = campoTelefone.getText().toString();
                pedido = campoPedido.getText().toString();

                if(nome.equals("")||nome==null){
                    campoNome.setHint("Invalido");
                }else{
                    if(telefone.equals("")||telefone==null){
                        campoTelefone.setHint("Invalido");
                        }else{
                        if(pedido.equals("")||pedido==null){
                            campoPedido.setHint("Invalido");
                        }else {
                            new envioEmail().execute();
                        }

                    }


                }



            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri tel = Uri.parse("tel:986139535");
                Intent it = new Intent(Intent.ACTION_CALL,tel);
                startActivity(it);

            }
        });

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_site) {

            Uri uri = Uri.parse("http://www.vendeagua.com.br/");
            Intent its = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(its);

            return true;
        }

        if (id == R.id.action_catalogo) {


            Intent it =new Intent(this,catalogo2.class);
            startActivity(it);

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://aguashorizonte.com.br.aguashorizonte/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://aguashorizonte.com.br.aguashorizonte/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    class envioEmail extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(MainActivity.this);
            pDialog.setMessage("Enviando Pedido");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        /**
         * Getting product details in background thread
         */
        protected String doInBackground(String... params) {
            try {
                String strurl = "http://vendeagua.com.br/site/email.php?cliente=" + URLEncoder.encode(nome, "UTF-8") + "&telefone=" + URLEncoder.encode(telefone, "UTF-8") + "&endereco=" + URLEncoder.encode(pedido, "UTF-8");

                URL url = new URL(strurl);

                InputStream is = url.openStream();

                int i;

                String conteudo = "";

                while ((i = is.read()) != -1) {
                    conteudo += ((char) i);
                }
                conteudo = conteudo.trim();
                if (conteudo.equals("sende();")) {
                    status = true;

                } else {
                    status = false;
                }


            } catch (Exception e) {
            }


            return null;
        }


        /**
         * After completing background task Dismiss the progress dialog
         **/
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once got all details
            pDialog.dismiss();
            if (status == true) {
                Toast.makeText(MainActivity.this, "Sucesso No Envio", Toast.LENGTH_LONG).show();
                campoNome.setText("");
                campoTelefone.setText("");
                campoPedido.setText("");

                campoNome.setHint("Carlos");
                campoTelefone.setHint("34946570");
                campoPedido.setHint("2 Agúas,cs-23,R-23");

            } else {
                Toast.makeText(MainActivity.this, "Falha No Envio", Toast.LENGTH_LONG).show();

            }

        }
    }

}
