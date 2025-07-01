package com.example.demo.controller;

import com.example.demo.model.PhotoModel;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class PhotoController {
  private final Map<String, PhotoModel> db = new HashMap<>() {{
    put("1", new PhotoModel("1", "photo.jpeg"));
    put("2", new PhotoModel("2", "photo2.jpeg"));
  }};

  @GetMapping("/photo")
  public Collection<PhotoModel> getPhoto() {
    return db.values();
  }

  @PostMapping("/photo")
  public PhotoModel create(@RequestBody PhotoModel photoModel) {
    photoModel.setId(UUID.randomUUID().toString());
    db.put(photoModel.getId(), photoModel);
    return photoModel;
  }

  @GetMapping("/photo/{id}")
  private PhotoModel getPhotoById(@PathVariable String id) {
    PhotoModel photoGetByID = db.get(id);
    if (photoGetByID == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Photo not found");
    }
    return photoGetByID;
  }

  @DeleteMapping("/photo/{id}")
  public void deletePhoto(@PathVariable String id) {
    PhotoModel photoDeleteByID = db.remove(id);
    if (photoDeleteByID == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
  }
}
