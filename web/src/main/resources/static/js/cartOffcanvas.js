document.addEventListener("DOMContentLoaded", function () {
  const offcanvasRight = document.getElementById("offcanvas-right");
  const cartBody = document.getElementById("cart-body-container");

  if (!offcanvasRight) {
    console.error("❌ offcanvas-right element not found in DOM");
    return;
  }

  offcanvasRight.addEventListener("show.bs.offcanvas", function () {
    console.log("🛒 Shopping cart offcanvas triggered");

    // show loading state
    if (cartBody) {
      cartBody.innerHTML = `
        <div class="text-center mt-5">
          <div class="spinner-border" role="status">
            <span class="visually-hidden">Loading...</span>
          </div>
          <p class="mt-3">Loading cart...</p>
        </div>
      `;
    }

    fetch("/cart")
      .then((response) => {
        if (!response.ok) throw new Error("HTTP error " + response.status);
        return response.text();
      })
      .then((html) => {
        console.log("✅ Cart HTML received:", html.length, "chars");
        if (cartBody) {
          cartBody.innerHTML = html;
        }
      })
      .catch((err) => {
        console.error("❌ Error loading cart:", err);
        if (cartBody) {
          cartBody.innerHTML = `
            <div class="alert alert-danger m-3">
              <i class="bi bi-exclamation-triangle"></i>
              Failed to load cart. Please refresh and try again.
              <br><small>${err.message}</small>
            </div>
          `;
        }
      });
  });
});

function updateQuantity(itemId, newQuantity) {
  fetch(`/cart/update?itemId=${itemId}&quantity=${newQuantity}`, {
    method: "POST",
  })
    .then((res) => res.text())
    .then((data) => {
      if (data === "success") {
        // Refresh the offcanvas to show updated cart
        fetch("/cart")
          .then((response) => response.text())
          .then((html) => {
            document.getElementById("cart-body-container").innerHTML = html;
          });
      } else {
        alert("Failed to update cart. Please try again.");
      }
    })
    .catch((err) => console.error("Error updating cart:", err));
}
