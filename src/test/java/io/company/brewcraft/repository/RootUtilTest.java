package io.company.brewcraft.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import javax.persistence.criteria.From;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InOrder;

public class RootUtilTest {
    class Layer0 {
        private Layer1 layer1;
    }

    class Layer1 {
        private Layer2 layer2;
    }

    class Layer2 {
        private Layer3 layer3;
    }

    class Layer3 {
    }

    private From<?, Layer0> mRoot;

    private From<?, Layer1> mLayer1;
    private From<?, Layer2> mLayer2;
    private From<?, Layer3> mLayer3;

    private Join<?, ?> mJoinL0L1;
    private Join<?, ?> mJoinL1L2;
    private Join<?, ?> mJoinL2L3;

    private RootUtil rootUtil;
    private CriteriaJoinProcessor mCjAnnotationProcessor;
    private CriteriaJoinProcessor mCjIgnorer;

    @BeforeEach
    public void init() {
        mRoot = mock(From.class);
        doReturn(Layer0.class).when(mRoot).getJavaType();

        mLayer1 = mock(From.class);
        doReturn(Layer1.class).when(mLayer1).getJavaType();
        doReturn(mLayer1).when(mRoot).get("layer1");

        mLayer2 = mock(From.class);
        doReturn(mLayer2).when(mLayer1).get("layer2");
        doReturn(Layer2.class).when(mLayer2).getJavaType();

        mLayer3 = mock(From.class);
        doReturn(mLayer3).when(mLayer2).get("layer3");
        doReturn(Layer3.class).when(mLayer3).getJavaType();

        mJoinL0L1 = mock(Join.class);
        doReturn(Layer0.class).when(mJoinL0L1).getJavaType();
        doReturn(mJoinL0L1).when(mRoot).join("layer1");

        mJoinL1L2 = mock(Join.class);
        doReturn(Layer1.class).when(mJoinL1L2).getJavaType();
        doReturn(mJoinL1L2).when(mJoinL0L1).join("layer2");

        mJoinL2L3 = mock(Join.class);
        doReturn(Layer1.class).when(mJoinL1L2).getJavaType();
        doReturn(mJoinL2L3).when(mJoinL1L2).join("layer3");

        mCjAnnotationProcessor = mock(CriteriaJoinAnnotationProcessor.class);
        mCjIgnorer = new CriteriaJoinIgnorer();

        rootUtil = new RootUtil(mCjAnnotationProcessor, mCjIgnorer);
    }

    @Test
    public void testGetPath_ReturnsDeepPathOnJoin_WhenPathAndJoinIsNotNull() {
        doReturn(mLayer1).when(mJoinL0L1).get("layer1");
        assertEquals(mLayer1, rootUtil.getPath(mRoot, new String[] { "layer1" }, new String[] { "layer1" }));

        doReturn(mLayer1).when(mJoinL1L2).get("layer1");
        doReturn(mLayer2).when(mJoinL1L2).get("layer2");
        assertEquals(mLayer2, rootUtil.getPath(mRoot, new String[] { "layer1", "layer2" }, new String[] { "layer1", "layer2" }));

        doReturn(mLayer1).when(mJoinL2L3).get("layer1");
        doReturn(mLayer2).when(mJoinL2L3).get("layer2");
        doReturn(mLayer3).when(mJoinL2L3).get("layer3");
        assertEquals(mLayer3, rootUtil.getPath(mRoot, new String[] { "layer1", "layer2", "layer3" }, new String[] { "layer1", "layer2", "layer3" }));
    }

    @Test
    public void testGetPath_ReturnsDeepPath_WhenPathsIsNotNull() {
        assertEquals(mLayer1, rootUtil.getPath(mRoot, null, new String[] {"layer1" }));
        assertEquals(mLayer2, rootUtil.getPath(mRoot, null, new String[] {"layer1", "layer2" }));
        assertEquals(mLayer3, rootUtil.getPath(mRoot, null, new String[] {"layer1", "layer2", "layer3" }));
    }

    @Test
    public void testGetPath_ThrowException_WhenPathIsNull() {
        assertThrows(IllegalArgumentException.class, () -> rootUtil.getPath(mRoot, new String[] {}, null));
    }

    @Test
    public void testGetPath_ThrowException_WhenPathIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> rootUtil.getPath(mRoot, null, new String[] {}));
    }

    @Test
    public void testGetPathWithJoin_ReturnsDeepPathAndAddInnerJoin_WhenPathsCriteriaJoinAnnotationIsMissing() {
        doReturn(mLayer1).when(mCjAnnotationProcessor).apply(mRoot, Layer0.class, "layer1");
        doReturn(mLayer2).when(mCjAnnotationProcessor).apply(mLayer1, Layer1.class, "layer2");
        doReturn(mLayer3).when(mCjAnnotationProcessor).apply(mLayer2, Layer2.class, "layer3");

        Path<?> path = rootUtil.getPathWithJoin(mRoot, null, new String[] {"layer1", "layer2", "layer3"});
        assertEquals(mLayer3, path);

        InOrder order = inOrder(mCjAnnotationProcessor);
        order.verify(mCjAnnotationProcessor).apply(mRoot, Layer0.class, "layer1");
        order.verify(mCjAnnotationProcessor).apply(mLayer1, Layer1.class, "layer2");
        order.verify(mCjAnnotationProcessor).apply(mLayer2, Layer2.class, "layer3");
    }

    @Test
    public void testGetPathWithJoin_ThrowException_WhenPathIsNull() {
        assertThrows(IllegalArgumentException.class, () -> rootUtil.getPathWithJoin(mRoot, new String[] {}, null));
    }

    @Test
    public void testGetPathWithJoin_ThrowException_WhenPathIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> rootUtil.getPathWithJoin(mRoot, null, new String[] {}));
    }
}
