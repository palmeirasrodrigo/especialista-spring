package com.rodrigo.foodapi.domain.model;

import com.rodrigo.foodapi.core.validation.Groups;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "photo_product")
public class PhotoProduct {

    @EqualsAndHashCode.Include
    @Id
    @Column(name = "product_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    private Product product;

    @Column(name = "file_name")
    private String fileName;

    private String description;

    @Column(name = "content_type")
    private String contentType;

    private Long bulk;

    public Long getRestaurantId(){
        if(getProduct() != null){
            return getProduct().getRestaurant().getId();
        }
        return null;
    }
}
