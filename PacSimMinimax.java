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

/* Need to implement possibleMoves of the looping for( PacCell[][] move : possibleMoves )*/
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
		PacmanCell pcChosen = PacUtils.findPacman( this.chosenMove );
		PacmanCell pcCurrent = PacUtils.findPacman( grid );
		
		return PacUtils.direction( pcCurrent.getLoc(), pcChosen.getLoc());
		//return PacUtils.randomOpenForPacman( pcCurrent.getLoc(), grid );
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
		for( int i = 0; i < possibleMoves.size(); i++ )
		{
			if( moveValue[i] == v )
			{
				this.chosenMove = possibleMoves.get(i);
				break;
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
	
	// used to assign value to any given game-state 
	public int evalFunction( PacCell[][] grid )
    {
		/*
		score = this.initialGoodies - ( PacUtils.numFood( grid ) + PacUtils.numPower( grid ) );
		return score + distanceToNearestFood + distanceToNearestGhost;
		*/
		int rank = 0;
        int distanceToNearestFood;
        int distanceToNearestGhost;
        int totalDistance = 0;
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
		
		//DEBUG
		PacCell blinky = getBlinkyLocation(grid);
		PacCell inky = getInkyLocation(grid);
		PacmanCell pacman = PacUtils.findPacman(grid);
		
		System.out.println(/*"Blinky Loc: " + blinky.getLoc() + "\nInk Loc: " + inky.getLoc() + */"\nPacman Loc: " + pacman.getLoc() );
		
		// test possible moves that are generated
		List<PacCell[][]> possibleMovesBlinky = generatePossibleMovesBlinky(grid);
		List<PacCell[][]> possibleMovesInky = generatePossibleMovesInky(grid);
		List<PacCell[][]> possibleMovesPac = generatePossibleMovesPac(grid);
		
		// print possible moves PACMAN
		System.out.println("\n\nPOSSIBLE MOVES Pacman");
		for( int i = 0; i < possibleMovesPac.size(); i++ )
		{
			System.out.println("Move " + i + ": " + PacUtils.findPacman( possibleMovesPac.get(i) ).getLoc());
		}

		int retVal = miniMax( grid, 0 );
		System.out.println("\nPacman Chosen next move: " + PacUtils.findPacman( this.chosenMove ).getLoc() );
		System.out.println("\nRETVAL: " + retVal);
		return generateMove( grid );
		
		//return newFace;
	}
}