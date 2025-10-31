package com.example.mintikeissues.web;

import com.example.mintikeissues.domain.Issue;
import com.example.mintikeissues.domain.Status;
import com.example.mintikeissues.service.IssueService;
import jakarta.validation.Valid;
import org.springframework.web.multipart.MultipartFile;
import com.example.mintikeissues.service.AttachmentService;
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
    private final AttachmentService attachmentService;

    public IssueController(IssueService issueService, AttachmentService attachmentService) {
        this.issueService = issueService;
        this.attachmentService = attachmentService;
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
        issue.setStatus(Status.NEW);
        model.addAttribute("issue", issue);
        model.addAttribute("statuses", Arrays.asList(Status.values()));
        return "register";
    }

    @PostMapping("/issues")
    public String create(@Valid @ModelAttribute("issue") Issue issue,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(name = "files", required = false) MultipartFile[] files) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", Arrays.asList(Status.values()));
            return "register";
        }
        Issue created = issueService.create(issue);
        attachmentService.saveAll(created.getId(), files);
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
                         Model model,
                         @RequestParam(name = "files", required = false) MultipartFile[] files) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("statuses", Arrays.asList(Status.values()));
            return "register";
        }
        issue.setId(id);
        issueService.update(issue);
        attachmentService.saveAll(id, files);
        return "redirect:/issues";
    }

    @PostMapping("/issues/{id}/delete")
    public String delete(@PathVariable("id") long id) {
        issueService.delete(id);
        return "redirect:/issues";
    }
}


