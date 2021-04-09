package com.example.coffeeorderingapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    int quantity = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText nameField = findViewById(R.id.name_field);
        EditText addressField = findViewById(R.id.address_field);
        String name = nameField.getText().toString();
        String address = addressField.getText().toString();
        CheckBox whippedCreamCheckBox = findViewById(R.id.whipped_cream_checkbox);
        CheckBox chocolateCheckBox = findViewById(R.id.chocolate_checkbox);
        boolean isWhippedCreamChecked = whippedCreamCheckBox.isChecked();
        boolean isChocoateChecked = chocolateCheckBox.isChecked();



        int price = calculatePrice(isWhippedCreamChecked, isChocoateChecked);

        String orderSummary = createOrderSummary(price, name, address, isChocoateChecked, isWhippedCreamChecked);
        displayOrderSummary(orderSummary);

        //Using Implicit Intent to activate mail app
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee order for: " + name);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if(intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }


    }

    private void displayOrderSummary(String orderSummary) {
        TextView orderSummaryTextView = findViewById(R.id.order_summary_text);
        orderSummaryTextView.setText(orderSummary);
    }

    private String createOrderSummary(int price, String name, String address, boolean isChocoateChecked, boolean isWhippedCreamChecked) {

          String orderSummary = "Name: " + name
                               +"\nAddress: " + address
                               +"\nCream Checked: " + isWhippedCreamChecked
                               +"\nChocolate Checked: " + isChocoateChecked
                               +"\nTotal Price: " + price
                               +"\nThankyou! ";
                return orderSummary;

    }

    public int calculatePrice(boolean isWhippedCreamChecked, boolean isChocolateChecked) {
        int initialPrice = 30;
        if(isWhippedCreamChecked) {
            initialPrice = initialPrice + 5;
        }
        if(isChocolateChecked) {
            initialPrice = initialPrice + 3;
        }
        initialPrice = initialPrice * quantity;
        return initialPrice;
    }
    public void increment(View view) {
        if(quantity == 50) {
            Toast.makeText(getApplicationContext(), "You cannot order more than 50 cup of coffee!", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view)
    {
        if(quantity == 1) {
            Toast.makeText(this, "You cannot order less than 1 cup of coffee!", Toast.LENGTH_LONG).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);

    }

    public void displayQuantity(int quantity) {
        TextView displayQuantity = findViewById(R.id.quantity_text_view);
        displayQuantity.setText(String.valueOf(quantity));
        //displayQuantity.setText("" + quantity);
    }
}