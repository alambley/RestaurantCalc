package com.example.alex.restaurantcalc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Spinner;


public class GetFilter extends AppCompatActivity {

    DatePicker getDate;
    Spinner getTimeFrame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_filter);
        getDate = (DatePicker) findViewById(R.id.datePicker2);
        getTimeFrame = (Spinner) findViewById(R.id.spinner2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_filter, menu);
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

    public void actFilterClickFilter(View v){
        Intent data = new Intent();
        data.setData(Uri.parse(getTimeFrame.getSelectedItem().toString() + " " + (getDate.getMonth()+1) + " " + getDate.getDayOfMonth() + " " + getDate.getYear()));
        setResult(RESULT_OK, data);
        finish();
    }
}
