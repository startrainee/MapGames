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

import java.util.*;
import java.util.stream.Collectors;

public class MapActivity extends AppCompatActivity {

    private SparseIntArray hashMap = new SparseIntArray();
    private int[] barrierNumbs = new int[]{105, 106, 107, 108, 109,
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
            hashMap.put(cellCount, Color.WHITE);
            ImageView imageView = (ImageView) getLayoutInflater().inflate(R.layout.textview_cell, gridLayout, false);
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

        do {
            endCellIndex = random.nextInt(mapSize * mapSize);
            startCellIndex = random.nextInt(mapSize * mapSize);
            Log.d("Random :", startCellIndex + "," + endCellIndex);
            Log.d("hash color :", hashMap.get(startCellIndex) + "," + hashMap.get(endCellIndex));

        } while (hashMap.get(startCellIndex) == Color.BLACK ||
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
        hashMap.put(index, type);
    }

}

class FindWaysSolution {

    private int[] map;
    private int mapSideLength;
    private int[] barrierNumbs;
    private int[] resultWays;

    public FindWaysSolution(int mapLength) {

        map = new int[mapLength * mapLength];
        this.mapSideLength = mapLength;
        barrierNumbs = new int[0];
        Arrays.fill(map, 0);

    }

    public FindWaysSolution(int mapSize, int[] barrierNumbs) {

        map = new int[mapSize * mapSize];
        this.barrierNumbs = getRightBarrierNumbs(map.length, barrierNumbs);
        Arrays.fill(map, 0);

    }

    public void findWays(int start, int end) {
        if (start < 0 || end < 0) {
            Log.d("findWays", "wrong start or end : (" + start + " " + end + ")");
            return;
        }
        if (start >= map.length || end >= map.length) {
            Log.d("findWays", "wrong start or end : (" + start + " " + end + ")");
            return;
        }
        Stack<Integer> hasFoundPoint = new Stack<>();
        Stack<Integer> foundWays = new Stack<>();
        hasFoundPoint.push(start);
        foundWays.push(start);
        findWays(start, end, mapSideLength, hasFoundPoint, foundWays);

    }
    //todo
    private int findWays(int start, int end, int mapSideLength, Stack<Integer> hasFoundPoint, Stack<Integer> foundWays) {
        if (start == end) {
            resultWays = getResultWays(hasFoundPoint);
            return end;
        }
        int[] directions = new int[]{-1, 1, mapSideLength, -mapSideLength};
        int distance = Integer.MAX_VALUE;
        for (int i : directions) {
            int newPoint = start + i;
            if (newPoint < 0 || newPoint > mapSideLength * mapSideLength) continue;
            if (newPoint % mapSideLength == 0
                    && start % mapSideLength != 1
                    && start % mapSideLength != 0) continue;
            if (newPoint % mapSideLength == mapSideLength - 1
                    && start % mapSideLength != mapSideLength - 2
                    && start % mapSideLength != mapSideLength - 1) continue;
            distance = Math.max(distance, getdistance(newPoint, end, mapSideLength));

        }
        return 0;
    }

    private int[] getResultWays(Stack<Integer> hasFoundPoint) {
        return null;
    }

    private int getdistance(int start, int end, int mapSideLength) {
        int[] startPoint = new int[]{start / mapSideLength, start % mapSideLength};
        int[] endPoint = new int[]{end / mapSideLength, end % mapSideLength};
        int x = Math.abs(startPoint[0] - endPoint[0]);
        int y = Math.abs(startPoint[1] - endPoint[1]);
        return x * x + y * y;
    }


    private int[] getRightBarrierNumbs(int mapSize, int[] barrierNumbs) {
        List<Integer> list = new ArrayList<>();
        for (int i : barrierNumbs) {
            if (i >= 0 && i < mapSize) list.add(i);
        }
        int[] ret = new int[list.size()];
        for (int a = 0; a < ret.length; a++)
            ret[a] = list.get(a);
        return ret;
    }
}
