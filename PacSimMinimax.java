/* 
 * University of Central Florida
 * CAP4630
 * Authors: Matthew Saucedo and Daniel Canas
 *
 */

import java.awt.Point;
import java.util.*;
import pacsim.BFSPath;
import pacsim.PacAction;
import pacsim.PacCell;
import pacsim.PacFace;
import pacsim.PacSim;
import pacsim.PacUtils;
import pacsim.PacmanCell;
import pacsim.BlinkyCell;
import pacsim.InkyCell;
import pacsim.WallCell;
import pacsim.HouseCell;
import pacsim.GhostCell;

// class to keep track of each simulation run
class pacRun 
{
	public int wins, losses, moves;
	
	pacRun()
	{
		this.wins = 0;
		this.losses = 0;
		this.moves = 0;
	}
	
	void win()
	{
		this.wins = 1;
	}
	
	void lose()
	{
		this.losses = 1;
	}
	
	void move()
	{
		this.moves++;
	}
	
	// formatting for displaying preliminary run results
	void printResults()
	{
		System.out.println("\t" + this.wins + " wins,\t" + this.losses + " losses,\t" + this.moves + " avg moves");
	}
}

/* Need to implement possibleMoves of the looping for( PacCell[][] move : possibleMoves )*/
public class PacSimMinimax implements PacAction
{
	int depth, currentDepth, initialGoodies = 96;
	PacmanCell pc;
	PacCell blinky;
	PacCell inky;
	
	// updates class variables holding locations of ghosts
	public void getGhostLocations( PacCell[][] grid )
	{
		// store first ghost location
		int x1 = PacUtils.findGhosts( grid ).get(0).x;
		int y1 = PacUtils.findGhosts( grid ).get(0).y;
		
		// store second ghost location
		int x2 = PacUtils.findGhosts( grid ).get(1).x;
		int y2 = PacUtils.findGhosts( grid ).get(1).y;
		
		if( grid[x1][y1] instanceof BlinkyCell )
		{
			this.blinky = grid[x1][y1];
			this.inky = grid[x2][y2];
		}
		else
		{
			this.blinky = grid[x2][y2];
			this.inky = grid[x1][y1];
		}
	}
	
	public PacSimMinimax( int depth, String fname, int te, int gran, int max )
	{
		// must store depth as it is not passed to PacSim
		this.depth = depth;
		
		PacSim sim = new PacSim( fname, te, gran, max );
		sim.init( this );
	}
	
