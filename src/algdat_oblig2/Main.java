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

        for (int i = 1; i <= 10; i++) liste.leggInn(i);
    }
}
