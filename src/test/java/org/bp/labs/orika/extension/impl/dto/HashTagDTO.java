package org.bp.labs.orika.extension.impl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;


@Accessors(chain = true)
@Getter
@Setter
public class HashTagDTO {

    private Long id;
    private String value;
    private Set<UserDTO> users;
    private UserDTO owner;
}
