package com.suites.server.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.suites.server.core.Chore;

import java.util.List;

public class ChoreView {
    private final Chore chore;
    private final List<Integer> assignees;

    @JsonCreator
    public ChoreView(@JsonProperty("id") int id,
                     @JsonProperty("name") String name,
                     @JsonProperty("description") String description,
                     @JsonProperty("currentTurn") int currentTurn,
                     @JsonProperty("assignees") List<Integer> assignees) {
        chore = new Chore (id, name, description, currentTurn);
        this.assignees = assignees;
    }

    public ChoreView(Chore chore, List<Integer> assignees) {
        this.chore = chore;
        this.assignees = assignees;
    }

    @JsonProperty("id")
    public int getId() {
        return chore.getId();
    }

    @JsonProperty("name")
    public String getName() {
        return chore.getName();
    }

    @JsonProperty("description")
    public String getDescription() {
        return chore.getDescription();
    }

    @JsonProperty("currentTurn")
    public int getCurrentTurn() {
        return chore.getCurrentTurn();
    }

    @JsonProperty("assignees")
    public List<Integer> getAssignees() {
        return assignees;
    }
}
