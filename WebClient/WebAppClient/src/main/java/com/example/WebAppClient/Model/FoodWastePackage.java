package com.example.WebAppClient.Model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FoodWastePackage {

    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

    private String packageName;
    private int quantity = 1;
    private LocalTime start;
    private LocalTime end;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalDate pickUpDate;

    private String status = "Pending";
    private String description;
    private String category;
    private String[] itemList;

    @ManyToOne
    private Business business;

    @OneToMany (mappedBy = "collection")
    List<Item> items;

    public FoodWastePackage (String packageName, int quantity, LocalTime start, LocalTime end, LocalDate pickUpDate, String description, String category)
    {
        this.packageName = packageName;
        this.quantity = quantity;
        this.start = start;
        this.end = end;
        this.pickUpDate = pickUpDate;
        this.description = description;
        this.category = category;
    }

    public FoodWastePackage (String packageName, int quantity, LocalTime start, LocalTime end, LocalDate pickUpDate, String description, String category, Business business)
    {
        this.packageName = packageName;
        this.quantity = quantity;
        this.start = start;
        this.end = end;
        this.pickUpDate = pickUpDate;
        this.description = description;
        this.category = category;
        this.business = business;
    }

}