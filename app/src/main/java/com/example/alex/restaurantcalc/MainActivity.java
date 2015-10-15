package com.example.alex.restaurantcalc;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    EarningReport mainReport;
    TextView showUser;
    File saveFile;

    public final int GET_REPORT_REQUEST = 1;
    public final int GET_FILTER_REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showUser = (TextView) findViewById(R.id.textView);
        saveFile = new File(getApplicationContext().getFilesDir(), "savefile");
        mainReport = new EarningReport(saveFile);
        showUser.setText(mainReport.all().info);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void actMainClickAdd(View v){
        startActivityForResult(new Intent(this, GetEarningReport.class), GET_REPORT_REQUEST);
    }

    public void actMainClickFilter(View v){
        startActivityForResult(new Intent(this, GetFilter.class), GET_FILTER_REQUEST);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case GET_REPORT_REQUEST:
                    String reportCode = data.getData().toString();
                    mainReport.add(new EarningDay(reportCode));
                    showUser.setText(mainReport.all().info);
                    mainReport.save();
                    break;
                case GET_FILTER_REQUEST:
                    StringTokenizer tokenizer = new StringTokenizer(data.getData().toString()," ");
                    String selection = tokenizer.nextToken();
                    int tempMonth = Integer.parseInt(tokenizer.nextToken());
                    int tempDay = Integer.parseInt(tokenizer.nextToken());
                    int tempYear = Integer.parseInt(tokenizer.nextToken());
                    switch (selection){
                        case "All":
                            showUser.setText(mainReport.all().info);
                            break;
                        case "Year":
                            showUser.setText(mainReport.year(tempYear).info);
                            break;
                        case "Month":
                            showUser.setText(mainReport.month(tempMonth,tempYear).info);
                            break;
                        case "Week":
                            showUser.setText(mainReport.week(tempDay,tempMonth,tempYear).info);
                            break;
                    }
            }
        }
    }
}
