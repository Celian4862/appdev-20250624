package com.example.demo.controller;

import com.example.demo.model.PhotoModel;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PhotoController {
  private List<PhotoModel> db = List.of(new PhotoModel("1", "Photo1"),
                                         new PhotoModel("2", "Photo2"),
                                         new PhotoModel("3", "Photo3"));

  @GetMapping("/photo")
  private List<PhotoModel> getPhoto() {
    return db;
  }

  @GetMapping("/photo/{id}")
  private PhotoModel getPhotoById(@PathVariable String id) {
    return db.stream()
             .filter(photo -> photo.getId().equals(id))
             .findFirst()
             .orElse(null);
  }
}
