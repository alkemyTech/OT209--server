package com.alkemy.ong.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alkemy.ong.models.request.CategoryRequest;
import com.alkemy.ong.models.response.CategoryBasicResponse;
import com.alkemy.ong.models.response.CategoryResponse;
import com.alkemy.ong.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/categories")
@CrossOrigin("*")
@RequiredArgsConstructor
public class CategoryController {
	@Autowired
	private CategoryService categoryService;
	
	@GetMapping
	public ResponseEntity<List<CategoryBasicResponse>> getAll(){
		List<CategoryBasicResponse> response = categoryService.getCategories();
		return ResponseEntity.ok(response);
	}
	
	@PostMapping
	public ResponseEntity<CategoryResponse> save(@Valid @RequestBody CategoryRequest categoryRequest){
		CategoryResponse response = categoryService.saveCategory(categoryRequest);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<CategoryResponse> update (@PathVariable Long id, @Valid @RequestBody CategoryRequest categoryRequest){
		CategoryResponse response = categoryService.updateCategory(id, categoryRequest);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteCategory(@PathVariable Long id){
		categoryService.delete(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
