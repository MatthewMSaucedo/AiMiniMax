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
	int depth, currentDepth;
	PacmanCell pc;
	Point ghostA;
	Point ghostB;
	
	public PacSimMinimax( int depth, String fname, int te, int gran, int max )
	{
		// must store depth as it is not passed to PacSim
		this.depth = depth;
		
		PacSim sim = new PacSim( fname, te, gran, max );
		sim.init( this );
	}
	
	public PacFace generateMove( PacCell[][] grid )
	{
		this.currentDepth = 0;
		
		//return miniMax( grid );
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
		// MIN's turn to move
		else
		{
			return minValue( grid, alpha, beta );
		}
	}
	
	// returns largest game-state value selected by ideal rational agent
	public int maxValue( PacCell[][] grid, int alpha, int beta )
	{
		int v = Integer.MIN_VALUE;
		
		// for( PacCell[][] move : possibleMoves )
		// {
			// v = Math.max( v, miniMax( move, alpha, beta ) );
			
			// if( v >= beta )
			// {
				// return v;
			// }
			
			// alpha = Math.max( alpha, v );
		// }
		
		return v;
	}
	
	// returns smallest game-state value selected by ideal rational agent
	public int minValue( PacCell[][] grid, int alpha, int beta )
	{
		int v = Integer.MAX_VALUE;
		
		// for( PacCell[][] move : possibleMoves )
		// {
			// v = Math.min( v, miniMax( move, alpha, beta ) );
			
			// if( v <= alpha )
			// {
				// return v;
			// }
			
			// beta = Math.min( beta, v );
		// }
		
		return v;
	}
	
	// used to assign value to any given game-state 
	public int evalFunction( PacCell[][] grid )
	{
		// returns current score
		return PacUtils.numFood( grid ) + PacUtils.numPower( grid );
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
		System.out.println("\tSearch depth : " + depth + "\n");
		
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
		this.ghostA = PacUtils.findGhosts( grid ).get(0);
		this.ghostB = PacUtils.findGhosts( grid ).get(1);
		
		//TODO
		
		//return generateMove( grid );
		
		return newFace;
	}
	
	/*
	
	* you can calculate current game score by counting how many goodies there are left.
	
	* you need to use moveGhost and movePacman in this assignment.
	
	* you can check if the object is an instanceof WallCell
	
	*/
}