package com.example.alex.restaurantcalc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.StringTokenizer;

/**
 * Created by Alex on 9/23/2015.
 */
public class EarningReport {
    public File saveFile;
    public class Stat{
        public String info;
        public int days;
        public double bartenderMoney;
        public double bartenderHours;
        public double serverMoney;
        public double serverHours;
        public Stat(){
            info = "";
            days = 0;
            bartenderMoney = 0;
            bartenderHours = 0;
            serverMoney = 0;
            serverHours = 0;
        }
    }
    private ArrayList<EarningDay> report = new ArrayList<EarningDay>();
    public EarningReport(File initSaveFile){
        saveFile = initSaveFile;
        String input;
        try{
            BufferedReader in = new BufferedReader(new FileReader(saveFile));
            while(in.ready()){
                input = in.readLine();
                StringTokenizer tokenizer = new StringTokenizer(input," ");
                String initmoney = tokenizer.nextToken();
                String inithours = tokenizer.nextToken();
                String initmonth = tokenizer.nextToken();
                String initday = tokenizer.nextToken();
                String inityear = tokenizer.nextToken();
                String initrole = tokenizer.nextToken();
                EarningDay temp = new EarningDay(Integer.parseInt(initmonth), Integer.parseInt(initday), Integer.parseInt(inityear), Double.parseDouble(initmoney), Double.parseDouble(inithours), initrole);
                report.add(temp);
            }
            in.close();
        }catch(FileNotFoundException e){
            System.out.println("File couldn't be found");
        }catch(IOException e){
            System.out.println("IOException");
        }
    }

    public void add(EarningDay temp){
        boolean found = false;
        for(int findrep = 0; findrep < report.size(); findrep++){
            int tempmonth = report.get(findrep).getMonth();
            int tempday = report.get(findrep).getDay();
            int tempyear = report.get(findrep).getYear();
            if(tempmonth == temp.getMonth() && tempday == temp.getDay() && tempyear == temp.getYear()){
                found = true;
            }
        }
        if(!found){
            report.add(temp);
            for(int c1 = 1; c1 < report.size(); c1++){
                for(int c2 = c1; c2 > 0; c2--){
                    if(report.get(c2).getYear() < report.get(c2-1).getYear() || report.get(c2).getSecdate() < report.get(c2-1).getSecdate() && report.get(c2).getYear() == report.get(c2-1).getYear()){
                        Collections.swap(report, c2, c2 - 1);
                    }
                }
            }
        }
    }

    public Stat all(){
        Stat temp = new Stat();
        for (int counter = 0; counter < report.size(); counter++) {
            temp.info += report.get(counter).getDayofweek();
            temp.info += " ";
            temp.info += String.format("%-2d/%-2d/%-4d", report.get(counter).getMonth(), report.get(counter).getDay(), report.get(counter).getYear());
            temp.info += " ";
            temp.info += String.format("$%-3.0f %5.2f", report.get(counter).getMoney(), report.get(counter).getHours());
            temp.info += " " + report.get(counter).getRole();
            temp.info += "\n";
            temp.days++;
            switch (report.get(counter).getRole()) {
                case "Bartender":
                    temp.bartenderMoney += report.get(counter).getMoney();
                    temp.bartenderHours += report.get(counter).getHours();
                    break;
                case "Server":
                    temp.serverMoney += report.get(counter).getMoney();
                    temp.serverHours += report.get(counter).getHours();
                    break;
            }
        }
        if(temp.serverHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Server Total Earnings: $%.2f", temp.serverMoney) + "\n";
            temp.info += String.format("Server Total Hours: %.2f", temp.serverHours) + "\n";
            temp.info += String.format("Server Average Hourly: $%.2f", temp.serverMoney / temp.serverHours) + "\n";
        }
        if(temp.bartenderHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Bartender Total Earnings: $%.2f", temp.bartenderMoney) + "\n";
            temp.info += String.format("Bartender Total Hours: %.2f", temp.bartenderHours) + "\n";
            temp.info += String.format("Server Average Hourly: $%.2f", temp.bartenderMoney / temp.bartenderHours) + "\n";
        }
        if(temp.bartenderHours > 0 && temp.serverHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Total Earnings: $%.2f", temp.serverMoney + temp.bartenderMoney) + "\n";
            temp.info += String.format("Total Hours: %.2f", temp.serverHours + temp.bartenderHours) + "\n";
            temp.info += String.format("Total Average Hourly: $%.2f", (temp.serverMoney + temp.bartenderMoney) / (temp.serverHours + temp.bartenderHours)) + "\n";
        }
        if(temp.bartenderHours == 0 && temp.serverHours == 0){
            temp.info += "No records found.";
        }
        return temp;
    }

