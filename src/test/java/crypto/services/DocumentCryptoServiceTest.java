package crypto.services;

import crypto.key.HardcodedKeyProvider;
import org.junit.Test;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Random;

import static org.junit.Assert.*;

/**
 * Created by julienfurgerot on 24/05/2017.
 */
public class DocumentCryptoServiceTest {

    private static final byte[] SOME_DOC_CONTENT = new byte[25];
    private static final String KEY_ALGO = "AES";
    private static final String CIPHER_ALGO = "AES/CBC/PKCS5Padding";
    private static final int KEY_SIZE = 128;

    static {
        new Random().nextBytes( SOME_DOC_CONTENT );
    }

    @Test
    public void testEncryptDecrypt() throws GeneralSecurityException {

        HardcodedKeyProvider kp = new HardcodedKeyProvider( KEY_ALGO, KEY_SIZE );

        JreCryptoService cs = new JreCryptoService( null, CIPHER_ALGO );

        DocumentCryptoService dcs = new DocumentCryptoService( cs, kp );

        assertEquals(Arrays.toString( SOME_DOC_CONTENT ), Arrays.toString( dcs.decrypt( dcs.encrypt( SOME_DOC_CONTENT ) ) ) );
    }


}