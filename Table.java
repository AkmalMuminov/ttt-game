
/**
 * Write a description of class leaderboard here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
import javax.swing.*;    
import java.util.ArrayList;
import java.util.Collections;
public class Table  {    
    JFrame f=new JFrame();   
    String data[][]={{"","",""}};  
    String column[]={"№","NAME","WINS"};   
    ArrayList<Player> list = new ArrayList<Player>();
    ArrayList<Player> sortedList = new ArrayList<Player>();

    public  Table(){}
    //displays the table along with puting number, name and win values into prospective cells
    public synchronized void display()
    {       
        data = new String[list.size()][3];
        for(int i =0; i < list.size();i++)
        {
            for(int j=0; j<3; j++)
            {
                if(column[j].equals("№"))

                    data[i][j] = Integer.toString(list.get(i).getNumber());
                if (column[j].equals("NAME"))
                    data[i][j] = list.get(i).getName();
                if (column[j].equals("WINS"))
                    data[i][j] = Integer.toString(list.get(i).getWins());
            }
        }

        JTable jt=new JTable(data,column);    
        jt.setBounds(30,40,200,300);          
        JScrollPane sp=new JScrollPane(jt);    
        f.add(sp);          
        f.setSize(300,400);    
        f.setVisible(true);    
    }
    //adds player's wins
    public synchronized void addToList(String name, int wins)
    {      
        for (int i = 0; i < list.size(); i++)
        {
            Player temp = list.get(i);
            if (temp.getName().equals(name))
                temp.setWins(temp.getWins() + wins);
        }      
    }
    //Adds new a player's name to the list
    public synchronized void populateList(String name) 
    {
        Player player = new Player(0, name, 0);
        list.add(player);
        for (int i =0; i < list.size();i++)
        {
            Player temp = list.get(i);            
            temp.setNumber(list.indexOf(temp)+1);
        }
    }
    //sorts player's wins in decreasing order,and returns a sorted list
    synchronized ArrayList<Player> sort ()
    {
        Player temp; 
        int wins, maxIndex;

        for (int i = 0; i < list.size()-1 ; i++)
        {
            maxIndex = i; //index of current player
            //wins = list.get(minElement).getWins();//number of wins of current player

            for(int j=i+1; j < list.size(); j++)
            {
                if (list.get(j).getWins() > list.get(maxIndex).getWins())
                {
                    maxIndex = j;
                }
                temp = list.get(i);
                list.set(i,list.get(maxIndex));
                list.set(maxIndex,temp);
            }
        }
        //resets number position in ascending order
        for (int i = 0; i < list.size(); i++) 
        {
          list.get(i).setNumber(i+1);  
        }

        return list;
    }

    

}  
