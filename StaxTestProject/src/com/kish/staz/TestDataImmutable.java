package com.kish.staz;


public class TestDataImmutable
{

    public static void main(String s[])
    {
        DataImmutable immutable=new DataImmutable("Kishore", new Double(9000), new java.util.Date("11/10/2016"));
        System.out.println(immutable.getjDate());
        System.out.println(immutable.getjDate());
        System.out.println(immutable.getjDate());
        immutable.getjDate().setDate(10000);
        System.out.println(immutable.getjDate());
    }
}
