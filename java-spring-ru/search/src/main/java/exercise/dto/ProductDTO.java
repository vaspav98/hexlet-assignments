package exercise.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ProductDTO {
    private Long id;
    private Long categoryId;
    private  String categoryName;
    private String title;
    private int price;
    private double rating;
    private Date createdAt;
    private Date updatedAt;
}
