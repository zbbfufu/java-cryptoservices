package crypto.key;

import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public class HardcodedKeyProvider implements KeyProvider {

    private final Key key;

    public HardcodedKeyProvider( String algorithm, int size ) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance( algorithm );
        keyGenerator.init( size );
        this.key = keyGenerator.generateKey();
    }

    @Override
    public Key getKey( Mode mode ) {
        return key;
    }

    @Override
    public String getKeyAlgorithm(Mode mode) {
        return key.getAlgorithm();
    }
}
