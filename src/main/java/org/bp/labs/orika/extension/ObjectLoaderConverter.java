package org.bp.labs.orika.extension;

import ma.glasnost.orika.ConverterException;
import ma.glasnost.orika.CustomConverter;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.metadata.Type;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class ObjectLoaderConverter<SO, S, E, D> extends CustomConverter<S, D> {

    private static final Random CONVERTER_ID_RANDOM = new Random();

    private Function<SO, Collection<S>> idListGetterForSource;
    private Function<SO, S> idGetterForSource;
    private Function<Iterable<S>, Iterable<E>> loadObjectsFunction;
    private Function<E, S> idGetterFromRelObject;
    private String contextPropertyName;

    private ObjectLoaderConverter(Function<SO, Collection<S>> idListGetterForSource, Function<SO, S> idGetterForSource, Function<Iterable<S>, Iterable<E>> loadObjectsFunction, Function<E, S> idGetterFromRelObject) {
        this.idListGetterForSource = idListGetterForSource;
        this.idGetterForSource = idGetterForSource;
        this.loadObjectsFunction = loadObjectsFunction;
        this.idGetterFromRelObject = idGetterFromRelObject;
        this.contextPropertyName = "rel_objects_" + this.getClass().getSimpleName() + "_" + CONVERTER_ID_RANDOM.nextLong();
    }

    public static <SO, S, E, D> ObjectLoaderConverter<SO, S, E, D> forSingleIdProp(Function<SO, S> idGetterForSource, Function<Iterable<S>, Iterable<E>> loadObjectsFunction, Function<E, S> idGetterFromRelObject) {
        return new ObjectLoaderConverter<>(null, idGetterForSource, loadObjectsFunction, idGetterFromRelObject);
    }

    public static <SO, S, E, D> ObjectLoaderConverter<SO, S, E, D> forMultipleIdProp(Function<SO, Collection<S>> idListGetterForSource, Function<Iterable<S>, Iterable<E>> loadObjectsFunction, Function<E, S> idGetterFromRelObject) {
        return new ObjectLoaderConverter<>(idListGetterForSource, null, loadObjectsFunction, idGetterFromRelObject);
    }

    @Override
    public D convert(S source, Type<? extends D> destinationType, MappingContext mappingContext) {
        if (source == null) {
            return null;
        }

        Iterable<SO> convertingObjectList = (Iterable<SO>) mappingContext.getProperty(ExtendedConfigurableMapper.SOURCE_LIST_CTX_PROPERTY_NAME);
        if (convertingObjectList != null) {
            Map<S, E> relatedObjectsMap = (Map<S, E>) mappingContext.getProperty(contextPropertyName);
            if (relatedObjectsMap == null) {
                relatedObjectsMap = initRelatedObjectsMap(convertingObjectList);
                mappingContext.setProperty(contextPropertyName, relatedObjectsMap);
            }
            return mapForCollection(source, destinationType, relatedObjectsMap);
        } else {
            return mapForSingleObject(source, destinationType);
        }
    }

    /**
     * Method is used for mapping of collection.
     * E.x. when {@link ma.glasnost.orika.MapperFacade#mapAsList(Iterable, Class)} was called
     */
    private D mapForCollection(S source, Type<? extends D> destinationType, Map<S, E> relatedObjectsMap) {
        if (source instanceof Collection) {
            return (D) ((Collection) source).stream()
                    .map(id -> mapperFacade.map(relatedObjectsMap.get(id), destinationType.getComponentType().getRawType()))
                    .collect(getCollectorForDestinationType(destinationType));
        } else {
            return mapperFacade.map(relatedObjectsMap.get(source), destinationType.getRawType());
        }
    }

    /**
     * Method is used for mapping of single object.
     * E.x. when {@link ma.glasnost.orika.MapperFacade#map(Object, Class)} was called
     */
    private D mapForSingleObject(S source, Type<? extends D> destinationType) {
        if (source instanceof Collection) {
            Iterable<E> relatedObjects = loadObjectsFunction.apply((Iterable<S>) source);
            return (D) StreamSupport.stream(relatedObjects.spliterator(), false)
                    .map(it -> mapperFacade.map(it, destinationType.getComponentType().getRawType()))
                    .collect(getCollectorForDestinationType(destinationType));
        } else {
            Iterable<E> relatedObjects = loadObjectsFunction.apply(Collections.singleton(source));
            return mapperFacade.map(relatedObjects.iterator().next(), destinationType.getRawType());
        }
    }

    private Collector getCollectorForDestinationType(Type<? extends D> destinationType) {
        Collector collector;
        if (destinationType.getRawType().isAssignableFrom(List.class)) {
            collector = Collectors.toList();
        } else if (destinationType.getRawType().isAssignableFrom(Set.class)) {
            collector = Collectors.toSet();
        } else {
            throw new ConverterException();
        }
        return collector;
    }

    private Map<S, E> initRelatedObjectsMap(Iterable<SO> sourceCollection) {

        Stream<S> idsStream = (idListGetterForSource != null ?
                StreamSupport.stream(sourceCollection.spliterator(), false).flatMap(it -> idListGetterForSource.apply(it).stream()) :
                StreamSupport.stream(sourceCollection.spliterator(), false).map(idGetterForSource));

        List<S> ids = idsStream
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        Iterable<E> relatedObjects = loadObjectsFunction.apply(ids);
        return StreamSupport.stream(relatedObjects.spliterator(), false)
                .collect(Collectors.toMap(idGetterFromRelObject, it -> it));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObjectLoaderConverter that = (ObjectLoaderConverter) o;
        return Objects.equals(contextPropertyName, that.contextPropertyName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), contextPropertyName);
    }
}
