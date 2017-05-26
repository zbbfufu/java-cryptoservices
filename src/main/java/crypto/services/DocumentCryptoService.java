package crypto.services;

import crypto.key.KeyProvider;
import crypto.key.Mode;

import javax.crypto.spec.SecretKeySpec;
import java.security.GeneralSecurityException;
import java.security.Key;

import static crypto.key.Mode.DECRYPT;

/**
 * Created by julienfurgerot on 18/05/2017.
 */
public class DocumentCryptoService {

    private final CryptoService cryptoService;
    private final KeyProvider keyGenerator;

    public DocumentCryptoService(CryptoService cryptoService, KeyProvider keyGenerator) {
        this.cryptoService = cryptoService;
        this.keyGenerator = keyGenerator;
    }

    public DocAndKeyPair encrypt(byte[] docContent ) throws GeneralSecurityException {
        Key key = keyGenerator.getKey( Mode.ENCRYPT );
        byte[] cipheredContent = cryptoService.encrypt( docContent, key );
        return new DocAndKeyPair( cipheredContent, key.getEncoded() );
    }

    public byte[] decrypt( DocAndKeyPair docAndKeyPair ) throws GeneralSecurityException {
        Key decryptKey = new SecretKeySpec( docAndKeyPair.getKeyBytes(), keyGenerator.getKeyAlgorithm( DECRYPT ) );
        return cryptoService.decrypt(docAndKeyPair.getDocContent(), decryptKey );
    }

    public static class DocAndKeyPair {
        private final byte[] docContent;
        private final byte[] keyBytes;

        public DocAndKeyPair(byte[] docContent, byte[] keyBytes) {
            this.keyBytes = keyBytes;
            this.docContent = docContent;
        }

        public byte[] getKeyBytes() {
            return keyBytes;
        }

        public byte[] getDocContent() {
            return docContent;
        }
    }
}
