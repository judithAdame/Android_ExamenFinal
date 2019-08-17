package com.example.android_examenfinal_servwebgooglemaps_dropdonwlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main2Activity extends AppCompatActivity {
    String urlServWeb;
    TextView outTextViewDestination,outTextViewOrigin, outTextViewElements;
    JSONObject obj, objDistance, objDuration, objDistanceText, objDurationText, obj2, obj3;
    JSONArray jsonarrayDestination, jsonarrayOrigine, jsonarrayRows, jsonarrayRowsElements;
    List<String> outDistanceText = new ArrayList<String>();
    List<String> outDurationText = new ArrayList<String>();
    String strDistance ="";
    String strDuration ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        double outLatOrigine, outLongOrigine, outLatDestination, outLongDestination;
        String outTypeTransport;
        String servWebIni = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=";
        String servWebDestination = "&destinations=";
        String servWebType = "&mode=";
        String servWebFin = "&language=fr-FR&key=AIzaSyARiki0HBLlyR7xH0K3e4eifaSLzx8b-7E";

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent monIntent = getIntent();
        outLatOrigine = ((Intent) monIntent).getDoubleExtra("latOrigine",99);
        outLongOrigine = ((Intent) monIntent).getDoubleExtra("longOrigine",99);
        outLatDestination = ((Intent) monIntent).getDoubleExtra("latDestination",99);
        outLongDestination = ((Intent) monIntent).getDoubleExtra("longDestination",99);
        outTypeTransport = ((Intent) monIntent).getStringExtra("typeTransport");
        //System.out.println(" outLatOrigine: "+ outLatOrigine+ " outLongOrigine: "+ outLongOrigine+" outLatDestination: " + outLatDestination + " outLongDestination: "+ outLongDestination + " outTypeTransport: "+ outTypeTransport);

        urlServWeb = servWebIni+outLatOrigine+","+outLongOrigine+servWebDestination+outLatDestination+","+outLongDestination+servWebType+outTypeTransport+servWebFin;
        //System.out.println(url);


        outTextViewDestination = (TextView) findViewById(R.id.textViewLabelOrigine);
        outTextViewOrigin = (TextView) findViewById(R.id.textViewLabelDestination);
        outTextViewElements = (TextView) findViewById(R.id.textViewElements);

        new MyTask().execute();
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        String oDestination_addresses, oOrigin_addresses;

        @Override
        protected Void doInBackground(Void... params) {
            java.net.URL url = null;
            try{
                url = new URL(urlServWeb);
                HttpURLConnection client;
                client = (HttpURLConnection) url.openConnection();
                client.setRequestMethod("GET");
                int responseCode = client.getResponseCode();

                //System.out.println("\n Sending 'GET' request to URL: " + url);
                System.out.println("\n Response code: " + responseCode);
                InputStreamReader myInput = new InputStreamReader (client.getInputStream());
                BufferedReader in = new BufferedReader(myInput);
                String inputLine;
                StringBuffer response = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                //System.out.println("response.toString(): "+response.toString());

                obj = new JSONObject(response.toString());
                System.out.println("obj: "+obj.toString());

                jsonarrayDestination = new JSONArray(obj.getString("destination_addresses"));
                jsonarrayOrigine = new JSONArray(obj.getString("origin_addresses"));
                jsonarrayRows = new JSONArray(obj.getString("rows"));

                oDestination_addresses = jsonarrayDestination.toString();
                oOrigin_addresses = jsonarrayOrigine.toString();
                //System.out.println("oDestination_addresses: "+oDestination_addresses);
                //System.out.println("oOrigin_addresses: "+oOrigin_addresses);
                //System.out.println("jsonarrayRows: "+jsonarrayRows.toString());
                for (int i = 0; i < jsonarrayRows.length(); i++) {
                    obj2 = jsonarrayRows.getJSONObject(i);
                    //System.out.println("obj2: "+obj2.toString());
                    jsonarrayRowsElements = obj2.getJSONArray("elements");
                    System.out.println("jsonarrayRowsElements: "+jsonarrayRowsElements.toString());
                    for (int j = 0; j < jsonarrayRowsElements.length(); j++) {
                        obj3 = jsonarrayRowsElements.getJSONObject(i);
                        objDistance = obj3.getJSONObject("distance");
                        //System.out.println("objDistance: "+ objDistance);
                        objDuration = obj3.getJSONObject("duration");
                        //System.out.println("objDuration: "+ objDuration);
                        outDistanceText.add(objDistance.getString("text"));
                        outDurationText.add(objDuration.getString("text"));
                        strDistance += objDistance.getString("text")+", ";
                        strDuration += objDuration.getString("text")+", ";
                    }

                    //System.out.println("dist.toString() : "+ outDistanceText.toString());
                    //System.out.println("dura.toString() "+ outDurationText.toString());

                }

                System.out.println("strDistance : "+ strDistance);
                System.out.println("strDuration: "+ strDuration);

            }catch (MalformedURLException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }catch(JSONException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            outTextViewDestination.setText(oDestination_addresses);
            outTextViewOrigin.setText(oOrigin_addresses);
            String str = "Distances: " + strDistance +"Duration : "+strDuration;
            outTextViewElements.setText(str);
            super.onPostExecute(result);
            //setTable();
        }
    }
}
