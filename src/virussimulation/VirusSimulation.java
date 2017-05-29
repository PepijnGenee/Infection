/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package virussimulation;
import java.util.Random;
import java.util.Arrays;
//import java.util.Scanner;
/**
 *
 * @author Jorrit
 */

public class VirusSimulation {
    double[][] vList = new double[][]{//hieronder: 1. min leeftijd 2. max leeftijd 3. deel van bevolking
        {1,18,0.1},{65,99,0.2}
    };
    
    
    
    int[] lastResult;
    int populatie;
    double alpha;
    double beta;
    int contacten;
    int turn;
    boolean vaccOn;
    Random rand = new Random();
    Persoon[] personen;
    
    
    public VirusSimulation(int a, double b, double c, int d, int e,boolean healthOn, boolean vacc){
        turn  = 1;
        populatie = a;
        alpha = b;
        beta = c;
        contacten = d;
        vaccOn = vacc;
        personen = new Persoon[populatie];
        for (int i=0; i<populatie; i++) {
            personen[i] = new Persoon(healthOn);
        }
        for (int i=0; i<e;i++){
            int n = rand.nextInt(populatie);
            if (personen[n].state=='i'){
                i--;
            }else{
                personen[n].state = 'i';
            }
        }
        lastResult = results();
        System.out.println(Arrays.toString(lastResult));
    }
    
    
    public void nextTurn(){
        for(int person=0; person<populatie; person++){
            contact(person);
            vaccResist(person,vList);
            
        }
        update();
        int[] deltaResult = lastResult;
        lastResult = results();
        System.out.println("");
        System.out.println(Arrays.toString(lastResult));
        for (int count=0; count<3;count++){
            deltaResult[count] = lastResult[count] - deltaResult[count];
        } 
        System.out.println(Arrays.toString(deltaResult));
    }
    
    
    public void contact(int n){
        if (personen[n].state=='s'){
            double i = 1;
            for (int count=1; count<contacten; count++){
                int p = rand.nextInt(populatie);
                if (p != n && personen[p].state=='i'){
                    i = (1-alpha) *i;
                }
                if (n == p){
                    count--;
                }
            }
            if (Math.random()>i*personen[n].health&&i!=1){
                personen[n].buffer = 'i';
            }
        }
    }
    
    
    public int[] results(){
        int[]result = new int[]{0,0,0};
        for (int count = 0; count<populatie; count++){
            if (personen[count].state=='i'){
                result[1]++;
            }
            if (personen[count].state=='r'){
                result[2]++;
            }
            if (personen[count].state=='s'){
                result[0]++;
            }
        }
        return result;
    }
    
    
    public void vaccResist(int n, double[][] vList){
        if (vaccOn){
            for (double[] vList1 : vList) {
                if (vList1[0] <= personen[n].age && vList1[1] >= personen[n].age) {
                    if (Math.random() < vList1[2]) {
                        personen[n].state = 'r';
                        personen[n].buffer = 's';
                    }
                }
            }
        }
        if (Math.random()<beta*personen[n].health&&personen[n].state =='i'){
            personen[n].state = 'r'; 
            personen[n].buffer = 's';
        }
    }
    
    
    public void update(){
        for (int count=0; count<populatie; count++){
            if (personen[count].state == 's'){
                personen[count].state = personen[count].buffer;
            }
        }
    }
    
    
    /**
     * @param args the command line arguments
     */
    
    public static void main(String[] args) {
        // TODO code application logic here
        //Scanner keyboard = new Scanner(System.in);
        //1. populatie 2. alpha 3. beta 4. contacten per beurt pp 5. aantal patients zero's 6. gezondheid 7. inenting programmma
        JFrame form = new JFrame();
        while (JFrame.loopEnd){
            form.setVisible(true);
        }
        form.setVisible(false);
        //VirusSimulation virus = new VirusSimulation(10000,0.6,0.4,50,1,true,false);
        VirusSimulation virus = new VirusSimulation(JFrame.populatieB, JFrame.alfaB, JFrame.betaB, JFrame.contactenB, 1, JFrame.gezondB, JFrame.vaccB);
        int intpress =1;
        while (intpress!=0 && virus.lastResult[2]!=virus.populatie){
            //intpress = keyboard.nextInt();
            virus.nextTurn();
        }
    }
}


