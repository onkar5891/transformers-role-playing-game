package org.hasbro.transformers.resource;

public final class TransformerMode implements Cybertronian.AlternateMode {
    private final String identity;
    private final int powerImpact;

    public TransformerMode(String identity, int powerImpact) {
        this.identity = identity;
        this.powerImpact = powerImpact;
    }

    @Override
    public String getIdentity() {
        return this.identity;
    }

    @Override
    public int powerImpact() {
        return this.powerImpact;
    }
}
