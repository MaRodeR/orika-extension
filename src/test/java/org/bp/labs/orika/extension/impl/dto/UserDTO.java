package org.bp.labs.orika.extension.impl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String userName;
}
