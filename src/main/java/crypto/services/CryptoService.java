package crypto.services;

import java.security.GeneralSecurityException;
import java.security.Key;

/**
 * Created by julienfurgerot on 16/05/2017.
 */
public interface CryptoService {

    String encrypt( String payload ) throws GeneralSecurityException;
    byte[] encrypt( byte[] payload ) throws GeneralSecurityException;
    byte[] encrypt( byte[] payload, Key encryptKey ) throws GeneralSecurityException;
    
    String decrypt( String ciphered ) throws GeneralSecurityException;
    byte[] decrypt( byte[] ciphered ) throws GeneralSecurityException;
    byte[] decrypt( byte[] ciphered, Key decryptKey ) throws GeneralSecurityException;
}
