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
	
	pacRun( void )
	{
		this.wins = 0;
		this.losses = 0;
		this.moves = 0;
	}
	
	win( void )
	{
		this.wins = 1;
	}
	
	lose( void )
	{
		this.losses = 1;
	}
	
	move( void )
	{
		this.moves++;
	}
	
	// formatting for displaying preliminary run results
	printResults( void )
	{
		System.out.println("\t" + this.wins + " wins,\t" + this.losses + " losses,\t" + this.moves + " avg moves");
	}
}

public class PacSimMinimax implements PacAction
{
	int depth;
	
	public PacSimMinimax( int depth, String fname, int te, int gran, int max )
	{
		// must store depth as it is not passed to PacSim
		this.depth = depth
		
		PacSim sim = new PacSim( fname, te, gran, max );
		sim.init( this );
	}
	
	public miniMax( void )
	{
		return value();
	}
	
	public value()
	{
		if( /*state == terminal*/ )
		{
			return /*the state's utility*/
		}
		else if( /*MAX's turn to move*/ )
		{
			return maxValue( /*state*/ )
		}
		else
		{
			return maxValue( /*state*/ )
		}
	}
	
	public maxValue( /*state*/ )
	{
		int v = Integer.MIN_VALUE;
		
		for(/*each successor s' of s*/)
		{
			v = Math.max( v, value( s' ) );
		}
		
		return v;
	}
	
	public minValue( /*state*/ )
	{
		int v = Integer.MAX_VALUE;
		
		for(/*each successor s' of s*/)
		{
			v = Math.min( v, value( s' ) );
		}
		
		return v;
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
		
		System.out.println("\nAdversarial Search using Minimax by Matthew Saucedo and Daniel Canas:");
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
	
	@Overridepublic void init( void ) {}
	
	@Override
	public PacFace action( Object state )
	{
		PacCell[][] grid = (PacCel[][]) state;
		PacFace newFace = null;
		
		//TODO
		
		return newFace;
	}
}