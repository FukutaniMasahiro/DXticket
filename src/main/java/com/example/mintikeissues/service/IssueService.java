package com.example.mintikeissues.service;

import com.example.mintikeissues.domain.Issue;
import com.example.mintikeissues.repository.IssueRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    public IssueService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    public List<Issue> listAll() {
        return issueRepository.findAll();
    }

    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }

    public Issue create(@Valid Issue issue) {
        return issueRepository.save(issue);
    }

    public Issue update(@Valid Issue issue) {
        return issueRepository.update(issue);
    }

    public boolean delete(long id) {
        return issueRepository.deleteById(id);
    }
}


