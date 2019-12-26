package com.revolut.money.domain.transaction;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Transaction {
    @Id
    private String id;

    private boolean completed;

    protected Transaction() {}

    public Transaction(String id) {
        this.id = id;
        this.completed = false;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getId() {
        return id;
    }

    public void complete() {
        completed = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction request = (Transaction) o;
        return completed == request.completed &&
                Objects.equals(id, request.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, completed);
    }
}
