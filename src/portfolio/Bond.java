/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package portfolio;

/**
 *
 * @author Annabel
 */
public class Bond extends Investment{
    String primaryExchange; //primary exchange platform
    
    public Bond (String t, double v, double g, boolean o, String pE){
        super ( t, "Bond", v, g, o, "Low");
        this.primaryExchange = pE;
}
    
  
    
}
