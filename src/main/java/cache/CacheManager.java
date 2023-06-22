package cache;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.collection.IList;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import dao.BookRepository;
import model.BookModel;

import java.util.List;

public class CacheManager {
    protected static HazelcastInstance clusterInstance;

    static {
        clusterInstance = Hazelcast.newHazelcastInstance();
        initCache();
    }

    private static void initCache() {
        new BookCache();
    }

    public static void shutdownCluster() {
        clusterInstance.shutdown();
    }

    public HazelcastInstance getCluster() {
        return clusterInstance;
    }
}
