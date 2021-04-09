import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    private static Restaurant restaurant;
    private static LocalTime openingTime;
    private static LocalTime closingTime;

    @BeforeAll
    public static void beforeAll() {
        LocalTime openingTime = LocalTime.parse ("09:00:00");
        LocalTime closingTime = LocalTime.parse("23:30:00");

        restaurant = new Restaurant("Honest Rivera","Sanand",openingTime,closingTime);

    }

    @BeforeEach
    public void beforeEach() {
        restaurant.addToMenu("Pao Bhaji",123);
        restaurant.addToMenu("masala dosa", 456);
    }

    @AfterEach
    public void afterEach() throws itemNotFoundException {
        List<Item> menuItems = restaurant.getMenu();
        menuItems.clear ();

    }
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        Restaurant myRestaurant = Mockito.spy(restaurant);
        LocalTime currTime = LocalTime.parse ("11:17:33");

        Mockito.when(myRestaurant.getCurrentTime ()).thenReturn (currTime);
        assertTrue(myRestaurant.isRestaurantOpen ());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        Restaurant myRestaurant = Mockito.spy(restaurant);
        LocalTime currTime = LocalTime.parse ("01:12:00");

        Mockito.when(myRestaurant.getCurrentTime ()).thenReturn (currTime);
        assertFalse (myRestaurant.isRestaurantOpen ());

    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant = new Restaurant("Honest Rivera", "Sanand", openingTime, closingTime);
        restaurant.addToMenu("Pao Bhaji", 123);
        restaurant.addToMenu("masala dosa", 456);

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Hakka Nooodles", 210);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {


        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("masala dosa");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {


        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}
