package com.example.alex.restaurantcalc;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class GetEarningReport extends AppCompatActivity {

    EditText getMoney, getHours, showDate;
    Spinner getRole;
    String getDate = "";

    static final int GET_DATE_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_earning_report);
        getMoney = (EditText) findViewById(R.id.editText);
        getHours = (EditText) findViewById(R.id.editText2);
        getRole = (Spinner) findViewById(R.id.spinner);
        showDate = (EditText) findViewById(R.id.editText3);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_get_earning_report, menu);
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

    public void actGetEarningClickDate(View v){
        startActivityForResult(new Intent(this, GetDate.class), GET_DATE_REQUEST);
    }

    public void actGetEarningClickOK(View v){
        Intent data = new Intent();
        if (getDate.isEmpty() || getMoney.getText().toString().isEmpty() || getHours.getText().toString().isEmpty()){
            Toast.makeText(this, "Make sure you input your money, hours, and date.", Toast.LENGTH_SHORT).show();
        }else{
            data.setData(Uri.parse(getMoney.getText() + " " + getHours.getText() + " " + getDate + " " + getRole.getSelectedItem().toString()));
            setResult(RESULT_OK, data);
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            if(requestCode == GET_DATE_REQUEST){
                getDate = data.getData().toString();
                showDate.setText(getDate);
            }
        }
    }
}
