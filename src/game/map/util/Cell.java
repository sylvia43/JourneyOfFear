package game.map.util;

import java.util.Arrays;

public class Cell {
    
    public static void main(String[] args) {
        int[][] cells = genCells(10, 10, 1000);
        
        System.out.println();
        printArray(cells);
    }
    
    public static int[][] genCells(int width, int height, int iterations) {
        int[][] cellBlock = new int[width][height];
        randomize(cellBlock);
        printArray(cellBlock);
        for(int i = 0; i < iterations; i++)
            iterateCells(cellBlock);
        return cellBlock;
    }
    
    private static void iterateCells(int[][] array) {
        int count;
        int[][] ptr;
        int[][] temp = new int[array.length][array[0].length];
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array[0].length; j++) {
                count = 0;
                ptr = getAdjacentIndices(i, j);
                for(int[] index : ptr) {
                    if((index[0] < array.length && index[0] >= 0) && (index[1] < array[0].length && index[1] >= 0)) {
                        if(array[index[0]][index[1]] != array[i][j])
                            count++;
                    }
                }
                temp[i][j] = count <= 3 && count >= 2 ? 1 : 0;
            }
        }
        
        for(int i = 0; i < array.length; i++)
            System.arraycopy(temp[i], 0, array[i], 0, array[0].length);
    }
    
    private static void randomize(int[][] array) {
        for(int[] subArray : array)
            for(int i = 0; i < subArray.length; i++)
                subArray[i] = (int)(Math.random() * 2);
    }
    
    private static int[][] getAdjacentIndices(int index1, int index2) {
        return new int[][]
        { 
            {index1+1, index2},
            {index1+1, index2-1},
            {index1, index2-1},
            {index1-1, index2-1},
            {index1-1, index2},
            {index1-1, index2+1},
            {index1, index2+1},
            {index1+1, index2+1}
        };
    }
    
    private static void printArray(int[][] array) {
        for(int[] cell : array)
            System.out.println(Arrays.toString(cell));
    }
}