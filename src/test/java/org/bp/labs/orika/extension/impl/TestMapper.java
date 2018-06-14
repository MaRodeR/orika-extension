package org.bp.labs.orika.extension.impl;

import ma.glasnost.orika.MapperFactory;
import org.bp.labs.orika.extension.ExtendedConfigurableMapper;
import org.bp.labs.orika.extension.impl.dto.HashTagDTO;
import org.bp.labs.orika.extension.impl.dto.UserDTO;
import org.bp.labs.orika.extension.impl.entity.HashTag;
import org.bp.labs.orika.extension.impl.entity.User;

import static org.bp.labs.orika.extension.ObjectLoaderConverter.forMultipleIdProp;
import static org.bp.labs.orika.extension.ObjectLoaderConverter.forSingleIdProp;

public class TestMapper extends ExtendedConfigurableMapper {

    private TestObjectLoader testObjectLoader;

    public TestMapper(TestObjectLoader testObjectLoader) {
        super(false);
        this.testObjectLoader = testObjectLoader;
        init();
    }

    @Override
    protected void configure(MapperFactory factory) {

        factory.classMap(User.class, UserDTO.class)
                .byDefault()
                .register();

//        hash tag
        factory.getConverterFactory()
                .registerConverter("hashTagOwnerIdToOwner",
                        forSingleIdProp(HashTag::getOwnerId, testObjectLoader::findUsersBy, User::getId));
        factory.getConverterFactory()
                .registerConverter("hashTagUsersIdToUsers",
                        forMultipleIdProp(HashTag::getUserIds, testObjectLoader::findUsersBy, User::getId));

        factory.classMap(HashTag.class, HashTagDTO.class)
                .fieldMap("ownerId", "owner").aToB().converter("hashTagOwnerIdToOwner").add()
                .fieldBToA("owner.id", "ownerId")
                .fieldMap("userIds", "users").aToB().converter("hashTagUsersIdToUsers").add()
                .fieldBToA("users{id}", "userIds{}")
                .byDefault()
                .register();
    }
}
