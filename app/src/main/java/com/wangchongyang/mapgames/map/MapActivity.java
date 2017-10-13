package com.wangchongyang.mapgames.map;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import com.wangchongyang.mapgames.R;

import java.util.Random;

public class MapActivity extends AppCompatActivity {

    private SparseIntArray hashMap = new SparseIntArray();
    private int[] barrierNumbs = new int[]{ 105, 106, 107, 108, 109,
                                    110, 111, 131, 151, 171,
                                    191, 211, 231, 251, 271,
                                    291, 290, 289};
    private int startCellIndex = 0;
    private int endCellIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        init();
    }

    private void init() {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.bg_gridlayout);
        gridLayout.setColumnCount(20);
        gridLayout.setRowCount(20);
        int cellCount = 0;
        hashMap.clear();
        while (cellCount < 400) {
            hashMap.put(cellCount,Color.WHITE);
            ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.textview_cell, gridLayout,false);
            imageView.setId(cellCount);
            gridLayout.addView(imageView);
            cellCount++;
        }
    }

    public void setBarrier(View view) {
        setCellColor(Color.WHITE, startCellIndex);
        setCellColor(Color.WHITE, endCellIndex);
        setCellColor(Color.BLACK, barrierNumbs);
    }

    public void setStartAndEnd(View view) {

        setCellColor(Color.WHITE, startCellIndex);
        setCellColor(Color.WHITE, endCellIndex);

        Random random = new Random();

        do{
            endCellIndex = random.nextInt(400);
            startCellIndex = random.nextInt(400);
            Log.d("Random :",startCellIndex + "," + endCellIndex);
            Log.d("hash color :",hashMap.get(startCellIndex) + "," + hashMap.get(endCellIndex));

        }while (hashMap.get(startCellIndex) == Color.BLACK ||
                hashMap.get(endCellIndex) == Color.BLACK ||
                startCellIndex == endCellIndex);
        //int endCellIndex = 195;
        //int startCellIndex = 206;
        setCellColor(Color.YELLOW, startCellIndex);
        setCellColor(Color.RED, endCellIndex);
    }

    /*
    * set cellColor with Color.color and integer array.
    * */
    private void setCellColor(int type, int[] indexes) {
        for (int i : indexes) {
            setCellColor(type, i);
        }
    }

    private void setCellColor(int type, int index) {
        GridLayout gridLayout = (GridLayout) findViewById(R.id.bg_gridlayout);
        ImageView imageView = (ImageView) gridLayout.getChildAt(index);
        Drawable drawable;
        switch (type) {
            case Color.RED:
                drawable = getDrawable(R.mipmap.cell_red);
                break;
            case Color.BLACK:
                drawable = getDrawable(R.mipmap.cell_black);
                break;
            case Color.BLUE:
                drawable = getDrawable(R.mipmap.cell_blue);
                break;
            case Color.YELLOW:
                drawable = getDrawable(R.mipmap.cell_orange);
                break;
            case Color.GREEN:
                drawable = getDrawable(R.mipmap.cell_green);
                break;
            case Color.WHITE:
            default:
                drawable = getDrawable(R.mipmap.cell_white);
        }
        imageView.setImageDrawable(drawable);
        hashMap.put(index,type);
    }
}
