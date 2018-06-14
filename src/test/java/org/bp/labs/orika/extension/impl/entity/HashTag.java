package org.bp.labs.orika.extension.impl.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.HashSet;
import java.util.Set;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class HashTag {

    private Long id;
    private String value;
    private Long ownerId;
    private Set<Long> userIds = new HashSet<>();
}

