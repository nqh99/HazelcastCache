package client;

import cache.CacheManager;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.core.HazelcastInstance;

public abstract class Client {
    private HazelcastInstance client;
    public Client() {
        ClientConfig clientConfig = new ClientConfig();
        client = HazelcastClient.newHazelcastClient(clientConfig);
    }
    public abstract void run();

    public HazelcastInstance getClient() {
        return client;
    }
}
