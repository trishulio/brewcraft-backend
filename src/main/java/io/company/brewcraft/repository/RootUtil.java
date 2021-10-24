package io.company.brewcraft.repository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootUtil {
    private static final Logger log = LoggerFactory.getLogger(RootUtil.class);
    public static RootUtil INSTANCE = new RootUtil();

    private CriteriaJoinProcessor cjAnnotationProcessor;
    private CriteriaJoinProcessor cjIgnorer;

    protected RootUtil() {
        this(CriteriaJoinProcessor.ANNOTATION_PROCESSOR, CriteriaJoinProcessor.JOIN_IGNORER);
    }

    protected RootUtil(CriteriaJoinProcessor cjAnnotationProcessor, CriteriaJoinProcessor cjIgnorer) {
        this.cjAnnotationProcessor = cjAnnotationProcessor;
        this.cjIgnorer = cjIgnorer;
    }

    public <X, Y> Path<X> getPath(From<?, ?> root, String[] joins, String[] paths) {
        return get(root, joins, paths, this.cjIgnorer);
    }

    public <X, Y> Path<X> getPathWithJoin(From<?, ?> root, String[] joins, String[] paths) {
        return get(root, joins, paths, this.cjAnnotationProcessor);
    }

    // Note: Private method is a code-smell. It signals that a new class be created.
    private <X, Y> Path<X> get(From<?, ?> root, String[] joins, String[] paths, CriteriaJoinProcessor cjProcessor) {
        if (paths == null || paths.length <= 0) {
            String msg = String.format("No field names provided: %s", paths == null ? null : paths.toString());
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        @SuppressWarnings("unchecked")
        From<X, Y> j = (From<X, Y>) root;
        if (joins != null) {
            for (int i = 0; i < joins.length; i++) {
                j = j.join(joins[i]);
            }
        }

        Path<X> path = j.get(paths[0]);
        j = cjProcessor.apply(j, j.getJavaType(), paths[0]);

        for (int i = 1; i < paths.length; i++) {
            path = path.get(paths[i]);
            j = cjProcessor.apply(j, j.getJavaType(), paths[i]);
        }

        return path;
    }
}
