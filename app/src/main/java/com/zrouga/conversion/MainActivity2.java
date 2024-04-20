package com.zrouga.conversion;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {
    private Button conver;
    private TextView txtres, txtdest, convres;
    private EditText temp;
    private RadioButton r1, r2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initview();
    }

    private void initview() {
        txtres = findViewById(R.id.txts);
        txtdest = findViewById(R.id.txtdest);
        temp = findViewById(R.id.temp);
        convres = findViewById(R.id.convres);
        conver = findViewById(R.id.convertBtn);
        r1 = findViewById(R.id.from);
        r2 = findViewById(R.id.to);
        registerForContextMenu(findViewById(R.id.from));
        registerForContextMenu(findViewById(R.id.to));
        conver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String str = temp.getText().toString();
               Double res =0.0;
                String unit = "";
                if (str.isEmpty()) {
                    showAlert();
                } else {
                    double tempToConvert = Double.parseDouble(str);
                    if (r1.isChecked())
                    {


                         res =  (tempToConvert * 9/5) + 32;
                        unit="TC";
                    }   else  {

                         res = (tempToConvert - 32) * 5/9;
                        unit="TF";
                    }

                    String formattedResult = String.format("%.2f %s", Math.abs(res), unit);
                    convres.setText(formattedResult);
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0, 1, 0, "Currency Conversion  ");
        menu.add(0, 2, 0, "Quitter");
        return super.onCreateOptionsMenu(menu);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case 1:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case 2:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}