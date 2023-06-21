package dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.BookModel;

import java.io.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class BookRepository {

    private String resource = System.getProperty("user.dir") + "/src/main/resources/Resource.txt";

    public List<BookModel> queryBooks() {
        try(BufferedReader reader = new BufferedReader(new FileReader(resource))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<BookModel> books = objectMapper.readValue(content.toString(), new TypeReference<List<BookModel>>() {});
            return books;
        } catch (Exception e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
            return null;
        }
    }

    public BookModel queryByID(int id) {
        AtomicReference<BookModel> ret = new AtomicReference<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(resource))) {
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<BookModel> books = objectMapper.readValue(content.toString(), new TypeReference<List<BookModel>>() {});

            books.forEach(e -> {
                if(e.getId() == id)
                    ret.set(e);
            });

            return ret.get();
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            throw new RuntimeException(e);
        }
    }

    public boolean insert(BookModel model) {
        AtomicBoolean ret = new AtomicBoolean(true);
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(resource));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<BookModel> books = objectMapper.readValue(content.toString(), new TypeReference<List<BookModel>>() {});
            books.forEach(e -> {
                if (e.getId() == model.getId()) {
                    ret.set(false);
                }
            });

            if (ret.get()) {
                books.add(model);
                Collections.sort(books);
                writer = new BufferedWriter(new FileWriter(resource));
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, books);
            }
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred while close resource.");
                e.printStackTrace();
            }
        }
        return ret.get();
    }

    public BookModel updateByID(int id, BookModel model) {
        AtomicReference<BookModel> ret = new AtomicReference<>();
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new FileReader(resource));
            StringBuilder content = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }

            ObjectMapper objectMapper = new ObjectMapper();
            List<BookModel> books = objectMapper.readValue(content.toString(), new TypeReference<List<BookModel>>() {});

            books.forEach(e -> {
                if(e.getId() == id) {
                    ret.set(e);
                }
            });
            if(ret.get() != null) {
                BookModel temp = ret.get();
                temp.setId(id);
                temp.setName(model.getName());
                temp.setAuthor(model.getAuthor());
                temp.setPrice(model.getPrice());
                temp.setCreateBy(model.getCreateBy());
                temp.setLastModify(model.getLastModify());
            } else {
                ret.set(model);
                books.add(model);
            }
            writer = new BufferedWriter(new FileWriter(resource));
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(writer, books);
        } catch (IOException e) {
            System.out.println("An error occurred while reading the file.");
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.out.println("An error occurred while close resource.");
                e.printStackTrace();
            }
        }
        return ret.get();
    }
}
