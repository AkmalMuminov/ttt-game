import java.io.Serializable;
/**t j
 * Write a description of class Move here.
 *
 * @author (<3)
 * @version (a version number or a date)
 */
public class Move implements Serializable
{   
    private String move = "";
    private String state = "";
    private String name = "";
    private String result = "";
    public Move(String move)
    {
        this.move = move;
    }

    public String getMove()
    {
        return this.move;
    }

    public String setMove(String move)
    {
        return this.move = move;
    }

    public String getState()
    {
        return this.state;
    }

    public String setState(String state)
    {
        return this.state = state;
    }

    public String getName()
    {
        return this.name;
    }

    public String setName(String name)
    {
        return this.name = name;
    }
    
    public String getResult()
    {
        return this.result;
    }

    public String setResult(String result)
    {
        return this.result = result;
    }
}
