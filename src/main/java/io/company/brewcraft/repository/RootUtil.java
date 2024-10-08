package io.company.brewcraft.repository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RootUtil {
    private static final Logger log = LoggerFactory.getLogger(RootUtil.class);
    public static RootUtil INSTANCE = new RootUtil(JpaJoiner.JPA_JOINER);

    private JpaJoiner jpaJoiner;

    protected RootUtil(JpaJoiner jpaJoiner) {
        this.jpaJoiner = jpaJoiner;
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
            j = jpaJoiner.join(j, paths[i]);
        }

        return jpaJoiner.get(j, paths[i]);
    }
}
