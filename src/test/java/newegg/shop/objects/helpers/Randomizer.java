package newegg.shop.objects.helpers;

import java.util.Random;

public class Randomizer {

    public static int geInt(int size) {
        Random rand = new java.util.Random();
        return rand.nextInt(size);
    }
}