    public Stat year(int tempyear){
        Stat temp = new Stat();
        for (int counter = 0; counter < report.size(); counter++) {
            if (report.get(counter).getYear() == tempyear) {
                temp.info += report.get(counter).getDayofweek();
                temp.info += " ";
                temp.info += String.format("%-2d/%-2d/%-4d", report.get(counter).getMonth(), report.get(counter).getDay(), report.get(counter).getYear());
                temp.info += " ";
                temp.info += String.format("$%-3.0f %5.2f", report.get(counter).getMoney(), report.get(counter).getHours());
                temp.info += " " + report.get(counter).getRole();
                temp.info += "\n";
                temp.days++;
                switch (report.get(counter).getRole()) {
                    case "Bartender":
                        temp.bartenderMoney += report.get(counter).getMoney();
                        temp.bartenderHours += report.get(counter).getHours();
                        break;
                    case "Server":
                        temp.serverMoney += report.get(counter).getMoney();
                        temp.serverHours += report.get(counter).getHours();
                        break;
                }
            }
        }
        if(temp.serverHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Server Total Earnings: $%.2f", temp.serverMoney) + "\n";
            temp.info += String.format("Server Total Hours: %.2f", temp.serverHours) + "\n";
            temp.info += String.format("Server Average Hourly: $%.2f", temp.serverMoney / temp.serverHours) + "\n";
        }
        if(temp.bartenderHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Bartender Total Earnings: $%.2f", temp.bartenderMoney) + "\n";
            temp.info += String.format("Bartender Total Hours: %.2f", temp.bartenderHours) + "\n";
            temp.info += String.format("Server Average Hourly: $%.2f", temp.bartenderMoney / temp.bartenderHours) + "\n";
        }
        if(temp.bartenderHours > 0 && temp.serverHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Total Earnings: $%.2f", temp.serverMoney + temp.bartenderMoney) + "\n";
            temp.info += String.format("Total Hours: %.2f", temp.serverHours + temp.bartenderHours) + "\n";
            temp.info += String.format("Total Average Hourly: $%.2f", (temp.serverMoney + temp.bartenderMoney) / (temp.serverHours + temp.bartenderHours)) + "\n";
        }
        if(temp.bartenderHours == 0 && temp.serverHours == 0){
            temp.info += "No records found.";
        }
        return temp;
    }

    public Stat month (int tempmonth, int tempyear){
        Stat temp = new Stat();
        for (int counter = 0; counter < report.size(); counter++) {
            if (report.get(counter).getYear() == tempyear && report.get(counter).getMonth() == tempmonth) {
                temp.info += report.get(counter).getDayofweek();
                temp.info += " ";
                temp.info += String.format("%-2d/%-2d/%-4d", report.get(counter).getMonth(), report.get(counter).getDay(), report.get(counter).getYear());
                temp.info += " ";
                temp.info += String.format("$%-3.0f %5.2f", report.get(counter).getMoney(), report.get(counter).getHours());
                temp.info += " " + report.get(counter).getRole();
                temp.info += "\n";
                temp.days++;
                switch (report.get(counter).getRole()) {
                    case "Bartender":
                        temp.bartenderMoney += report.get(counter).getMoney();
                        temp.bartenderHours += report.get(counter).getHours();
                        break;
                    case "Server":
                        temp.serverMoney += report.get(counter).getMoney();
                        temp.serverHours += report.get(counter).getHours();
                        break;
                }
            }
        }
        if(temp.serverHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Server Total Earnings: $%.2f", temp.serverMoney) + "\n";
            temp.info += String.format("Server Total Hours: %.2f", temp.serverHours) + "\n";
            temp.info += String.format("Server Average Hourly: $%.2f", temp.serverMoney / temp.serverHours) + "\n";
        }
        if(temp.bartenderHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Bartender Total Earnings: $%.2f", temp.bartenderMoney) + "\n";
            temp.info += String.format("Bartender Total Hours: %.2f", temp.bartenderHours) + "\n";
            temp.info += String.format("Server Average Hourly: $%.2f", temp.bartenderMoney / temp.bartenderHours) + "\n";
        }
        if(temp.bartenderHours > 0 && temp.serverHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Total Earnings: $%.2f", temp.serverMoney + temp.bartenderMoney) + "\n";
            temp.info += String.format("Total Hours: %.2f", temp.serverHours + temp.bartenderHours) + "\n";
            temp.info += String.format("Total Average Hourly: $%.2f", (temp.serverMoney + temp.bartenderMoney) / (temp.serverHours + temp.bartenderHours)) + "\n";
        }
        if(temp.bartenderHours == 0 && temp.serverHours == 0){
            temp.info += "No records found.";
        }
        return temp;
    }

