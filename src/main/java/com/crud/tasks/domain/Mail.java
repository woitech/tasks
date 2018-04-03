package com.crud.tasks.domain;

import lombok.*;

@RequiredArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class Mail {
    @NonNull
    private String receiverEmail;

    @NonNull
    private String subject;

    @NonNull
    private String message;

    private String toCc;
}
