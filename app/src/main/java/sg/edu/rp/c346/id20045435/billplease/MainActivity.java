package sg.edu.rp.c346.id20045435.billplease;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {

    // Declare the field variables
    EditText amount;
    EditText numPax;
    EditText discount;
    ToggleButton svs;
    ToggleButton gst;
    TextView bill;
    TextView eachPays;
    Button split;
    Button reset;
    RadioGroup rgPayment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link the field variables to UI components in layout
        amount =findViewById(R.id.editTextNumberDecimalAmt);
        numPax = findViewById(R.id.editTextNumberPax);
        discount = findViewById(R.id.editTextNumbeDiscount);
        svs = findViewById(R.id.toggleButtonSVS);
        gst = findViewById(R.id.toggleButtonGST);
        bill = findViewById(R.id.textViewTotalBill);
        eachPays = findViewById(R.id.textViewEachPay);
        split = findViewById(R.id.buttonSplit);
        reset = findViewById(R.id.buttonReset);
        rgPayment = findViewById(R.id.radioGroup);

        split.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amtInput = amount.getText().toString();
                String paxInput = numPax.getText().toString();
                String disInput = discount.getText().toString();

                // check for the amount input and number of pax input
                if (amtInput.trim().length() != 0 && paxInput.trim().length() != 0) {
                    double newAmount = 0.0;

                    // check if got gst/svs or no gst/svs
                    if (!gst.isChecked() && !svs.isChecked()){
                        newAmount = Double.parseDouble(amtInput);
                    }
                    else if (!svs.isChecked() && gst.isChecked()) {
                        newAmount = Double.parseDouble(amtInput) * 1.07;
                    }
                    else if (!gst.isChecked() && svs.isChecked()){
                        newAmount = Double.parseDouble(amtInput) * 1.10;
                    }
                    else {
                        newAmount = Double.parseDouble(amtInput) * 1.17;
                    }

                    // calculate discount
                    if (disInput.trim().length() != 0) {
                        newAmount = 1 - Double.parseDouble(disInput) / 100;
                    }

                    // Display total bill
                    String billOutput = String.format("%.2f", newAmount);
                    bill.setText("Total Bill: $" + billOutput);

                    int checkRadioId = rgPayment.getCheckedRadioButtonId();
                    int numPax = Integer.parseInt(paxInput);
                    String output = String.format("%.2f", newAmount / numPax);

                    // Display each pays
                    if (numPax != 1 && checkRadioId == R.id.radioButtonCash) {   // if "cash" is selected
                        eachPays.setText("Each Pays: $" + output + " in cash");
                    }
                    else if (numPax != 1 && checkRadioId == R.id.radioButtonPN) {   // if "paynow" is selected
                        eachPays.setText("Each Pays: $" + output + " via PayNow to 90495687");
                    }
                }
            }
        });

        gst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gst.isChecked()){
                    amount.setEnabled(true);
                    numPax.setEnabled(true);
                }
                else {
                    amount.setEnabled(false);
                    numPax.setEnabled(false);
                }
                amount.setEnabled(split.isEnabled());
                numPax.setEnabled(split.isEnabled());
            }
        });

        // reset to default
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount.setText("");
                numPax.setText("");
                svs.setChecked(false);
                gst.setChecked(false);
                discount.setText("");
                rgPayment.setSelected(false);
            }
        });
    }
}