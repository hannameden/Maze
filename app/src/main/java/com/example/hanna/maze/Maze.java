package com.example.hanna.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Maze {

    public static Cell[][] cells;

    private Cell nextCell;

    private Cell goalCell;
    private Cell startCell;

    private Stack<Cell> stack;

    private ArrayList<Cell> neighbours;

    private Random random;

    private int mazeWidth, mazeHeight;

    private boolean goalIsFound;

    public Maze(int size, int seed) {
        this.mazeHeight = size;
        this.mazeWidth = size;

        random = new Random();
        random.setSeed(seed);

        initializeCells();

        stack = new Stack<Cell>();

        startCell = cells[cells.length-1][cells[0].length-1];

        goalCell = cells[0][0];

        createMaze(startCell);
    }

    private void initializeCells() {
        cells = new Cell[mazeWidth][mazeHeight];

        for(int x = 0; x < cells.length; x++)
            for(int y = 0; y < cells[x].length; y++)
                cells[x][y] = new Cell(x, y);
    }

    private void createMaze(Cell currentCell) {

        if(isAllVisited())
            return;
        if(currentCell.equals(goalCell) && !goalIsFound)
            goalIsFound = true;

        currentCell.setVisited(true);
        neighbours = findNeighbours(currentCell);

        if(neighbours.size() == 0) {
            nextCell = stack.pop();
            if(!goalIsFound)
                currentCell.setSolution(false);
        }else {
            nextCell = selectRandomNeighbour(neighbours);
            if(!goalIsFound)
                currentCell.setSolution(true);

            stack.push(currentCell);
            removeWallBetween(currentCell, nextCell);
        }

        createMaze(nextCell);

    }

    private boolean isAllVisited() {
        for(int x = 0; x < cells.length; x++)
            for(int y = 0; y < cells[x].length; y++)
                if(cells[x][y].getIsVisited() == false)
                    return false;
        return true;
    }

    private void removeWallBetween(Cell cc, Cell nc) {

        int ccX = cc.getX();
        int ccY = cc.getY();

        int ncX = nc.getX();
        int ncY = nc.getY();

        if(ccY - ncY == 1) {
            cc.walls[0] = 0;
            nc.walls[1] = 0;
        }

        if(ccY - ncY == -1) {
            cc.walls[1] = 0;
            nc.walls[0] = 0;
        }

        if(ccX - ncX == 1) {
            cc.walls[2] = 0;
            nc.walls[3] = 0;
        }

        if(ccX - ncX == -1) {
            cc.walls[3] = 0;
            nc.walls[2] = 0;
        }

    }

    private ArrayList<Cell> findNeighbours(Cell currentCell) {

        ArrayList<Cell> neighbours = new ArrayList<Cell>();

        if(!(currentCell.getY() == 0)) {
            Cell up = cells[currentCell.getX()][currentCell.getY() - 1];
            if(!up.getIsVisited())
                neighbours.add(up);
        }

        if(!(currentCell.getX() == 0)) {
            Cell west = cells[currentCell.getX() - 1][currentCell.getY()];
            if(!west.getIsVisited())
                neighbours.add(west);
        }

        if(!(currentCell.getY() == cells[0].length - 1)) {
            Cell down = cells[currentCell.getX()][currentCell.getY() + 1];
            if(!down.getIsVisited())
                neighbours.add(down);
        }

        if(!(currentCell.getX() == cells.length - 1)) {
            Cell east = cells[currentCell.getX() + 1][currentCell.getY()];
            if(!east.getIsVisited())
                neighbours.add(east);
        }

        return neighbours;

    }

    private Cell selectRandomNeighbour(ArrayList<Cell> neighbours) {

        Cell randomCell = neighbours.get(random.nextInt(neighbours.size()));

        return randomCell;

    }


    public void update() {

    }

    public void render(Canvas canvas) {

        Paint paint = new Paint();

        //Draw walls
        for(int x = 0; x < cells.length; x++)
            for(int y = 0; y < cells[x].length; y++)
                cells[x][y].drawWalls(canvas);

        paint.setColor(Color.BLUE);
        startCell.fillCell(canvas, paint);

        paint.setColor(Color.GREEN);
        goalCell.fillCell(canvas, paint);


        //Solution test, for future functionality
	/*	for(int x = 0; x < cells.length; x++)
			for(int y = 0; y < cells[x].length; y++) {
				if(cells[x][y].isSolution()) {
					paint.setColor(Color.MAGENTA);
					cells[x][y].fillCell(canvas, paint);
				}
			}*/
	}
}

