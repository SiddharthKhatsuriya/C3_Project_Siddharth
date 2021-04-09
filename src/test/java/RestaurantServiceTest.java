import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class RestaurantServiceTest {

    private static RestaurantService service = new RestaurantService();
    private static Restaurant restaurant;

    private static LocalTime openingTime;
    private static LocalTime closingTime;

    @BeforeAll
    public static void beforeAll() {

        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");

    }

    @BeforeEach
    public void beforeEach() {

        restaurant = service.addRestaurant("Honest Rivera", "Sanand", openingTime, closingTime);
        restaurant.addToMenu("Pao Bhaji", 123);
        restaurant.addToMenu("masala dosa", 456);
    }

    @AfterEach
    public void afterEach() throws restaurantNotFoundException {
        List<Restaurant> restaurants = service.getRestaurants();
        restaurants.clear();
    }

    @Test
    public void searching_for_existing_restaurant_should_return_expected_restaurant_object() throws restaurantNotFoundException {
        Restaurant res = service.findRestaurantByName("Honest Rivera");
        assertNotNull(res);
    }

    @Test
    public void searching_for_non_existing_restaurant_should_throw_exception() throws restaurantNotFoundException {
        assertThrows(restaurantNotFoundException.class, new Executable() {

            @Override
            public void execute() throws Throwable {
                Restaurant res = service.findRestaurantByName("My Punjabi Dhaba");
                assertNull(res);
            }
        });

    }
    //<<<<<<<<<<<<<<<<<<<<SEARCHING>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>ADMIN: ADDING & REMOVING RESTAURANTS<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void remove_restaurant_should_reduce_list_of_restaurants_size_by_1() throws restaurantNotFoundException {
        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.removeRestaurant("Honest Rivera");
        assertEquals(initialNumberOfRestaurants - 1, service.getRestaurants().size());
    }

    @Test
    public void removing_restaurant_that_does_not_exist_should_throw_exception() throws restaurantNotFoundException {

        assertThrows(restaurantNotFoundException.class, () -> service.removeRestaurant("Pantry d'or"));
    }

    @Test
    public void add_restaurant_should_increase_list_of_restaurants_size_by_1() {

        int initialNumberOfRestaurants = service.getRestaurants().size();
        service.addRestaurant("Pumpkin Tales", "Sanand", LocalTime.parse("12:00:00"), LocalTime.parse("23:00:00"));
        assertEquals(initialNumberOfRestaurants + 1, service.getRestaurants().size());
    }
    //<<<<<<<<<<<<<<<<<<<<ADMIN: ADDING & REMOVING RESTAURANTS>>>>>>>>>>>>>>>>>>>>>>>>>>

    //<<<<<<<<<<<<<<<<<<<<System: Getting Total for Items>>>>>>>>>>>>>>>>>>>>>>>>>>

    @Test
    public void getting_cost_for_food_items_in_existing_restaurant_should_return_total_cost() {
        ArrayList<String> selectedItems = new ArrayList<String>();
        selectedItems.add("Pao Bhaji");
        selectedItems.add("masala dosa");

        int totalCost = service.getTotalCostForOrder("Honest Rivera", selectedItems);

        assertEquals(579, totalCost);
    }
}