package org.bp.labs.orika.extension.impl;

import org.bp.labs.orika.extension.impl.entity.User;

import java.util.List;
import java.util.stream.StreamSupport;

import static java.util.stream.Collectors.toList;

public class TestObjectLoader {

    public List<User> findUsersBy(Iterable<Long> ids) {
        return StreamSupport.stream(ids.spliterator(), false)
                .map(id -> new User().setId(id).setUserName(id + "-name"))
                .collect(toList());
    }
}
