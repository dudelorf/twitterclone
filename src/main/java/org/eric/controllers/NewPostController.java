package org.eric.controllers;

import org.eric.services.PostService;

public class NewPostController extends BaseController{

    PostService postService;

    public NewPostController(PostService postService){
        this.postService = postService;    
    }

}