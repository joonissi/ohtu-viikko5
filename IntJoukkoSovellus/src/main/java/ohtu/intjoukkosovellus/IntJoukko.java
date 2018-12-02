
package ohtu.intjoukkosovellus;

public class IntJoukko {

    public final static int TAULUKKO_KOKO = 5; // aloitustalukon koko
    public final static int KASVATA = 5;  // luotava uusi taulukko on 
    // näin paljon isompi kuin vanha
    private int kasvatuskoko;     // Uusi taulukko on tämän verran vanhaa suurempi.
    private int[] ljono;      // Joukon luvut säilytetään taulukon alkupäässä. 
    private int alkioidenLkm;    // Tyhjässä joukossa alkioiden_määrä on nolla. 

    public IntJoukko() {
        
        ljono = new int[TAULUKKO_KOKO];
        
        this.alustaTaulukko(ljono);
        
        this.alkioidenLkm = 0;
        this.kasvatuskoko = KASVATA;
    }

    public IntJoukko(int kapasiteetti) {
        
        if (kapasiteetti < 0) {
            return;
        }
        
        ljono = new int[kapasiteetti];
        
        this.alustaTaulukko(ljono);

        this.alkioidenLkm = 0;
        this.kasvatuskoko = KASVATA;

    }
    
    
    public IntJoukko(int kapasiteetti, int kasvatuskoko) {
        
        if (kapasiteetti < 0 || kasvatuskoko < 0) {
            return;
        }

        ljono = new int[kapasiteetti];
        
        this.alustaTaulukko(ljono);
        
        this.alkioidenLkm = 0;
        this.kasvatuskoko = kasvatuskoko;

    }
    
    public void alustaTaulukko(int[] taulukko) {
        
        for (int i = 0;i < this.ljono.length; i++) {
            this.ljono[i] = 0;
        }
    }

    public boolean lisaa(int luku) {
        
        if (this.alkioidenLkm == 0) {
            this.ljono[0] = luku;
            this.alkioidenLkm++;
            return true;
        } else if (!kuuluu(luku)) {
            this.ljono[this.alkioidenLkm] = luku;
            this.alkioidenLkm++;
            
            if (this.alkioidenLkm % this.ljono.length == 0) {
                int[] taulukkoOld = new int[this.ljono.length];
                taulukkoOld = this.ljono;
                kopioiTaulukko(this.ljono, taulukkoOld);
                this.ljono = new int[this.alkioidenLkm + this.kasvatuskoko];
                kopioiTaulukko(taulukkoOld, this.ljono);
            }
            return true;
        }
        return false;
    }

    public boolean kuuluu(int luku) {
        
        for (int i = 0; i < alkioidenLkm; i++) {
            if (luku == ljono[i]) {
                return true;
            }
        }
        
        return false;
    }

    public boolean poista(int luku) {
        
        int kohta = -1;
        int apu;
        
        for (int i = 0; i < this.ljono.length; i++) {
            if (luku == ljono[i]) {
                kohta = i; //siis luku löytyy tuosta kohdasta :D
                ljono[i] = 0;
                break;
            }
        }
        
        if (kohta != -1) {
            for (int j = kohta; j < this.ljono.length - 1; j++) {
                apu = ljono[j];
                ljono[j] = ljono[j + 1];
                ljono[j + 1] = apu;
            }
            alkioidenLkm--;
            return true;
        }


        return false;
    }

    private void kopioiTaulukko(int[] vanha, int[] uusi) {
        for (int i = 0; i < vanha.length; i++) {
            uusi[i] = vanha[i];
        }

    }

    public int mahtavuus() {
        return alkioidenLkm;
    }


    @Override
    public String toString() {
        
        String tuotos = "";
        
        if (alkioidenLkm == 0) {
            return tuotos + "{}";
        } else if (alkioidenLkm == 1) {
            return tuotos  + "{" + ljono[0] + "}";
        } else {
            
            tuotos = "{";
            
            for (int i = 0; i < alkioidenLkm - 1; i++) {
                tuotos += ljono[i];
                tuotos += ", ";
            }
            
            tuotos += ljono[alkioidenLkm - 1];
            tuotos += "}";
            
            return tuotos;
        }
    }

    public int[] toIntArray() {
        
        int[] taulu = new int[alkioidenLkm];
        
        for (int i = 0; i < taulu.length; i++) {
            taulu[i] = ljono[i];
        }
        
        return taulu;
    }
   

    public static IntJoukko yhdiste(IntJoukko a, IntJoukko b) {
        
        IntJoukko x = new IntJoukko();
        
        int[] aTaulu = a.toIntArray();
        int[] bTaulu = b.toIntArray();
        
        for (int i = 0; i < aTaulu.length; i++) {
            x.lisaa(aTaulu[i]);
        }
        
        for (int i = 0; i < bTaulu.length; i++) {
            x.lisaa(bTaulu[i]);
        }
        
        return x;
    }

    public static IntJoukko leikkaus(IntJoukko a, IntJoukko b) {
        
        IntJoukko uusiTaulu = new IntJoukko();
        
        for (int i = 0; i < a.ljono.length; i++) {
            for (int j = 0; j < b.ljono.length; j++) {
                if (a.ljono[i] == b.ljono[j]) {
                    uusiTaulu.lisaa(b.ljono[j]);
                }
            }
        }
        
        return uusiTaulu;

    }
    
    public static IntJoukko erotus (IntJoukko a, IntJoukko b) {
        
        IntJoukko uusiTaulu = new IntJoukko();
        
        for (int i = 0; i < a.ljono.length; i++) {
            uusiTaulu.lisaa(a.ljono[i]);
        }
        
        for (int i = 0; i < b.ljono.length; i++) {
            uusiTaulu.poista(i);
        }
 
        return uusiTaulu;
    }
        
}