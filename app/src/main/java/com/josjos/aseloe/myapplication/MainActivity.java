package com.josjos.aseloe.myapplication;

import android.graphics.Color;
import android.service.autofill.Dataset;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.red;

public class MainActivity extends AppCompatActivity {

    double dekat = 0, aman = 0, jauh = 0, lambat = 0, normal = 0, cepat = 0, kendor = 0, sedang = 0, kencang = 0;
    double temp_a = 0, temp_b = 0;

    /*Valueset adalah Dataset dinamis yang nilai entery-nya berubah-ubah.*/
    LineDataSet valueset1;
    LineDataSet valueset2;
    LineDataSet valueset3;
    /*DataSet ini fungsinya menampung semua Dataset, baik Statis/Dinamis, tetapi yang Statis sudah menggunakan porsi Index 0,1,2.*/
    List<ILineDataSet> dataSets;
    /*LineData ini fungsinya sama dengan DataSet di C#, sebagai acuan Chart.*/
    LineData lineData;

    TextView defuzzy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LineChart line = (LineChart) findViewById(R.id.chart);
        line.setScaleEnabled(false);//Chart LineChart
        /* ListEntry mewakili satu garis linear. Dan titik-titiknya ditentukan oleh Entry individu didalamnya, seperti dibawah*/
        List<Entry> kendor1 = new ArrayList<Entry>();
        kendor1.add(new Entry(0f,0));
        kendor1.add(new Entry(200f,1));
        kendor1.add(new Entry(400f,0));
        kendor1.add(new Entry(800f,0));

        List<Entry> sedang1 = new ArrayList<Entry>();
        sedang1.add(new Entry(0f,0));
        sedang1.add(new Entry(200f,0));
        sedang1.add(new Entry(400f,1));
        sedang1.add(new Entry(600f,0));

        List<Entry> kencang1 = new ArrayList<Entry>();
        kencang1.add(new Entry(0f,0));
        kencang1.add(new Entry(400f,0));
        kencang1.add(new Entry(600f,1));
        kencang1.add(new Entry(1000f,1));

        /* dataset == ListEntery, namun kendali Labeling dan styling garis disini.*/
        LineDataSet dataset1 = new LineDataSet(kendor1,"Kendor");
        dataset1.setColor(-65281);
        dataset1.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet dataset2 = new LineDataSet(sedang1,"Sedang");
        dataset2.setColor(-16711936);
        dataset2.setAxisDependency(YAxis.AxisDependency.LEFT);
        LineDataSet dataset3 = new LineDataSet(kencang1,"Kencang");
        dataset3.setColor(-16776961);
        dataset3.setAxisDependency(YAxis.AxisDependency.LEFT);


        List<Entry> value1 = new ArrayList<Entry>();
        List<Entry> value2 = new ArrayList<Entry>();
        List<Entry> value3 = new ArrayList<Entry>();


        value1.add(new Entry(0f,0));
        value2.add(new Entry(0f,0));
        value3.add(new Entry(0f,0));

        valueset1 = new LineDataSet(value1, ":");
        valueset2 = new LineDataSet(value2, ":");
        valueset3 = new LineDataSet(value3, ":");

        valueset1.setAxisDependency(YAxis.AxisDependency.LEFT);
        valueset1.setDrawFilled(true);
        valueset2.setAxisDependency(YAxis.AxisDependency.LEFT);
        valueset2.setDrawFilled(true);
        valueset3.setAxisDependency(YAxis.AxisDependency.LEFT);
        valueset3.setDrawFilled(true);

        //new Value
        if(kendor!=0){
            int kijol = (int) ((kendor*(200-0)) + 0);
            int kijul = (int) (-((kendor*(400-200)) - 400));


            value1.add(new Entry((float) kijol, (float) kendor));
            value1.add(new Entry((float) kijul, (float) kendor));
            value1.add(new Entry(400f, 0));
        }
        if(sedang!=0){
            int kijol = (int) ((sedang*(400-200)) + 200);
            int kijul = (int) (-((sedang*(600-400)) - 600));

            value2.add(new Entry(200f,0));
            value2.add(new Entry((float) kijol, (float) sedang));
            value2.add(new Entry((float) kijul, (float) sedang));
            value2.add(new Entry(600f, 0));
        }
        if(kencang!=0){
            int kijol = (int) ((kencang*(600-400)) + 400);

            value3.add(new Entry(400f,0));
            value3.add(new Entry((float) kijol, (float) kencang));
            value3.add(new Entry(1000f, (float) kencang));
        }


