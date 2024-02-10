package org.safal.bookify.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {
    @Id
    @GeneratedValue
    private long book_id;

    //(picture , book name , author name , description(500 words) , book file(epub))
   private String book_name;

   private String author_name;

   private long no_of_downloads;

   private long no_of_ratings;

   private float rating ;

   private String category;

   

   @Column(length = 500)
   private String description;

   private String cover_image_path;

   private String book_file_path;

   @ManyToOne(fetch=FetchType.LAZY)  
   private Users user;
   


   
}
