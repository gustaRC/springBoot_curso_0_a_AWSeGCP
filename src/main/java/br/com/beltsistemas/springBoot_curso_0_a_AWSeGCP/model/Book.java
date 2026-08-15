package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model;

import jakarta.persistence.*;
import org.hibernate.temporal.TemporalTableStrategy;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String author;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE) //Professor utilizou essa abordagem depreciada. Contudo, recomenda-se utilizar a abordagem do Java 8 (LocalDate ou LocalDateTime) para lidar com datas.
    private Date launch_date;

    @Column(nullable = false, precision = 65, scale = 2)
    private BigDecimal price;

    private String title;

    public Book() {
    }

    public Book(String author, Integer id, Date launch_date, BigDecimal price, String title) {
        setAuthor(author);
        setId(id);
        setLaunch_date(launch_date);
        setPrice(price);
        setTitle(title);
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLaunch_date() {
        return launch_date;
    }

    public void setLaunch_date(Date launch_date) {
        this.launch_date = launch_date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(getId(), book.getId()) && Objects.equals(getAuthor(), book.getAuthor()) && Objects.equals(getLaunch_date(), book.getLaunch_date()) && Objects.equals(getPrice(), book.getPrice()) && Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
    }
}
