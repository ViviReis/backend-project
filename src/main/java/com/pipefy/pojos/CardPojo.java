package com.pipefy.pojos;

public class CardPojo {
    private String pipeUuid;
    private String phaseUuid;
    private boolean includeParentCards;


    public CardPojo(String pipeUuid, String phaseUuid, boolean includeParentCards) {
        this.pipeUuid = pipeUuid;
        this.phaseUuid = phaseUuid;
        this.includeParentCards = includeParentCards;
    }

    public String getPipeUuid() {
        return pipeUuid;
    }

    public void setPipeUuid(String pipeUuid) {
        this.pipeUuid = pipeUuid;
    }

    public String getPhaseUuid() {
        return phaseUuid;
    }

    public void setPhaseUuid(String phaseUuid) {
        this.phaseUuid = phaseUuid;
    }

    public boolean getIncludeParentCards() {
        return includeParentCards;
    }

    public void setIncludeParentCards(String name) {
        this.includeParentCards = includeParentCards;
    }
}
