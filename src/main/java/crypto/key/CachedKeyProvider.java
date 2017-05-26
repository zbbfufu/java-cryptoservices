package crypto.key;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.security.Key;
import java.util.concurrent.TimeUnit;

/**
 * Created by julienfurgerot on 17/05/2017.
 */
public class CachedKeyProvider implements KeyProvider {

    private LoadingCache<Mode,Key> keyCache;

    public CachedKeyProvider( KeyProvider keyProvider, int cacheDuration, TimeUnit timeUnit ) {
        keyCache = CacheBuilder.newBuilder()
                .expireAfterAccess( cacheDuration, timeUnit )
                .build(new CacheLoader<Mode, Key>() {
                    @Override
                    public Key load( Mode mode ) throws Exception {
                        return keyProvider.getKey( mode );
                    }
                });
    }

    @Override
    public Key getKey( Mode mode ) {
        return keyCache.getUnchecked( mode );
    }

    @Override
    public String getKeyAlgorithm( Mode mode ) {
        return keyCache.getUnchecked( mode ).getAlgorithm();
    }
}
