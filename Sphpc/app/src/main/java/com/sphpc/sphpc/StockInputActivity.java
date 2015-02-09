package com.sphpc.sphpc;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Cedric on 09/02/15.
 */
public class StockInputActivity extends Activity {

    public final static String STOCK_SYMBOL = "com.sphpc.sphpc.stockSymbol";

    EditText stockEditText;
    Button stockButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.simulizaion_layout);

        stockButton = (Button) findViewById(R.id.stockSearchButton);
        stockEditText = (EditText) findViewById(R.id.stockEditText);

        stockButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockInputActivity.this, Simulation.class);
                String companyName = stockEditText.getText().toString();
                intent.putExtra(STOCK_SYMBOL, companyName);
                startActivity(intent);
            }
        });
    }
}
