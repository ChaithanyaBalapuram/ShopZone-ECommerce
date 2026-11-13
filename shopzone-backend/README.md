# ShopZone Backend (Spring Boot + Java)

REST API backend for the ShopZone frontend. It is an educational project, not a production marketplace.

## Requirements
- JDK 17 or newer
- Maven 3.6.3+
- Eclipse IDE with Spring Tools (or Eclipse Maven project support)
- Optional: MySQL 8+

Spring Boot 3.5.16 is used here. Spring Boot 3.x requires Java 17 or newer.

## Import into Eclipse
1. Extract `shopzone-backend.zip`.
2. In Eclipse choose **File → Import → Maven → Existing Maven Projects**.
3. Select the extracted `shopzone-backend` folder.
4. Click **Finish** and wait for Maven dependencies to download.
5. Open `ShopZoneApplication.java`.
6. Right-click → **Run As → Java Application** (or Spring Boot App if Spring Tools is installed).

The backend starts at `http://localhost:8080`.

## Database (MySQL is the default)

The application now starts with the `mysql` Spring profile. It connects to MySQL at `localhost:3306` and creates the `shopzone` database if it does not already exist. Hibernate creates/updates the required tables automatically.

1. Install and start MySQL Server 8.x.
2. In MySQL Workbench, connect to your local server and run:
   ```sql
   CREATE DATABASE IF NOT EXISTS shopzone;
   USE shopzone;
   ```
3. Set the database credentials before launching the Spring Boot app. In Eclipse: **Run → Run Configurations → your ShopZone application → Environment** and add:
   - `DB_USERNAME` = your MySQL username (commonly `root`)
   - `DB_PASSWORD` = your MySQL password
4. Run `ShopZoneApplication.java`. Watch the Eclipse Console for `HikariPool` and Hibernate startup messages.

For a quick local-only setup, you can also edit `application-mysql.properties` and replace the empty default password. Do not commit real passwords to GitHub.

### Verify the database

After the backend starts, run this in MySQL Workbench:
```sql
USE shopzone;
SHOW TABLES;
SELECT id, name, email FROM customers;
SELECT id, name, category, price FROM products;
SELECT id, order_number, customer_id, total, status FROM shop_orders;
```
The `customers`, `products`, `shop_orders`, and `shop_order_items` tables should be created by JPA. The product seeder inserts the 12 demo products only when the products table is empty.

To use H2 instead, remove `spring.profiles.active=mysql` from `application.properties` and configure the H2 datasource explicitly.

## API endpoints
All endpoints use `/api`.

| Method | Endpoint | Purpose |
|---|---|---|
| GET | `/api/products` | List products |
| GET | `/api/products?category=electronics` | Filter category |
| GET | `/api/products?q=headphones` | Search product names |
| GET | `/api/products/{id}` | Product details |
| POST | `/api/auth/register` | Register; starts session |
| POST | `/api/auth/login` | Sign in; starts session |
| GET | `/api/auth/me` | Current signed-in customer |
| POST | `/api/auth/logout` | End session |
| GET | `/api/cart` | Current browser session cart |
| POST | `/api/cart/items` | Add `{ "productId": 1, "quantity": 1 }` |
| PUT | `/api/cart/items/{productId}` | Set quantity |
| DELETE | `/api/cart/items/{productId}` | Remove item |
| DELETE | `/api/cart` | Clear cart |
| GET | `/api/wishlist` | Get session wishlist |
| POST | `/api/wishlist/{productId}` | Add to wishlist |
| DELETE | `/api/wishlist/{productId}` | Remove from wishlist |
| POST | `/api/orders` | Place order (no payment; requires sign-in) |
| GET | `/api/orders` | Signed-in customer's orders |
| GET | `/api/orders/{id}` | One of customer's orders |

## Session and CORS
The API uses an HTTP session cookie. Frontend requests use `credentials: "include"`. The frontend API client automatically uses the same hostname as the Live Server page (`localhost` or `127.0.0.1`) so the session cookie is not lost by mixing hostnames. CORS currently permits ports 5500 and 5501. If you use another port, add that origin in `AppConfig`.

## Important limitations
- Cart and wishlist are stored in the HTTP session, not yet persisted to database.
- Customer records and orders are persisted in the database.
- Passwords are hashed with BCrypt.
- Checkout creates an order only; it does not collect or process payment.
- No admin product-management API, shipping address workflow, inventory reservation, email verification, password reset, JWT, rate limiting or production deployment hardening yet.
- For production, add authorization policies, CSRF strategy appropriate to the deployment, secure cookie settings, HTTPS, validation/error handling, inventory checks, and security testing.
