package crypto.services;

import crypto.key.*;
import org.junit.Test;

import java.security.Key;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public class CryptoServicesIT {

    private static final String KEY_ALGO = "AES";
    private static final String CIPHER_ALGO = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 128;

    private static final byte[] SOME_DOC_CONTENT = new byte[25];
    private static final String SOME_TEXT = "Hello World!";

    static {
        new Random().nextBytes( SOME_DOC_CONTENT );
    }

    @Test
    public void testFullApplicationCryptoServicesSystem() throws Exception {

        HardcodedKeyProvider masterKeyProvider = new HardcodedKeyProvider( KEY_ALGO, KEY_SIZE );
        JreCryptoService masterCS = new JreCryptoService( masterKeyProvider, CIPHER_ALGO );

        final byte[] interediateKeyCipheredBytes = masterCS.encrypt(
                new HardcodedKeyProvider( KEY_ALGO, KEY_SIZE ).getKey( Mode.ENCRYPT ).getEncoded()
        );

        CipheredKeyProvider intermediateKP = new CipheredKeyProvider( masterCS, KEY_ALGO, mode -> interediateKeyCipheredBytes );
        CachedKeyProvider intermediateKeyCache = new CachedKeyProvider( intermediateKP, 5, TimeUnit.SECONDS );
        CryptoService cs = new JreCryptoService( intermediateKeyCache, CIPHER_ALGO );

        // Cipher/decipher some data
        assertEquals( SOME_TEXT, cs.decrypt( cs.encrypt( SOME_TEXT ) ) );

        KeyProvider randomKeyProvider = new RandomKeyProvider( KEY_ALGO, KEY_SIZE );
        DocumentCryptoService documentCS = new DocumentCryptoService( cs, randomKeyProvider );

        assertEquals(
                Arrays.toString( SOME_DOC_CONTENT ),
                Arrays.toString( documentCS.decrypt( documentCS.encrypt( SOME_DOC_CONTENT ) ) ) );
    }
}