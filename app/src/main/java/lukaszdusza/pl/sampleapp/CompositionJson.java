package lukaszdusza.pl.sampleapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CompositionJson extends JSONObject{

    public JSONObject toJsonFormat(String currentDate, Costs costs) {

        JSONObject obj = new JSONObject();
        JSONObject result = new JSONObject();
        JSONArray arr = new JSONArray();
        try {
            obj.put("currentDate", currentDate);
            obj.put("subject", costs.getSubject());
            obj.put("cost", costs.getCost());
            arr.put(obj);
            result.put("costs", arr);
        } catch (JSONException e) {
            e.printStackTrace();

        }
        return result;
    }

    public static JSONObject toJsonFormatFromArray(List<Costs> costs) {


        JSONObject result = new JSONObject();
        JSONArray arr = new JSONArray();
        try {

            for (Costs c: costs) {
                JSONObject obj = new JSONObject();
                obj.put("currentDate", c.getDate());
                obj.put("subject", c.getSubject());
                obj.put("cost", c.getCost());
                arr.put(obj);
            }
            result.put("costs", arr);

        } catch (JSONException e) {
            e.printStackTrace();

        }
        return result;
    }

    public static void readFile(File file) throws IOException {

        if(file != null) {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String textLine = bufferedReader.readLine();

            do {
                if(textLine != null) {
                    Log.d("file", textLine);
                }
                textLine = bufferedReader.readLine();
            } while(textLine != null);
            bufferedReader.close();
        }
    }

    public static String newDate() {
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String result = dateFormat.format(date);
        return result;
    }

    public static String newDate(long dateLong) {
        Date date = new Date(dateLong);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String result = dateFormat.format(date);
        return result;
    }

    public static String parseToStringRow(Costs costs) {
        String result = costs.getDate() + "=" + costs.getSubject() + "=" + costs.getCost();
        return  result;
    }

    public static List<Costs> toCostList(File file) throws IOException {
        List<Costs> result = new ArrayList<>();
        if(file != null) {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String textLine = bufferedReader.readLine();

            do {
                if(textLine != null) {
                    Log.d("file", textLine);
                    String[] content = textLine.split("=");
                    result.add(new Costs(content[0], content[1], Integer.parseInt(content[2])));
                }
                textLine = bufferedReader.readLine();
            } while(textLine != null);
            bufferedReader.close();
        }

        return result;
    }

    public static String presentAllSubjectsFromListByCurrentDate(List<Costs> costs, String date) {

        StringBuilder result = new StringBuilder();
        int count = 0;
        String currentDate = newDate();

        for (Costs c: costs ) {
            if(date.equals(c.getDate())) {
                count++;
                result.append(count).append(". ").append(c.getSubject()).append(". ").append(c.getCost()).append(" zł \n");
            }
        }
        return result.toString();
    }


    public static String getCurrentDayCostSum(List<Costs> costs, String date) {

        String result;
        int count = 0;
        String currentDate = newDate();

        for (Costs c: costs ) {
            if(date.equals(c.getDate())) {
                count = count + c.getCost();
            }
        }
        result = String.valueOf(count);
        return result;
    }

    public static String getAllDayCostSum(List<Costs> costs) {

        String result;
        int count = 0;

        for (Costs c: costs ) {
            count = count + c.getCost();
        }
        result = String.valueOf(count);
        return result;
    }


    public static String getMonthCostSum(List<Costs> costs) {

        String result;
        int count = 0;
        String currentDate = newDate();

        for (Costs c: costs ) {
            if(currentDate.substring(0,7).equals(c.getDate().substring(0,7))) {
                count = count + c.getCost();
                Log.d("currentDate", currentDate.substring(0,7));
            }
        }
        Log.d("currentDate", String.valueOf(count));
        result = String.valueOf(count);
        return result;


    }
}
