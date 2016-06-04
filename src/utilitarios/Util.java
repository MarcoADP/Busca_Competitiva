package utilitarios;

import java.util.Random;


public class Util {
    private final static Random random = new Random();
    
    public static int getRandomInt(int limite){
        return random.nextInt(limite);
    }
}
