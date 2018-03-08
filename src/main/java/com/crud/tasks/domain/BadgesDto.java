package com.crud.tasks.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BadgesDto {
    @JsonProperty("votes")
    private Integer votes;

    @JsonProperty("attachmentsByType")
    private AttachmentsByTypeDto attachmentsByType;

    @JsonProperty("viewingMemberVoted")
    private Boolean viewingMemberVoted;

    @JsonProperty("subscribed")
    private Boolean subscribed;

    @JsonProperty("fogbugz")
    private String fogbugz;

    @JsonProperty("checkItems")
    private Integer checkItems;

    @JsonProperty("checkItemsChecked")
    private Integer checkItemsChecked;

    @JsonProperty("comments")
    private Integer comments;

    @JsonProperty("attachments")
    private Integer attachments;

    @JsonProperty("description")
    private Boolean description;

    @JsonProperty("due")
    private String due;

    @JsonProperty("dueComplete")
    private Boolean dueComplete;
}

