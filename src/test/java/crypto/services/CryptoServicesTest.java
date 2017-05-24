package crypto.services;

import crypto.key.*;
import org.junit.Test;

import java.security.Key;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public class CryptoServicesTest {

    private static final String KEY_ALGO = "AES";
    private static final String CIPHER_ALGO = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 128;

    private static final byte[] SOME_DATA = new byte[0];
    private static final String SOME_TEXT = "Hello World!";

    @Test
    public void testCreateJreCryptoService() throws Exception {

        HardcodedKeyProvider masterKeyProvider = new HardcodedKeyProvider( KEY_ALGO, KEY_SIZE );

        JreCryptoService masterCS = new JreCryptoService( masterKeyProvider, CIPHER_ALGO );

        final byte[] interediateKeyCipheredBytes = masterCS.encrypt(
                new HardcodedKeyProvider( KEY_ALGO, KEY_SIZE ).getKey( Mode.ENCRYPT ).getEncoded()
        );

        CipheredKeyProvider intermediateKP = new CipheredKeyProvider( masterCS, KEY_ALGO, mode -> interediateKeyCipheredBytes );

        CachedKeyProvider intermediateKeyCache = new CachedKeyProvider( intermediateKP, 5, TimeUnit.SECONDS );

        CryptoService cs = new JreCryptoService( intermediateKeyCache, CIPHER_ALGO );

        assertEquals( SOME_TEXT, cs.decrypt( cs.encrypt( SOME_TEXT ) ) );
    }

    @Test
    public void testGeneratedKeyDataCryptoService() throws Exception {

        KeyProvider keyProvider = new HardcodedKeyProvider( KEY_ALGO, KEY_SIZE);

        Key docKey = keyProvider.getKey(Mode.ENCRYPT);

        JreCryptoService cs = new JreCryptoService( mode -> docKey, CIPHER_ALGO );

        byte[] cipheredDoc = cs.encrypt( SOME_DATA );

        // save docKey and cipheredDoc

        // later reload document and key


    }
}