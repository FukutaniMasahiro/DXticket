package com.example.mintikeissues.repository.csv;

import com.example.mintikeissues.repository.SequenceRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Repository
public class CsvSequenceRepository implements SequenceRepository {

    private final Path seqPath;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public CsvSequenceRepository(@Value("${app.csv.sequence-path:data/sequence.csv}") String seqPathStr) {
        this.seqPath = Path.of(seqPathStr);
        ensureFile();
    }

    private void ensureFile() {
        try {
            if (Files.notExists(seqPath.getParent())) {
                Files.createDirectories(seqPath.getParent());
            }
            if (Files.notExists(seqPath)) {
                Files.writeString(seqPath, "0", StandardCharsets.UTF_8);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize sequence file: " + seqPath, e);
        }
    }

    @Override
    public long nextId() {
        lock.writeLock().lock();
        try {
            long current;
            try {
                String content = Files.readString(seqPath, StandardCharsets.UTF_8).trim();
                current = content.isEmpty() ? 0 : Long.parseLong(content);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            long next = current + 1;
            try {
                Files.writeString(seqPath, Long.toString(next), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return next;
        } finally {
            lock.writeLock().unlock();
        }
    }
}