    public Stat week(int tempday, int tempmonth, int tempyear){
        Stat temp = new Stat();
        EarningDay dummy = new EarningDay();
        int secretdate = dummy.calcsecdate(tempmonth, tempday, tempyear);
        int dayofweek = dummy.integdayofweek(tempmonth, tempday, tempyear);
        secretdate -= dayofweek;
        for (int counter = 0; counter < report.size(); counter++) {
            if (report.get(counter).getSecdate() >= secretdate && report.get(counter).getSecdate() <= secretdate + 6 && report.get(counter).getYear() == tempyear) {
                temp.info += report.get(counter).getDayofweek();
                temp.info += " ";
                temp.info += String.format("%-2d/%-2d/%-4d", report.get(counter).getMonth(), report.get(counter).getDay(), report.get(counter).getYear());
                temp.info += " ";
                temp.info += String.format("$%-3.0f %5.2f", report.get(counter).getMoney(), report.get(counter).getHours());
                temp.info += " " + report.get(counter).getRole();
                temp.info += "\n";
                temp.days++;
                switch (report.get(counter).getRole()) {
                    case "Bartender":
                        temp.bartenderMoney += report.get(counter).getMoney();
                        temp.bartenderHours += report.get(counter).getHours();
                        break;
                    case "Server":
                        temp.serverMoney += report.get(counter).getMoney();
                        temp.serverHours += report.get(counter).getHours();
                        break;
                }
            }
        }
        if(temp.serverHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Server Total Earnings: $%.2f", temp.serverMoney) + "\n";
            temp.info += String.format("Server Total Hours: %.2f", temp.serverHours) + "\n";
            temp.info += String.format("Server Average Hourly: $%.2f", temp.serverMoney / temp.serverHours) + "\n";
        }
        if(temp.bartenderHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Bartender Total Earnings: $%.2f", temp.bartenderMoney) + "\n";
            temp.info += String.format("Bartender Total Hours: %.2f", temp.bartenderHours) + "\n";
            temp.info += String.format("Server Average Hourly: $%.2f", temp.bartenderMoney / temp.bartenderHours) + "\n";
        }
        if(temp.bartenderHours > 0 && temp.serverHours > 0) {
            temp.info += "\n";
            temp.info += String.format("Total Earnings: $%.2f", temp.serverMoney + temp.bartenderMoney) + "\n";
            temp.info += String.format("Total Hours: %.2f", temp.serverHours + temp.bartenderHours) + "\n";
            temp.info += String.format("Total Average Hourly: $%.2f", (temp.serverMoney + temp.bartenderMoney) / (temp.serverHours + temp.bartenderHours)) + "\n";
        }
        if(temp.bartenderHours == 0 && temp.serverHours == 0){
            temp.info += "No records found.";
        }
        return temp;
    }

    public void save(){
        try{
            PrintWriter out = new PrintWriter(new FileWriter(saveFile));
            for(int counter = 0; counter < report.size(); counter++){
                out.print(report.get(counter).getMoney() + " " +
                        report.get(counter).getHours() + " " +
                        report.get(counter).getMonth() + " " +
                        report.get(counter).getDay() + " " +
                        report.get(counter).getYear() + " " +
                        report.get(counter).getRole() + "\n");
            }
            out.close();
        }catch(IOException e){
            System.out.println("IOException");
        }
    }
}