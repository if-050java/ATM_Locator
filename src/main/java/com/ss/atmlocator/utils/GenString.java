package com.ss.atmlocator.utils;


import java.util.Random;

/*
* Static Class for generating pseudo random Strings(consist of numbers and chars)
* method genString(int val) generate random String with length = val
* */
public final class GenString {

    private GenString(){}

    private static char[][] chars = new char[3][];

    static{
        chars[0] = new char[10];
        chars[1] = new char[26];
        chars[2] = new char[26];

        for(int j = 48, i=0; j < 58; i++, j++) {chars[0][i] = (char) j;}
        for(int j = 65, i=0; j < 91; i++, j++) {chars[1][i] = (char) j;}
        for(int j = 97, i=0; j < 123; i++, j++) {chars[2][i] = (char) j;}
    }



    public static String genString(int val){
        Random rnd = new Random();
        char[] genChar = new char[val];
        for (int i=0; i < val; i++ ){
            int type = getNum(0,2,rnd);
            genChar[i] = chars[type][rnd.nextInt(type == 0 ? 10 : 26)];
        }
        return String.valueOf(genChar);
    }

    private static int getNum(int begin, int end, Random random){
        long range = (long)end - (long)begin + 1;
        long fraction = (long)(range * random.nextDouble());
        return (int)(fraction + begin);
    }

}