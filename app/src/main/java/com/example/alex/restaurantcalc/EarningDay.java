package com.example.alex.restaurantcalc;

import java.util.StringTokenizer;

/**
 * Created by Alex on 9/23/2015.
 */
public class EarningDay {
    private int month;
    private int day;
    private int year;
    private int secdate;
    private double money;
    private double hours;
    private String dayofweek;
    private String role;

    public EarningDay(){

    }

    public EarningDay(int initmonth, int initday, int inityear, double initmoney, double inithours, String initRole){
        month = initmonth;
        day = initday;
        year = inityear;
        money = initmoney;
        hours = inithours;
        secdate = calcsecdate(initmonth, initday, inityear);
        dayofweek = DayOfWeek();
        role = initRole;
    }

    public EarningDay(String initString){
        StringTokenizer tokenizer = new StringTokenizer(initString," ");
        money =  Double.parseDouble(tokenizer.nextToken());
        hours =  Double.parseDouble(tokenizer.nextToken());
        month = Integer.parseInt(tokenizer.nextToken());
        day = Integer.parseInt(tokenizer.nextToken());
        year = Integer.parseInt(tokenizer.nextToken());
        role = tokenizer.nextToken();
        secdate = calcsecdate(month, day, year);
        dayofweek = DayOfWeek();
    }

    public int calcsecdate(int inputmonth, int inputday, int inputyear){
        int temp = inputday;
        switch(inputmonth){
            case 1:
                break;
            case 2:
                temp += 31;
                break;
            case 3:
                temp += 59;
                break;
            case 4:
                temp += 90;
                break;
            case 5:
                temp += 120;
                break;
            case 6:
                temp += 151;
                break;
            case 7:
                temp += 181;
                break;
            case 8:
                temp += 212;
                break;
            case 9:
                temp += 243;
                break;
            case 10:
                temp += 273;
                break;
            case 11:
                temp += 304;
                break;
            case 12:
                temp += 334;
                break;
            default:
        }
        if((inputyear % 4 == 0)&&(inputmonth > 2)){
            temp++;
        }
        return temp;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getYear() {
        return year;
    }

    public int getSecdate() {
        return secdate;
    }

    public double getMoney() {
        return money;
    }

    public double getHours() {
        return hours;
    }

    public String getDayofweek() {
        return dayofweek;
    }

    public String DayOfWeek()
    {
        int dayofweek;
        String dayString;
        int tempyear = year, tempmonth = month, tempday = day;
        int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        if(tempmonth < 3){
            tempyear -= 1;
        }
        dayofweek = (tempyear + tempyear/4 - tempyear/100 + tempyear/400 + t[tempmonth-1] + tempday) % 7;
        switch(dayofweek)
        {
            case 0: dayString = "Sun";
                break;
            case 1: dayString = "Mon";
                break;
            case 2: dayString = "Tue";
                break;
            case 3: dayString = "Wed";
                break;
            case 4: dayString = "Thu";
                break;
            case 5: dayString = "Fri";
                break;
            case 6: dayString = "Sat";
                break;
            default: dayString = "Uhh";
        }

        return dayString;
    }

    public int integdayofweek(int tempmonth, int tempday, int tempyear){
        int dayofweek;
        int t[] = {0, 3, 2, 5, 0, 3, 5, 1, 4, 6, 2, 4};
        if(tempmonth < 3){
            tempyear -= 1;
        }
        dayofweek = (tempyear + tempyear/4 - tempyear/100 + tempyear/400 + t[tempmonth-1] + tempday) % 7;
        return dayofweek;
    }

    public String getRole(){
        return role;
    }
}
