/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat_oblig2;

/**
 *
 * @author tomtr
 */
public class Main {

    public static void main(String[] args) {
        Integer[] c = {};
        DobbeltLenketListe<Integer> liste = new DobbeltLenketListe<>();
        DobbeltLenketListe<Integer> liste2 = new DobbeltLenketListe<>();

        for (int i = 1; i <= 999999; i++) liste.leggInn(i);
        //for (int j = 1; j <= 999999; j++) liste2.leggInn(j);
        
        double tid = System.currentTimeMillis();
        liste.nullstill2();
        tid = System.currentTimeMillis() - tid;
        System.out.println(tid);
        
        /*double tid2 = System.currentTimeMillis();
        liste2.nullstill2();
        tid2 = System.currentTimeMillis() - tid2;
        System.out.println(tid2);*/
    }
}
