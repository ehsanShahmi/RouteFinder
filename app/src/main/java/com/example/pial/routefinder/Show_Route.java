package com.example.pial.routefinder;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Show_Route extends AppCompatActivity {

    String finalPath="";
    String pial,ehsan;
    final static String[] location = new String[]{
            "Azimpur",
            "Kalabagan",
            "RaselSquare",
            "Panthapath",
            "Bashundhara",
            "Karwanbazar",
            "Shatrasta",
            "Nabisco",
            "Tibat",
            "Mohakhali",
            "Kakoli",
            "Khilkhet",
            "Bisshoroad",
            "Airport",
            "Uttara",
            "Azampur",
            "HouseBuilding",
            "TongiStation",
            "CollegeGate",
            "HosenMarket",
            "Motijheel",
            "Gulsitan",
            "PressClub",
            "Shahbag",
            "ScienceLab",
            "CityCollege",
            "KalaBagan",
            "Sukrabad",
            "AsadGate",
            "Mohammadpur"


    };

    final static int INF = 99999, V = 20;
    int s,d;
    String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__route);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


        Bundle bundle = getIntent().getExtras();
        String src = bundle.getString("data1");
        String dest = bundle.getString("data2");
        pial = src;
        ehsan = dest;
        String radio = bundle.getString("data3");
        TextView tv = (TextView)findViewById(R.id.hell);

        String line = "";
        StringBuilder finalString = new StringBuilder();
        InputStream istream = getResources().openRawResource(R.raw.stopage);
        BufferedReader breader = new BufferedReader(new InputStreamReader(istream));
        //String[] parts = new String[100];

        try {
            while((line = breader.readLine()) != null)
            {
                //parts = line.split(",");

                finalString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(int i=0;i<20;i++)
        {
            if(location[i].equals(src))
            {
                s = i;
            }
            if(location[i].equals(dest))
            {
                d = i;
            }
        }

        //tv.setText(src+ " " + dest + " " + radio);
        tv.setText("Source: "  + location[s] + "\n"  + "Destination: " + location[d]);
        showResult(radio);


    }


    private int floydWarshall(int graph[][],int src,int dest)
    {
        int dist[][] = new int[V][V];
        int i, j, k;
        int path[][] = new int[V][V];
        result = "";

        /* Initialize the solution matrix same as input graph matrix.
           Or we can say the initial values of shortest distances
           are based on shortest paths considering no intermediate
           vertex. */
        for (i = 0; i < V; i++)
        {
            for (j = 0; j < V; j++)
            {
                dist[i][j] = graph[i][j];
                if(graph[i][j] == INF)
                {
                    path[i][j] = -1;
                }

                else
                {
                    path[i][j] = i;
                }
            }


        }
        for(i=0;i<V;i++)
            path[i][i] = i;


        for (k = 0; k < V; k++)
        {
            // Pick all vertices as source one by one
            for (i = 0; i < V; i++)
            {
                // Pick all vertices as destination for the
                // above picked source
                for (j = 0; j < V; j++)
                {
                    // If vertex k is on the shortest path from
                    // i to j, then update the value of dist[i][j]
                    if (dist[i][k] + dist[k][j] < dist[i][j])
                    {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        path[i][j] = path[k][j];

                    }
                }
            }
        }

        // Print the shortest distance matrix
        //printSolution(dist);
        for(i=0;i<V;i++)
        {
            for(j=0;j<V;j++)
            {
                //result+= Integer.toString(path[i][j]) + " ";
            }
            //result += "\n";
        }
        int d = dist[src][dest];
        printPath(path,src,dest);
        return d;
    }

    private void printPath(int path[][], int src, int dest)
    {
        int i = 0;
        finalPath = "Your route has the following stoppages:\n";
        finalPath = location[dest] + "-->";

        result = dest + "";
        while(path[src][dest] != src)
        {
            result = path[src][dest] + " " + result;
            finalPath = location[path[src][dest]] + "-->" + finalPath;
            dest = path[src][dest];
        }
        result = src + " " + result;
        finalPath = location[src] + "-->" + finalPath;
        finalPath += "\n\n";

    }

    void printSolution(int dist[][])
    {
        //System.out.println("Following matrix shows the shortest "+
        //"distances between every pair of vertices");
        result += "Following matrix shows the shortest distances between every pair of vertices\n";
        for (int i=0; i<V; ++i)
        {
            for (int j=0; j<V; ++j)
            {
                if (dist[i][j]==INF)
                {    //System.out.print("INF ");
                    result+=  "INF";
                }
                else
                //System.out.print(dist[i][j]+"   ");
                {
                    ;
                    result = result+ " " + Integer.toString(dist[i][j]);
                }
            }
            //System.out.println();
            result += "\n";
        }
    }

    public void showResult(String radio)
    {

        int[][] graph = new int[1000][1000];


        String line = "";
        StringBuilder finalString = new StringBuilder();
        InputStream istream = getResources().openRawResource(R.raw.edge);
        BufferedReader breader = new BufferedReader(new InputStreamReader(istream));
        String[] parts = new String[100];

        //init graph
        for(int i = 0;i<V;i++)
        {
            for(int j=0;j<V;j++)
            {
                graph[i][j] = INF;
            }
        }

        try {
            while((line = breader.readLine()) != null)
            {
                finalString.append(line);
                parts = line.split(",");
                graph[Integer.parseInt(parts[0])][Integer.parseInt(parts[1])] = Integer.parseInt(parts[2]);
                graph[Integer.parseInt(parts[1])][Integer.parseInt(parts[0])] = Integer.parseInt(parts[2]);


            }
        } catch (IOException e) {
            e.printStackTrace();
        }



        int dist;
        dist = floydWarshall(graph,s,d);

        TextView textview = (TextView)findViewById(R.id.write);

        if(radio.equals("Fare"))
        {
            Float fare = dist*1.5f;
            finalPath = finalPath + "\n" + "Toral fare is: " + fare + " taka"  ;


        }
        if(radio.equals("Time"))
        {
            Float time = dist/40.0f;
            finalPath = finalPath + "\n" + "Total time is: " + time + " hour(s)"  ;

        }
        if(radio.equals("Distance"))
        {
            finalPath = finalPath + "\n" + "Total Distance is: " + dist + " km"  ;

        }
        //textview.setText(result);
        //testclass tc = new testclass();
        //int temp = tc.getVal();
        assert textview != null;
        textview.setText("pial");

    }

    public void gotoMap(View view)
    {
        Intent intent = new Intent(this,MapsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("pial",pial);
        bundle.putString("ehsan",ehsan);
        intent.putExtras(bundle);
        startActivity(intent);

    }

}