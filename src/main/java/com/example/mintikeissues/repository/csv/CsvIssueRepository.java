package com.example.mintikeissues.repository.csv;

import com.example.mintikeissues.domain.Issue;
import com.example.mintikeissues.domain.Status;
import com.example.mintikeissues.repository.IssueRepository;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class CsvIssueRepository implements IssueRepository {

    private final Path csvPath;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public CsvIssueRepository(@Value("${app.csv.issues-path:data/issues.csv}") String csvPathStr) {
        this.csvPath = Path.of(csvPathStr);
        ensureFile();
    }

    private void ensureFile() {
        try {
            if (Files.notExists(csvPath.getParent())) {
                Files.createDirectories(csvPath.getParent());
            }
            if (Files.notExists(csvPath)) {
                try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
                    CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                            "id", "issueDate", "title", "description", "proposer", "status"));
                    printer.flush();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize CSV file: " + csvPath, e);
        }
    }

    @Override
    public List<Issue> findAll() {
        lock.readLock().lock();
        try {
            List<Issue> list = new ArrayList<>();
            try (CSVParser parser = CSVParser.parse(csvPath, StandardCharsets.UTF_8,
                    CSVFormat.DEFAULT.withFirstRecordAsHeader())) {
                for (CSVRecord r : parser) {
                    list.add(recordToIssue(r));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            list.sort(Comparator.comparingLong(Issue::getId));
            return list;
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public Optional<Issue> findById(long id) {
        return findAll().stream().filter(i -> i.getId() == id).findFirst();
    }

    @Override
    public Issue save(Issue issue) {
        lock.writeLock().lock();
        try {
            List<Issue> list = findAll();
            list.add(issue);
            writeAll(list);
            return issue;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public Issue update(Issue issue) {
        lock.writeLock().lock();
        try {
            List<Issue> list = findAll();
            boolean replaced = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() == issue.getId()) {
                    list.set(i, issue);
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                throw new IllegalArgumentException("Issue not found: id=" + issue.getId());
            }
            writeAll(list);
            return issue;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean deleteById(long id) {
        lock.writeLock().lock();
        try {
            List<Issue> list = findAll();
            boolean removed = list.removeIf(i -> i.getId() == id);
            if (removed) {
                writeAll(list);
            }
            return removed;
        } finally {
            lock.writeLock().unlock();
        }
    }

    private Issue recordToIssue(CSVRecord r) {
        Issue issue = new Issue();
        issue.setId(Long.parseLong(r.get("id")));
        issue.setIssueDate(LocalDate.parse(r.get("issueDate")));
        issue.setTitle(r.get("title"));
        issue.setDescription(r.get("description"));
        issue.setProposer(r.get("proposer"));
        issue.setStatus(Status.fromDisplay(r.get("status")));
        return issue;
    }

    private void writeAll(List<Issue> list) {
        try (BufferedWriter writer = Files.newBufferedWriter(csvPath, StandardCharsets.UTF_8)) {
            CSVPrinter printer = new CSVPrinter(writer, CSVFormat.DEFAULT.withHeader(
                    "id", "issueDate", "title", "description", "proposer", "status"));
            for (Issue i : list) {
                printer.printRecord(
                        i.getId(),
                        i.getIssueDate(),
                        i.getTitle(),
                        i.getDescription(),
                        i.getProposer(),
                        i.getStatus().name()
                );
            }
            printer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}