        dataSets = new ArrayList<ILineDataSet>();
        dataSets.add(dataset1); dataSets.add(dataset2); dataSets.add(dataset3);
        dataSets.add(valueset1); dataSets.add(valueset2); dataSets.add(valueset3);

        lineData = new LineData(dataSets);
        line.setData(lineData);
        line.animateXY(3000,1000);


        //SEEKBAR
        final EditText jarak, kecepatan;
        jarak = (EditText) findViewById(R.id.jarak);
        kecepatan = (EditText) findViewById(R.id.kecepatan);

        final SeekBar jarakseekbar = (SeekBar) findViewById(R.id.jarakseekbar) ;
        SeekBar kecepatanseekbar = (SeekBar) findViewById(R.id.kecepatanseekbar);

        jarakseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                jarak.setText("" + i);
                OnCalculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        kecepatanseekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {


            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                kecepatan.setText("" + i);
                OnCalculate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }


    public void setData(ChartData data) {

    }

    public void OnCalculate(){
        EditText jarak, kecepatan;
        jarak = (EditText) findViewById(R.id.jarak);
        kecepatan = (EditText) findViewById(R.id.kecepatan);
        LineChart line = (LineChart) findViewById(R.id.chart);

        jarak(Double.parseDouble(jarak.getText().toString()));
        kecepatan(Double.parseDouble(kecepatan.getText().toString()));


        double kendor = 0, sedang = 0, kencang = 0;

        if(dekat!=0){
            if(cepat!=0){
                temp_a = temp_a + (((Math.min(dekat,cepat)*200)+400)*Math.min(dekat,cepat));
                temp_b = temp_b + Math.min(dekat,cepat);
                kencang = (Math.min(dekat,cepat)>kencang)?Math.min(dekat,cepat):kencang;
            }
            if(normal!=0){
                temp_a = temp_a + (((Math.min(dekat,normal)*200)+200)*Math.min(dekat,normal));
                temp_b = temp_b + Math.min(dekat,normal);
                sedang = (Math.min(dekat,normal)>sedang)?Math.min(dekat,normal):sedang;
            }
            if(lambat!=0){
                temp_a = temp_a + (((Math.min(dekat,lambat)*200)+200)*Math.min(dekat,lambat));
                temp_b = temp_b + Math.min(dekat,lambat);
                sedang = (Math.min(dekat,lambat)>sedang)?Math.min(dekat,lambat):sedang;
            }
        }

        if(aman!=0){
            if(cepat!=0){
                temp_a = temp_a + (((Math.min(aman,cepat)*200)+200)*Math.min(aman,cepat));
                temp_b = temp_b + Math.min(aman,cepat);
                sedang = (Math.min(aman,cepat)>sedang)?Math.min(aman,cepat):sedang;
            }

            if(normal!=0){
                temp_a = temp_a + (((Math.min(aman,normal)*200)+200)*Math.min(aman,normal));
                temp_b = temp_b + Math.min(aman,normal);
                sedang = (Math.min(aman,normal)>sedang)?Math.min(aman,normal):sedang;
            }

            if(lambat!=0){
                temp_a = temp_a + (((Math.min(aman,lambat)*200)-400)*Math.min(aman,lambat));
                temp_b = temp_b + Math.min(aman,lambat);
                kendor = (Math.min(aman,lambat)>kendor)?Math.min(aman,lambat):kendor;
            }

        }

        if(jauh!=0){
            if(cepat!=0){
                temp_a = temp_a + (((Math.min(jauh,cepat)*200)+200)*Math.min(jauh,cepat));
                temp_b = temp_b + Math.min(jauh,cepat);
                sedang = (Math.min(jauh,cepat)>sedang)?Math.min(jauh,cepat):sedang;
            }

            if(normal!=0){
                temp_a = temp_a + (((Math.min(jauh,normal)*200)-400)*Math.min(jauh,normal));
                temp_b = temp_b + Math.min(jauh,normal);
                kendor = (Math.min(jauh,normal)>kendor)?Math.min(jauh,normal):kendor;
            }

            if(lambat!=0){
                temp_a = temp_a + (((Math.min(jauh,lambat)*200)-400)*Math.min(jauh,lambat));
                temp_b = temp_b + Math.min(jauh,lambat);
                kendor = (Math.min(jauh,lambat)>kendor)?Math.min(jauh,lambat):kendor;
            }

        }
        defuzzy = (TextView) findViewById(R.id.defuzzy);
        DecimalFormat df = new DecimalFormat("####0.00");
        defuzzy.setText(df.format(temp_a / temp_b));

        List<Entry> value1 = new ArrayList<Entry>();
        List<Entry> value2 = new ArrayList<Entry>();
        List<Entry> value3 = new ArrayList<Entry>();

        for(int i = 5;i>2;i--)
        lineData.removeDataSet(i);


        /////

        value1.add(new Entry(0f,0));
        value2.add(new Entry(0f,0));
        value3.add(new Entry(0f,0));

        valueset1 = new LineDataSet(value1, ":");
        valueset2 = new LineDataSet(value2, ":");
        valueset3 = new LineDataSet(value3, ":");

        valueset1.setAxisDependency(YAxis.AxisDependency.LEFT);
        valueset1.setDrawFilled(true);
        valueset2.setAxisDependency(YAxis.AxisDependency.LEFT);
        valueset2.setDrawFilled(true);
        valueset3.setAxisDependency(YAxis.AxisDependency.LEFT);
        valueset3.setDrawFilled(true);

        //new Value
        if(kendor!=0){
            int kijol = (int) ((kendor*(400-200)) + 0);
            value1.add(new Entry(0f,0));
            value1.add(new Entry((float) kijol,0));
            value1.add(new Entry((float) kijol, (float) kendor));
        }
        if(sedang!=0){
            int kijol = (int) ((sedang*(400-200)) + 200);
            value2.add(new Entry(0f,0));
            value2.add(new Entry((float) kijol,0));
            value2.add(new Entry((float) kijol, (float) sedang));
        }
        if(kencang!=0){
            int kijol = (int) ((kencang*(600-400)) + 400);
            value3.add(new Entry(0f,0));
            value3.add(new Entry((float) kijol,0));
            value3.add(new Entry((float) kijol, (float) kencang));
        }

        //https://github.com/PhilJay/MPAndroidChart/wiki/Setting-Data

        dataSets.add(valueset1); dataSets.add(valueset2); dataSets.add(valueset3);
        line.setData(lineData);
        line.notifyDataSetChanged();
        line.animateX(1000, Easing.EasingOption.EaseOutSine);

    }


    public void jarak(double input){
    //dekat
        if(input<=30&&input>=5)
            dekat = 1;
        else if(input>30&&input<75)
            dekat = (75-input)/45;
        else
            dekat = 0;

    //aman
        if(input>=30&&input<=75)
            aman = (input-30)/45;
        else if(input>=75&&input<=120)
            aman = (120-input)/45;
        else
            aman = 0;

  //jauh
        if(input>=75&&input<=120)
            jauh =  (input-75)/45;
        else if(input>120)
            jauh = 1;
        else
            jauh = 0;

    }




    public void kecepatan(double input){
     //lambat
        if(input<=30&&input>=0)
            lambat = 1;
        else if(input>30&&input<55)
            lambat = (55-input)/25;
        else
            lambat = 0;


    //normal
        if(input>=30&&input<=55)
            normal = (input-30)/25;
        else if(input>=55&&input<=80)
            normal = (80-input)/25;
        else
            normal = 0;


    //cepat
        if(input>=55&&input<=80)
            cepat = (input-55)/45;
        else if(input>80)
            cepat = 1;
        else
            cepat = 0;
    }



}
