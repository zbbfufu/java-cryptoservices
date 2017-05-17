package crypto.services;

import java.security.GeneralSecurityException;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public interface CryptoService {

    String encrypt( String payload ) throws GeneralSecurityException;
    String decrypt( String cipher ) throws GeneralSecurityException;
    byte[] encrypt( byte[] payload ) throws GeneralSecurityException;
    byte[] decrypt( byte[] cipher ) throws GeneralSecurityException;
}
