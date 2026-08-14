package br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.data.dto.v1;

import br.com.beltsistemas.springBoot_curso_0_a_AWSeGCP.model.Book;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;

public class BookDTO extends RepresentationModel<BookDTO> {

    private Integer id;

    private String author;

    private Date launch_date;

    private BigDecimal price;

    private String title;

    public BookDTO() {
    }

    public BookDTO(String author, Integer id, Date launch_date, BigDecimal price, String title) {
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
        if (!super.equals(o)) return false;
        BookDTO bookDTO = (BookDTO) o;
        return Objects.equals(getId(), bookDTO.getId()) && Objects.equals(getAuthor(), bookDTO.getAuthor()) && Objects.equals(getLaunch_date(), bookDTO.getLaunch_date()) && Objects.equals(getPrice(), bookDTO.getPrice()) && Objects.equals(getTitle(), bookDTO.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getId(), getAuthor(), getLaunch_date(), getPrice(), getTitle());
    }
}
