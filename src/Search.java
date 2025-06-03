import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Search {
     /**
     * Finds the location of the nearest reachable cheese from the rat's position.
     * Returns a 2-element int array: [row, col] of the closest 'c'. If there are multiple
     * cheeses that are tied for the same shortest distance to reach, return
     * any one of them.
     * 
     * 'R' - the rat's starting position (exactly one)
     * 'o' - open space the rat can walk on
     * 'w' - wall the rat cannot pass through
     * 'c' - cheese that the rat wants to reach
     * 
     * If no rat is found, throws EscapedRatException.
     * If more than one rat is found, throws CrowdedMazeException.
     * If no cheese is reachable, throws HungryRatException
     *
     * oooocwco
     * woowwcwo
     * ooooRwoo
     * oowwwooo
     * oooocooo
     *
     * The method will return [0,4] as the nearest cheese.
     *
     * @param maze 2D char array representing the maze
     * @return int[] location of the closest cheese in row, column format
     * @throws EscapedRatException if there is no rat in the maze
     * @throws CrowdedMazeException if there is more than one rat in the maze
     * @throws HungryRatException if there is no reachable cheese
     */
    public static int[] nearestCheese(char[][] maze) throws EscapedRatException, CrowdedMazeException, HungryRatException 
    {
        boolean[][] visited = new boolean[maze.length][maze[0].length];
        int[] start = locateRat(maze);

        //Queue
        Queue<int[]> queue = new LinkedList<>();

        //add
        queue.add(start);

        //loop
        while(!queue.isEmpty())
        {
            int[] current = queue.poll();
            int curR = current[0];
            int curC = current[1];

            if(visited[curR][curC])
            {
                continue;
            }
            visited[curR][curC] = true;

            if(maze[curR][curC] == 'c')
            {
                return current;
            }

            queue.addAll(getNeighbors(maze, current));
        }

        throw new HungryRatException();

        /*
            empty q
            q.add(start)
            while q is not empty
                current = q.pop
                if visited: continue
                    mark visited
                if current is cheese: return current
                    q.add(neighbors) add all neighbors to q

            hungryRatException
        */ 

    }



    public static List<int[]> getNeighbors(char[][] maze, int[] current)
    {
        int curR = current[0];
        int curC = current[1];

        int[][] directions = {
            {-1, 0}, //up
            {1, 0}, //down
            {0, -1}, //left
            {0, 1}, //right
        };

        List<int[]> possibleMoves = new ArrayList<>();

        for(int[] direction : directions)
        {
            int changeR = direction[0];
            int changeC = direction[1];

            int newR = curR + changeR;
            int newC = curC + changeC;

            if(newR >= 0 && newR < maze.length && newC >= 0 && newC < maze[newR].length && maze[newR][newC] != 'w')
            {
                int[] validMove = new int[]{newR,newC};
                possibleMoves.add(validMove);
            }
        }

        return possibleMoves;
    }

    public static int[] locateRat(char[][] maze) throws EscapedRatException, CrowdedMazeException
    {   
        int[] location = null;

        //Searching
        for(int r = 0; r < maze.length; r++)
        {
            for(int c = 0; c < maze[r].length; c++)
            {
                if(maze[r][c] == 'R')
                {
                    if(location != null)
                    {
                        throw new CrowdedMazeException();
                    }
                    location = new int[]{r,c};
                } 
            }
        }


        if(location == null)
        {
            //Didnt Find
            throw new EscapedRatException();
        }
        return location;
    }
}