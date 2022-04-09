// package io.company.brewcraft.migration;

// import static org.junit.jupiter.api.Assertions.*;

// import java.util.regex.Pattern;

// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// public class RandomGeneratorImplTest {
//     private RandomGenerator rand;

//     @BeforeEach
//     public void init() {
//         rand = new RandomGeneratorImpl();
//     }

//     @Test
//     public void testString_ReturnsRandomStringOfLength10_WhenInputIs10() {
//         String s = rand.string(10);
//         assertEquals(10, s.length());
//     }

//     @Test
//     public void testString_ReturnsRandomStringOfLength20_WhenInputIs20() {
//         String s = rand.string(20);
//         assertEquals(20, s.length());
//     }

//     @Test
//     public void testString_ReturnsString_WithAlphaNumAndSpecialChars() {
//         String s = rand.string(50);

//         Pattern lowercase = Pattern.compile("[a-z]");
//         Pattern uppercase = Pattern.compile("[A-Z]");
//         Pattern digit = Pattern.compile("[0-9]");
//         Pattern special = Pattern.compile("[!@#$%^&*_=+-/.?<>)]");

//         assertTrue(1 < lowercase.matcher(s).results().count());
//         assertTrue(1 < uppercase.matcher(s).results().count());
//         assertTrue(1 < digit.matcher(s).results().count());
//         assertTrue(1 < special.matcher(s).results().count());
//     }
// }
