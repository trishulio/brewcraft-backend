package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DeepRootTest {

    private DeepRoot root;

    From<?, ?> mRoot;

    @BeforeEach
    public void init() {
        mRoot = mock(From.class);
        root = new DeepRoot(mRoot);
    }

    @Test
    public void testGet_ReturnsDeepPath_WhenPathsIsNotNull() {
        Join<String, Object> mJoin1 = mock(Join.class);
        Join<String, Object> mJoin2 = mock(Join.class);
        Join<String, Object> mJoin3 = mock(Join.class);
        
        doReturn(mJoin1).when(mRoot).join("JOIN_1");
        doReturn(mJoin2).when(mJoin1).join("JOIN_2");
        doReturn(mJoin3).when(mJoin2).join("JOIN_3");

        Path<?> mPath1 = mock(Path.class);
        Path<?> mPath2 = mock(Path.class);
        Path<?> mPath3 = mock(Path.class);

        doReturn(mPath1).when(mRoot).get("PATH_1");
        doReturn(mPath1).when(mJoin3).get("PATH_1");
        doReturn(mPath2).when(mPath1).get("PATH_2");
        doReturn(mPath3).when(mPath2).get("PATH_3");

        assertEquals(mPath1, root.get(null, new String[] { "PATH_1" }));
        assertEquals(mPath1, root.get(new String[] {}, new String[] { "PATH_1" }));
        assertEquals(mPath1, root.get(new String[] { "JOIN_1", "JOIN_2", "JOIN_3" }, new String[] { "PATH_1" }));
        assertEquals(mPath2, root.get(new String[] { "JOIN_1", "JOIN_2", "JOIN_3" }, new String[] { "PATH_1", "PATH_2" }));
        assertEquals(mPath3, root.get(new String[] { "JOIN_1", "JOIN_2", "JOIN_3" }, new String[] { "PATH_1", "PATH_2", "PATH_3" }));
    }

    @Test
    public void testGet_ThrowException_WhenPathIsNull() {
        assertThrows(IllegalArgumentException.class, () -> root.get(null));
    }

    @Test
    public void testGet_ThrowException_WhenPathIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> root.get(new String[] {}));
    }
}
