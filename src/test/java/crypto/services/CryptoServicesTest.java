package crypto.services;

import crypto.key.CachedKeyProvider;
import crypto.key.CipheredKeyProvider;
import crypto.key.HardcodedKeyProvider;
import org.junit.Test;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public class CryptoServicesTest {

    private static final String KEY_ALGO = "AES";
    private static final int KEY_SISE = 128;

    private static final byte[] keyBites = new byte[0];


    @Test
    public void testIntToBytes() throws Exception {
        byte[] val = { (byte)125 >> 0x00001 };
        System.out.println(Arrays.toString( val ) );
    }

    @Test
    public void testCreateJreCryptoService() throws Exception {

        HardcodedKeyProvider keyProvider = new HardcodedKeyProvider( KEY_ALGO, KEY_SISE );

        JreCryptoService masterCS = new JreCryptoService( keyProvider );

        CipheredKeyProvider intermediateKP = new CipheredKeyProvider( masterCS, KEY_ALGO, mode -> keyBites );

        CachedKeyProvider intermediateKeyCache = new CachedKeyProvider( intermediateKP, 5, TimeUnit.SECONDS );

        JreCryptoService cs = new JreCryptoService( intermediateKeyCache );

        cs.encrypt("Hello World!");
    }
}