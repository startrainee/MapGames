package com.wangchongyang.mapgames.map;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import com.wangchongyang.mapgames.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class MapActivity extends AppCompatActivity {

    private SparseIntArray hashMap = new SparseIntArray();
    private int[] barrierNumbs = new int[]{ 105, 106, 107, 108, 109,
                                    110, 111, 131, 151, 171,
                                    191, 211, 231, 251, 271,
                                    291, 290, 289};
    private int startCellIndex = 0;
    private int endCellIndex = 0;
    private int mapSize = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setGridCell(20);
    }

    private void setGridCell(int number) {
        mapSize = number;
        GridLayout gridLayout = (GridLayout) findViewById(R.id.bg_gridlayout);
        gridLayout.removeAllViews();
        gridLayout.setColumnCount(number);
        gridLayout.setRowCount(number);
        int cellCount = 0;
        hashMap.clear();
        while (cellCount < number * number) {
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
            endCellIndex = random.nextInt(mapSize*mapSize);
            startCellIndex = random.nextInt(mapSize*mapSize);
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
class FindWaysSolution {

    int[][] map;
    int[] barrierNumbs;

    public FindWaysSolution(int mapSize) {

        map = new int[mapSize][mapSize];
        barrierNumbs = new int[0];
        for(int[] a : map){
              Arrays.fill(a,0);
        }

    }

    public FindWaysSolution(int mapSize,int[] barrierNumbs) {

        map = new int[mapSize][mapSize];
        this.barrierNumbs = getRightBarrierNumbs(mapSize,barrierNumbs);
        for(int[] a : map){
            Arrays.fill(a,0);
        }

    }

    public void findWays(int start,int end){
        
    }




    private int[] getRightBarrierNumbs(final int mapSize, int[] barrierNumbs) {
        List<Integer> list = new ArrayList<>();
        for(int i : barrierNumbs){
            if(i >=0 && i < mapSize * mapSize) list.add(i);
        }
        int[] ret = new int[list.size()];
        for(int a = 0;a < ret.length;a++)
            ret[a] = list.get(a);
        return ret;
    }
}
