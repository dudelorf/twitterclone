package com.ericrkinzel.controllers;

import javax.servlet.ServletContext;

import org.apache.velocity.VelocityContext;
import com.ericrkinzel.models.Post;
import com.ericrkinzel.services.PostService;

public class NewPostController extends BaseController{

    protected PostService postService;

    public NewPostController(
		PostService postService,
		ServletContext context
	){
    	super(context);
        this.postService = postService;    
    }

    public String getNewPostForm(){
        VelocityContext ctx = new VelocityContext();
        return renderView(ctx, "pages/newpost.vm");
    }

    public String processPostForm(String postBody, int userId, String userName){

        Post post = new Post();
        post.setPostBody(postBody);
        post.setUserId(userId);
        post.setUserName(userName);

        if(postBody.length() == 0){
            return "Please enter post.";
        }else if(!postService.saveNewPost(post)){
            return "Unable to save post. Try again later.";
        }else{
            return null;
        }
    }

    public String showPostSaveErrors(String errorMsg){
        
        VelocityContext ctx = new VelocityContext();

        ctx.put("error", errorMsg);

        return renderView(ctx, "pages/newpost.vm");
    }
}