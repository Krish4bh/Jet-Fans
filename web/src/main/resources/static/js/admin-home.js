document.addEventListener("DOMContentLoaded", () => {
  const usersTab = document.querySelector(".users-tab");
  const homeTab = document.querySelector(".home-tab");
  const productsTab = document.querySelector(".products-tab");

  const homeContent = document.querySelector(".home-content");
  const usersContent = document.querySelector(".users-content");
  const productsContent = document.querySelector(".products-content");

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
});

function populateModal(button) {
  document.getElementById("editId").value =
    button.getAttribute("data-admin-id");
  document.getElementById("editName").value =
    button.getAttribute("data-admin-name");
  document.getElementById("editEmail").value =
    button.getAttribute("data-admin-email");
}

deleteAdmin = (button) => {
  const adminName = button.getAttribute("data-admin-name");
  const adminId = button.getAttribute("data-admin-id");

  document.getElementById("admin-name").textContent = adminName;
  document.getElementById("delete-form").action = `/delete/admin/${adminId}`;
};

populateUserModal = (button) => {
  document.getElementById("edit-user-id").value =
    button.getAttribute("data-user-id");
  document.getElementById("edit-user-name").value =
    button.getAttribute("data-user-name");
  document.getElementById("edit-user-email").value =
    button.getAttribute("data-user-email");
  document.getElementById("edit-user-contact").value =
    button.getAttribute("data-user-contact");
};

removeUser = (button) => {
  const userName = button.getAttribute("data-user-name");
  const userId = button.getAttribute("data-user-id");

  document.getElementById("user-name").textContent = userName;
  document.getElementById("remove-user-form").action = `/user/delete/${userId}`;
};

populateProductModal = (button) => {
  document.getElementById("edit-product-id").value =
    button.getAttribute("data-product-id");
  document.getElementById("edit-product-title").value =
    button.getAttribute("data-product-title");
  document.getElementById("edit-product-description").value =
    button.getAttribute("data-product-description");
  document.getElementById("edit-product-price").value =
    button.getAttribute("data-product-price");
  document.getElementById("edit-product-category").value = button.getAttribute(
    "data-product-category"
  );
};

removeFormModal = (button) => {
  const productId = button.getAttribute("data-product-id");
  const form = document.getElementById("delete-product-form");
  form.action = `/product/delete/${productId}`;
  console.log("Delete URL set to:", form.action);
};
