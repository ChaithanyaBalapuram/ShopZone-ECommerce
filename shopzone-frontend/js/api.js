/* ShopZone REST API client */
(() => {
  // Automatically use the same hostname as the frontend.
  // Example: 127.0.0.1:5500 -> 127.0.0.1:8080
  const API_BASE = `${window.location.protocol}//${window.location.hostname}:8080/api`;

  async function request(path, options = {}) {
    const headers = {
      Accept: "application/json",
      ...(options.body ? { "Content-Type": "application/json" } : {}),
      ...(options.headers || {}),
    };

    const response = await fetch(`${API_BASE}${path}`, {
      ...options,
      headers,
      credentials: "include",
    });

    const raw = await response.text();

    let data = null;

    try {
      data = raw ? JSON.parse(raw) : null;
    } catch {
      data = raw;
    }

    if (!response.ok) {
      const message =
        data?.detail ||
        data?.message ||
        data?.error ||
        (typeof data === "string" ? data : null) ||
        `Request failed (${response.status})`;

      throw new Error(message);
    }

    return data;
  }

  window.ShopZoneAPI = {
    request,

    // Products
    products: () => request("/products"),
    product: (id) => request(`/products/${id}`),

    // Authentication
    register: (body) =>
      request("/auth/register", {
        method: "POST",
        body: JSON.stringify(body),
      }),

    login: (body) =>
      request("/auth/login", {
        method: "POST",
        body: JSON.stringify(body),
      }),

    me: () => request("/auth/me"),

    logout: () =>
      request("/auth/logout", {
        method: "POST",
      }),

    // Cart
    cart: () => request("/cart"),

    addCart: (productId, quantity = 1) =>
      request("/cart/items", {
        method: "POST",
        body: JSON.stringify({
          productId: Number(productId),
          quantity,
        }),
      }),

    updateCart: (id, quantity) =>
      request(`/cart/items/${id}`, {
        method: "PUT",
        body: JSON.stringify({
          productId: Number(id),
          quantity,
        }),
      }),

    removeCart: (id) =>
      request(`/cart/items/${id}`, {
        method: "DELETE",
      }),

    // Wishlist
    wishlist: () => request("/wishlist"),

    addWishlist: (id) =>
      request(`/wishlist/${id}`, {
        method: "POST",
      }),

    removeWishlist: (id) =>
      request(`/wishlist/${id}`, {
        method: "DELETE",
      }),

    // Orders
    placeOrder: () =>
      request("/orders", {
        method: "POST",
      }),

    orders: () => request("/orders"),
  };
})();
