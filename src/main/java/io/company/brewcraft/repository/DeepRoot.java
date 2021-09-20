package io.company.brewcraft.repository;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeepRoot {
    private static final Logger log = LoggerFactory.getLogger(DeepRoot.class);

    private From<?, ?> root;

    public DeepRoot(From<?, ?> root) {
        this.root = root;
    }

    public <X, Y> Path<X> get(String[] paths) {
        return get(null, paths);
    }

    public <X, Y> Path<X> get(String[] joins, String[] paths) {
        if (paths == null || paths.length <= 0) {
            String msg = String.format("No field names provided: %s", paths == null ? null : paths.toString());
            log.error(msg);
            throw new IllegalArgumentException(msg);
        }

        From<X, Y> j = (From<X, Y>) root;
        if (joins != null) {
            for (int i = 0; i < joins.length; i++) {
                j = j.join(joins[i]);
            }
        }

        Path<X> path = j.get(paths[0]);

        for (int i = 1; i < paths.length; i++) {
            path = path.get(paths[i]);
        }

        return path;
    }
}
