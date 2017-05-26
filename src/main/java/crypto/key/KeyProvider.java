package crypto.key;

import java.security.*;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public interface KeyProvider {

    Key getKey( Mode mode ) throws GeneralSecurityException;

    String getKeyAlgorithm( Mode mode );
}
