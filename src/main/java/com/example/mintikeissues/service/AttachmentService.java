package com.example.mintikeissues.service;

import com.example.mintikeissues.domain.Attachment;
import com.example.mintikeissues.repository.mybatis.AttachmentMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Service
public class AttachmentService {
    private final AttachmentMapper mapper;
    private final Path uploadDir;

    public AttachmentService(AttachmentMapper mapper,
                             @Value("${app.data.dir:data}") String dataDir) throws IOException {
        this.mapper = mapper;
        this.uploadDir = Path.of(dataDir, "uploads");
        Files.createDirectories(this.uploadDir);
    }

    public void saveAll(long issueId, MultipartFile[] files) {
        if (files == null) return;
        for (MultipartFile f : files) {
            if (f.isEmpty()) continue;
            try {
                String ext = extractExt(f.getOriginalFilename());
                String stored = UUID.randomUUID().toString().replace("-", "") + (ext.isEmpty() ? "" : ("." + ext));
                Path dest = uploadDir.resolve(stored);
                f.transferTo(dest);

                Attachment a = new Attachment();
                a.setIssueId(issueId);
                a.setOriginalName(f.getOriginalFilename());
                a.setStoredPath(dest.toString());
                a.setContentType(f.getContentType());
                a.setSizeBytes(f.getSize());
                mapper.insert(a);
            } catch (IOException e) {
                throw new RuntimeException("添付ファイル保存に失敗しました", e);
            }
        }
    }

    public List<Attachment> listByIssueId(long issueId) {
        return mapper.selectByIssueId(issueId);
    }

    public void deleteByIssueId(long issueId) {
        mapper.deleteByIssueId(issueId);
    }

    private String extractExt(String name) {
        if (name == null) return "";
        int i = name.lastIndexOf('.');
        return i > -1 ? name.substring(i + 1) : "";
    }
}


