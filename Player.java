
/**
 * Write a description of class Player here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Player 
{
    String name; 
    int number, wins;
        
    public Player (int number, String name, int wins) 
    {
        this.number = number;
        this.name = name;
        this.wins = wins;
    }
    
    public int getNumber() 
    {
        return this.number;
    }
     
    public int setNumber(int number) 
    {
        return this.number = number;
    }
    
    public String getName() 
    {
        return this.name;
    }
     
    public String setName(String name) 
    {
        return this.name = name;
    }
    
    public int getWins() 
    {
        return this.wins;
    }
     
    public int setWins(int wins) 
    {
        return this.wins = wins;
    }

}
