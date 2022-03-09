package com.sigmundgranaas.forgero.core.properties.attribute;

import com.sigmundgranaas.forgero.core.properties.TargetTypes;

import java.util.Set;

/**
 * Basic class for containing a single target, like a block.
 */
public record SingleTarget(TargetTypes target, Set<String> tags) implements Target {
    @Override
    public Set<TargetTypes> getTypes() {
        return Set.of(target);
    }

    @Override
    public Set<String> getTags() {
        return tags;
    }
}
