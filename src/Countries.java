import java.io.*;
import java.util.Scanner;

public class Countries {
    private int[] countryID;
    private String[] countryName;
    private String[] countryAbbreviations;
    private String[] participatingCountries;
    protected final static int TOTAL_COUNTRIES = 193;

    Countries(){
        init();
    }

    public void init(){
        countryID = new int[TOTAL_COUNTRIES];
        countryName = new String[TOTAL_COUNTRIES];
        countryAbbreviations = new String[TOTAL_COUNTRIES];

    }

    ///////////////////////////////////////////////////////
    // Accessor Functions
    public void addCountry(){

    }

    public int getCountryID(int index){
        return countryID[index];
    }

    public String getCountries(int index){
        return countryName[index];
    }

    public String getCountryAbbreviations(int index){
        return countryAbbreviations[index];
    }

    /////////////////////////////////////////////////////
    // Mutator Functions
    public void setCountryID(int id, int index){
        countryID[index] = id;
    }

    public void setCountryName(String name, int index){
        countryName[index] = name;
    }

    public void setCountryAbbreviations(String n, int index){
        countryAbbreviations[index] = n;
    }

    public static void main(String[] args) throws FileNotFoundException {
        Countries Country = new Countries();

        Scanner sc = new Scanner(new File("C:\\Olympic-Games-Management\\src\\csvFiles\\countries.csv"));
        sc.useDelimiter(",|\\n");

        while (sc.hasNext()) {
            int i = 0;
            Country.setCountryID(sc.nextInt(), i);
            Country.setCountryName(sc.next(), i);
            Country.setCountryAbbreviations(sc.next(), i);

            System.out.print(Country.getCountryID(i) + " ");
            System.out.print(Country.getCountries(i) + " ");
            System.out.print(Country.getCountryAbbreviations(i) + "\n" );
            i++;

        }

    }

}

