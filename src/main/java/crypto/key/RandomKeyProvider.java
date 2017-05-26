package crypto.key;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public class RandomKeyProvider implements KeyProvider {

    private final KeyGenerator keyGenerator;
    private final String algorithm;

    public RandomKeyProvider(String algorithm, int size ) throws NoSuchAlgorithmException {
        this.algorithm = algorithm;
        this.keyGenerator = KeyGenerator.getInstance( algorithm );
        this.keyGenerator.init( size );
    }

    @Override
    public Key getKey( Mode mode ) {
        return keyGenerator.generateKey();
    }

    @Override
    public String getKeyAlgorithm(Mode mode) {
        return algorithm;
    }
}
