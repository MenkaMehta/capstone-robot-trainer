package com.example.avatarmind.robotmotion;

import android.app.Activity;
import android.os.Bundle;
import android.robot.motion.RobotMotion;
import android.robot.speech.SpeechManager;
import android.robot.speech.SpeechService;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.xguzm.pathfinding.grid.GridCell;
import org.xguzm.pathfinding.grid.NavigationGrid;
import org.xguzm.pathfinding.grid.finders.AStarGridFinder;
import org.xguzm.pathfinding.grid.finders.GridFinderOptions;

import java.util.List;

public class WheelActivity extends Activity implements OnClickListener {

    private ImageView mTitleBack;

    private EditText mEtDistance;

    private EditText mEtAngle;

    private Button mBtnDistance;

    private Button mBtnAngle;

    private RobotMotion mRobotMotion = new RobotMotion();
    private SpeechManager mSpeechManager;

    final int mapWidth = 7, mapHeight = 9;

    final String TAG = "HasBooking";

    NavigationGrid<GridCell> navGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActionBar() != null) {
            getActionBar().hide();
        }

        initView();
        initListener();

        GridCell[][] cells = new GridCell[mapWidth][mapHeight];
        for (int x = 0 ; x < mapWidth; x++){
            for (int y = 0 ; y < mapHeight; y++){
                cells[x][y] = new GridCell(x, y, true);
                cells[x][y].setWalkable(true);
            }
        }

        //cells[0][0].setWalkable(false); disable access to cell
//
//        for (int i = 0; i <= 5; i++) {
//            cells[i][2].setWalkable(false);
//        }
//
//        cells[3][4].setWalkable(false);
//        cells[3][5].setWalkable(false);
//
//        for (int i = 3; i <= 6; i++) {
//            cells[i][6].setWalkable(false);
//        }

        //create your cells with whatever data you need
        //cells = createCells();

        //create a navigation grid with the cells you just created
        navGrid = new NavigationGrid<GridCell>(cells);
    }

    private void initView() {
        setContentView(R.layout.activity_wheel);

        mTitleBack = (ImageView) findViewById(R.id.common_title_back);
        TextView title = (TextView) findViewById(R.id.common_title_text);
        title.setText(R.string.unit_wheel);

        mEtDistance = (EditText) findViewById(R.id.wheel_distance);
        mEtAngle = (EditText) findViewById(R.id.wheel_angle);

        mBtnDistance = (Button) findViewById(R.id.wheel_walk);
        mBtnAngle = (Button) findViewById(R.id.wheel_turn);

        mSpeechManager = (SpeechManager) getSystemService(SpeechService.SERVICE_NAME);
    }

    private void initListener() {
        mTitleBack.setOnClickListener(this);
        mBtnDistance.setOnClickListener(this);
        mBtnAngle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.common_title_back:
                finish();
                break;
            case R.id.wheel_walk:
                String length = mEtDistance.getText().toString();
                if (!TextUtils.isEmpty(length)) {
                    //int distance = Integer.parseInt(length);
                    //mRobotMotion.startWalk(distance, 1, 0);
                    findPath();
                }
                break;
            case R.id.wheel_turn:
                String degree = mEtAngle.getText().toString();
                if (!TextUtils.isEmpty(degree)) {
                    //int angle = Integer.parseInt(degree);
                    //mRobotMotion.turn(angle, 2);
                }
                mSpeechManager.startSpeaking("Hello Menka and John, please take me home");
                break;
            default:
                break;
        }
    }


    public void findPath() {
        //or create your own pathfinder options:
        GridFinderOptions opt = new GridFinderOptions();
        opt.allowDiagonal = false;
        opt.dontCrossCorners = true;
        opt.isYDown = true; //change to false in real testing

        //these should be stored as [x][y]
        AStarGridFinder<GridCell> finder = new AStarGridFinder<GridCell>(GridCell.class, opt);

        List<GridCell> pathToEnd = finder.findPath(2, 8, 4, 3, navGrid);

        Log.d(TAG, "Got path");

        String output;
        StringBuilder str = new StringBuilder();
        for (GridCell cell : pathToEnd) {
            str.append("x: ").append(cell.x).append(", y: ").append(cell.y);
            str.append("\n");
        }
        output = str.toString();

        //output += "\n\nMatrix:\n" + printMatrix(cells);

        Log.d(TAG, output);

        //TextView tv = findViewById(R.id.output);

        //tv.setText(output);

        Runnable runnable = () -> {
            try {
//                mRobotMotion.startWalk(510, 3, 0);
//                Thread.sleep(5600);
//                mRobotMotion.turn(180, 2);
                double currentHeading = 0.0f;
                for (int i = 0; i < pathToEnd.size(); i++) {
                    GridCell currentCell = pathToEnd.get(i);
                    GridCell nextCell;
                    if ((i + 1) == pathToEnd.size()) {
                        //nextCell = pathToEnd.get(i);
                        continue;
                    }
                    else {
                        nextCell = pathToEnd.get(i + 1);
                    }

                    //double turnDegrees = get_new_direction_in_degrees(currentCell, nextCell);
                    double turnDegrees = logicalGetDirection(currentCell, nextCell); //absolute degrees from 0
                    double turnAngle = Math.abs(turnDegrees - currentHeading);

                    if (turnDegrees != currentHeading) {
                        Log.d(TAG, "turning angle: " + turnAngle);
                        mRobotMotion.turn((int)turnAngle, 2);
                        Thread.sleep(5000);
                        currentHeading = turnDegrees;
                    }

                    Log.d(TAG, "Moving Forward from " + currentCell + " to " + nextCell);
                    mRobotMotion.startWalk(510, 3, 0);
                    Thread.sleep(5000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Thread t = new Thread(runnable);
        t.start();
    }

    double logicalGetDirection(GridCell cell1, GridCell cell2) {
        //add angles for diagonals
        if (cell2.x == cell1.x && cell2.y < cell1.y) {
            //direction is forward
            return 0.0f;
        }
        else if (cell2.x > cell1.x && cell2.y < cell2.y) {
            return 45.0f;
        }
        else if (cell2.x > cell1.x && cell2.y == cell1.y) {
            return 90.0f;
        }
        else if (cell2.x > cell1.x && cell2.y < cell1.y) {
            return 135.0f;
        }
        else if (cell2.x == cell1.x && cell2.y > cell1.y) {
            return 180.0f;
        }
        else if (cell2.x < cell1.x && cell2.y < cell1.y) {
            return 225.0f;
        }
        else if (cell2.x < cell1.x && cell2.y == cell1.y) {
            return 270.0f;
        }
        else if (cell2.x < cell1.x && cell2.y < cell1.y) {
            return 315.0f;
        }
        return 0.0f;
    }

    double get_new_direction_in_degrees(GridCell cell1, GridCell cell2){
        double baseRadianDirection = Math.atan2(cell2.x - cell1.x, cell2.y - cell1.y);
        double degrees = baseRadianDirection * (180/Math.PI);
        double adjustedDegrees = degrees + 90;
        double modulo = adjustedDegrees % 360;
        return modulo;
    }
}
