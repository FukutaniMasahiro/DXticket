package com.example.mintikeissues.repository.mybatis;

import com.example.mintikeissues.domain.Attachment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttachmentMapper {
    int insert(Attachment attachment);
    List<Attachment> selectByIssueId(@Param("issueId") long issueId);
    int deleteByIssueId(@Param("issueId") long issueId);
}


