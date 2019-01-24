package portfolio;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Annabel
 */
public class Portfolio {
    private static int[] merge(int[] a, int[] b) {
        int[] c = new int[a.length + b.length];
        int i = 0;
        int j = 0;
        int k = 0;
        
	int lenA = a.length, lenB = b.length;

        while ( i < lenA && j < lenB ) {

            if ( a[i] <= b[j] ) {
                c[k] = a[i];
                i++;
            } 
	    
	    else {
                c[k] = b[j];
                j++;
            }

            k++;
        }

        if ( i == lenA ) 
            for (int m = j; m < b.length; m++) {
                c[k] = b[m];
                k++;
            }
         	
	else 
            for (int m = i; m < a.length; m++) {
                c[k] = a[m];
                k++;
            }
        
        return c;
    }

    
    public static int[] mergeSort(int[] array, int start, int end ) {
        if (start == end) { // base case
             int[] arrayWithOneElement = { array[start] };
            return arrayWithOneElement;
        } 
        
        else {
            int middle = (end + start) / 2;

            int[] sortedLeftHalf = mergeSort(array, start, middle);  // recursive call
            int[] sortedRightHalf = mergeSort(array, middle + 1, end); // recursive call

            return merge( sortedLeftHalf, sortedRightHalf );  // merge the two sorted halves
        }
    }

    public static String getJSONString(String t) throws MalformedURLException, IOException {
        URL url = new URL("https://api.iextrading.com/1.0/stock/" + t + "/batch?types=quote,chart&range=3m&last=1");
        Scanner scan = new Scanner(url.openStream());
        String mainJSON = new String();

        while (scan.hasNext()) {
            mainJSON += scan.nextLine();
        }
        scan.close();

        return mainJSON;
    }

    public static double getInvestmentValue(String t, String date) throws MalformedURLException, IOException {
        int dateIndex = getJSONString(t).indexOf(date);
        String smallerRange = getJSONString(t).substring(dateIndex, dateIndex + 170); //reducing the range to the statistics of the specific date
        int openPriceIndex = smallerRange.indexOf("open"); //openPrice is the opening price of the bond for that date
        int endIndex = smallerRange.indexOf("high");
        String value = smallerRange.substring(openPriceIndex + 6, endIndex - 3);
        return Double.parseDouble(value);
    }

    public static double getInvestmentGrowth(String t, String date) throws MalformedURLException, IOException {
        int dateIndex = getJSONString(t).indexOf(date);
        String smallerRange = getJSONString(t).substring(dateIndex, dateIndex + 170); //reducing the range to the statistics of the specific date
        int changeIndex = smallerRange.indexOf("changePercent");
        int endIndex = smallerRange.indexOf("vwap");
        String percentage = smallerRange.substring(changeIndex + 15, endIndex - 2);
        return Double.parseDouble(percentage);
    }

    public static String getBondExchange(String t) throws IOException {
        int exchangeIndex = getJSONString(t).indexOf("primaryExchange");
        int endIndex = getJSONString(t).indexOf("sector");
        String exchange = getJSONString(t).substring(exchangeIndex + 18, endIndex - 3);
        return exchange;
    }

    public static String getStockCompany(String t) throws MalformedURLException, IOException {
        int companyIndex = getJSONString(t).indexOf("companyName");
        int endIndex = getJSONString(t).indexOf("primaryExchange");
        String company = getJSONString(t).substring(companyIndex + 14, endIndex - 3);
        return company;
    }

    public static String getStockSector(String t) throws MalformedURLException, IOException {
        int sectorIndex = getJSONString(t).indexOf("sector");
        int endIndex = getJSONString(t).indexOf("calculationPrice");
        String sector = getJSONString(t).substring(sectorIndex + 9, endIndex - 3);
        return sector;
    }

    
    
    
    public static Date firstDate () throws MalformedURLException, IOException{
        getJSONString("aapl"); //using any stock, in this case Apple, as an example 
        int dateIndex = getJSONString("aapl").indexOf("\"date\"");
        int year = Integer.parseInt(getJSONString("aapl").substring(dateIndex+8, dateIndex+12)) ;
        int month = Integer.parseInt(getJSONString("aapl").substring(dateIndex+13, dateIndex+15));
        int day = Integer.parseInt(getJSONString("aapl").substring(dateIndex+16, dateIndex+18)) ;
        
        Date d = new Date (year,month,day);
        return d;
    }
    
    
    
    //calculates the next dates according to the interval chosen by the user
    public static String nextDate (int interval, Date d){  //parameters: interval and the current date; 
        
        int nextDay = 0;
        int nextMonth = 0;
        if (d.day + interval>=30){ //if adding the interval on reaches the end of the month
            nextDay = 1; //moves onto the first day of the next month
            nextMonth = d.month + 1;
        }
        
        else{
            nextDay = d.day+ interval; //days increase, the month stays the same
            nextMonth = d.month;
        }
        
        Date nextDate = new Date (d.year,nextMonth,nextDay); 
        return nextDate.dateToString(); 
    }
    
    
    
    public static void main(String[] args) throws IOException, JSONException {

        //*******************USER INPUTS*******************
        System.out.println("Time period is defaulted 3 months.");
        System.out.println("The starting date of your game is exactly 3 months away from the day you are playing.");
        
        Scanner reader = new Scanner(System.in);  // Reading from System.in 
        System.out.println("By which time interval (days) would you like to trade stocks (Enter 2 for every two days, 3 for every three days, etc): ");
        int interval = reader.nextInt();
        
        Scanner sc = new Scanner(System.in);  // asking user for starting amount
        System.out.println("What is your starting capital (Please enter an integer): ");
        int capital = sc.nextInt();
        
        System.out.println("You will start on " + firstDate().dateToString());
        System.out.println("");
        System.out.println("The next date for trading will be " + nextDate(interval, firstDate()) );
        System.out.println("");
        
        //*******************BONDS*******************
        FileReader fr = new FileReader("Bonds.txt");
        Scanner s = new Scanner(fr);

        Bond[] bondArray = new Bond[10]; //10 bonds
        String[] bondNames = new String[10];

        String date = firstDate().dateToString(); //convert to string to find it in API
        int a = 0;
        while (s.hasNext() == true) { //fill the bond names array
            bondNames[a] = s.next();
            a++;
        }

        System.out.println("Available bonds: ");

        for (int i = 0; i < bondArray.length; i++) {
            String ticker = bondNames[i];
            double value = getInvestmentValue(bondNames[i], date);
            double growth = getInvestmentGrowth(bondNames[i], date);
            boolean own = false;
            String exchange = getBondExchange(bondNames[i]);
            bondArray[i] = new Bond(ticker, value, growth, own, exchange);
            bondArray[i].Display();
        }
        System.out.println("");
 

        //*******************STOCKS*******************
        FileReader fR = new FileReader("Stocks.txt");
        Scanner scan = new Scanner(fR);

        Stock[] stockArray = new Stock[10]; //10 stocks
        String[] stockNames = new String[10];

        int b = 0;
        while (scan.hasNext() == true) { //fill the stock names array
            stockNames[b] = scan.next();
            b++;
        }

                    System.out.println("Available stocks: ");

        for (int i = 0; i < stockArray.length; i++) {
            String ticker = stockNames[i];
            double value = getInvestmentValue(stockNames[i], date);
            double growth = getInvestmentGrowth(stockNames[i], date);
            boolean own = false;
            String company = getStockCompany(stockNames[i]);
            String sector = getStockSector(stockNames[i]);
            stockArray[i] = new Stock(ticker, value, growth, own, company, sector);
            stockArray[i].Display();
        }
        
        

    }
}
