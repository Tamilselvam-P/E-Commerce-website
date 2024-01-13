import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class Product {
    String name;
    double price;
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }
}
class ShoppingCart {
    List<Product> items = new ArrayList<>();
    public void addProduct(Product product) {
        items.add(product);
    }
    public double calculateTotal() {
        return items.stream().mapToDouble(product -> product.price).sum();
    }
}
class User {
    String username;
    String password;
    ShoppingCart cart;
    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.cart = new ShoppingCart();
    }
}
public class Main {
    static List<Product> productList = new ArrayList<>();
    static List<User> users = new ArrayList<>();
    public static void main(String[] args) {
        initializeProducts();
        initializeUsers();
        Scanner scanner = new Scanner(System.in);
        User currentUser = null;
        while (true) {
            System.out.println("\nE-commerce Website Menu:");
            System.out.println("1. Browse Products");
            System.out.println("2. Login");
            System.out.println("3. Register");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    browseProducts();
                    break;
                case 2:
                    currentUser = login(scanner);
                    break;
                case 3:
                    registerUser(scanner);
                    break;
                case 4:
                    System.out.println("Exiting E-commerce Website. Goodbye!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
            if (currentUser != null) {
                shoppingMenu(scanner, currentUser);
            }
        }
    }
    private static void initializeProducts() {
        productList.add(new Product("Product 1", 19.99));
        productList.add(new Product("Product 2", 29.99));
        productList.add(new Product("Product 3", 39.99));
    }
    private static void initializeUsers() {
        users.add(new User("user1", "password1"));
        users.add(new User("user2", "password2"));
    }
    private static void browseProducts() {
        System.out.println("\nAvailable Products:");
        for (int i = 0; i < productList.size(); i++) {
            System.out.println((i + 1) + ". " + productList.get(i).name + " - $" + productList.get(i).price);
        }
    }
    private static User login(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        for (User user : users) {
            if (user.username.equals(username) && user.password.equals(password)) {
                System.out.println("Login successful. Welcome, " + username + "!");
                return user;
            }
        }
        System.out.println("Invalid username or password. Please try again.");
        return null;
    }
    private static void registerUser(Scanner scanner) {
        System.out.print("Enter new username: ");
        String newUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String newPassword = scanner.nextLine();
        users.add(new User(newUsername, newPassword));
        System.out.println("User registration successful. Welcome, " + newUsername + "!");
    }
    private static void shoppingMenu(Scanner scanner, User user) {
        while (true) {
            System.out.println("\nShopping Menu:");
            System.out.println("1. Add Product to Cart");
            System.out.println("2. View Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Logout");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character
            switch (choice) {
                case 1:
                    addProductToCart(scanner, user);
                    break;
                case 2:
                    viewCart(user);
                    break;
                case 3:
                    checkout(user);
                    break;
                case 4:
                    System.out.println("Logging out. Goodbye, " + user.username + "!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
    private static void addProductToCart(Scanner scanner, User user) {
        browseProducts();
        System.out.print("Enter the number of the product to add to your cart: ");
        int productNumber = scanner.nextInt();
        if (productNumber >= 1 && productNumber <= productList.size()) {
            Product selectedProduct = productList.get(productNumber - 1);
            user.cart.addProduct(selectedProduct);
            System.out.println("Added " + selectedProduct.name + " to your cart.");
        } else {
            System.out.println("Invalid product number. Please try again.");
        }
    }
    private static void viewCart(User user) {
        System.out.println("\nShopping Cart for " + user.username + ":");
        for (Product product : user.cart.items) {
            System.out.println("- " + product.name + " - $" + product.price);
        }
        System.out.println("Total: $" + user.cart.calculateTotal());
    }
    private static void checkout(User user) {
        viewCart(user);
        System.out.println("Thank you for shopping with us, " + user.username + "!");
        user.cart.items.clear();
    }
}