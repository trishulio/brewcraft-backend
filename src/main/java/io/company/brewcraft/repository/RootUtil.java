package io.company.brewcraft.repository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootUtil {
    private static final Logger log = LoggerFactory.getLogger(RootUtil.class);
    public static RootUtil INSTANCE = new RootUtil(CriteriaJoinProcessor.CRITERIA_JOINER);

    private CriteriaJoinProcessor cjAnnotationProcessor;

    protected RootUtil(CriteriaJoinProcessor cjAnnotationProcessor) {
        this.cjAnnotationProcessor = cjAnnotationProcessor;
    }

    public <X, Y> Path<X> getPath(From<?, ?> root, String[] paths) {
        if (paths == null || paths.length <= 0) {
            String msg = String.format("No field names provided: %s", paths == null ? null : paths.toString());
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        @SuppressWarnings("unchecked")
        From<X, Y> j = (From<X, Y>) root;

        int i;
        for (i = 0; i < paths.length - 1; i++) {
            j = cjAnnotationProcessor.apply(j, j.getJavaType(), paths[i]);
        }

        return j.get(paths[i]);
    }
}
