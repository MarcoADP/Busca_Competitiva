package utilitarios;

import java.text.DecimalFormat;
import java.util.Random;


public class Util {
    private final static Random RANDOM = new Random();
    private final static DecimalFormat DF = new DecimalFormat("R$ #,###,###.##");
    
    private Util() {}
    
    public static int getRandomInt(int limite){
        return RANDOM.nextInt(limite);
    }
    
    public static double getRandomDouble(double min, double max){
        return RANDOM.nextDouble()*(max - min) + min;
    }
    
    public static String formatarDinheiro(double valor){
        return DF.format(valor);
    }
}
