package com.akproject.runshare.controllers;

import com.akproject.runshare.models.Comment;
import com.akproject.runshare.models.DTO.NewCommentDTO;
import com.akproject.runshare.models.Runner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.Console;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/comments")
public class CommentController extends MainController{

    @GetMapping
    public String displayCommentIndex(HttpServletRequest request, Model model){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Comments");
        model.addAttribute("comments", commentRepository.findFirst10ByOrderByDateCreatedDescTimeCreatedDesc());
        return "/comments/index";
    }

    @GetMapping(value={"/createComment", "/createComment/{id}"})
    public String displayCreateComment(@PathVariable(required=false) Integer entityId, HttpServletRequest request, Model model){
        setRunnerInModel(request, model);
        model.addAttribute("title", "Create Comment");
        model.addAttribute("nullTrail", null);
        model.addAttribute("nullRunSession", null);
        model.addAttribute("runners", runnerRepository.findAll());
        model.addAttribute("trails", trailRepository.findAll());
        model.addAttribute("runSessions", runSessionRepository.findAll());
        model.addAttribute(new NewCommentDTO());
        return "/comments/createComment";
    }

    @PostMapping("/createComment")
    public String processCreateComment(@ModelAttribute @Valid NewCommentDTO newCommentDTO, @RequestParam(value = "runnersList", required=false) int[] runnersList, Errors errors, Model model, HttpServletRequest request, HttpSession session){
        setRunnerInModel(request, model);
        if (errors.hasErrors()){
            model.addAttribute("title", "Create Comment");
            model.addAttribute("nullTrail", null);
            model.addAttribute("nullRunSession", null);
            model.addAttribute("runners", runnerRepository.findAll());
            model.addAttribute("trails", trailRepository.findAll());
            model.addAttribute("runSessions", runSessionRepository.findAll());
            return "/comments/createComment";
        }

        if (runnersList != null) {
            for (int value : runnersList) {
                newCommentDTO.runners.add(runnerRepository.findById(value));
            }
        }
        newCommentDTO.runners.add(runnerRepository.findById(getRunnerFromSession(session).getId()).get());
        HttpSession commentSession = request.getSession();
        Runner commentCreator = getRunnerFromSession(commentSession);
        Comment savedComment = new Comment(newCommentDTO.getMessageTitle(), newCommentDTO.getMessage(), commentCreator, LocalDate.now(), LocalTime.now(), newCommentDTO.getTrail(), newCommentDTO.getRunSession(), newCommentDTO.getRunners(), newCommentDTO.getPrivateMessage());
        commentRepository.save(savedComment);
        return "redirect:/comments";


    }
}
