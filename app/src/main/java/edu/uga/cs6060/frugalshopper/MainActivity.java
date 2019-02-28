package edu.uga.cs6060.frugalshopper;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity {

    private static final double CONVERSION_RATE = 16; // 1lb = 16
    private static final String LOG_TAG = "MainActivity";

    private EditText etPrice1;
    private EditText etPrice2;
    private EditText etPrice3;

    private EditText etLbs1;
    private EditText etLbs2;
    private EditText etLbs3;

    private EditText etOz1;
    private EditText etOz2;
    private EditText etOz3;

    private TextView tvResult;

    private TextView tvP1;
    private TextView tvP2;
    private TextView tvP3;


    // To store respective values
    private double p1;
    private double p2;
    private double p3;
    private double lb1;
    private double lb2;
    private double lb3;
    private double oz1;
    private double oz2;
    private double oz3;

    // To store calculated values
    private double pricePerOz1;
    private double pricePerOz2;
    private double pricePerOz3;
    private boolean validP1;
    private boolean validP2;
    private boolean validP3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
    }

    /**
     * This method initialized the widgets necessary for the application
     */

    public void initializeViews() {
        etPrice1 = findViewById(R.id.etPrice1);
        etPrice2 = findViewById(R.id.etPrice2);
        etPrice3 = findViewById(R.id.etPrice3);

        etLbs1 = findViewById(R.id.etLbs1);
        etLbs2 = findViewById(R.id.etLbs2);
        etLbs3 = findViewById(R.id.etLbs3);


        etOz1 = findViewById(R.id.etOz1);
        etOz2 = findViewById(R.id.etOz2);
        etOz3 = findViewById(R.id.etOz3);

        tvResult = findViewById(R.id.tvResult);

        tvP1 = findViewById(R.id.tvP1);
        tvP2 = findViewById(R.id.tvP2);
        tvP3 = findViewById(R.id.tvP3);
    }


    public void checkIfValidInput() {
        if (etPrice1.getText().toString().isEmpty() ||
                (etLbs1.getText().toString().isEmpty() && etOz1.getText().toString().isEmpty())) {
            validP1 = false;
        } else {
            validP1 = true;
        }

        if (etPrice2.getText().toString().isEmpty() ||
                (etLbs2.getText().toString().isEmpty() && etOz2.getText().toString().isEmpty())) {
            validP2 = false;
        } else {
            validP2 = true;
        }

        if (etPrice3.getText().toString().isEmpty() ||
                (etLbs3.getText().toString().isEmpty() && etOz3.getText().toString().isEmpty())) {
            validP3 = false;
        } else {
            validP3 = true;
        }
    }


    private void getValuesFromTextFields() {
//        p1 = p2 = p3 = lb1 = lb2 = lb3 = oz1 = oz2 = oz3 = 0.0;

        // Catch exceptions if invalid input is given

        try {
            if (!etPrice1.getText().toString().isEmpty()) {
                p1 = Double.parseDouble(etPrice1.getText().toString());
            }
            if (!etPrice2.getText().toString().isEmpty()) {
                p2 = Double.parseDouble(etPrice2.getText().toString());
            }
            if (!etPrice3.getText().toString().isEmpty()) {
                p3 = Double.parseDouble(etPrice3.getText().toString());
            }
            if (!etLbs1.getText().toString().isEmpty()) {
                lb1 = Double.parseDouble(etLbs1.getText().toString());
            }
            if (!etLbs2.getText().toString().isEmpty()) {
                lb2 = Double.parseDouble(etLbs2.getText().toString());
            }
            if (!etLbs3.getText().toString().isEmpty()) {
                lb3 = Double.parseDouble(etLbs3.getText().toString());
            }
            if (!etOz1.getText().toString().isEmpty()) {
                oz1 = Double.parseDouble(etOz1.getText().toString());
            }
            if (!etOz2.getText().toString().isEmpty()) {
                oz2 = Double.parseDouble(etOz2.getText().toString());
            }
            if (!etOz3.getText().toString().isEmpty()) {
                oz3 = Double.parseDouble(etOz3.getText().toString());
            }

        } catch (Exception e) {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Enter only positive decimal values",
                    Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

    }


    public void calcPricePerOz() {
        // Check ig inputs are valid
        checkIfValidInput();

        getValuesFromTextFields();

        //TODO Handle infinity exception here when no weight is entered
        if (validP1) {
            pricePerOz1 = p1 / (lb1 * CONVERSION_RATE + oz1);
        }

        if (validP2) {
            pricePerOz2 = p2 / (lb2 * CONVERSION_RATE + oz2);
        }

        if (validP3) {
            pricePerOz3 = p3 / (lb3 * CONVERSION_RATE + oz3);
        }
    }

    /**
     * This callback method is activated when the user clicks on the
     * FindCheapest button. The listener is automatically managed behind
     * the scenes because the onClick parameter is set in the respective
     * layout file.
     *
     * @param view
     */

    public void findCheapest(View view) {
        calcPricePerOz();

        NumberFormat formatter = new DecimalFormat("#0.00");
//        formatter = NumberFormat.getCurrencyInstance(Locale.US);

        Log.i(LOG_TAG, "Price per oz for Product 1: " + pricePerOz1);
        Log.i(LOG_TAG, "Price per oz for Product 2: " + pricePerOz2);
        Log.i(LOG_TAG, "Price per oz for Product 3: " + pricePerOz3);

        if (validP1) {
            tvP1.setText("$" + formatter.format(pricePerOz1));
        } else {
            tvP1.setText("-");
        }

        if (validP2) {
            tvP2.setText("$" + formatter.format(pricePerOz2));
        } else {
            tvP2.setText("-");
        }

        if (validP3) {
            tvP3.setText("$" + formatter.format(pricePerOz3));
        } else {
            tvP3.setText("-");
        }


        if (validP1 && validP2 && validP3) {
            if (pricePerOz1 < pricePerOz2 && pricePerOz1 < pricePerOz3) {
                tvResult.setText("The cheapest is Product 1 with $/oz: " + formatter.format(pricePerOz1));
            } else if (pricePerOz2 < pricePerOz1 && pricePerOz2 < pricePerOz3) {
                tvResult.setText("The cheapest is Product 2 with $/oz: " + formatter.format(pricePerOz2));
            } else if (pricePerOz3 < pricePerOz1 && pricePerOz3 < pricePerOz2) {
                tvResult.setText("The cheapest is Product 3 with $/oz: " + formatter.format(pricePerOz3));
            } else {
                tvResult.setText("All products have the same value.");
            }
        } else if (validP1 && validP2 && !validP3) {
            if (pricePerOz1 < pricePerOz2)
                tvResult.setText("The cheapest is Product 1 with $/oz: " + formatter.format(pricePerOz1));
            else if (pricePerOz2 < pricePerOz1)
                tvResult.setText("The cheapest is Product 2 with $/oz: " + formatter.format(pricePerOz2));
        } else if (validP1 && validP3 && !validP2) {
            if (pricePerOz1 < pricePerOz3)
                tvResult.setText("The cheapest is Product 1 with $/oz: " + formatter.format(pricePerOz1));
            else if (pricePerOz3 < pricePerOz1)
                tvResult.setText("The cheapest is Product 2 with $/oz: " + formatter.format(pricePerOz3));
        } else if (validP2 && validP3 && !validP1) {
            if (pricePerOz3 < pricePerOz2)
                tvResult.setText("The cheapest is Product 1 with $/oz: " + formatter.format(pricePerOz3));
            else if (pricePerOz2 < pricePerOz3)
                tvResult.setText("The cheapest is Product 2 with $/oz: " + formatter.format(pricePerOz2));
        } else if (validP1) {
            tvResult.setText("The cheapest is Product 1 with $/oz: " + formatter.format(pricePerOz1));
            Toast.makeText(this, "Only showing 1 product.", Toast.LENGTH_SHORT).show();
        } else if (validP2) {
            tvResult.setText("The cheapest is Product 2 with $/oz: " + formatter.format(pricePerOz2));
            Toast.makeText(this, "Only showing 1 product.", Toast.LENGTH_SHORT).show();
        } else if (validP3) {
            tvResult.setText("The cheapest is Product 3 with $/oz: " + formatter.format(pricePerOz3));
            Toast.makeText(this, "Only showing 1 product.", Toast.LENGTH_SHORT).show();
        } else {
            tvResult.setText("PLEASE ENTER A VALID VALUE!");
            Toast.makeText(getApplicationContext(), "Not enough values to comapare!", Toast.LENGTH_SHORT).show();
        }
    }


    private void displayEmpty() {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Empty values detected. Please fill the text fields.",
                Toast.LENGTH_SHORT);
        toast.show();
        return;
    }

}

