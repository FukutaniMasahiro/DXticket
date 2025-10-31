package com.example.mintikeissues.repository.mybatis;

import com.example.mintikeissues.domain.Issue;
import com.example.mintikeissues.repository.IssueRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Primary
public class MyBatisIssueRepository implements IssueRepository {

    private final IssueMapper mapper;

    public MyBatisIssueRepository(IssueMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Issue> findAll() {
        return mapper.selectAll();
    }

    @Override
    public Optional<Issue> findById(long id) {
        return Optional.ofNullable(mapper.selectById(id));
    }

    @Override
    public Issue save(Issue issue) {
        mapper.insert(issue);
        // categories
        if (issue.getCategoryIds() != null) {
            for (Integer cid : issue.getCategoryIds()) {
                mapper.insertIssueCategory(issue.getId(), cid);
            }
        }
        return issue;
    }

    @Override
    public Issue update(Issue issue) {
        mapper.update(issue);
        mapper.deleteIssueCategories(issue.getId());
        if (issue.getCategoryIds() != null) {
            for (Integer cid : issue.getCategoryIds()) {
                mapper.insertIssueCategory(issue.getId(), cid);
            }
        }
        return issue;
    }

    @Override
    public boolean deleteById(long id) {
        mapper.deleteIssueCategories(id);
        return mapper.delete(id) > 0;
    }
}


