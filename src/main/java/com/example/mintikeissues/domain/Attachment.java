package com.example.mintikeissues.domain;

public class Attachment {
    private long id;
    private long issueId;
    private String originalName;
    private String storedPath;
    private String contentType;
    private long sizeBytes;

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    public long getIssueId() { return issueId; }
    public void setIssueId(long issueId) { this.issueId = issueId; }
    public String getOriginalName() { return originalName; }
    public void setOriginalName(String originalName) { this.originalName = originalName; }
    public String getStoredPath() { return storedPath; }
    public void setStoredPath(String storedPath) { this.storedPath = storedPath; }
    public String getContentType() { return contentType; }
    public void setContentType(String contentType) { this.contentType = contentType; }
    public long getSizeBytes() { return sizeBytes; }
    public void setSizeBytes(long sizeBytes) { this.sizeBytes = sizeBytes; }
}


