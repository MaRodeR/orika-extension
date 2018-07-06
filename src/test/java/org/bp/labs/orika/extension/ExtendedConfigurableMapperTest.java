package org.bp.labs.orika.extension;

import org.bp.labs.orika.extension.impl.TestMapper;
import org.bp.labs.orika.extension.impl.TestObjectLoader;
import org.bp.labs.orika.extension.impl.dto.HashTagDTO;
import org.bp.labs.orika.extension.impl.entity.HashTag;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

public class ExtendedConfigurableMapperTest {

    private TestMapper testMapper = new TestMapper(new TestObjectLoader());

    @Test
    public void mapObjectWithDatabase() {

        HashTag hashTag = new HashTag()
                .setId(1L)
                .setOwnerId(10L)
                .setUserIds(new HashSet<>(asList(11L, 12L)));

        HashTagDTO hashTagDTO = testMapper.map(hashTag, HashTagDTO.class);
        assertThat(hashTagDTO)
                .extracting("id", "owner.id")
                .containsExactly(1L, 10L);

        assertThat(hashTagDTO.getUsers())
                .extracting("id")
                .containsExactlyInAnyOrder(11L, 12L);
    }

    @Test
    public void mapListWithDatabase() {

        List<HashTag> hashTags = asList(
                new HashTag()
                        .setId(1L)
                        .setOwnerId(10L)
                        .setUserIds(new HashSet<>(asList(11L, 12L))),
                new HashTag()
                        .setId(2L)
                        .setOwnerId(20L)
                        .setUserIds(new HashSet<>(asList(21L, 22L)))
        );

        List<HashTagDTO> hashTagDTOS = testMapper.mapAsList(hashTags, HashTagDTO.class);
        assertThat(hashTagDTOS)
                .extracting("id", "owner.id")
                .containsExactlyInAnyOrder(
                        tuple(1L, 10L),
                        tuple(2L, 20L)
                );
        assertThat(hashTagDTOS)
                .flatExtracting("users")
                .hasSize(4)
                .doesNotContainNull();
    }
}
