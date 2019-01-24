/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package portfolio;

/**
 *
 * @author Annabel
 */
public class Date {
    //FIELDS
    int year;
    int month;
    int day;
 
    
    public Date(int y, int m, int d){
        this.year = y;
        this.month = m;
        this.day = d; 
        
    }
    
    public String dateToString (){ //converts the date to a string (need to keep the leading 0s to use them in other methods)
        if (this.month<10 && this.day<10){
             return (this.year+"-"+("0"+ Integer.toString(this.month))+"-"+("0"+ Integer.toString(this.day))).toString();//special case for months and days such as 01, 02, 03, etc
        }
        
        else if (this.month<10){
            return (this.year+"-"+("0"+ Integer.toString(this.month))+"-"+this.day).toString();
        }
        
        else if (this.day <10){
            return (this.year+"-"+this.month+"-"+("0"+ Integer.toString(this.day))).toString();
        }
        
        
        else{
            return ((this.year+"-"+this.month+"-"+this.day).toString());
        }
    }
}

