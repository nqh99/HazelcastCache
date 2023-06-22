import cache.CacheManager;
import client.Client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.hazelcast.core.HazelcastInstance;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class App {
    private static final ExecutorService threadPool = Executors.newCachedThreadPool();
    public static void main(String[] args) throws JsonProcessingException {
        new CacheManager();

        new CacheManager();
    }
}