	// returns list of grid with possible moves of Pacman
	public List<PacCell[][]> generatePossibleMovesPac( PacCell[][] grid )
	{
		List<PacCell[][]> possibleMoves = new ArrayList<>();
		
		int pcX = this.pc.getX();
		int pcY = this.pc.getY();
		Point next;
		
		// check cell above PacMan
		if( !(grid[pcX][pcY+1] instanceof WallCell) && !(grid[pcX][pcY+1] instanceof HouseCell) && !(grid[pcX][pcY+1] instanceof GhostCell) )
		{
			next = new Point( pcX, pcY+1 );
			possibleMoves.add( PacUtils.movePacman( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell below PacMan
		if( !(grid[pcX][pcY-1] instanceof WallCell) && !(grid[pcX][pcY-1] instanceof HouseCell) && !(grid[pcX][pcY-1] instanceof GhostCell) )
		{
			next = new Point( pcX, pcY-1 );
			possibleMoves.add( PacUtils.movePacman( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell to the right of PacMan
		if( !(grid[pcX+1][pcY] instanceof WallCell) && !(grid[pcX+1][pcY] instanceof HouseCell) && !(grid[pcX+1][pcY] instanceof GhostCell) )
		{
			next = new Point( pcX+1, pcY );
			possibleMoves.add( PacUtils.movePacman( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell to the left of PacMan
		if( !(grid[pcX-1][pcY] instanceof WallCell) && !(grid[pcX-1][pcY] instanceof HouseCell) && !(grid[pcX-1][pcY] instanceof GhostCell) )
		{
			next = new Point( pcX-1, pcY );
			possibleMoves.add( PacUtils.movePacman( this.pc.getLoc(), next, grid ) );
		}
		
		return possibleMoves;
	}
	
	// returns list of grid with possible moves of Blinky
	public List<PacCell[][]> generatePossibleMovesBlinky( PacCell[][] grid )
	{
		List<PacCell[][]> possibleMoves = new ArrayList<>();
		
		int x = this.blinky.getX();
		int y = this.blinky.getY();
		Point next;
		
		// check cell above Blinky
		if( !(grid[x][y+1] instanceof WallCell) && !(grid[x][y+1] instanceof HouseCell) )
		{
			next = new Point( x, y+1 );
			possibleMoves.add( PacUtils.moveGhost( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell below Blinky
		if( !(grid[x][y-1] instanceof WallCell) && !(grid[x][y-1] instanceof HouseCell) )
		{
			next = new Point( x, y-1 );
			possibleMoves.add( PacUtils.moveGhost( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell to the right of Blinky
		if( !(grid[x+1][y] instanceof WallCell) && !(grid[x+1][y] instanceof HouseCell) )
		{
			next = new Point( x+1, y );
			possibleMoves.add( PacUtils.moveGhost( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell to the left of Blinky
		if( !(grid[x-1][y] instanceof WallCell) && !(grid[x-1][y] instanceof HouseCell) )
		{
			next = new Point( x-1, y );
			possibleMoves.add( PacUtils.moveGhost( this.pc.getLoc(), next, grid ) );
		}
		
		return possibleMoves;
	}
	
	// returns list of grid with possible moves of Inky
	public List<PacCell[][]> generatePossibleMovesInky( PacCell[][] grid )
	{
		List<PacCell[][]> possibleMoves = new ArrayList<>();
		
		int x = this.inky.getX();
		int y = this.inky.getY();
		Point next;
		
		// check cell above Inky
		if( !(grid[x][y+1] instanceof WallCell) && !(grid[x][y+1] instanceof HouseCell) )
		{
			next = new Point( x, y+1 );
			possibleMoves.add( PacUtils.moveGhost( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell below Inky
		if( !(grid[x][y-1] instanceof WallCell) && !(grid[x][y-1] instanceof HouseCell) )
		{
			next = new Point( x, y-1 );
			possibleMoves.add( PacUtils.moveGhost( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell to the right of Inky
		if( !(grid[x+1][y] instanceof WallCell) && !(grid[x+1][y] instanceof HouseCell) )
		{
			next = new Point( x+1, y );
			possibleMoves.add( PacUtils.moveGhost( this.pc.getLoc(), next, grid ) );
		}
		
		// check cell to the left of Inky
		if( !(grid[x-1][y] instanceof WallCell) && !(grid[x-1][y] instanceof HouseCell) )
		{
			next = new Point( x-1, y );
			possibleMoves.add( PacUtils.moveGhost( this.pc.getLoc(), next, grid ) );
		}
		
		return possibleMoves;
	}
	
	public PacFace generateMove( PacCell[][] grid )
	{
		this.currentDepth = 0;
		
		//return miniMax( grid, Integer.MIN_VALUE, Integer.MAX_VALUE );
		return null;
	}
	
	public int miniMax( PacCell[][] grid, int alpha, int beta )
	{
		// terminal node
		if( currentDepth == this.depth )
		{
			return evalFunction( grid );
		}
		// MAX's turn to move
		else if( currentDepth % 2 == 1 )
		{
			return maxValue( grid, alpha, beta );
		}
		// Blinky's turn to move
		else if( true )
		{
			return minValueBlinky( grid, alpha, beta );
		}
		// Inky's turn to move
		else
		{
			return minValueInky( grid, alpha, beta );
		}
	}
	
	// returns largest game-state value selected by ideal rational agent
	public int maxValue( PacCell[][] grid, int alpha, int beta )
	{
		int v = Integer.MIN_VALUE;
		
		List<PacCell[][]> possibleMoves = generatePossibleMovesPac( grid );
		
		for( PacCell[][] move : possibleMoves )
		{
			v = Math.max( v, miniMax( move, alpha, beta ) );
			
			if( v >= beta )
			{
				return v;
			}
			
			alpha = Math.max( alpha, v );
		}
		
		return v;
	}
	
	// returns smallest game-state value selected by ideal rational agent
	public int minValueBlinky( PacCell[][] grid, int alpha, int beta )
	{
		int v = Integer.MAX_VALUE;
		
		List<PacCell[][]> possibleMoves = generatePossibleMovesBlinky( grid );
		
		for( PacCell[][] move : possibleMoves )
		{
			v = Math.min( v, miniMax( move, alpha, beta ) );
			
			if( v <= alpha )
			{
				return v;
			}
			
			beta = Math.min( beta, v );
		}
		
		return v;
	}
	
	// returns smallest game-state value selected by ideal rational agent
	public int minValueInky( PacCell[][] grid, int alpha, int beta )
	{
		int v = Integer.MAX_VALUE;
		
		List<PacCell[][]> possibleMoves = generatePossibleMovesInky( grid );
		
		for( PacCell[][] move : possibleMoves )
		{
			v = Math.min( v, miniMax( move, alpha, beta ) );
			
			if( v <= alpha )
			{
				return v;
			}
			
			beta = Math.min( beta, v );
		}
		
		return v;
	}
	
	// used to assign value to any given game-state 
	public int evalFunction( PacCell[][] grid )
    {
        int rank = 0;
        int distanceToNearestFood;
        int distanceToNearestGhost;
        int totalDistance = 0;
        List<Point> foodArray;
        PacmanCell pc = PacUtils.findPacman( grid );
        Point pacLoc;
        Point nearestFood;
        GhostCell nearestGhost;

        // Get PacMan's current location
        pacLoc = pc.getLoc();

        // Find the closest ghost, closest pellet, and a list of all the remaining food pellets available
        nearestFood = PacUtils.nearestGoody(pacLoc, grid);
        nearestGhost = PacUtils.nearestGhost(pacLoc, grid);
        foodArray = PacUtils.findFood(grid);

        // Compute the distance between pacman and nearest food and ghost items
        distanceToNearestFood = PacUtils.manhattanDistance(nearestFood, pacLoc);
        distanceToNearestGhost = PacUtils.manhattanDistance(nearestGhost.getLoc(), pacLoc);

        // Compute the total distance between pacman and all remaining food pellets
        for (Point pellet : foodArray)
        {
            totalDistance = totalDistance + PacUtils.manhattanDistance(pacLoc, pellet);
        }

        // If the distance to the nearest ghost is less than 2 we return 0
        if(distanceToNearestGhost < 2) 
        {
            return Integer.MIN_VALUE;
        }
        else 
        {
            rank = rank + 10;
        }

        rank = rank - (10 * distanceToNearestFood);
        rank = rank - (10 * totalDistance);

        if(distanceToNearestFood < distanceToNearestGhost)
        {
            rank = rank + 10;
        }

        if(distanceToNearestFood < 2)
        {
            rank = rank + 10;
        }

        return rank;
    }
	
	public static void main( String[] args )
	{
		String fname = args[ 0 ];
		int depth = Integer.parseInt( args[ 1 ] );
		
		// number of simulation epochs
		int te = 0;
		
		// granularity for training epoch statistics
		int gr = 0;
		
		// max number of moves allowed
		int ml = 0;
		
		if( args.length == 5 )
		{
			te = Integer.parseInt( args[ 2 ] );
			gr = Integer.parseInt( args[ 3 ] );
			ml = Integer.parseInt( args[ 4 ] );
		}
		
		new PacSimMinimax( depth, fname, te, gr, ml );
		
		System.out.println("\nAdversarial Search using Minimax by Matthew Saucedo and Daniel Canas");
		System.out.println("\n\tGame board\t: " + fname);
		System.out.println("\tSearch depth\t: " + depth + "\n");
		
		if( te > 0 ) 
		{
			System.out.println("\tPreliminary runs : " + te
			+ "\n\tGranularity\t: " + gr
			+ "\n\tMax move limit\t: " + ml
			+ "\n\nPreliminary run results :\n");
		}
	}
	
	@Override
	public void init() {}
	
	@Override
	public PacFace action( Object state )
	{
		PacCell[][] grid = (PacCell[][]) state;
		PacFace newFace = null;
		
		// store location of PacMan and ghosts
		this.pc = PacUtils.findPacman( grid );
		getGhostLocations( grid );
		
		//TODO
		//DEBUG
		System.out.println("Blinky Loc: " + this.blinky.getLoc() + "\nInk Loc: " + this.inky.getLoc() );
		
		//return generateMove( grid );
		
		return newFace;
	}
}