const usersTab = document.querySelector(".users-tab");
const homeTab = document.querySelector(".home-tab");
const homeContent = document.querySelector(".home-content");
const usersContent = document.querySelector(".users-content");
const productsTab = document.querySelector(".products-tab");
const productsContent = document.querySelector(".product-content");

homeTab.addEventListener("click", (e) => {
  e.preventDefault();
  homeTab.classList.add("active");
  usersTab.classList.remove("active");
  productsTab.classList.remove("active");

  homeContent.classList.remove("d-none");
  usersContent.classList.add("d-none");
  productsContent.classList.add("d-none");
});

usersTab.addEventListener("click", (e) => {
  e.preventDefault();
  usersTab.classList.add("active");
  homeTab.classList.remove("active");
  productsTab.classList.remove("active");

  usersContent.classList.remove("d-none");
  homeContent.classList.add("d-none");
  productsContent.classList.add("d-none");
});

productsTab.addEventListener("click", (e) => {
  e.preventDefault();
  productsTab.classList.add("active");
  usersTab.classList.remove("active");
  homeTab.classList.remove("active");

  productsContent.classList.remove("d-none");
  usersContent.classList.add("d-none");
  homeContent.classList.add("d-none");
});
