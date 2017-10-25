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
        Character[] c = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',};
        DobbeltLenketListe<Character> liste = new DobbeltLenketListe<>(c);
        System.out.println(liste.subliste(3, 8)); // [D, E, F, G, H]
        System.out.println(liste.subliste(5, 5)); // []
        System.out.println(liste.subliste(8, liste.antall()));
    }
}
