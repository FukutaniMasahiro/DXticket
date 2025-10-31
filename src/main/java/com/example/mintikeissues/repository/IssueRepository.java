package com.example.mintikeissues.repository;

import com.example.mintikeissues.domain.Issue;

import java.util.List;
import java.util.Optional;

public interface IssueRepository {
    List<Issue> findAll();
    Optional<Issue> findById(long id);
    Issue save(Issue issue);
    Issue update(Issue issue);
    boolean deleteById(long id);
}


