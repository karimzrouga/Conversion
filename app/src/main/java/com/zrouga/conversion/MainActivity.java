package com.zrouga.conversion;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private Button conver;
    private Spinner sps, spd;
    private TextView txtres, txtdest, res, convres;
    private EditText amount;
    private RadioButton r1, r2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initview();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Conversion");
        menu.add(0, 1, 0, r1.getText());
        menu.add(0, 2, 0, r2.getText());
        menu.add(0, 3, 0, " Conversion Température");
        menu.add(0, 4, 0, "Quitter");

    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Toast.makeText(this, r1.getText(), Toast.LENGTH_SHORT).show();
                return true;
            case 2:
                Toast.makeText(this, r2.getText(), Toast.LENGTH_SHORT).show();
                return true;
            case 3:
                startActivity(new Intent(this, MainActivity2.class));

                return true;
            case 4:
                finish();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 1, 0, "Conversion C <--> F");
        menu.add(0, 2, 0, "Quitter");
        return super.onCreateOptionsMenu(menu);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 1:
                Intent intent = new Intent(this, MainActivity2.class);
                startActivity(intent);
                return true;
            case 2:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void initview() {

        txtres = findViewById(R.id.txts);
        txtdest = findViewById(R.id.txtdest);

        amount = findViewById(R.id.amount);
        res = findViewById(R.id.exchangeRate);
        sps = findViewById(R.id.fromCurrency);
        spd = findViewById(R.id.toCurrency);
        convres = findViewById(R.id.convres);
        conver = findViewById(R.id.convertButton);
        r1 = findViewById(R.id.from);
        r2 = findViewById(R.id.to);
        registerForContextMenu(findViewById(R.id.from));
        registerForContextMenu(findViewById(R.id.to));
        sps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                String currency = extracted(i);
                txtres.setText(currency);


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String currency = extracted(i);
                txtdest.setText(currency);
                r2.setText(currency + "  -----> " + txtres.getText());
                r1.setText(txtres.getText() + "  -----> " + currency);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        conver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = amount.getText().toString();
                if (str.isEmpty()) {
                    showAlert();
                } else {
                    double amountToConvert = Double.parseDouble(str);

                    double x = getExchangeRate(txtres.getText().toString(), txtdest.getText().toString());

                    double val = amountToConvert * x;
                    res.setText("Exchange Rate: " + x);
                    convres.setText(val + "");
                }

            }

        });

    }

    private void showAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Champ vide").setMessage("Le champ ne peut pas être vide.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String extracted(int i) {
        String currency = "";
        switch (i) {
            case 0:
                currency = "TND";
                break;
            case 1:
                currency = "EUR";
                break;
            case 2:
                currency = "DZD";
            case 3:
                currency = "EUR";
                break;
            case 4:
                currency = "SEK";
                break;
            case 5:
                currency = "CHF";
                break;
            case 6:
                currency = "TRY";
                break;
            case 7:
                currency = "UAH";
                break;
            case 8:
                currency = "GBP";
                break;
        }
        return currency;
    }

    private double getExchangeRate(String sourceCurrency, String targetCurrency) {
        HashMap<String, Double> exchangeRates = new HashMap<>();
        initmap(exchangeRates);
        String key = "";
        if (r1.isChecked()) {
            key = sourceCurrency + "_" + targetCurrency;
        } else {
            key = targetCurrency + "_" + sourceCurrency;
        }


        if (exchangeRates.containsKey(key)) {
            return exchangeRates.get(key);
        } else {
            return 1.0;
        }
    }

    private void initmap(HashMap<String, Double> exchangeRates) {
        // Euro
        exchangeRates.put("EUR_USD", 1.12);
        exchangeRates.put("USD_EUR", 0.89);
        exchangeRates.put("EUR_DZD", 134.39);
        exchangeRates.put("DZD_EUR", 0.0075);
        exchangeRates.put("EUR_TND", 0.33);
        exchangeRates.put("TND_EUR", 3.30);
        exchangeRates.put("EUR_SEK", 10.39);
        exchangeRates.put("SEK_EUR", 0.096);
        exchangeRates.put("EUR_CHF", 1.08);
        exchangeRates.put("CHF_EUR", 0.93);
        exchangeRates.put("EUR_TRY", 9.63);
        exchangeRates.put("TRY_EUR", 0.10);
        exchangeRates.put("EUR_UAH", 31.35);
        exchangeRates.put("UAH_EUR", 0.032);
        exchangeRates.put("EUR_GBP", 0.85);
        exchangeRates.put("GBP_EUR", 1.18);
        // Dollar américain (USD)
        exchangeRates.put("USD_DZD", 120.39);
        exchangeRates.put("DZD_USD", 0.0083);
        exchangeRates.put("USD_TND", 2.74);
        exchangeRates.put("TND_USD", 0.365);
        exchangeRates.put("USD_SEK", 9.30);
        exchangeRates.put("SEK_USD", 0.107);
        exchangeRates.put("USD_CHF", 1.00);
        exchangeRates.put("CHF_USD", 1.00);
        exchangeRates.put("USD_TRY", 8.62);
        exchangeRates.put("TRY_USD", 0.116);
        exchangeRates.put("USD_UAH", 27.87);
        exchangeRates.put("UAH_USD", 0.036);
        exchangeRates.put("USD_GBP", 0.75);
        exchangeRates.put("GBP_USD", 1.33);
// Dinar tn
        exchangeRates.put("TND_EUR", 3.30);
        exchangeRates.put("TND_DZD", 367.91);
        exchangeRates.put("DZD_TND", 0.0027);
        exchangeRates.put("TND_USD", 0.365);
        exchangeRates.put("USD_TND", 2.74);
        exchangeRates.put("TND_SEK", 3.53);
        exchangeRates.put("SEK_TND", 0.283);
        exchangeRates.put("TND_CHF", 3.26);
        exchangeRates.put("CHF_TND", 0.307);
        exchangeRates.put("TND_TRY", 29.01);
        exchangeRates.put("TRY_TND", 0.034);
        exchangeRates.put("TND_UAH", 90.28);
        exchangeRates.put("UAH_TND", 0.011);
        exchangeRates.put("TND_GBP", 0.59);
        exchangeRates.put("GBP_TND", 1.70);
// Couronne suédoise (SEK)
        exchangeRates.put("SEK_DZD", 12.94);
        exchangeRates.put("DZD_SEK", 0.077);
        exchangeRates.put("SEK_TND", 3.54);
        exchangeRates.put("TND_SEK", 0.283);
        exchangeRates.put("SEK_USD", 0.107);
        exchangeRates.put("USD_SEK", 9.30);
        exchangeRates.put("SEK_CHF", 1.00);
        exchangeRates.put("CHF_SEK", 1.00);
        exchangeRates.put("SEK_TRY", 8.56);
        exchangeRates.put("TRY_SEK", 0.117);
        exchangeRates.put("SEK_UAH", 2.97);
        exchangeRates.put("UAH_SEK", 0.337);
        exchangeRates.put("SEK_GBP", 0.081);
        exchangeRates.put("GBP_SEK", 12.32);
//  suisse (CHF)
        exchangeRates.put("CHF_DZD", 117.45);
        exchangeRates.put("DZD_CHF", 0.0085);
        exchangeRates.put("CHF_TND", 3.09);
        exchangeRates.put("TND_CHF", 0.324);
        exchangeRates.put("CHF_USD", 1.00);
        exchangeRates.put("USD_CHF", 1.00);
        exchangeRates.put("CHF_SEK", 1.00);
        exchangeRates.put("SEK_CHF", 1.00);
        exchangeRates.put("CHF_TRY", 8.54);
        exchangeRates.put("TRY_CHF", 0.117);
        exchangeRates.put("CHF_UAH", 28.14);
        exchangeRates.put("UAH_CHF", 0.036);
        exchangeRates.put("CHF_GBP", 0.73);
        exchangeRates.put("GBP_CHF", 1.37);
// Livre turque (TRY)
        exchangeRates.put("TRY_DZD", 13.78);
        exchangeRates.put("DZD_TRY", 0.072);
        exchangeRates.put("TRY_TND", 0.097);
        exchangeRates.put("TND_TRY", 10.34);
        exchangeRates.put("TRY_USD", 0.116);
        exchangeRates.put("USD_TRY", 8.62);
        exchangeRates.put("TRY_SEK", 0.117);
        exchangeRates.put("SEK_TRY", 8.56);
        exchangeRates.put("TRY_CHF", 0.117);
        exchangeRates.put("CHF_TRY", 8.54);
        exchangeRates.put("TRY_UAH", 3.53);
        exchangeRates.put("UAH_TRY", 0.283);
        exchangeRates.put("TRY_GBP", 0.079);
        exchangeRates.put("GBP_TRY", 12.60);
// Hryvnia ukrainienne (UAH)
        exchangeRates.put("UAH_DZD", 3.55);
        exchangeRates.put("DZD_UAH", 0.282);
        exchangeRates.put("UAH_TND", 0.011);
        exchangeRates.put("TND_UAH", 90.28);
        exchangeRates.put("UAH_USD", 0.036);
        exchangeRates.put("USD_UAH", 27.87);
        exchangeRates.put("UAH_SEK", 0.337);
        exchangeRates.put("SEK_UAH", 2.97);
        exchangeRates.put("UAH_CHF", 0.036);
        exchangeRates.put("CHF_UAH", 28.14);
        exchangeRates.put("UAH_TRY", 0.283);
        exchangeRates.put("TRY_UAH", 3.53);
        exchangeRates.put("UAH_GBP", 0.030);
        exchangeRates.put("GBP_UAH", 33.56);
// Livre sterling (GBP)
        exchangeRates.put("GBP_DZD", 1.70);
        exchangeRates.put("DZD_GBP", 0.59);
        exchangeRates.put("GBP_TND", 1.70);
        exchangeRates.put("TND_GBP", 0.59);
        exchangeRates.put("GBP_USD", 1.33);
        exchangeRates.put("USD_GBP", 0.75);
        exchangeRates.put("GBP_SEK", 12.32);
        exchangeRates.put("SEK_GBP", 0.081);
        exchangeRates.put("GBP_CHF", 1.37);
        exchangeRates.put("CHF_GBP", 0.73);
        exchangeRates.put("GBP_TRY", 12.60);
        exchangeRates.put("TRY_GBP", 0.079);
        exchangeRates.put("GBP_UAH", 33.56);
        exchangeRates.put("UAH_GBP", 0.030);

    }
}