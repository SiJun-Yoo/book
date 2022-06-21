package com.simba.book.controller;

import com.simba.book.dto.PostResponseDto;
import com.simba.book.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final PostService postService;

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("posts",postService.findAllDesc());
        return "index";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model){
        PostResponseDto dto = postService.findById(id);
        model.addAttribute("post",dto);
        return "post-update";
    }


}
