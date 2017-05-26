package crypto.key;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyStore;

/**
 * Created by julienfurgerot on 17/05/2017.
 */
public class KeyStoreKeyProvider implements KeyProvider {

    private KeyStore keyStore;
    private String encryptKeyAlias;
    private char[] encryptKeyPassword;
    private String encryptkeyAlgorithm;
    private String decryptKeyAlias;
    private char[] decryptKeyPassword;
    private String decryptkeyAlgorithm;


    public KeyStoreKeyProvider(
            KeyStore keyStore,
            String encryptKeyAlias, char[] encryptKeyPassword,
            String decryptKeyAlias, char[] decryptKeyPassword
    ) throws GeneralSecurityException {
        this.keyStore = keyStore;
        this.encryptKeyAlias = encryptKeyAlias;
        this.encryptKeyPassword = encryptKeyPassword;
        this.encryptkeyAlgorithm = getAlgorithm( keyStore, encryptKeyAlias, encryptKeyPassword);
        this.decryptKeyAlias = decryptKeyAlias;
        this.decryptKeyPassword = decryptKeyPassword;
        this.decryptkeyAlgorithm = getAlgorithm( keyStore, decryptKeyAlias, decryptKeyPassword);
    }

    public KeyStoreKeyProvider(
        String keystoreUrl, String keystoreType, char[] keystorePassword,
        String encryptKeyAlias, char[] encryptKeyPassword,
        String decryptKeyAlias, char[] decryptKeyPassword
    ) throws GeneralSecurityException, IOException {
        this(
            loadKeystore( keystoreUrl, keystoreType, keystorePassword),
            encryptKeyAlias, encryptKeyPassword,
            decryptKeyAlias, decryptKeyPassword
        );
    }

    private static KeyStore loadKeystore( String keystoreUrl, String keystoreType, char[] keystorePassword )
            throws GeneralSecurityException, IOException
    {
        try ( InputStream is = new URL( keystoreUrl ).openStream() ) {
            KeyStore keyStore = KeyStore.getInstance( keystoreType );
            keyStore.load( is, keystorePassword );
            return keyStore;
        }
    }

    private static String getAlgorithm( KeyStore ks, String keyAlias, char[] keyPassword ) throws GeneralSecurityException {
        return ks.getKey( keyAlias, keyPassword).getAlgorithm();
    }


    @Override
    public Key getKey(Mode mode) throws GeneralSecurityException {
        switch ( mode ) {
            case ENCRYPT: return keyStore.getKey( encryptKeyAlias, encryptKeyPassword );
            case DECRYPT: return keyStore.getKey( decryptKeyAlias, decryptKeyPassword );
            default: throw new IllegalArgumentException( "Unknow mode" );
        }
    }

    @Override
    public String getKeyAlgorithm(Mode mode) {
        switch ( mode ) {
            case ENCRYPT: return encryptkeyAlgorithm;
            case DECRYPT: return decryptkeyAlgorithm;
            default: throw new IllegalArgumentException( "Unknow mode" );
        }
    }
}
