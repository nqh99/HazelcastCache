package cache;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.map.IMap;
import com.hazelcast.map.MapEvent;
import com.hazelcast.map.listener.*;
import dao.BookRepository;
import model.BookModel;

public class BookCache {
    private BookRepository bookRepo = new BookRepository();
    public BookCache() {
        IMap<Integer, BookModel> books = CacheManager.clusterInstance.getMap("BOOKS");
        books.addEntryListener(new BookEntryListener(), true);
    }

    /**
     * Listener for book cache
     * Using write-through caching design pattern
     */
    private class BookEntryListener implements EntryAddedListener<Integer, BookModel>
            , EntryRemovedListener<Integer, BookModel>
            , EntryUpdatedListener<Integer, BookModel>
            , EntryEvictedListener<Integer, BookModel>
            , MapEvictedListener
            , MapClearedListener {
        @Override
        public void entryAdded(EntryEvent<Integer, BookModel> event) {
            System.out.println("Insert new book.");
            System.out.println(bookRepo.insert(event.getValue()));
        }

        @Override
        public void entryEvicted(EntryEvent<Integer, BookModel> event) {
            System.out.println("Evict operation" + event);
        }

        @Override
        public void entryRemoved(EntryEvent<Integer, BookModel> event) {
            System.out.println("Insert new book.");
            System.out.println(bookRepo.insert(event.getValue()));
        }

        @Override
        public void entryUpdated(EntryEvent<Integer, BookModel> event) {
            System.out.println("Update book.");
            BookModel book = event.getValue();
            System.out.println(bookRepo.updateByID(book.getId(), book));
        }

        @Override
        public void mapCleared(MapEvent event) {
            System.out.println("Clear all book cache");
        }

        @Override
        public void mapEvicted(MapEvent event) {
            System.out.println("Evict all book cache");
        }
    }
}
