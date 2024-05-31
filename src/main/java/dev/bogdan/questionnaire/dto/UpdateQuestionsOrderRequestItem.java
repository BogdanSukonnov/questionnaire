package dev.bogdan.questionnaire.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateQuestionsOrderRequestItem(

        @NotNull Long questionId,

        @NotNull Integer version,

        @NotNull Integer orderNumber

) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UpdateQuestionsOrderRequestItem that = (UpdateQuestionsOrderRequestItem) o;
        return questionId.equals(that.questionId);
    }

    @Override
    public int hashCode() {
        return questionId.hashCode();
    }
}