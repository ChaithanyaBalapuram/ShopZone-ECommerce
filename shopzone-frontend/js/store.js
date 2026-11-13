(() => {
  const fallbackProducts = [
    [1,"Studio Wireless Headphones","electronics",2499,3999,4.6,1248,38,"🎧","Comfortable over-ear wireless headphones.","Comfort-fit ear cushions;Bluetooth connectivity;Built-in microphone;Long-lasting battery"],
    [2,"Smartwatch with Fitness Tracking","electronics",1899,2999,4.3,862,37,"⌚","Track your activity and receive notifications.","Activity tracking;Call and message notifications;Multiple watch faces;Adjustable strap"],
    [3,"Everyday Running Sneakers","fashion",1499,2499,4.4,530,40,"👟","Lightweight everyday sneakers.","Lightweight construction;Cushioned footbed;Durable outsole;Everyday style"],
    [4,"Minimal Ceramic Table Lamp","home",999,1499,4.2,316,33,"💡","A warm accent for your desk or bedside table.","Ceramic base;Soft ambient lighting;Compact footprint;Easy to style"],
    [5,"Portable Bluetooth Speaker","electronics",1299,1999,4.5,978,35,"🔊","Portable speaker for music at home or outdoors.","Bluetooth pairing;Portable design;Simple controls;Rechargeable battery"],
    [6,"Classic Everyday Backpack","fashion",899,1399,4.1,421,36,"🎒","A versatile backpack for commutes and short trips.","Spacious compartment;Front utility pocket;Adjustable straps;Everyday design"],
    [7,"The Art of Thinking Clearly","books",349,599,4.7,2104,42,"📘","An introduction to clearer decision-making.","Paperback edition;Accessible chapters;Curious readers;Gift choice"],
    [8,"Stainless Steel Cookware Set","home",2199,3499,4.3,287,37,"🍳","Practical cookware for everyday home cooking.","Stainless steel;Easy-care surfaces;Everyday use;Multiple pieces"],
    [9,"Wireless Optical Mouse","electronics",599,999,4.4,1570,40,"🖱️","Compact wireless mouse for work and study.","Wireless connection;Optical tracking;Compact shape;Comfortable grip"],
    [10,"Cotton Everyday T-Shirt","fashion",499,799,4.0,694,38,"👕","A simple wardrobe staple.","Cotton-rich fabric;Regular fit;Easy to pair;Everyday comfort"],
    [11,"Indoor Ceramic Planter","home",399,699,4.2,198,43,"🪴","A neat home for your favourite indoor plant.","Ceramic construction;Modern look;Indoor use;Easy to style"],
    [12,"Atomic Habits","books",399,699,4.8,3890,43,"📗","A practical book about building good habits.","Paperback edition;Practical examples;Personal development;Reader favourite"]
  ].map(x=>({id:x[0],name:x[1],category:x[2],price:x[3],oldPrice:x[4],rating:x[5],reviews:x[6],reviewCount:x[6],discount:x[7],emoji:x[8],description:x[9],features:x[10].split(";")}));
  let products = fallbackProducts, cart = [], wishlist = [], user = null;
  const money = amount => new Intl.NumberFormat("en-IN",{style:"currency",currency:"INR",maximumFractionDigits:0}).format(Number(amount)||0);
  const escapeHTML = value => String(value ?? "").replace(/[&<>"']/g,c=>({"&":"&amp;","<":"&lt;",">":"&gt;",'"':"&quot;","'":"&#39;"}[c]));
  const mapProduct = p => ({...p, id:Number(p.id), price:Number(p.price), oldPrice:Number(p.oldPrice), reviews:Number(p.reviewCount ?? p.reviews ?? 0), features:Array.isArray(p.features)?p.features:String(p.features||"").split(/[;|\n]/).filter(Boolean)});
  async function loadProducts(){try{products=(await ShopZoneAPI.products()).map(mapProduct);}catch(e){console.error("Could not load products from backend:",e);throw e;}return products;}
  async function refreshCart(){const data=await ShopZoneAPI.cart();cart=(data.items||[]).map(x=>({id:Number(x.product.id),quantity:x.quantity,product:mapProduct(x.product)}));return cart;}
  async function refreshWishlist(){wishlist=(await ShopZoneAPI.wishlist()).map(p=>mapProduct(p));return wishlist;}
  async function refreshUser(){try{user=await ShopZoneAPI.me();}catch{user=null;}header();return user;}
  const getCart=()=>cart.map(({id,quantity})=>({id,quantity}));
  const getWishlist=()=>wishlist.map(p=>p.id);
  const getUser=()=>user;
  const getOrders=()=>[];
  const setUser=value=>{user=value;header();};
  const setCart=()=>{}; const setWishlist=()=>{}; const setOrders=()=>{};
  const subtotal=()=>cart.reduce((sum,item)=>sum+(item.product?.price||products.find(p=>p.id===item.id)?.price||0)*item.quantity,0);
  const cartCount=()=>cart.reduce((sum,item)=>sum+item.quantity,0);
  async function addToCart(id){await ShopZoneAPI.addCart(id,1);await refreshCart();header();}
  async function updateQuantity(id,delta){const item=cart.find(x=>x.id===Number(id));if(!item)return;const quantity=item.quantity+delta;if(quantity<=0)await ShopZoneAPI.removeCart(id);else await ShopZoneAPI.updateCart(id,quantity);await refreshCart();header();}
  async function removeFromCart(id){await ShopZoneAPI.removeCart(id);await refreshCart();header();}
  async function toggleWishlist(id){const n=Number(id),exists=wishlist.some(p=>p.id===n);const list=exists?await ShopZoneAPI.removeWishlist(n):await ShopZoneAPI.addWishlist(n);wishlist=list.map(mapProduct);header();return !exists;}
  function header(){document.querySelectorAll("[data-cart-count]").forEach(el=>el.textContent=cartCount());document.querySelectorAll("[data-user-greeting]").forEach(el=>el.textContent=user?`Hello, ${user.name.split(" ")[0]}`:"Hello, sign in");}
  async function init(){await Promise.allSettled([loadProducts(),refreshCart(),refreshWishlist(),refreshUser()]);header();}
  window.ShopZone={get products(){return products;},loadProducts,init,refreshCart,refreshWishlist,refreshUser,mapProduct,getCart,setCart,getWishlist,setWishlist,getUser,setUser,getOrders,setOrders,money,cartCount,subtotal,addToCart,updateQuantity,removeFromCart,toggleWishlist,header,escapeHTML};
})();
