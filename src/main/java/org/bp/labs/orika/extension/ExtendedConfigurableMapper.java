package org.bp.labs.orika.extension;

import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.MappingContextFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import ma.glasnost.orika.metadata.Type;

import java.util.*;

public abstract class ExtendedConfigurableMapper extends ConfigurableMapper {

    static final String SOURCE_LIST_CTX_PROPERTY_NAME = "rel_objects_ext_source";

    private static MappingContextFactory contextFactory = new MappingContext.Factory();

    public static MappingContext createMappingContextWith(Map<Object, Object> properties){
        MappingContext context = contextFactory.getContext();
        properties.forEach(context::setProperty);
        return context;
    }

    public ExtendedConfigurableMapper() {
    }

    public ExtendedConfigurableMapper(boolean autoInit) {
        super(autoInit);
    }

    @Override
    protected void configureFactoryBuilder(DefaultMapperFactory.Builder factoryBuilder) {
        super.configureFactoryBuilder(factoryBuilder);
//        TODO: add custom specification for generating code. It would make using this extension simpler than now.
//        factoryBuilder.getCodeGenerationStrategy()
//                .addSpecification();
    }

    @Override
    public <S, D> Set<D> mapAsSet(Iterable<S> source, Type<S> sourceType, Type<D> destinationType) {
        MappingContext context = initCtxWithObjects(source);
        return super.mapAsSet(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(Iterable<S> source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        addObjectsToCtx(context, source);
        return super.mapAsSet(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(S[] source, Type<S> sourceType, Type<D> destinationType) {
        MappingContext context = initCtxWithObjects(Arrays.asList(source));
        return super.mapAsSet(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(S[] source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        addObjectsToCtx(context, Arrays.asList(source));
        return super.mapAsSet(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Type<S> sourceType, Type<D> destinationType) {
        MappingContext context = initCtxWithObjects(source);
        return super.mapAsList(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        addObjectsToCtx(context, source);
        return super.mapAsList(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> List<D> mapAsList(S[] source, Type<S> sourceType, Type<D> destinationType) {
        MappingContext context = initCtxWithObjects(Arrays.asList(source));
        return super.mapAsList(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> List<D> mapAsList(S[] source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        addObjectsToCtx(context, Arrays.asList(source));
        return super.mapAsList(source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, Iterable<S> source, Type<S> sourceType, Type<D> destinationType) {
        MappingContext context = initCtxWithObjects(source);
        return super.mapAsArray(destination, source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType) {
        MappingContext context = initCtxWithObjects(Arrays.asList(source));
        return super.mapAsArray(destination, source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, Iterable<S> source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        addObjectsToCtx(context, source);
        return super.mapAsArray(destination, source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, S[] source, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        addObjectsToCtx(context, Arrays.asList(source));
        return super.mapAsArray(destination, source, sourceType, destinationType, context);
    }

    @Override
    public <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Class<D> destinationClass) {
        MappingContext context = initCtxWithObjects(source);
        super.mapAsCollection(source, destination, destinationClass, context);
    }

    @Override
    public <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Class<D> destinationClass, MappingContext context) {
        addObjectsToCtx(context, source);
        super.mapAsCollection(source, destination, destinationClass, context);
    }

    @Override
    public <S, D> void mapAsCollection(S[] source, Collection<D> destination, Class<D> destinationCollection) {
        MappingContext context = initCtxWithObjects(Arrays.asList(source));
        super.mapAsCollection(source, destination, destinationCollection, context);
    }

    @Override
    public <S, D> void mapAsCollection(S[] source, Collection<D> destination, Class<D> destinationCollection, MappingContext context) {
        addObjectsToCtx(context, Arrays.asList(source));
        super.mapAsCollection(source, destination, destinationCollection, context);
    }

    @Override
    public <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Type<S> sourceType, Type<D> destinationType) {
        MappingContext context = initCtxWithObjects(source);
        super.mapAsCollection(source, destination, sourceType, destinationType, context);
    }

    @Override
    public <S, D> void mapAsCollection(S[] source, Collection<D> destination, Type<S> sourceType, Type<D> destinationType) {
        MappingContext context = initCtxWithObjects(Arrays.asList(source));
        super.mapAsCollection(source, destination, sourceType, destinationType, context);
    }

    @Override
    public <S, D> void mapAsCollection(Iterable<S> source, Collection<D> destination, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        addObjectsToCtx(context, source);
        super.mapAsCollection(source, destination, sourceType, destinationType, context);
    }

    @Override
    public <S, D> void mapAsCollection(S[] source, Collection<D> destination, Type<S> sourceType, Type<D> destinationType, MappingContext context) {
        addObjectsToCtx(context, Arrays.asList(source));
        super.mapAsCollection(source, destination, sourceType, destinationType, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(Iterable<S> source, Class<D> destinationClass) {
        MappingContext context = initCtxWithObjects(source);
        return super.mapAsSet(source, destinationClass, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(Iterable<S> source, Class<D> destinationClass, MappingContext context) {
        addObjectsToCtx(context, source);
        return super.mapAsSet(source, destinationClass, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(S[] source, Class<D> destinationClass) {
        MappingContext context = initCtxWithObjects(Arrays.asList(source));
        return super.mapAsSet(source, destinationClass, context);
    }

    @Override
    public <S, D> Set<D> mapAsSet(S[] source, Class<D> destinationClass, MappingContext context) {
        addObjectsToCtx(context, Arrays.asList(source));
        return super.mapAsSet(source, destinationClass, context);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass) {
        MappingContext context = initCtxWithObjects(source);
        return super.mapAsList(source, destinationClass, context);
    }

    @Override
    public <S, D> List<D> mapAsList(Iterable<S> source, Class<D> destinationClass, MappingContext context) {
        addObjectsToCtx(context, source);
        return super.mapAsList(source, destinationClass, context);
    }

    @Override
    public <S, D> List<D> mapAsList(S[] source, Class<D> destinationClass) {
        MappingContext context = initCtxWithObjects(Arrays.asList(source));
        return super.mapAsList(source, destinationClass, context);
    }

    @Override
    public <S, D> List<D> mapAsList(S[] source, Class<D> destinationClass, MappingContext context) {
        addObjectsToCtx(context, Arrays.asList(source));
        return super.mapAsList(source, destinationClass, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, Iterable<S> source, Class<D> destinationClass) {
        MappingContext context = initCtxWithObjects(source);
        return super.mapAsArray(destination, source, destinationClass, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, S[] source, Class<D> destinationClass) {
        MappingContext context = initCtxWithObjects(Arrays.asList(source));
        return super.mapAsArray(destination, source, destinationClass, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, Iterable<S> source, Class<D> destinationClass, MappingContext context) {
        addObjectsToCtx(context, source);
        return super.mapAsArray(destination, source, destinationClass, context);
    }

    @Override
    public <S, D> D[] mapAsArray(D[] destination, S[] source, Class<D> destinationClass, MappingContext context) {
        addObjectsToCtx(context, Arrays.asList(source));
        return super.mapAsArray(destination, source, destinationClass, context);
    }

    private <S> MappingContext initCtxWithObjects(Iterable<S> source) {
        MappingContext context = contextFactory.getContext();
        addObjectsToCtx(context, source);
        return context;
    }

    private <S> void addObjectsToCtx(MappingContext context, Iterable<S> source) {
        context.setProperty(SOURCE_LIST_CTX_PROPERTY_NAME, source);
    }
}
