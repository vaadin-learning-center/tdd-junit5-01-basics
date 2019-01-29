package junit.com.vaadin.tutorials.tdd.junit5;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
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


}
