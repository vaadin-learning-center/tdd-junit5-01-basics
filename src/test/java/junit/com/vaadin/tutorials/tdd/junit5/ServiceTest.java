package junit.com.vaadin.tutorials.tdd.junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.api.condition.EnabledIf;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import com.vaadin.tutorials.tdd.junit5.Service;

public class ServiceTest {


  @Test
  void test001() {
    final int result = new Service().add(0 , 0);
    assertEquals(0 , result);
  }

  @ParameterizedTest(name = "{0} + {1} = {2}")
  @CsvSource({
      "0,    1,   1" ,
      "1,    2,   3" ,
      "49,  51, 100" ,
      "1,  100, 101"
  })
  void test002(int first , int second , int expectedResult) {
    Service service = new Service();
    assertEquals(expectedResult , service.add(first , second) ,
                 () -> first + " + " + second + " should equal " + expectedResult);
  }

  @ParameterizedTest(name = "{0} + {1} = {2}")
  @MethodSource(value = "factoryMethod")
  void test003(int first , int second , int expectedResult) {
    Service service = new Service();
    assertEquals(expectedResult , service.add(first , second) ,
                 () -> first + " + " + second + " should equal " + expectedResult);
  }

  private static Stream<Arguments> factoryMethod() {
    return IntStream
        .range(0 , 5)
        .mapToObj(i -> Arguments.of(i , i , i + i));
  }


//  @Test
//  @EnabledIf(value = "true", reason = "test runs because it is true")
//  void test004() {
//    // ;-)
//  }

//  @DisabledIf(Math.random()+"")
//  @RepeatedTest(10)
//  void test005() {
//    // ;-)
//  }


  @Test
  void test006() {
//    List<String> names = Arrays.asList("Sergio" , "Juan" , "Peter");
    List<String> names = Arrays.asList("Sergio" , "Juan" , "Adolfo");
    assertAll("names" ,
              () -> assertEquals("Sergio" , names.get(0)) ,
              () -> assertEquals("Juan" , names.get(1)) ,
              () -> assertEquals("Adolfo" , names.get(2)));
  }

  @Test
  void test007() {
    Throwable runtimeException = assertThrows(RuntimeException.class , () -> {
      throw new RuntimeException("exception");
    });
    assertEquals("exception" , runtimeException.getMessage());
  }


  //nested Tests - simple life cycle
  private List<String> strings;

  @Nested
  class NestedTestClass {

    @BeforeEach //only for this nested class
    void init() {
      strings = new ArrayList<>();
    }

    @Test
    void listIsEmpty() {
      assertTrue(strings.isEmpty());
    }

    @Nested //sub nested classes are possible
    class afterAddingString {

      @BeforeEach
      void init() {
        strings.add("hello");
      }

      @Test
      void listIsNotEmpty() {
        assertFalse(strings.isEmpty());
      }
    }
  }


  @TestInstance(TestInstance.Lifecycle.PER_CLASS)
  interface Interfaces{
    @BeforeAll
    default void initAll(TestReporter reporter){
      reporter.publishEntry("@BeforeAll runs once before all tests");
    }
    @BeforeEach
    default void init(TestReporter reporter){
      reporter.publishEntry("@BeforeEach runs before each test");
    }
    @AfterAll
    default void tearDownAll(TestReporter reporter){
      reporter.publishEntry("@AfterAll runs once after all tests");
    }
    @AfterEach
    default void tearDown(TestReporter reporter){
      reporter.publishEntry("@AfterEach runs after each test");
    }
  }
  @Nested
  class ClassImplementingInterfaceTests implements Interfaces {
    @Test
    void myTest(TestReporter reporter){
      reporter.publishEntry("the test itself");
    }
  }




}