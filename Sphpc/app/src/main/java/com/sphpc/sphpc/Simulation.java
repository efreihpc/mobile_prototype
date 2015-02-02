package com.sphpc.sphpc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


public class Simulation extends ActionBarActivity {

    //simulation test
    TextView companyNameTextView;
    TextView yearLowTextView;
    TextView yearHighTextView;
    TextView daysLowTextView;
    TextView daysHighTextView;
    TextView lastTradePriceOnlyTextView;
    TextView changeTextView;
    TextView daysRangeTextView;

    static final String TAG = "simulation";

    //xml keys
    static final String KEY_ITEM = "quote"; // parent node
    static final String KEY_NAME = "Name";
    static final String KEY_YEAR_LOW = "YearLow";
    static final String KEY_YEAR_HIGH = "YearHigh";
    static final String KEY_DAYS_LOW = "DaysLow";
    static final String KEY_DAYS_HIGH = "DaysHigh";
    static final String KEY_LAST_TRADE_PRICE = "LastTradePriceOnly";
    static final String KEY_CHANGE = "Change";
    static final String KEY_DAYS_RANGE = "DaysRange";

    //xml data to retrieve
    String name = "";
    String yearLow = "";
    String yearHigh = "";
    String daysLow = "";
    String daysHigh = "";
    String lastTradePriceOnly = "";
    String change = "";
    String daysRange = "";

    //url
    // Used to make the URL to call for XML data
    String yahooURLFirst = "http://query.yahooapis.com/v1/public/yql?q=select%20*%20from%20yahoo.finance.quote%20where%20symbol%20in%20(%22";
    String yahooURLSecond = "%22)&env=store%3A%2F%2Fdatatables.org%2Falltableswithkeys";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        setContentView(R.layout.activity_simulation);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String stockSymbol = intent.getStringExtra(MainActivity.STOCK_SYMBOL);
        companyNameTextView = (TextView) findViewById(R.id.companyNameTextView);
        yearLowTextView = (TextView) findViewById(R.id.yearLowTextView);
        yearHighTextView = (TextView) findViewById(R.id.yearHighTextView);
        daysLowTextView = (TextView) findViewById(R.id.daysLowTextView);
        daysHighTextView = (TextView) findViewById(R.id.daysHighTextView);
        lastTradePriceOnlyTextView = (TextView) findViewById(R.id.lastPriceTextView);
        changeTextView = (TextView) findViewById(R.id.changeTextView);
        daysRangeTextView = (TextView) findViewById(R.id.dailyPriceRangeTextView);

        final String yahooUrl = yahooURLFirst+stockSymbol+yahooURLSecond;
        new MyAsyncTask().execute(yahooUrl);

    }

    private class MyAsyncTask extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... params) {
            try{
                URL url = new URL(params[0]);
                URLConnection connection;
                connection = url.openConnection();
                HttpURLConnection httpConnection = (HttpURLConnection) connection;

                int responseCode = httpConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK){
                    InputStream in = httpConnection.getInputStream();
                    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                    DocumentBuilder db = dbf.newDocumentBuilder();
                    Document dom = db.parse(in);
                    Element docElement = dom.getDocumentElement();
                    NodeList nodeList = docElement.getElementsByTagName("quote");
                    if (nodeList != null && nodeList.getLength()>0){
                        for(int i = 0;i<nodeList.getLength();i++){
                            StockInfo theStock = getStockInformation(docElement);
                                    // Gets the values stored in the StockInfo object
                            daysLow = theStock.getDaysLow();
                            daysHigh = theStock.getDaysHigh();
                            yearLow = theStock.getYearLow();
                            yearHigh = theStock.getYearHigh();
                            name = theStock.getName();
                            lastTradePriceOnly = theStock.getLastTradePriceonly();
                            change = theStock.getChange();
                            daysRange = theStock.getDaysRange();
                        }
                    }
                }else{
                    name = "no such company!";
                }
                    } catch (MalformedURLException e) {
                        Log.d(TAG, "MalformedURLException", e);
                    } catch (IOException e) {
                        Log.d(TAG, "IOException", e);
                    } catch (ParserConfigurationException e) {
                        Log.d(TAG, "Parser Configuration Exception", e);
                    } catch (SAXException e) {
                        Log.d(TAG, "SAX Exception", e);
                    }

                    return null;
                }
            // Changes the values for a bunch of TextViews on the GUI
            protected void onPostExecute(String result){

                companyNameTextView.setText(name);
                yearLowTextView.setText("Year Low: " + yearLow);
                yearHighTextView.setText("Year High: " + yearHigh);
                daysLowTextView.setText("Days Low: " + daysLow);
                daysHighTextView.setText("Days High: " + daysHigh);
                lastTradePriceOnlyTextView.setText("Last Price: " + lastTradePriceOnly);
                changeTextView.setText("Change: " + change);
                daysRangeTextView.setText("Daily Price Range: " + daysRange);

            }


        }

            // Sends the root xml tag and the tag name we are searching for to
            // getTextValue for processing. Then uses that information to create
            // a new StockInfo object
        private StockInfo getStockInformation(Element entry){

            String stockName = getTextValue(entry, "Name");
            String stockYearLow = getTextValue(entry, "YearLow");
            String stockYearHigh = getTextValue(entry, "YearHigh");
            String stockDaysLow = getTextValue(entry, "DaysLow");
            String stockDaysHigh = getTextValue(entry, "DaysHigh");
            String stocklastTradePriceOnlyTextView = getTextValue(entry, "LastTradePriceOnly");
            String stockChange = getTextValue(entry, "Change");
            String stockDaysRange = getTextValue(entry, "DaysRange");

            StockInfo theStock = new StockInfo(stockDaysLow, stockDaysHigh, stockYearLow,
                    stockYearHigh, stockName, stocklastTradePriceOnlyTextView,
                    stockChange, stockDaysRange);

            return theStock;

        }

        // Searches through the XML document for a tag that matches
        // the tagName passed in. Then it gets the value from that
        // tag and returns it

        private String getTextValue(Element entry, String tagName){

            String tagValueToReturn = null;

            NodeList nl = entry.getElementsByTagName(tagName);

            if(nl != null && nl.getLength() > 0){

                Element element = (Element) nl.item(0);
                try{
                    tagValueToReturn = element.getFirstChild().getNodeValue();
                }catch(Exception e){ e.printStackTrace();}
            }

            return tagValueToReturn;

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_simulation, menu);
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
}
