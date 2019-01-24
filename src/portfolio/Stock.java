/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package portfolio;

/**
 *
 * @author Annabel
 */
public class Stock extends Investment{
    String company; 
    String sector;
    
    public Stock (String t, double v, double g, boolean o, String c, String s){
        super (t, "Stock", v, g, o, "High");
        this.company = c;
        this.sector = s;
}
      
}
