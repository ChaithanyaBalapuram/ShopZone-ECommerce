document.addEventListener("DOMContentLoaded",async()=>{
 const S=ShopZone;await S.init();const error=document.getElementById("formError");
 const fail=message=>{error.textContent=message;error.classList.add("show");};
 const success=user=>{S.setUser(user);location.href="profile.html";};
 const reg=document.getElementById("registerForm");
 if(reg)reg.addEventListener("submit",async e=>{e.preventDefault();error.classList.remove("show");const name=document.getElementById("name").value.trim(),email=document.getElementById("email").value.trim().toLowerCase(),password=document.getElementById("password").value,confirm=document.getElementById("confirmPassword").value;if(password!==confirm)return fail("Passwords do not match.");if(password.length<6)return fail("Password must contain at least 6 characters.");try{success(await ShopZoneAPI.register({name,email,password}));}catch(err){fail(err.message);}});
 const login=document.getElementById("loginForm");
 if(login)login.addEventListener("submit",async e=>{e.preventDefault();error.classList.remove("show");const email=document.getElementById("email").value.trim().toLowerCase(),password=document.getElementById("password").value;try{success(await ShopZoneAPI.login({email,password}));}catch(err){fail(err.message);}});
});
