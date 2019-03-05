/* 
 * University of Central Florida
 * CAP4630 - Spring 2019
 * Author(s): Matthew Saucedo and Daniel Canas
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

public class PacSimMinimax implements PacAction
{
	int depth, initialGoodies = 96;
	PacCell[][] chosenMove;	
	
	public PacSimMinimax( int depth, String fname, int te, int gran, int max )
	{
		// must store depth as it is not passed to PacSim
		// as each layer of depth has 3 levels (min, max, max) we must mult by 3
		this.depth = depth * 3;									
		
		PacSim sim = new PacSim( fname, te, gran, max );
		sim.init( this );
	}
	
	public PacCell getBlinkyLocation( PacCell[][] grid )
	{
		List<Point> ghosts = PacUtils.findGhosts( grid );
		
		// store first ghost location
		int x1 = ghosts.get(0).x;
		int y1 = ghosts.get(0).y;
		
		// store second ghost location
		int x2 = ghosts.get(1).x;
		int y2 = ghosts.get(1).y;
		
		if( grid[x1][y1] instanceof BlinkyCell )
		{
			return grid[x1][y1];
		}
		else
		{
			return grid[x2][y2];
		}
	}
	
	public PacCell getInkyLocation( PacCell[][] grid )
	{
		List<Point> ghosts = PacUtils.findGhosts( grid );
		
		// store first ghost location
		int x1 = ghosts.get(0).x;
		int y1 = ghosts.get(0).y;
		
		// store second ghost location
		int x2 = ghosts.get(1).x;
		int y2 = ghosts.get(1).y;
		
		if( grid[x1][y1] instanceof InkyCell )
		{
			return grid[x1][y1];
		}
		else
		{
			return grid[x2][y2];
		}
	}
	
	// returns list of grid with possible moves of Pacman
	public List<PacCell[][]> generatePossibleMovesPac( PacCell[][] grid )
	{
		List<PacCell[][]> possibleMoves = new ArrayList<>();
		Point next;
		
		// store location of Pacman on this grid
		PacmanCell pc = PacUtils.findPacman( grid );
		int pcX = pc.getX();
		int pcY = pc.getY();
		
		// check cell above PacMan
		if( !(grid[pcX][pcY+1] instanceof WallCell) && !(grid[pcX][pcY+1] instanceof HouseCell) && !(grid[pcX][pcY+1] instanceof GhostCell) )
		{
			next = new Point( pcX, pcY+1 );
			possibleMoves.add( PacUtils.movePacman( pc.getLoc(), next, grid ) );
		}
		
		// check cell below PacMan
		if( !(grid[pcX][pcY-1] instanceof WallCell) && !(grid[pcX][pcY-1] instanceof HouseCell) && !(grid[pcX][pcY-1] instanceof GhostCell) )
		{
			next = new Point( pcX, pcY-1 );
			possibleMoves.add( PacUtils.movePacman( pc.getLoc(), next, grid ) );
		}
		
		// check cell to the right of PacMan
		if( !(grid[pcX+1][pcY] instanceof WallCell) && !(grid[pcX+1][pcY] instanceof HouseCell) && !(grid[pcX+1][pcY] instanceof GhostCell) )
		{
			next = new Point( pcX+1, pcY );
			possibleMoves.add( PacUtils.movePacman( pc.getLoc(), next, grid ) );
		}
		
		// check cell to the left of PacMan
		if( !(grid[pcX-1][pcY] instanceof WallCell) && !(grid[pcX-1][pcY] instanceof HouseCell) && !(grid[pcX-1][pcY] instanceof GhostCell) )
		{
			next = new Point( pcX-1, pcY );
			possibleMoves.add( PacUtils.movePacman( pc.getLoc(), next, grid ) );
		}
		
		return possibleMoves;
	}
	
	// returns list of grid with possible moves of Blinky
	public List<PacCell[][]> generatePossibleMovesBlinky( PacCell[][] grid )
	{
		List<PacCell[][]> possibleMoves = new ArrayList<>();
		Point next;
		
		// store location of Blinky on this grid
		PacCell blinky = getBlinkyLocation( grid );
		int x = blinky.getX();
		int y = blinky.getY();
		
		// check cell above Blinky
		if( !(grid[x][y+1] instanceof WallCell) )
		{
			next = new Point( x, y+1 );
			possibleMoves.add( PacUtils.moveGhost( blinky.getLoc(), next, grid ) );
		}
		
		// check cell below Blinky
		if( !(grid[x][y-1] instanceof WallCell) )
		{
			next = new Point( x, y-1 );
			possibleMoves.add( PacUtils.moveGhost( blinky.getLoc(), next, grid ) );
		}
		
		// check cell to the right of Blinky
		if( !(grid[x+1][y] instanceof WallCell) )
		{
			next = new Point( x+1, y );
			possibleMoves.add( PacUtils.moveGhost( blinky.getLoc(), next, grid ) );
		}
		
		// check cell to the left of Blinky
		if( !(grid[x-1][y] instanceof WallCell) )
		{
			next = new Point( x-1, y );
			possibleMoves.add( PacUtils.moveGhost( blinky.getLoc(), next, grid ) );
		}
		
		return possibleMoves;
	}
	
	// returns list of grid with possible moves of Inky
	public List<PacCell[][]> generatePossibleMovesInky( PacCell[][] grid )
	{
		List<PacCell[][]> possibleMoves = new ArrayList<>();
		Point next;
		
		// store location of Inky on this grid
		PacCell inky = getInkyLocation( grid );
		int x = inky.getX();
		int y = inky.getY();
		
		// check cell above Inky
		if( !(grid[x][y+1] instanceof WallCell) )
		{
			next = new Point( x, y+1 );
			possibleMoves.add( PacUtils.moveGhost( inky.getLoc(), next, grid ) );
		}
		
		// check cell below Inky
		if( !(grid[x][y-1] instanceof WallCell) )
		{
			next = new Point( x, y-1 );
			possibleMoves.add( PacUtils.moveGhost( inky.getLoc(), next, grid ) );
		}
		
		// check cell to the right of Inky
		if( !(grid[x+1][y] instanceof WallCell) )
		{
			next = new Point( x+1, y );
			possibleMoves.add( PacUtils.moveGhost( inky.getLoc(), next, grid ) );
		}
		
		// check cell to the left of Inky
		if( !(grid[x-1][y] instanceof WallCell) )
		{
			next = new Point( x-1, y );
			possibleMoves.add( PacUtils.moveGhost( inky.getLoc(), next, grid ) );
		}
		
		return possibleMoves;
	}
	
	public PacFace generateMove( PacCell[][] grid )
	{
		// call minimax to generate chosenMove
		miniMax( grid, 0 );
		
		// store pacman location in chosen move and current state
		PacmanCell pcChosen = PacUtils.findPacman( this.chosenMove );
		PacmanCell pcCurrent = PacUtils.findPacman( grid );
		
		// return direction Pacman must take to reach state in chosenMove
		return PacUtils.direction( pcCurrent.getLoc(), pcChosen.getLoc());
	}
	
	public int miniMax( PacCell[][] grid, int currentDepth )
	{
		// make sure Pac-Man is in this game
		PacmanCell pc = PacUtils.findPacman( grid );
		boolean pacDead = false;
		if( pc == null )
		{
			pacDead = true;
		}
		
		// terminal node
		if( currentDepth == this.depth || pacDead || !PacUtils.foodRemains( grid ) )
		{
			return evalFunction( grid );
		}
		// MAX's turn to move
		else if( currentDepth % 3 == 0 )
		{
			return maxValue( grid, currentDepth );
		}
		// Blinky's turn to move
		else if( currentDepth == 1 || currentDepth == 4 || currentDepth == 7 || currentDepth == 10 )
		{
			return minValueBlinky( grid, currentDepth );
		}
		// Inky's turn to move
		else
		{
			return minValueInky( grid, currentDepth );
		}
	}
	
	// returns largest game-state value selected by ideal rational agent
	public int maxValue( PacCell[][] grid, int currentDepth )
	{
		int v = Integer.MIN_VALUE;
		List<PacCell[][]> possibleMoves = generatePossibleMovesPac( grid );
		int [] moveValue = new int [possibleMoves.size()];
		
		// for each possible move, call minimax and update top value (v) if applicable
		for( int i = 0; i < possibleMoves.size(); i++ )
		{
			v = Math.max( v, miniMax( possibleMoves.get(i), currentDepth + 1 ) );
			
			moveValue[i] = v;
		}
		
		// update chosenMove as the move which returned the largest v value 
		// (AKA first move to return final v value, as the best value propagated forward)
		if( currentDepth == 0 )
		{
			for( int i = 0; i < possibleMoves.size(); i++ )
			{
				if( moveValue[i] == v )
				{
					this.chosenMove = possibleMoves.get(i);
					break;
				}
			}
		}
		
		return v;
	}
	
	// returns smallest game-state value selected by ideal rational agent
	public int minValueBlinky( PacCell[][] grid, int currentDepth )
	{
		int v = Integer.MAX_VALUE;
		
		// for each possible move, call minimax and update low value (v) if applicable
		List<PacCell[][]> possibleMoves = generatePossibleMovesBlinky( grid );
		for( PacCell[][] move : possibleMoves )
		{
			v = Math.min( v, miniMax( move, currentDepth + 1 ) );
		}
		
		return v;
	}
	
	// returns smallest game-state value selected by ideal rational agent
	public int minValueInky( PacCell[][] grid, int currentDepth )
	{
		int v = Integer.MAX_VALUE;
		
		// for each possible move, call minimax and update low value (v) if applicable
		List<PacCell[][]> possibleMoves = generatePossibleMovesInky( grid );
		for( PacCell[][] move : possibleMoves )
		{
			v = Math.min( v, miniMax( move, currentDepth + 1 ) );
		}
		
		return v;
	}
		
	/*
	Our evalFunction is used to evaluate different board states and assign a weighted rank to them accordingly. First the
    function checks if pacman is still in the game and if there is food left. If there is no food left we have won the game
    and we can return that max rank possible. If pacman is no longer in the game we return the smallest possible rank. Next
    our function takes into account the nearest food pellet to pacman and the nearest ghost. We also find the combined
    distance between pacman and every pellet left in the game. If pacman is located less than 2 spaces away from a ghost,
    we return the smallest possible rank. This is implemented to dissuade pacman from making moves that would leave him 2
    spaces or less from a ghost. Next we subtract 3/4 of the total distance calculated from pacman to every pellet remaining.
    This is used to make seek out pellets more aggressively in the beginning of the game. Next we add 10 to our rank if our
    nearest food pellet is closer than the nearest ghost. This is to reward states that result in pellets being eaten while
    keeping pacman safe. Finally we heavily reward states that eat pellets aggressively by adding a *5 multiplier. These
    states are highly preferred by our function and get our win rate to about an average of 40%.
	*/
	
	// used to assign value to any given game-state 
	public int evalFunction( PacCell[][] grid )
    {
        int rank = 0;
        int distanceToNearestFood;
        int distanceToNearestGhost;
        int totalDistance = 0;
        int foodLeft = PacUtils.numFood(grid);
        List<Point> foodArray;
        PacmanCell pc = PacUtils.findPacman( grid );
        Point pacLoc;
        Point nearestFood;
        GhostCell nearestGhost;

        // make sure Pac-Man is in this game
        if( pc == null )
        {
            return Integer.MIN_VALUE;
        }

        // if there is no food left we win the game
        if (foodLeft == 0)
        {
            return Integer.MAX_VALUE;
        }

        // get PacMan's current location
        pacLoc = pc.getLoc();

        // find the closest ghost, closest pellet, and a list of all the remaining food pellets available
        nearestFood = PacUtils.nearestGoody(pacLoc, grid);
        nearestGhost = PacUtils.nearestGhost(pacLoc, grid);
        foodArray = PacUtils.findFood(grid);

        // compute the distance between pacman and nearest food and ghost items
        distanceToNearestFood = PacUtils.manhattanDistance(nearestFood, pacLoc);
        distanceToNearestGhost = PacUtils.manhattanDistance(nearestGhost.getLoc(), pacLoc);

        // compute the total distance between pacman and all remaining food pellets
        for (Point pellet : foodArray)
        {
            totalDistance = totalDistance + PacUtils.manhattanDistance(pacLoc, pellet);
        }
        
        // we subtractt the initial amount of food pellets minus the food currently left on the board
        rank = 50 * (initialGoodies - foodLeft);

        // if the distance to the nearest ghost is less than 2 we return 0
        if(distanceToNearestGhost < 2) 
        {
            return Integer.MIN_VALUE;
        }

        rank -= 0.75 * totalDistance;

        if(distanceToNearestFood < distanceToNearestGhost)
        {
            rank += 10;
        }

        if(distanceToNearestFood < 2)
        {
            rank += 10 * 5;
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
		// initialize state of pacman
		PacCell[][] grid = (PacCell[][]) state;
		PacFace newFace = null;
		
		// move pacman
		newFace = generateMove( grid );
		
		// return move
		return newFace;
	}
}
