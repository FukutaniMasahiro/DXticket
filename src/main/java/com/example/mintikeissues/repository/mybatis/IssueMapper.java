package com.example.mintikeissues.repository.mybatis;

import com.example.mintikeissues.domain.Issue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IssueMapper {
    List<Issue> selectAll();
    Issue selectById(@Param("id") long id);
    int insert(Issue issue);
    int update(Issue issue);
    int delete(@Param("id") long id);

    // categories
    int deleteIssueCategories(@Param("issueId") long issueId);
    int insertIssueCategory(@Param("issueId") long issueId, @Param("categoryId") int categoryId);
}


