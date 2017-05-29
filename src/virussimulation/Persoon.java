/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package virussimulation;
import java.util.Random;
/**
 *
 * @author Jorrit
 */
class Persoon {
    public char state;
    public int age;
    public double health;
    public char buffer;
    Random rand = new Random();
    
    
    public Persoon(boolean healthOn){
        state = 's';
        age = rand.nextInt(100);
        if(healthOn){
            health = (-1 * (Math.pow(this.age, 2)) + 100 * (this.age)) / (2500/0.4) + 0.01 * rand.nextInt(10) + 0.51;
        }else{
            health = 1;
        }
        buffer = 's';        
    }
}

