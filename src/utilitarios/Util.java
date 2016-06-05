package utilitarios;

import java.text.DecimalFormat;
import java.util.Random;


public class Util {
    private final static Random random = new Random();
    private final static DecimalFormat df = new DecimalFormat("R$ #,###,###.##");
    
    public static int getRandomInt(int limite){
        return random.nextInt(limite);
    }
    
    public static String formatarDinheiro(double valor){
        return df.format(valor);
    }
}
