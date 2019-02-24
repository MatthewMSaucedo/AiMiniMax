• MiniMax AI agent
• Designed with use of a Pac-Man video game library
• Helps Pac-Man to find all food pellets on given course and avoid ghosts using MiniMax method

To run this Pac-Man MiniMax agent
1) Open a command prompt/terminal 
2) Navigate to the folder that contains this repository  
3) type the following:
	java -jar PacSimMinimax.jar game-small-new DEPTH
			OR	
	java -jar PacSimMinimax.jar game-small-new DEPTH EPOCHS GRANULARITY MAX_MOVES
	
	where, 
	DEPTH = integer indicating max depth to use for MiniMax
	EPOCHS = integer indicating number of simulation epochs
	GRANULARITY = integer indicating granularity for training epoch statistics
	MAX_MOVES = integer indicating max number of moves allowed by PacMan

	
	******** COMPILE AND RUNWITHOUT JAR ********
	javac -cp lib/PacSimLib.jar PacSimMinimax.java
	
	MAC: java -cp .:lib/PacSimLib.jar PacSimMinimax game-small-new DEPTH
	PC: java -cp .;lib/PacSimLib.jar PacSimMinimax game-small-new DEPTH