package com.example.mintikeissues.web;

import com.example.mintikeissues.domain.Issue;
import com.example.mintikeissues.domain.Status;
import com.example.mintikeissues.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Arrays;

@Controller
@RequestMapping("/")
public class IssueController {
    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    public String index() {
        return "redirect:/issues";
    }

    @GetMapping("/issues")
    public String list(Model model, @RequestParam(value = "q", required = false) String query) {
        model.addAttribute("issues", issueService.listAll());
        model.addAttribute("statuses", Arrays.asList(Status.values()));
        model.addAttribute("q", query == null ? "" : query);
        return "search";
    }

    @GetMapping("/issues/new")
    public String createForm(Model model) {
        Issue issue = new Issue();
        issue.setIssueDate(LocalDate.now());
        issue.setStatus(Status.受付);
        model.addAttribute("issue", issue);
        model.addAttribute("statuses", Arrays.asList(Status.values()));
        return "register";
    }

    @PostMapping("/issues")
    public String create(@Valid @ModelAttribute("issue") Issue issue,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", Arrays.asList(Status.values()));
            return "register";
        }
        issueService.create(issue);
        return "redirect:/issues";
    }

    @GetMapping("/issues/{id}/edit")
    public String editForm(@PathVariable("id") long id, Model model) {
        Issue issue = issueService.findById(id).orElseThrow();
        model.addAttribute("issue", issue);
        model.addAttribute("statuses", Arrays.asList(Status.values()));
        return "register";
    }

    @PostMapping("/issues/{id}")
    public String update(@PathVariable("id") long id,
                         @Valid @ModelAttribute("issue") Issue issue,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", Arrays.asList(Status.values()));
            return "register";
        }
        issue.setId(id);
        issueService.update(issue);
        return "redirect:/issues";
    }

    @PostMapping("/issues/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        issueService.delete(id);
        return "redirect:/issues";
    }
}


