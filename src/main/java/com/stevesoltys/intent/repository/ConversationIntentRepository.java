package com.stevesoltys.intent.repository;

import com.stevesoltys.intent.Intent;
import com.stevesoltys.intent.IntentRepository;
import com.stevesoltys.intent.model.IntentEntry;
import lombok.Getter;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Steve Soltys
 */
public class ConversationIntentRepository implements IntentRepository {

    @Getter
    private Set<IntentEntry> entries = new HashSet<>();

    public IntentEntry getEntry(Intent intent) {
        return entries.stream()
                .filter(entry -> entry.getIntent().equals(intent))
                .findAny().orElse(null);
    }

    public void register(IntentEntry entry) {
        entries.add(entry);
    }

    @Override
    public Optional<Intent> getIntentByIdentifier(String identifier) {
        return entries.stream()
                .map(IntentEntry::getIntent)
                .filter(entry -> entry.getIdentifier().equals(identifier))
                .findAny();
    }

    @Override
    public Set<Intent> getIntents() {
        return entries.stream().map(IntentEntry::getIntent).collect(Collectors.toSet());
    }
}
