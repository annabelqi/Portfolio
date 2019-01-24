/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package portfolio;

import java.io.IOException;
import java.net.MalformedURLException;
import static portfolio.Portfolio.getJSONString;

/**
 *
 * @author Annabel
 */
public class Investment {

    String ticker;
    String type;
    double value;
    double growth; //growth or decrease
    boolean own; 
    String risk;
    
    public Investment (String tckr, String t, double v, double g, boolean o, String r){
        this.ticker = tckr;
        this.type = t;
        this.value = v;
        this.growth = g;
        this.own = o;
        this.risk = r; 
        
    }
    
    public void buy (){
        this.own = true; 
    }
    
    public  void sell (){
        this.own = false; 
    }
    
    public void Display (){
        System.out.println(this.ticker+" is currently selling at " + this.value + " and has a change percentage of "+ this.growth + " with a  " + this.risk + " risk.");
    }
     

}
