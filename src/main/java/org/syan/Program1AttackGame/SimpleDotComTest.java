package org.syan.Program1AttackGame;

import java.util.ArrayList;

public class SimpleDotComTest {


    public static void main(String[] args) {
        testHelper();
    }

    public static void testHelper() {
        Helper helper = new Helper();
        String[] strings = helper.generateName();
        System.out.println(strings[0] + " " + strings[1] + " " + strings[2]);
        ArrayList<String> str = helper.generatePos();
        for (String pos: str) {
            System.out.println(pos);
        }
        str = helper.generatePos();
        for (String pos: str) {
            System.out.println(pos);
        }
        str = helper.generatePos();
        for (String pos: str) {
            System.out.println(pos);
        }
    }
}
