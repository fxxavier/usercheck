package com.github.felipesx.usercheck.dto;

public record VoteStatusResponse(String status) {
    public static VoteStatusResponse ableToVote() {
        return new VoteStatusResponse("ABLE_TO_VOTE");
    }

    public static VoteStatusResponse unableToVote() {
        return new VoteStatusResponse("UNABLE_TO_VOTE");
    }
}
