package org.com.bio.inghub.domain.model.enums;

import lombok.Getter;

@Getter
public enum Installment {
            SIX(6),
            NINE(9),
            TWELVE(12),
            TWENTY_FOUR(24);

    private final int value;

    Installment(int value) {
        this.value = value;
    }
}
