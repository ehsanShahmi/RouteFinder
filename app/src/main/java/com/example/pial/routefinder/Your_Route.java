package com.example.pial.routefinder;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;


class Node
{
    int node;
    int distance;
    int time;
    int fare;
    int parent;
    String bus;
    Node(int n,int d,int t,int f,int p, String b)
    {
        this.node = n;
        this.distance = d;
        this.time = t;
        this.fare = f;
        this.parent = p;
        this.bus = b;
    }
    Node()
    {

    }

}

class Edge
{
    int x;
    int y;
    int distance;
    int time;
    int fare;
    int hasedge;
    String busname;
    Edge(int x,int y,int d, int t,int f,int h,String b)
    {
        this.x = x;
        this.y = y;
        this.distance =d;
        this.time = t;
        this.fare = f;
        this.hasedge = h;
        this.busname = b;
    }
    Edge()
    {

    }
}

class NodeComparator implements Comparator<Node>
{
    @Override
    public int compare(Node n1, Node n2)
    {
        //return 0;	// this is temporary

        return n1.distance-n2.distance;

        //return 0;
    }
}




public class Your_Route extends AppCompatActivity {

    String pial,ehsan;
    //Node node = new Node(1,2,3,4,"pial","rocks");
    public static Edge[][] edge = new Edge[1000][1000];
    public static Node[] node = new Node[1000];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your__route);
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
        TextView tv = (TextView)findViewById(R.id.result);
        //testclass tc = new testclass();
        //int temp = tc.getVal();
        //tv.setText(src + " " + dest + " " + radio);
        //tv.setText(temp + " pial");

        for(int i=0;i<1000;i++)
        {
            for(int j=0;j<1000;j++)
            {
                edge[i][j] = new Edge();
                //edge[i][j].distance = 10000;
                edge[i][j].hasedge = 0;
            }
            node[i] = new Node();
            node[i].distance = 10000;
            node[i].time = 10000;

            node[i].fare = 10000;

        }


        Map<String, Integer> myMap = new HashMap<String, Integer>();
        Map<Integer, String> map2 = new HashMap<Integer, String>();

        String str = "";
        String[] parts;
        int count = 0;
        StringBuilder finalString = new StringBuilder();



        try {
            InputStream istream = getResources().openRawResource(R.raw.stopage);
            BufferedReader breader = new BufferedReader(new InputStreamReader(istream));
            while((str = breader.readLine()) != null)
            {
                //System.out.println("inside file");
                //str = sc.next();
                //nodelist[count] = str.toString();
                finalString.append(str);
                myMap.put(str, count);
                map2.put(myMap.get(str), str);
                System.out.println(map2.get(count));
                node[count] = new Node(count,0,0,0,0,"");
                node[count].distance = 10000;
                node[count].time = 10000;
                node[count].fare = 10000;

                System.out.println(myMap.get(str));
                count++;


            }
            //sc.close();
        }  catch (IOException e) {
            e.printStackTrace();
        }

        //tv.setText(finalString);

        // first input from stopage is done here

        // input for edge begins
        int type;
        if(radio.equals("Distance"))
        {
            type = 1;
        }
        else if(radio.equals("Time"))
        {
            type = 2;
        }
        else
        {
            type = 3;
        }


        InputStream istream = getResources().openRawResource(R.raw.edge);
        BufferedReader breader = new BufferedReader(new InputStreamReader(istream));
        try {
            while((str = breader.readLine()) != null)
            {

                finalString.append(str);
                parts = str.split(",");
                int i = myMap.get(parts[0]);
                int j = myMap.get(parts[1]);

                /*edge[i][j].hasedge = 1;
                edge[j][i].hasedge = 1;

                edge[i][j].distance = Integer.parseInt(parts[2]);
                edge[j][i].distance = Integer.parseInt(parts[2]);

                edge[i][j].time = Integer.parseInt(parts[3]);
                edge[j][i].time = Integer.parseInt(parts[3]);

                edge[i][j].fare = Integer.parseInt(parts[4]);
                edge[j][i].fare = Integer.parseInt(parts[4]);
                //System.out.println(edge[i][j].time);

                edge[i][j].busname = parts[5];
                edge[j][i].busname = parts[5];
                */
                if(edge[i][j].hasedge == 0)
                {
                    System.out.println("inside if");
                    edge[i][j].hasedge = 1;
                    edge[j][i].hasedge = 1;

                    edge[i][j].distance = Integer.parseInt(parts[2]);
                    edge[j][i].distance = Integer.parseInt(parts[2]);

                    edge[i][j].time = Integer.parseInt(parts[3]);
                    edge[j][i].time = Integer.parseInt(parts[3]);

                    edge[i][j].fare = Integer.parseInt(parts[4]);
                    edge[j][i].fare = Integer.parseInt(parts[4]);
                    //System.out.println(edge[i][j].time);

                    edge[i][j].busname = parts[5];
                    edge[j][i].busname = parts[5];

                }
                else
                {
                    System.out.println("inside else");
                    edge[i][j].hasedge = 1;
                    edge[j][i].hasedge = 1;
                    if(edge[i][j].distance>Integer.parseInt(parts[2]) && type ==1)
                    {
                        System.out.println("inside else if");

                        edge[i][j].distance = Integer.parseInt(parts[2]);
                        edge[j][i].distance = Integer.parseInt(parts[2]);

                        edge[i][j].time = Integer.parseInt(parts[3]);
                        edge[j][i].time = Integer.parseInt(parts[3]);

                        edge[i][j].fare = Integer.parseInt(parts[4]);
                        edge[j][i].fare = Integer.parseInt(parts[4]);
                        //System.out.println(edge[i][j].time);

                        edge[i][j].busname = parts[5];
                        edge[j][i].busname = parts[5];
                    }

                    if(edge[i][j].time>Integer.parseInt(parts[3]) && type ==2)
                    {
                        System.out.println("inside else if");

                        edge[i][j].distance = Integer.parseInt(parts[2]);
                        edge[j][i].distance = Integer.parseInt(parts[2]);

                        edge[i][j].time = Integer.parseInt(parts[3]);
                        edge[j][i].time = Integer.parseInt(parts[3]);

                        edge[i][j].fare = Integer.parseInt(parts[4]);
                        edge[j][i].fare = Integer.parseInt(parts[4]);
                        //System.out.println(edge[i][j].time);

                        edge[i][j].busname = parts[5];
                        edge[j][i].busname = parts[5];
                    }

                    if(edge[i][j].fare>Integer.parseInt(parts[4]) && type ==3)
                    {
                        System.out.println("inside else if");

                        edge[i][j].distance = Integer.parseInt(parts[2]);
                        edge[j][i].distance = Integer.parseInt(parts[2]);

                        edge[i][j].time = Integer.parseInt(parts[3]);
                        edge[j][i].time = Integer.parseInt(parts[3]);

                        edge[i][j].fare = Integer.parseInt(parts[4]);
                        edge[j][i].fare = Integer.parseInt(parts[4]);
                        //System.out.println(edge[i][j].time);

                        edge[i][j].busname = parts[5];
                        edge[j][i].busname = parts[5];
                    }

                }





                //System.out.println(edge[]);
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        assert tv != null;


        int s = myMap.get(src);
        int d = myMap.get(dest);
        node[s].node = s;
        dijsktra(node[s],type);
        //int de = d;
        String result = s + " " + d + " " + type + " \n";

        //System.out.println("final path ");
        //System.out.println(dest);

        // this is the printpath section
        // this section should either be in seperated function
        // or beneath the if
        int counter = 0;
        int[] road = new int[100];
        /*road[counter++] = d;

        while(d!=s)
        {
            //System.out.println(dest + " src " + src);

            d = node[d].parent;
            road[counter++] = d;
            System.out.println(d);
        }

        for(int i=count;i>=0;i++)
        {
            result = Integer.toString(road[i]);
        }
        */


        if(type==1)
        {
            //tv.setText(result + "Final Result " + node[d].distance + " " + node[d].bus + " " + node[d].parent);
            //result += "Final Result " + node[d].distance + " " + node[d].bus + " " + node[d].parent;
            result += "Total distance is: " + node[d].distance + " KM";
        }
        if(type==2)
        {
            //tv.setText(result + "Final Result " + node[d].time + " " + node[d].bus + " " + node[d].parent);
            //result += "Final Result " + node[d].time + " " + node[d].bus + " " + node[d].parent;
            result += "Total time needed is: " + node[d].time + " Minute(s)";
        }
        if(type==3)
        {
            //tv.setText(result + "Final Result " + node[d].fare + " " + node[d].bus + " " + node[d].parent);
            //result += "Final Result " + node[d].fare + " " + node[d].bus + " " + node[d].parent;
            result += "Total cost is: " + node[d].fare + " Taka";
        }

        //System.out.println(map2.get(dest));
        //result += "\n\n";
        //result += map2.get(d);
        //result += " " + node[d].bus + " ";
        road[counter++] = d;
        while(d!=s)
        {
            //System.out.println(dest + " src " + src);
            d = node[d].parent;
            System.out.println(map2.get(d));
            road[counter++] = d;
            //result += " " + node[d].bus + " ";
            //result += map2.get(d);
        }
        String path="Your path is\n";
        for(int i = counter-1;i>=0;i--)
        {
            path += " " + node[road[i]].bus + "  " + map2.get(road[i]) + " ";
        }
        tv.setText(path + "\n" + result);
    }


    public static void dijsktra(Node src,int p)
    {
        Comparator<Node> comparator = new NodeComparator();
        PriorityQueue<Node> queue = new PriorityQueue<Node>(10000,comparator);

        src.distance = 0;
        src.time = 0;
        src.fare = 0;
        //int u = src.node;
        queue.add(src);
        //System.out.println("intial size of quque: " + queue.size());
        while(queue.size() != 0)
        {
            Node n = queue.remove();
            int u = n.node;
            //System.out.println(u);
            for(int i=0;i<=50;i++)	// 100 represents node number
            {
                //System.out.println(u + " " + i);

                if(edge[u][i].hasedge==1)		// if exists an edge between the two
                {

                    //System.out.println("inside hasedge");
                    if(p==1)
                    {
                        System.out.println(u + " " + i);
                        System.out.println(node[i].distance + " " + edge[u][i].busname);
                        if(node[i].distance>node[u].distance+edge[u][i].distance)
                        {
                            System.out.println("inside final" + " " + edge[u][i].busname);
                            node[i].distance = node[u].distance + edge[u][i].distance;
                            node[i].node = i;
                            node[i].parent = u;
                            node[i].bus = edge[u][i].busname ;
                            queue.add(node[i]);

                        }
                    }

                    if(p==2)
                    {
                        System.out.println("boo!!");
                        System.out.println(node[i].time + " " + node[u].time + " " + edge[u][i].time);
                        if(node[i].time>node[u].time+edge[u][i].time)
                        {
                            System.out.println("inside time final");
                            node[i].time = node[u].time + edge[u][i].time;
                            node[i].node = i;
                            node[i].parent = u ;
                            node[i].bus = edge[u][i].busname;
                            queue.add(node[i]);

                        }
                    }


                    if(p==3)
                    {
                        System.out.println("hoo!!");
                        if(node[i].fare>node[u].fare+edge[u][i].fare)
                        {
                            System.out.println("inside fare final");
                            node[i].fare = node[u].fare + edge[u][i].fare;
                            node[i].node = i;
                            node[i].parent = u;
                            node[i].bus = edge[u][i].busname ;
                            queue.add(node[i]);

                        }
                    }
                }
            }
            //System.out.println("size " + queue.size());
        }


    }


}