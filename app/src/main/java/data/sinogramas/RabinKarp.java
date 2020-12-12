package data.sinogramas;
/**
 * @author dequi
 */
public class RabinKarp {
    
    public boolean found;
    
    public void search(String T, String P) {
        found = false;
        int p = 10000019;
        int x = (int) Math.random() * (p - 1 + 1) + 1;
        int pHash = polyHash(P, p, x);
        int[] H = preComputeHashes(T, P.length(), p, x);
        for (int i = 0; i <= T.length()-P.length(); i++) {
            if (pHash != H[i])
                continue;
            if (areEqual(T.substring(i, i+P.length()), P)) {
                System.out.println(T);
                found = true;
                break;
            }
        }
    }
    
    private int polyHash(String S, int p, int x) {
        int hash = 0;
        for (int i = S.length()-1; i >= 0; i--)
            hash = (hash*x+S.charAt(i))%p;
        return hash;
    }
    
    private int[] preComputeHashes(String T, int lenP, int p, int x) {
        int[] H = new int[T.length()-lenP+1];
        String S = T.substring(T.length()-lenP, T.length());
        H[T.length()-lenP] = polyHash(S, p, x);
        int y = 1;
        for (int i = 1; i <= lenP; i++)
            y = (y*x)%p;
        for (int i = T.length()-lenP-1; i >= 0; i--)
            H[i] = (x*H[i+1]+T.charAt(i)-y*T.charAt(i+lenP))%p;
        return H;
    }
    
    private boolean areEqual(String S1, String S2) {
        if (S1.length() != S2.length())
            return false;
        for (int i = 0; i < S1.length(); i++) {
            if (S1.charAt(i) != S2.charAt(i))
                return false;
        }
        return true;
    }
}
