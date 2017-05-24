package crypto.services;

import crypto.key.KeyProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import java.security.GeneralSecurityException;
import java.util.Base64;

import static crypto.key.Mode.DECRYPT;
import static crypto.key.Mode.ENCRYPT;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public class JreCryptoService implements CryptoService {

    private final ThreadLocal<Cipher> localCipher;

    private KeyProvider keyProvider;

    private Base64.Encoder b64Encoder = Base64.getEncoder();
    private Base64.Decoder b64Decoder = Base64.getDecoder();

    public JreCryptoService(KeyProvider keyProvider, String algorithm ) throws GeneralSecurityException {

        // test that Cipher.getInstance will work later or throw GeneralSecurityException
        Cipher.getInstance( algorithm );

        this.keyProvider = keyProvider;

        this.localCipher = ThreadLocal.withInitial( () -> {
            try {
                return Cipher.getInstance( algorithm );
            } catch (GeneralSecurityException e) {
                throw new RuntimeException( "ThreadLocal failed to instanciate Cipher",  e );
            }
        } );
    }

    public String encrypt(String payload) throws GeneralSecurityException {
        return b64Encoder.encodeToString( encrypt( payload.getBytes() ) );
    }

    public byte[] encrypt(byte[] payload) throws GeneralSecurityException {
        Cipher cipher = localCipher.get();
        cipher.init( Cipher.ENCRYPT_MODE, keyProvider.getKey( ENCRYPT ) );
        return new Record( cipher.getIV(), cipher.doFinal( payload ) ).getBytes();
    }

    public String decrypt(String ciphered) throws GeneralSecurityException {
        return new String( decrypt( b64Decoder.decode( ciphered ) ) );
    }

    public byte[] decrypt(byte[] ciphered) throws GeneralSecurityException {
        Cipher decipher = localCipher.get();
        Record record = new Record( ciphered );
        decipher.init( Cipher.DECRYPT_MODE, keyProvider.getKey( DECRYPT ), new IvParameterSpec( record.getIv() ) );
        return decipher.doFinal( record.getData() );
    }
}
