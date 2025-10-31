package com.example.mintikeissues.service;

import com.example.mintikeissues.domain.Issue;
import com.example.mintikeissues.repository.IssueRepository;
import com.example.mintikeissues.repository.SequenceRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final SequenceRepository sequenceRepository;

    public IssueService(IssueRepository issueRepository, SequenceRepository sequenceRepository) {
        this.issueRepository = issueRepository;
        this.sequenceRepository = sequenceRepository;
    }

    public List<Issue> listAll() {
        return issueRepository.findAll();
    }

    public Optional<Issue> findById(long id) {
        return issueRepository.findById(id);
    }

    public Issue create(@Valid Issue issue) {
        long id = sequenceRepository.nextId();
        issue.setId(id);
        return issueRepository.save(issue);
    }

    public Issue update(@Valid Issue issue) {
        return issueRepository.update(issue);
    }

    public boolean delete(long id) {
        return issueRepository.deleteById(id);
    }
}


