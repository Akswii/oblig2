/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algdat_oblig2;

/**
 *
 * Aksel Wiig - s232324 Tommy Tran - s315573
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
            teller = antall - 1;
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

        
        int i = 0;
        for (; i < a.length && a[i] == null; i++);

        if (i < a.length) {
            Node<T> p = hode = new Node<>(a[i], null, null);  
            antall = 1;                                
            Node<T> temp;
            for (i++; i < a.length; i++) {
                if (a[i] != null) {
                    temp = p;
                    p = p.neste = new Node<>(a[i], p.forrige, null);   
                    p.forrige = temp;
                    antall++;
                }
            }
            hale = p;
        }

    }

    // subliste
    private static void fratilKontroll(int tablengde, int fra, int til) {
        if (fra < 0) // fra er negativ
        {
            throw new IndexOutOfBoundsException("fra(" + fra + ") er negativ!");
        }

        if (til > tablengde) // til er utenfor tabellen
        {
            throw new IndexOutOfBoundsException("til(" + til + ") > antall(" + tablengde + ")");
        }

        if (fra > til) // fra er større enn til
        {
            throw new IllegalArgumentException("fra(" + fra + ") > til(" + til + ") - illegalt intervall!");
        }
    }

    public Liste<T> subliste(int fra, int til) {
        fratilKontroll(antall, fra, til);
        DobbeltLenketListe<T> nyListe = new DobbeltLenketListe<>();
        if (fra == til) {
            return nyListe;
        }

        Node<T> p = finnNode(fra);
        nyListe.leggInn(p.verdi);
        til = til - 1;
        while (fra < til) {
            p = p.neste;
            nyListe.leggInn(p.verdi);
            fra++;
        }
        return nyListe;
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
        Objects.requireNonNull(verdi, "Verdi er null");
        Objects.requireNonNull(indeks, "Indeks er null");

        if (indeks > antall) {
            throw new IndexOutOfBoundsException("Indeks er større enn antall");
        }
        if (indeks < 0) {
            throw new IndexOutOfBoundsException("Indeks mindre enn 0");
        }
        if (tom() || indeks == antall) {
            leggInn(verdi);
        } else if (indeks == 0) {
            Node<T> n = new Node<>(verdi, null, hode);
            hode.forrige = n;
            hode = n;
            antall++;
            endringer++;
        } else if (indeks > 0 && indeks < antall) {
            Node<T> p = finnNode(indeks);
            Node<T> n = new Node<>(verdi, null, null);
            n.forrige = p.forrige;
            p.forrige.neste = n;
            n.neste = p;
            p.forrige = n;
            antall++;
            endringer++;
        }

    }

    @Override
    public boolean inneholder(T verdi) {
        return indeksTil(verdi) != -1;
    }

    @Override
    public T hent(int indeks) {
        Liste.super.indeksKontroll(indeks, false);
        return finnNode(indeks).verdi;
    }

    @Override
    public int indeksTil(T verdi) {
        for (int teller = 0; teller < antall; teller++) {
            if (hent(teller).equals(verdi)) {
                return teller;
            }
        }
        return -1;
    }

    @Override
    public T oppdater(int indeks, T nyverdi) {
        Objects.requireNonNull(nyverdi, "Verdi er null");
        Objects.requireNonNull(indeks, "Indeks er null");
        Liste.super.indeksKontroll(indeks, false);
        Node<T> p = finnNode(indeks);
        T gammelverdi = p.verdi;

        if (nyverdi != null) {
            p.verdi = nyverdi;
            endringer++;
        }

        return gammelverdi;
    }

    @Override
    public boolean fjern(T verdi) {//fikk ikke til å fungerer, methoden under er vårt løsningsforslag
        int indeks = 0;
        Node<T> p = hode, nyHale, nyttHode, nyF, nyN;
        int i = 0;
        if (i == indeks) {
            return false;
        }
        if (indeks < 0 || indeks > antall - 1) {
            return false;
        }
      
        while (indeks < antall) {
            if (p.verdi.equals(verdi)) {
                if (indeks == 0 && antall == 1) {
                    hode.neste = hode.forrige = hale.neste = hode.forrige = null;
                    hode.verdi = null;
                    hode = hale = null;
                    antall--;
                    return true;
                } else if (indeks == 0) {
                    nyttHode = hode.neste;
                    nyttHode.forrige = null;
                    hode = nyttHode;
                    antall--;
                    return true;
                } else if (indeks > 0 && indeks < antall - 1) {
                    nyF = p.forrige;
                    nyN = p.neste;
                    nyF.neste = nyN;
                    nyN.forrige = nyF;
                    antall--;
                    return true;
                } else if (indeks == antall - 1) {
                    nyHale = hale.forrige;
                    nyHale.neste = null;
                    hale = nyHale;
                    antall--;
                    return true;
                }
            }
            indeks++;
        }
        return false;
    }

    @Override
    public T fjern(int indeks) {
        if (indeks < 0 || indeks > antall - 1) {
            throw new IndexOutOfBoundsException("Indeks er feil");
        }
        Node<T> p = finnNode(indeks), nyHale, nyttHode, nyF, nyN;
        T returverdi = p.verdi;
        if (indeks == 0 && antall == 1) {
            hode.neste = hode.forrige = hale.neste = hode.forrige = null;
            hode.verdi = null;
            hode = hale = null;
            antall--;
            endringer++;
        } else if (indeks == 0) {
            nyttHode = hode.neste;
            nyttHode.forrige = null;
            hode = nyttHode;
            endringer++;
            antall--;
        } else if (indeks > 0 && indeks < antall - 1) {
            nyF = p.forrige;
            nyN = p.neste;
            nyF.neste = nyN;
            nyN.forrige = nyF;
            antall--;
            endringer++;
        } else if (indeks == antall - 1) {
            nyHale = hale.forrige;
            nyHale.neste = null;
            hale = nyHale;
            antall--;
            endringer++;
        }

        return returverdi;
    }

    @Override
    public void nullstill() {
        Node<T> p = hode;
        Node<T> temp;
        while (p != null) {
            p.verdi = null;
            p.forrige = null;
            temp = p.neste;
            p.neste = null;
            p = temp;
            antall--;
            endringer++;
        }
        hode.neste = null;
        hale.forrige = null;
    } //Forskjellene var minimale, fant at metode 1 var marginalt raskere.

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
        for (int n = liste.antall(); n > 0; n--) {
            Iterator<T> iterator = liste.iterator();
            int j = 0;
            T denne_verdi = iterator.next();
            for (int i = 1; i < n; i++) {
                T verdi = iterator.next();
                if (c.compare(verdi, denne_verdi) < 0) {
                    j = i;
                    denne_verdi = verdi;
                }
            }
            liste.leggInn(liste.fjern(j));
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new DobbeltLenketListeIterator();
    }

    public Iterator<T> iterator(int indeks) {
        indeksKontroll(indeks, false);
        return new DobbeltLenketListeIterator(indeks);
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
            denne = finnNode(indeks);
            fjernOK = false;
            iteratorendringer = endringer;
        }

        @Override
        public boolean hasNext() {
            return denne != null; // denne koden skal ikke endres!
        }

        @Override
        public T next() {
            if (!(iteratorendringer == endringer)) {
                throw new ConcurrentModificationException("Iteratorendringer != endringer");
            }
            if (!hasNext()) {
                throw new NoSuchElementException("Neste element eksisterer ikke");
            }

            T denne_verdi = denne.verdi;
            denne = denne.neste;

            fjernOK = true;

            return denne_verdi;
        }

        @Override
        public void remove() {
            if (!fjernOK) {
                throw new IllegalStateException("Kan ikke fjerne en verdi nÃ¥!");
            }

            if (iteratorendringer != endringer) {
                throw new ConcurrentModificationException("Listen har blitt endret!");
            }
            fjernOK = false;

            Node<T> p;
            if (denne == null) {
                p = hale;
            } else {
                p = denne.forrige;
            }

            if (p == hode) {
                if (antall == 1) {
                    hode = hale = null;
                } else {
                    (hode = hode.neste).forrige = null;
                }
            } else if (p == hale) {
                (hale = hale.forrige).neste = null;
            } else {
                (p.forrige.neste = p.neste).forrige = p.forrige;
            }
            antall--;
            endringer++;

            iteratorendringer++;
        }
    } // DobbeltLenketListeIterator
} // DobbeltLenketListe 
