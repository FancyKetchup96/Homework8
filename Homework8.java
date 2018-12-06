import java.io.*;
import java.util.*;

class PegPuzzle
{
	public static void main(String[] args)
	{
		Puzzle(0);
	}
	
	public static Gameboard move(Gameboard gb, Move m)
	{
		if(gb.cells[m.from] == 1 && gb.cells[m.over] == 1 && gb.cells[m.to] == 0)
		{
			gb.cells[m.from] = 0;
			gb.cells[m.over] = 0;
			gb.cells[m.to] = 1;
			gb.count = gb.count - 1;
			gb.changed = true;
			return gb;
		}
		else
		{
			gb.changed = false;
			return gb;
		}
	}
	
	public static void solve(Gameboard gb, Moves ms)
	{
		for(int i = 0; i < ms.count; i++)
		{
			Gameboard gbnew = new Gameboard(gb);
			Move temp = ms.getMove(i);
			gbnew = move(gbnew, temp);
			if(gbnew.changed)
			{
				gbnew.replay[gbnew.replayMoves] = temp;
				gbnew.replayMoves = gbnew.replayMoves + 1;
				if(gbnew.count < 2)
				{
					printSolution(gbnew);
				}
				solve(gbnew, ms);
			}
		}
	}
	
	public static void printSolution(Gameboard gb)
	{
		gb.resetGameboard();
		gb.print();
		for(int i = 0; i < gb.replayMoves; i++)
		{
			gb = move(gb, gb.replay[i]);
			gb.print();
		}
		Puzzle(gb.start + 1);
	}
	
	public static void Puzzle(int start)
	{
		if(start > 5)
		{
			System.exit(0);
		}
		System.out.println("=== " + start + " ===");
		Moves ms = new Moves();
		Gameboard gb = new Gameboard(start);
		solve(gb, ms);
	}
}
class Gameboard
{
	public int cells[] = new int[15];
	public int count;
	public boolean changed = false;
	public Move replay[] = new Move[1000];
	public int replayMoves = 0;
	public int start;
	public Gameboard(int s)
	{
		for(int i = 0; i < 15; i++)
		{
			cells[i] = 1;
		}
		cells[s] = 0;
		count = 14;
		start = s;
	}
	
	public Gameboard(Gameboard gb)
	{
		for(int i = 0; i < 15; i++)
		{
			cells[i] = gb.cells[i];
		}
		for(int i = 0; i < gb.replayMoves; i++)
		{
			replay[i] = gb.replay[i];
		}
		count = gb.count;
		replayMoves = gb.replayMoves;
		start = gb.start;
		changed = false;
	}
	
	public void resetGameboard()
	{
		for(int i = 0; i < 15; i++)
		{
			cells[i] = 1;
		}
		cells[start] = 0;
	}
	
	public void print()
	{
		System.out.println("    " + cells[0]);
		System.out.println("   " + cells[1] + " " + cells[2]);
		System.out.println("  " + cells[3] + " " + cells[4] + " " + cells[5]);
		System.out.println(" " + cells[6] + " " + cells[7] + " " + cells[8] + " " + cells[9]);
		System.out.println(cells[10] + " " + cells[11] + " " + cells[12] + " " + cells[13] + " " + cells[14]);
		System.out.print("\n");
	}
}

class Move
{
	public int from;
	public int over;
	public int to;
	
	public Move(int a, int b, int c)
	{
		from = a;
		over = b;
		to = c;
	}

	public void print()
	{
		System.out.println("(" + from + ", " + over + ", " + to + ")");
	}
}

class Moves
{
	public Move moves[] = new Move[36];
	public int count;
	
	public Moves()
	{
		moves[0] = new Move(0,1,3);
		moves[1] = new Move(0,2,5);
		moves[2] = new Move(1,3,6);
		moves[3] = new Move(1,4,8);
		moves[4] = new Move(2,4,7);
		moves[5] = new Move(2,5,9);
		moves[6] = new Move(3,6,10);
		moves[7] = new Move(3,7,12);
		moves[8] = new Move(4,7,11);
		moves[9] = new Move(4,8,13);
		moves[10] = new Move(5,8,12);
		moves[11] = new Move(5,9,14);
		moves[12] = new Move(3,4,5);
		moves[13] = new Move(6,7,8);
		moves[14] = new Move(7,8,9);
		moves[15] = new Move(10,11,12);
		moves[16] = new Move(11,12,13);
		moves[17] = new Move(12,13,14);
		
		int j = 18;
		for(int i = 0; i < 18; i++)
		{
			moves[j] = new Move(moves[i].to, moves[i].over, moves[i].from);
			j++;
		}
		
		count = 36;
	}
	
	public Move getMove(int i)
	{
		return moves[i];
	}
}