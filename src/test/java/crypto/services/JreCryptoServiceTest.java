package crypto.services;

import crypto.key.KeyProvider;
import crypto.key.Mode;
import org.junit.Test;

import javax.crypto.KeyGenerator;
import java.security.GeneralSecurityException;
import java.security.Key;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by julienfurgerot on 19/05/2017.
 */
public class JreCryptoServiceTest {

    @Test
    public void cipherDecipher( ) throws GeneralSecurityException {
        // Given
        String payload = "Hello World!";
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init( 128 );
        Key key = keyGenerator.generateKey();
        KeyProvider keyProvider = mock( KeyProvider.class );
        when( keyProvider.getKey( any( Mode.class ) ) ).thenReturn( key );

        JreCryptoService cs = new JreCryptoService( keyProvider, "AES/CBC/PKCS5Padding" );

        // When
        String result = cs.decrypt( cs.encrypt( payload ) );

        // Then
        assertThat( result, is( equalTo( payload ) ) );
    }
}