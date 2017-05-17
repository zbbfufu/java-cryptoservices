package crypto.key;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * Created by julienfurgerot on 17/05/2017.
 */
public class KeyStoreKeyProvider implements KeyProvider {

    private KeyStore keyStore;
    private String encryptKeyAlias;
    private char[] encryptKeyPassword;
    private String decryptKeyAlias;
    private char[] decryptKeyPassword;

    public KeyStoreKeyProvider(
            KeyStore keyStore,
            String encryptKeyAlias, char[] encryptKeyPassword,
            String decryptKeyAlias, char[] decryptKeyPassword
    ) {
        this.keyStore = keyStore;
        this.encryptKeyAlias = encryptKeyAlias;
        this.encryptKeyPassword = encryptKeyPassword;
        this.decryptKeyAlias = decryptKeyAlias;
        this.decryptKeyPassword = decryptKeyPassword;
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


    @Override
    public Key getKey(Mode mode) throws GeneralSecurityException {
        switch ( mode ) {
            case ENCRYPT: return keyStore.getKey( encryptKeyAlias, encryptKeyPassword );
            case DECRYPT: return keyStore.getKey( decryptKeyAlias, decryptKeyPassword );
            default: throw new IllegalArgumentException( "Unknow mode" );
        }
    }
}
