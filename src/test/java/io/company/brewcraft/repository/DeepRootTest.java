package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.From;
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
        Path<?> mPath1 = mock(Path.class);
        Path<?> mPath2 = mock(Path.class);
        Path<?> mPath3 = mock(Path.class);

        doReturn(mPath1).when(mRoot).get("PATH_1");
        doReturn(mPath2).when(mPath1).get("PATH_2");
        doReturn(mPath3).when(mPath2).get("PATH_3");

        assertEquals(mPath1, root.get(new String[] { "PATH_1" }));
        assertEquals(mPath2, root.get(new String[] { "PATH_1", "PATH_2" }));
        assertEquals(mPath3, root.get(new String[] { "PATH_1", "PATH_2", "PATH_3" }));
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
