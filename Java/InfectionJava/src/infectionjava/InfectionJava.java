
    
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package infectionjava;
import java.util.Random;
/**
 *
 * @author Jorrit
 */

public class InfectionJava {
    int[] lastResult;
    int populatie;
    double alpha;
    double beta;
    int contacten;
    int turn;
    Random rand = new Random();
    double[][] index;
    Persoon[] personen;
    
    
    public InfectionJava(int a, double b, double c, int d, int health){
        turn  = 1;
        populatie = a;
        alpha = b;
        beta = c;
        contacten = d;
        personen = new Persoon[populatie];
        for (int i=0; i<populatie; i++) {
            personen[i] = new Persoon();
        }
        
        
        
        
    }
    
    
    public void nextTurn(){
        for(int person=0; person<populatie; person++){
            contact(person);
        }
    }
    
    
    public void contact(int n){
        if (personen[n].inf==0 && personen[n].res!=1){
            double i = 1;
            for (int count=1; count<contacten; count++){
                int p = rand.nextInt(populatie);
                if (p != n && personen[p].inf==1){
                    i = (1-alpha) *i;
                }
                if (n == p){
                    count--;
                }
            }
            if (Math.random()<i*personen[n].health){
                personen[n].buffer = 1;
            }
        }
    }
    
    public int[] results(){
        int[] result;
        result = new int[3];
        for (int count = 0; count<populatie; count++){
            if (personen[count].inf==1){
                result[3]++;
            }
            if (personen[count].res==1){
                result[2]++;
            }
            if (personen[count].inf==0&&personen[count].res==0){
                result[1]++;
            }
        }
        return result;
    }
    
    public void vaccResist(int n, int[][] vList){
        for (int count=0; count<populatie; count++){
            if (vList[count][1] >= personen[n].age&&vList[count][2]<personen[n].age){
                if (Math.random()<vList[count][3]){
                    personen[n].inf = 0;
                    personen[n].res = 1;
                    personen[n].buffer = 0;
                }
            }
        }
        if (Math.random()<beta*personen[n].health&&personen[n].inf ==1){
            personen[n].res = 1;
            personen[n].inf = 0; 
        }
    }
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        
    }
}




