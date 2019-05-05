package com.example.sudoku;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.provider.FontsContract;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.provider.FontsContractCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.Arrays;
import java.util.LinkedList;


public class Sudoku extends View {
    // for create
    private int dimension;
    private int [][]board;
    private boolean[][]judge;
    private int row,col;
    public DLX dlx;
    public int [][][]result;
    public int resultCount;

    // for game
    private Context myContext;
    private int height;
    private int width;
    private int []btn;
    private int [][]problem;
    private int [][]answer;
    public int difficulty;
    private int curNum;
    private boolean draw = false;

    public void setCurNum(int n){
        curNum = n;
    }

    private void setPosition(int i,int j, int k){
        int index = (i - 1) * dimension +j;		// number of which column
        int r = (index - 1) * dimension + k;
        judge[r][index] = true;		// (i,j) has number

        int c = (i - 1) * dimension + dimension*dimension + k;
        judge[r][c] = true;		//	i row has k

        c = (j - 1) * dimension + dimension * dimension * 2 + k;
        judge[r][c] = true;		// j col has k

        int box = (i - 1)/(int)Math.sqrt((double)dimension) * (int)Math.sqrt((double)dimension) + (j - 1)/ (int)Math.sqrt((double)dimension) + 1;
        c =  (box - 1) * dimension  + (dimension * dimension * 3) + k;	//	this box has k
        judge[r][c] = true;
    }

    public Sudoku(Context context)  {
        super(context);
        myContext = context;
    }

    public Sudoku(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        myContext = context;
    }

    public Sudoku(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        myContext = context;
    }

    public void init(int dim,int dif){
        dimension = dim;
        difficulty = dif;
        curNum = 0;
        int cnt = 0;
        int []visit;
        btn = new int[dimension];
        visit = new int[dimension+1];
        board = new int[dimension+1][dimension+1];
        row = dimension * dimension * dimension;
        col = dimension * dimension * 4;
        judge = new boolean[row+1][col+1];
//        judge = new boolean[dimension+1][dimension+1];
        for(int i=0;i<dimension+1;i++)visit[i]=0;
        while(cnt < dimension) {
            int data = (int) (1 + Math.random() * dimension);
            if(visit[data] == 0){
                visit[data]=1;
                cnt++;
                board[1][cnt]=data;
            }
        }
        create();
        dlx.TraceBack(1);
        getResult();
        resultCount = dlx.getResultCount();
        draw = true;
        setProblem();
    }

    private void create(){
        for(int i=1;i<dimension+1;i++) {
            for(int j=1;j<dimension+1;j++){
                if(board[i][j]>0){
                    setPosition(i,j,board[i][j]);
                }
                else {
                    for(int k=1;k<dimension+1;k++){
                        setPosition(i,j,k);
                    }
                }
            }
        }
        dlx = new DLX(judge, row, col);
    }

    public void getResult() {
        resultCount = dlx.getResultCount();
        result = new int[resultCount][dimension+1][dimension+1];
        LinkedList<LinkedList<Node>> res = dlx.GetResult();
        res.forEach(tmp ->{
            tmp.forEach(node->{
                int t = node.Name;
                int k = t % dimension;
                if(k == 0) k = dimension;
                int index = (t - k) / dimension + 1;
                int j = index % dimension;
                if(j == 0)j = dimension;
                int i = (index - j)/dimension + 1;
                result[resultCount-1][i][j] = k ;
            });
            resultCount--;
        });
    }

    public void setProblem(){
        problem = new int[dimension+1][dimension+1];
        answer = new int[dimension+1][dimension+1];
        int []visit = new int[dimension+1];
        int count = 0;
        if(dimension == 4){
            if(difficulty == 1)count = 3;
            else if(difficulty == 2)count = 2;
            else count = 1;
        }
        else if(dimension == 9){
            if(difficulty == 1)count = 6;
            else if(difficulty == 2)count = 4;
            else count = 2;
        }
        else{
            if(difficulty == 1)count=8;
            else if(difficulty == 2)count=6;
            else count = 4;
        }
        for(int i=1;i<=dimension;i++){
            int t = 0;
            for(int j=0;j<=dimension;j++)
                visit[j]=0;
            while(t<count){
                int y = (int) (1 + Math.random() * dimension);
                if(visit[y]==0){
                    visit[y]=1;
                    t++;
                    problem[i][y]=result[0][i][y];
                    answer[i][y]=problem[i][y];
                }
            }
        }
    }

    @Override
    protected  void onDraw(Canvas canvas){
        if(draw == false);
        else{
            Paint paint = new Paint();

            paint.setAntiAlias(true);

            // draw lines
            width = getWidth()/dimension;
            height = getHeight()/dimension;
            for(int i=1;i<dimension;i++){
                if(i%(int)Math.sqrt((double)dimension) == 0){
                    paint.setColor(ContextCompat.getColor(myContext,R.color.line2));
                    paint.setStrokeWidth(6f);
                }
                else{
                    paint.setColor(ContextCompat.getColor(myContext, R.color.line1));
                    paint.setStrokeWidth(3f);
                }
                // the x axis
                canvas.drawLine(0, i * height, getWidth(), i * height, paint);
                canvas.drawLine(i * width, 0, i * width, getHeight(), paint);
            }

            paint.setTextAlign(Paint.Align.CENTER);
            paint.setTextSize((float) (height * 0.75));
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            paint.setTypeface(Typeface.SERIF);

            float x = width / 2;
            float y = height / 2 - (fontMetrics.ascent + fontMetrics.descent) / 2;

            for (int i = 1; i <= dimension; i++) {
                for (int j = 1; j <= dimension; j++) {
                    if(problem[i][j] != 0){
                        paint.setColor(ContextCompat.getColor(myContext, R.color.number1));
                        canvas.drawText(String.valueOf(problem[i][j]), (j-1) * width + x, (i-1) * height + y, paint);
                    }
                    else if(answer[i][j] != 0){
                        paint.setColor(ContextCompat.getColor(myContext, R.color.number2));
                        canvas.drawText(String.valueOf(answer[i][j]), (j-1) * width + x, (i-1) * height + y, paint);
                    }
                }
            }
            super.onDraw(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            final int x = (int) (event.getX() / width) + 1;
            final int y = (int) (event.getY() / height) + 1;
            if(curNum!=0 && problem[y][x]==0){
                answer[y][x]=curNum;
                postInvalidate();       // refresh
                curNum = 0;
            }
        }
        return super.onTouchEvent(event);
    }

    public int check(){
        for(int i=1;i<=dimension;i++){
            for(int j=1;j<=dimension;j++){
                if(answer[i][j]==0) return 1;  // have not finished
                if(answer[i][j]!=result[0][i][j])return 3;
            }
        }
        return 2;
    }
}
