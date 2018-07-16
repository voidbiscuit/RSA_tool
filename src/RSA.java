
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RSA {
    private long a, b, p, q, e, d;
    private int jump = 1000;
    private ArrayList<Long> primes = new ArrayList<>();
    private ArrayList<Long> remainders = new ArrayList<>();
    private Scanner s;

    public RSA() {
        this.a = 53;//getLong("[A] Enter a Prime : ");
        this.b = 73;//getLong("[B] Enter a Prime : ");
        System.out.println("[A] Prime 1 : " + a);
        System.out.println("[B] Prime 2 : " + b);
        calculatePQ();
        calculateDE();
    }

    private long getLong(String prompt) {
        long returnval = -1;
        s = new Scanner(System.in);
        while (returnval < 2) {
            try {
                Thread.sleep(100);
                System.out.print(prompt);
                returnval = Long.parseLong("" + s.nextLine());
                if (returnval < 2) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                System.err.println("Number is invalid");
            } catch (InterruptedException e) {
                System.err.println("Shouldn't be seeing this");
            }
        }
        s.close();
        return returnval;
    }

    private void calculatePQ() {
        this.p = a * b; // Calculate Product
        System.out.println("[P] Product : " + this.p);
        this.q = (a - 1) * (b - 1); // Calculate Totient
        System.out.println("[Q] Totient : " + this.q);
    }

    private void calculateDE() {
        // Use a random prime
        Random r = new Random();
        // Generate list of primes to use
        primes.add(2l);
        calcPrimes(r.nextInt(jump));
        this.e = primes.get(primes.size() - 1);
        while (d <= 0) {
            calcPrimes(jump);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            for (int i = primes.size() - jump; i < primes.size(); i++) {
                if (i >= remainders.size())
                    remainders.add((this.e * primes.get(i)) % this.q - (1 % this.q));
                if ((this.e * primes.get(i)) % this.q == 1 % this.q) {
                    this.d = primes.get(i);
                    break;
                }
            }
            //System.out.println(this.d + " " + primes);
            //System.out.println(this.d + " " + remainders);
        }
        System.out.println("[E] Encrypt : " + this.e);
        System.out.println("[D] Decrypt : " + this.d);
    }

    private void calcPrimes(int count) {
        for (long i = primes.get(primes.size() - 1); count > 0; ++i) {
            if (isPrime(primes, i)) {
                primes.add(i);
                --count;
            }
        }
    }

    private boolean isPrime(ArrayList<Long> primes, long val) {
        for (long prime : primes)
            if (val % prime == 0) return false;
        return true;
    }

    public long encrypt(long val) {
        return bigPowMod(val, e, p);

    }

    public long decrypt(long val) {
        return bigPowMod(val, d, p);
    }

    private long bigPowMod(long val, long pow, long mod) {
        // Calc bit pattern and powers
        boolean[] powbits = dec2bin(pow);
        long[] powvals = calcPowVals(val, mod, powbits.length);
        //Output shit
        for (int i = 0; i < powbits.length; i++) System.out.print(powbits[i] + " ");
        System.out.println();
        for (int i = 0; i < powvals.length; i++) System.out.print(powvals[i] + " ");
        System.out.println();
        // Calculates top val
        long endval = 1;
        for (int i = 0; i < powbits.length; i++) {
            if (powbits[i]) {
                endval *= powvals[i];
                endval %= mod;
            }
        }
        return endval;
    }

    private boolean[] dec2bin(long val) {
        long biggestpow = 1;
        int maxbit = 1;
        while (val > biggestpow) {
            biggestpow *= 2;
            maxbit++;
        }
        boolean[] returnbits = new boolean[maxbit];
        for (int i = maxbit - 1; i >= 0; i--) {
            if (val >= Math.pow(2, i)) {
                val -= Math.pow(2, i);
                returnbits[i] = true;
            } else {
                returnbits[i] = false;
            }
        }
        return returnbits;
    }

    private long[] calcPowVals(long val, long mod, int length) {
        long[] powvals = new long[length];
        powvals[0] = val % mod;
        for (int i = 1; i < length; i++) {
            powvals[i] = (powvals[i - 1] * powvals[i - 1]) % mod;
        }
        return powvals;
    }
}