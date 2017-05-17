package crypto.services;

import crypto.key.KeyProvider;

import javax.crypto.Cipher;
import java.security.GeneralSecurityException;
import java.util.Base64;

import static crypto.key.Mode.DECRYPT;
import static crypto.key.Mode.ENCRYPT;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public class JreCryptoService implements CryptoService {

    //FIXME: add thread local management

    private Cipher cipher;
    private Cipher decipher;

    private KeyProvider keyProvider;

    private Base64.Encoder b64Encoder = Base64.getEncoder();
    private Base64.Decoder b64Decoder = Base64.getDecoder();

    public JreCryptoService( KeyProvider keyProvider ) {
        this.keyProvider = keyProvider;
    }

    //FIXME: add IV management

    public String encrypt(String payload) throws GeneralSecurityException {
        cipher.init( Cipher.ENCRYPT_MODE, keyProvider.getKey( ENCRYPT ) );
        return b64Encoder.encodeToString( cipher.doFinal( payload.getBytes() ) );
    }

    public byte[] encrypt(byte[] payload) throws GeneralSecurityException {
        cipher.init( Cipher.ENCRYPT_MODE, keyProvider.getKey( ENCRYPT ) );
        return cipher.doFinal( payload );
    }

    public String decrypt(String cipher) throws GeneralSecurityException {
        decipher.init( Cipher.DECRYPT_MODE, keyProvider.getKey( DECRYPT ) );
        return new String( decipher.doFinal( b64Decoder.decode( cipher ) ) );
    }

    public byte[] decrypt(byte[] cipher) throws GeneralSecurityException {
        decipher.init( Cipher.DECRYPT_MODE, keyProvider.getKey( DECRYPT ) );
        return new byte[0];
    }
}
