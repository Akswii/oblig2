/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat_oblig2;

/**
 *
 * @author Aksel
 */
/////////// DobbeltLenketListe ////////////////////////////////////
import java.util.Comparator;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DobbeltLenketListe<T> implements Liste<T> {

    private static final class Node<T> // en indre nodeklasse
    {
        // instansvariabler

        private T verdi;
        private Node<T> forrige, neste;

        private Node(T verdi, Node<T> forrige, Node<T> neste) // konstruktør
        {
            this.verdi = verdi;
            this.forrige = forrige;
            this.neste = neste;
        }

        protected Node(T verdi) // konstruktør
        {
            this(verdi, null, null);
        }
    } // Node
    // instansvariabler
    private Node<T> hode; // peker til den første i listen
    private Node<T> hale; // peker til den siste i listen
    private int antall; // antall noder i listen
    private int endringer; // antall endringer i listen
    // hjelpemetode

    private Node<T> finnNode(int indeks) {
        int teller = 0;
        Node<T> p;
        if (indeks <= (antall / 2)) {
            p = hode;
            while (teller < indeks && teller != indeks) {
                p = p.neste;
                teller++;
            }
        } else {
            teller = antall-1;
            p = hale;

            while (teller > indeks && teller != indeks) {
                p = p.forrige;
                teller--;
            }
        }
        return p;
    }
    // konstruktør

    public DobbeltLenketListe() {
        hode = hale = null;
        antall = 0;
        endringer = 0;
    }
    // konstruktør

    public DobbeltLenketListe(T[] a) {
        this();
        Objects.requireNonNull(a, "Tabellen er tom!");

        // Finner den første i a som ikke er null
        int i = 0;
        for (; i < a.length && a[i] == null; i++);

        if (i < a.length) {
            Node<T> p = hode = new Node<>(a[i], null, null);  // den første noden
            antall = 1;                                 // vi har minst en node
            Node<T> temp;
            for (i++; i < a.length; i++) {
                if (a[i] != null) {
                    temp = p;
                    p = p.neste = new Node<>(a[i], p.forrige, null);   // en ny node
                    p.forrige = temp;
                    antall++;
                }
            }
            hale = p;
        }

    }
    // subliste

    public Liste<T> subliste(int fra, int til) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public int antall() {
        return antall;
    }

    @Override
    public boolean tom() {
        if (antall == 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Tom tabell!");

        if (tom()) {
            Node<T> p = new Node<>(verdi, null, null);
            hode = hale = p;
            antall++;
            endringer++;
            return true;
        }

        Node<T> p = new Node<>(verdi, hale, null);
        hale.neste = p;
        hale = p;
        antall++;
        endringer++;
        return true;
    }

    @Override
    public void leggInn(int indeks, T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean inneholder(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T hent(int indeks) {
        Liste.super.indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public T fjern(int indeks) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public void nullstill() {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public String toString() {
        StringBuilder utskrift = new StringBuilder("[");

        if (tom()) {
            utskrift.append("]");
            return utskrift.toString();
        }

        Node<T> p = hode;

        while (p.neste != null) {
            utskrift.append(p.verdi);
            utskrift.append(", ");
            p = p.neste;
        }

        utskrift.append(p.verdi);
        utskrift.append("]");

        return utskrift.toString();
    }

    public String omvendtString() {
        StringBuilder utskrift = new StringBuilder("[");
        if (tom()) {
            utskrift.append("]");
            return utskrift.toString();
        }

        Node<T> p = hale;

        while (p.forrige != null) {
            utskrift.append(p.verdi);
            utskrift.append(", ");
            p = p.forrige;
        }

        utskrift.append(p.verdi);
        utskrift.append("]");

        return utskrift.toString();
    }

    public static <T> void sorter(Liste<T> liste, Comparator<? super T> c) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    public Iterator<T> iterator(int indeks) {
        throw new UnsupportedOperationException("Ikke laget ennå!");
    }

    private class DobbeltLenketListeIterator implements Iterator<T> {

        private Node<T> denne;
        private boolean fjernOK;
        private int iteratorendringer;

        private DobbeltLenketListeIterator() {
            denne = hode; // denne starter på den første i listen
            fjernOK = false; // blir sann når next() kalles
            iteratorendringer = endringer; // teller endringer
        }

        private DobbeltLenketListeIterator(int indeks) {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public boolean hasNext() {
            return denne != null; // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Ikke laget ennå!");
        }
    } // DobbeltLenketListeIterator
} // DobbeltLenketListe 
