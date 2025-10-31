package com.example.mintikeissues.domain;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

public class Issue {
    private long id;

    @NotNull
    private LocalDate issueDate;

    // 基本情報
    @NotNull
    private Status status;

    @Size(max = 20)
    private String troubledPerson;

    @Size(max = 20)
    private String ideaPerson;

    @Size(max = 200)
    private String supportMembers;

    // 課題の概要
    @NotBlank
    @Size(max = 100)
    private String title;

    private LocalDate targetDueDate;

    // 課題の詳細
    private String currentProblem;
    private String riskIfUnresolved;

    // 関連情報
    private List<Integer> categoryIds; // categories テーブルの id
    @Size(max = 100)
    private String targetDepartments;

    // 進捗情報
    private String actionDetails;
    private String memo;

    // 完了情報
    private LocalDate completedDate;
    private String effectAfterExecution;
    private String reusability;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getTargetDueDate() { return targetDueDate; }
    public void setTargetDueDate(LocalDate targetDueDate) { this.targetDueDate = targetDueDate; }

    public String getCurrentProblem() { return currentProblem; }
    public void setCurrentProblem(String currentProblem) { this.currentProblem = currentProblem; }

    public String getRiskIfUnresolved() { return riskIfUnresolved; }
    public void setRiskIfUnresolved(String riskIfUnresolved) { this.riskIfUnresolved = riskIfUnresolved; }

    public List<Integer> getCategoryIds() { return categoryIds; }
    public void setCategoryIds(List<Integer> categoryIds) { this.categoryIds = categoryIds; }

    public String getTargetDepartments() { return targetDepartments; }
    public void setTargetDepartments(String targetDepartments) { this.targetDepartments = targetDepartments; }

    public String getActionDetails() { return actionDetails; }
    public void setActionDetails(String actionDetails) { this.actionDetails = actionDetails; }

    public String getMemo() { return memo; }
    public void setMemo(String memo) { this.memo = memo; }

    public LocalDate getCompletedDate() { return completedDate; }
    public void setCompletedDate(LocalDate completedDate) { this.completedDate = completedDate; }

    public String getEffectAfterExecution() { return effectAfterExecution; }
    public void setEffectAfterExecution(String effectAfterExecution) { this.effectAfterExecution = effectAfterExecution; }

    public String getReusability() { return reusability; }
    public void setReusability(String reusability) { this.reusability = reusability; }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getTroubledPerson() { return troubledPerson; }
    public void setTroubledPerson(String troubledPerson) { this.troubledPerson = troubledPerson; }

    public String getIdeaPerson() { return ideaPerson; }
    public void setIdeaPerson(String ideaPerson) { this.ideaPerson = ideaPerson; }

    public String getSupportMembers() { return supportMembers; }
    public void setSupportMembers(String supportMembers) { this.supportMembers = supportMembers; }
}


