import client.Client;
import com.hazelcast.client.HazelcastClient;
import com.hazelcast.collection.IList;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import dao.BookRepository;
import model.BookModel;
import org.junit.Test;

import java.math.BigDecimal;

public class Demo {
    private BookRepository bookRepo = new BookRepository();

    /**
     * Using read-through caching design pattern
     * Cache doesn't contain data
     * Add data to cache
     */
    @Test
    public void testFirstClient() {
        // prepare data
        int id = 11;
        BookModel bookModel = new BookModel();
        bookModel.setId(id);
        // run test
        Client client = new Client() {
            @Override
            public void run() {
                readThrough(getClient(), bookModel);
            }
        };
        client.run();
    }

    /**
     * New Client
     * Cache contain data
     * Get data from cache
     */
    @Test
    public void testSecondClient() {
        int id = 11;
        BookModel bookModel = new BookModel();
        bookModel.setId(id);
        Client client = new Client() {
            @Override
            public void run() {
                readThrough(getClient(), bookModel);
            }
        };
        client.run();
    }

    /**
     * Update Client
     * Update content data in cache and write into database
     */
    @Test
    public void testThirdClient() {
        int id = 11;
        BookModel bookModel = new BookModel();
        bookModel.setId(id);
        bookModel.setName("The In-Between: Unforgettable Encounters During Life's Final Moments");
        bookModel.setPrice(BigDecimal.valueOf(40.96));
        bookModel.setAuthor("Harry J. K");
        bookModel.setCreateBy("20/05/2022");
        bookModel.setLastModify("02/06/2023");

        Client client = new Client() {
            @Override
            public void run() {
                IMap bookCache = getClient().getMap("BOOKS");
                bookCache.put(id, bookModel);
            }
        };
        client.run();
    }

    // TODO =========================================================================

    private void logBookInfo(BookModel model) {
        System.out.println(model);
        System.out.println("id: " + model.getId());
        System.out.println("name: " + model.getName());
        System.out.println("price: " + model.getPrice());
        System.out.println("author:" + model.getAuthor());
        System.out.println("create by: " + model.getCreateBy());
        System.out.println("last modify: " + model.getLastModify());
    }

    private void logExecuteTime(long start) {
        System.out.println("----------------");
        System.out.println("Execute time: " + ((double) (System.nanoTime() - start) / 1000000000.0) + " seconds.");
    }

    /**
     * Read-through caching design pattern.
     * @param client
     * @param model
     */
    private void readThrough(HazelcastInstance client, BookModel model) {
        IMap bookCache = client.getMap("BOOKS");
        int id = model.getId();
        // Read data from cluster
        if (bookCache.containsKey(model.getId())) {
            long start = System.nanoTime();
            logBookInfo((BookModel) bookCache.get(id));
            logExecuteTime(start);
            return;
        }
        // Get data from database and write it to cluster
        long startTime = System.nanoTime();
        bookCache.put(id, bookRepo.queryByID(id));
        logExecuteTime(startTime);
    }
}
