package crypto.key;

import crypto.services.CryptoService;

import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.function.Function;

/**
 * Created by julienfurgerot on 17/05/2017.
 */
public class CipheredKeyProvider implements KeyProvider {

    private CryptoService cryptoService;
    private String keyAlgorithm;
    private Function<Mode, byte[]> binaryKeySupplier;

    public CipheredKeyProvider( CryptoService cryptoService, String keyAlgorithm, Function<Mode, byte[]> binaryKeySupplier ) {
        this.cryptoService = cryptoService;
        this.keyAlgorithm = keyAlgorithm;
        this.binaryKeySupplier = binaryKeySupplier;
    }

    @Override
    public Key getKey(Mode mode) throws GeneralSecurityException {
        byte[] binKey = cryptoService.decrypt( binaryKeySupplier.apply( mode ) );
        return new SecretKeySpec( binKey, keyAlgorithm );
    }
}
