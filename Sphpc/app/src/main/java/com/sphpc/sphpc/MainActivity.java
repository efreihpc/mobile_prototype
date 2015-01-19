package com.sphpc.sphpc;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivity extends ActionBarActivity {

    public final static String extraMessage1 = "extraMessage1";
    public final static String extraMessage2 = "extraMessage2";
    public final static String extraMessage3 = "extraMessage3";
    public final static String extraMessage4 = "extraMessage4";
    public final static String extraMessage5 = "extraMessage5";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void pictureClicked(View view){

    }

    public void block1Clicked(View view){
        Intent intent = new Intent(this, Modelisation.class);
        intent.putExtra(extraMessage1, "block1");
        startActivity(intent);

    }
    public void block2Clicked(View view){
        Intent intent = new Intent(this, Simulation.class);
        intent.putExtra(extraMessage2, "block2");
        startActivity(intent);

    }
    public void block3Clicked(View view){
        Intent intent = new Intent(this, AccountManagement.class);
        intent.putExtra(extraMessage3, "block3");
        startActivity(intent);

    }
    public void block4Clicked(View view){
        Intent intent = new Intent(this, HelpAndTips.class);
        intent.putExtra(extraMessage4, "block4");
        startActivity(intent);

    }
    public void block5Clicked(View view){
        Intent intent = new Intent(this, About.class);
        intent.putExtra(extraMessage5, "block5");
        startActivity(intent);

    }
}
