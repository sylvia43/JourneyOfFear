package game.map.util;

public class Cell {
    
    public static void main(String[] args) {
        char[][] cells = genCells(100, 100, 2);
        
        System.out.println();
        printArray(cells);
    }
    
    public static char[][] genCells(int width, int height, int iterations) {
        char[][] cellBlock = new char[width][height];
        randomize(cellBlock, 0.49);
        printArray(cellBlock);
        for(int i = 0; i < iterations; i++)
            smooth(iterations, 4, cellBlock);
        return cellBlock;
    }
    
    private static void smooth(int times, int liveAmount, char[][] array) {
        int count;
        int[][] ptr;
        char[][] temp = new char[array.length][array[0].length];
        for(int iter = 0; iter < times; iter++) {
            for(int i = 0; i < array.length; i++) {
                for(int j = 0; j < array[0].length; j++) {
                    count = array[i][j] == '#' ? 1 : 0;
                    ptr = getAdjacentIndices(i, j);
                    for(int[] index : ptr) {
                        if((index[0] < array.length && index[0] >= 0) && (index[1] < array[0].length && index[1] >= 0)) {
                            if(array[index[0]][index[1]] == '#')
                                count++;
                        }
                    }
                    temp[i][j] = count <= liveAmount ? '.' : '#';
                }
            }
            
            for(int i = 0; i < array.length; i++)
                System.arraycopy(temp[i], 0, array[i], 0, array[0].length);
        }
    }
    
    private static void randomize(char[][] array, double percentage) {
        for(char[] subArray : array)
            for(int i = 0; i < subArray.length; i++)
                subArray[i] = Math.random() < percentage ? '#' : '.';
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
    
    private static void printArray(char[][] array) {
        for(char[] cell : array) {
            for(char character : cell)
                System.out.print(character);
            System.out.println();
        }
    }
}