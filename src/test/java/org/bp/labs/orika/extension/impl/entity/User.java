package org.bp.labs.orika.extension.impl.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    private Long id;
    private String userName;
}
