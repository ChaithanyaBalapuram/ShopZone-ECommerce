# ShopZone frontend

Amazon-inspired educational storefront using HTML, CSS and JavaScript. The frontend now communicates with the Spring Boot backend through `js/api.js`.

## Run it
1. Start the ShopZone Spring Boot application in Eclipse. It should run at `http://localhost:8080`.
2. In VS Code, open this folder and start `index.html` with the Live Server extension.
3. Use the Live Server address on port 5500 (or 5501). These are the origins allowed by the backend CORS configuration.
4. Register an account, sign in, add products to cart/wishlist, then place an order. No payment gateway is included.

## Backend connection
`js/api.js` contains `API_BASE = "http://localhost:8080/api"`. Change it if your backend runs elsewhere. Requests use `credentials: "include"` so the browser sends the Spring session cookie.

## Features connected to Java APIs
- Product catalogue and product details
- Customer registration, login, current session and logout
- Cart add/update/remove and server-calculated subtotal
- Wishlist add/remove
- Place order and order history

Cart and wishlist are held in the backend HTTP session; customer accounts and orders are stored in the backend database. The backend defaults to a local H2 database. MySQL can be configured using the backend README.

## Important
Do not open the HTML files directly using `file://`; use Live Server. The backend must be running before the frontend can load server data. This is a learning/demo project and is not production-hardened.
