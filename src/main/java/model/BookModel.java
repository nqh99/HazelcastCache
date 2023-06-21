package model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookModel implements Comparable<BookModel> {
    private int id;
    private String name;
    private String author;
    private BigDecimal price;
    private String createBy;
    private String lastModify;

    @Override
    public int compareTo(BookModel o) {
        return Integer.compare(id, o.getId());
    }
}
