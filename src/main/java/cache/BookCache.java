package cache;

import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.config.ClientNetworkConfig;
import com.hazelcast.core.HazelcastInstance;

public class BookCache {
    private final String BOOKS = "BOOKS";
    ClientConfig clientConfig = new ClientConfig();
}
